package com.anyun100.storage.monitor.gateway;


import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.anyun100.storage.monitor.user_service.domain.UsernameAuditorAware;
import com.anyun100.storage.monitor.user_service.service.UserServiceConfiguration;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@Controller
@EnableZuulProxy
@EnableDiscoveryClient
//@EnableOAuth2Sso
@EnableJpaAuditing
@EntityScan({"com.anyun100.storage.monitor.user_service.domain", "com.anyun100.storage.monitor.audit_service.domain"})
@Import({UserServiceConfiguration.class, UsernameAuditorAware.class})
@EnableJpaRepositories({"com.anyun100.storage.monitor.user_service.repository", "com.anyun100.storage.monitor.audit_service.repository"})
public class GatewayApplication {

	@GetMapping(value = "/{path:[^\\.]*}")
	public String redirect() {
		return "forward:/";
	}

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	/*@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	@ComponentScan("com.anyun100.storage.monitor.user_service.domain")
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	@EnableOAuth2Sso
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

		public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService());
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http.formLogin().loginPage("/login").permitAll().and().httpBasic().and().logout().and().authorizeRequests()
					.antMatchers("/index.html", "/app.html", "/login", "/").permitAll().anyRequest().authenticated()
					.and().csrf().disable();
					//.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
			// @formatter:on		
			http.csrf().disable();
		}

		@Bean
		public UserDetailsService userDetailsService() {
			return new DomainUserDetailsService();
		}
	}*/

	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint constraint = new SecurityConstraint();
				constraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/*");
				constraint.addCollection(collection);
				context.addConstraint(constraint);
			}
		};
		tomcat.addAdditionalTomcatConnectors(httpConnector());
		return tomcat;
	}

	@Bean
	public Connector httpConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		// Connector监听的http的端口号
		connector.setPort(8888);
		connector.setSecure(false);
		// 监听到http的端口号后转向到的https的端口号
		connector.setRedirectPort(8442);
		return connector;
	}

}
