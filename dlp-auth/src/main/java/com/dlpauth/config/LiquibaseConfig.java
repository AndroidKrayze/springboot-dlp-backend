package com.dlpauth.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {

	@Bean
	public SpringLiquibase liquibase(LiquibaseProperties properties, DataSource dataSource) {
		SpringLiquibase liquibase = new SpringLiquibase();

		liquibase.setChangeLog(properties.getChangeLog());
		liquibase.setContexts(properties.getContexts());
		liquibase.setDataSource(dataSource);
		liquibase.setDefaultSchema(properties.getDefaultSchema());
		liquibase.setDropFirst(properties.isDropFirst());
		liquibase.setShouldRun(properties.isEnabled());
		liquibase.setRollbackFile(properties.getRollbackFile());
		liquibase.setDatabaseChangeLogLockTable(properties.getDatabaseChangeLogLockTable());
		liquibase.setDatabaseChangeLogTable(properties.getDatabaseChangeLogTable());
		liquibase.setChangeLogParameters(properties.getParameters());

		// ✅ Fix for XSD parsing error
		System.setProperty("liquibase.secureParsing", "false");

		return liquibase;
	}

	@Bean
	public LiquibaseProperties liquibaseProperties() {
		return new LiquibaseProperties();
	}
}
