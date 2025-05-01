package org.ekgns33.commerce;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.MountableFile;

public class PostgreSQLTestContainerConfig implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
        .withDatabaseName("testdb")
        .withUsername("testuser")
        .withPassword("testpass");

    static {

        File ddl = new File("ddl.sql");
        if (ddl.exists()) {
            postgres.withCopyFileToContainer(
                MountableFile.forHostPath(ddl.getAbsolutePath()),
                "/docker-entrypoint-initdb.d/000_ddl.sql"
            );
        }

        File dataDir = new File("data");
        if (dataDir.exists() && dataDir.isDirectory()) {
      Arrays.stream(dataDir.listFiles((dir, name) -> name.endsWith(".sql")))
          .sorted(Comparator.comparing(File::getName))
          .forEach(
              file -> {
                System.out.println(file.getAbsolutePath());
                postgres.withCopyFileToContainer(
                    MountableFile.forHostPath(file.getAbsolutePath()),
                    "/docker-entrypoint-initdb.d/" + file.getName());
              });
        }
        postgres.start();
    }

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        TestPropertyValues.of(
            "spring.datasource.url=" + postgres.getJdbcUrl(),
            "spring.datasource.username=" + postgres.getUsername(),
            "spring.datasource.password=" + postgres.getPassword(),
            "spring.datasource.driver-class-name=org.postgresql.Driver"
        ).applyTo(context.getEnvironment());
    }

}
