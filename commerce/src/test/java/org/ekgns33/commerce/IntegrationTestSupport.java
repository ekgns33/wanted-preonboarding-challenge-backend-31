package org.ekgns33.commerce;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = PostgreSQLTestContainerConfig.class)
public abstract class IntegrationTestSupport {}
