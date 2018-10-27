package com.anyun100.storage.monitor.gateway;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@Import(GatewayApplication.class)
@EnableAutoConfiguration
public class ApplicationTests {
	
	private static final Logger log = LoggerFactory.getLogger(ApplicationTests.class);
	public static final String REST_SERVICE_URI = "https://localhost:8442/api/v1/admin/user/login";

    public static final String AUTH_SERVER_URI = "http://localhost:9999/uaa/oauth/token";

	@LocalServerPort
	private int port;
	
	private TestRestTemplate template = new TestRestTemplate();

	@Test
	public void contextLoads() {
	}

    @Configuration
	public static class DataSourceConfig {
		
		//@Value("${spring.datasource.driverClassName}")
		private String driverClassName = "com.mysql.jdbc.Driver";
		
		private String url = "jdbc:mysql://demo.anyun100.com:3306/anyun_storage_test?useUnicode=true&characterEncoding=utf-8";
		
		//@Value("${spring.datasource.username}")
		private String username = "xd_guan";
		
		//@Value("${spring.datasource.password}")
		private String password = "Angang1109";
		
		@Bean
		public DataSource dataSource() {
			DataSource dataSource = new DataSource();
			
			dataSource.setDriverClassName(driverClassName);
			
			dataSource.setUrl(url);
			
			dataSource.setUsername(username);
			
			dataSource.setPassword(password);
			return dataSource;
		}

	}

}
