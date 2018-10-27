package com.anyun100.storage.monitor.region_center.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "clientConfigEntityManagerFactory", transactionManagerRef = "clientConfigTransactionManager", basePackages = {
		"com.anyun100.storage.monitor.client_config_service.repository", "com.anyun100.storage.monitor.audit_service.repository" })
public class ClientConfig {

	@Bean(name = "clientConfigDataSource")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource barDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "clientConfigEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean barEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("clientConfigDataSource") DataSource barDataSource) {
		return builder.dataSource(barDataSource).packages("com.anyun100.storage.monitor.client_config_service.domain", "com.anyun100.storage.monitor.audit_service.domain")
				.persistenceUnit("clientConfig").build();
	}

	@Bean(name = "clientConfigTransactionManager")
	public PlatformTransactionManager barTransactionManager(
			@Qualifier("clientConfigEntityManagerFactory") EntityManagerFactory clientConfigEntityManagerFactory) {
		return new JpaTransactionManager(clientConfigEntityManagerFactory);
	}

}
