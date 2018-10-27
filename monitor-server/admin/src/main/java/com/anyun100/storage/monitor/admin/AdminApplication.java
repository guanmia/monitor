package com.anyun100.storage.monitor.admin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.anyun100.storage.monitor.client_config_service.service.ClientConfigServiceConfiguration;
import com.anyun100.storage.monitor.user_service.domain.UsernameAuditorAware;
import com.anyun100.storage.monitor.user_service.redis.RedisConfig;
import com.anyun100.storage.monitor.user_service.service.UserServiceConfiguration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories; 
@SpringBootApplication
@EntityScan({"com.anyun100.storage.monitor.user_service.domain", "com.anyun100.storage.monitor.audit_service.domain", "com.anyun100.storage.monitor.client_config_service.domain"})
@Import({UserServiceConfiguration.class, UsernameAuditorAware.class, ClientConfigServiceConfiguration.class, RedisConfig.class})
@EnableJpaRepositories({"com.anyun100.storage.monitor.user_service.repository", "com.anyun100.storage.monitor.audit_service.repository", "com.anyun100.storage.monitor.client_config_service.repository"})
@EnableJpaAuditing
@EnableDiscoveryClient
public class AdminApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http.httpBasic().and().authorizeRequests()
					.antMatchers("/user/forgotpasswordstep1", "/user/forgotpasswordstep2").permitAll().anyRequest()
					.authenticated().and().csrf().disable();
			// @formatter:on
		}
	}

}
