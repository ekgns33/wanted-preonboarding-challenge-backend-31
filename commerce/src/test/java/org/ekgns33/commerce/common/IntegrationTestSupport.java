package org.ekgns33.commerce.common;

import org.ekgns33.commerce.config.PostgreSQLTestContainerConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = PostgreSQLTestContainerConfig.class)
public abstract class IntegrationTestSupport {}
