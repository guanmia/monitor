package com.anyun100.storage.monitor.region_center;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.anyun100.storage.monitor.audit_service.service.AuditServiceConfiguration;
import com.anyun100.storage.monitor.client_config_service.service.ClientConfigServiceConfiguration;
import com.anyun100.storage.monitor.metadata_service.redis.RedisConfig;
import com.anyun100.storage.monitor.metadata_service.service.MetaDataServiceConfiguration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling; 
@SpringBootApplication
//@EntityScan({"com.anyun100.storage.monitor.metadata_service.domain"})
@Import({MetaDataServiceConfiguration.class, RedisConfig.class, ClientConfigServiceConfiguration.class, AuditServiceConfiguration.class})
//@EnableJpaRepositories({"com.anyun100.storage.monitor.metadata_service.repository"})
@EnableJpaAuditing
@EnableDiscoveryClient
@EnableScheduling
public class RegionCenterApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(RegionCenterApplication.class, args);
	}

	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http.httpBasic().and().authorizeRequests()
					.antMatchers("/index.html", "/unauthenticated.html", "/app.html", "/").permitAll().anyRequest()
					.authenticated().and().csrf().disable();
			// @formatter:on
		}
	}

}
