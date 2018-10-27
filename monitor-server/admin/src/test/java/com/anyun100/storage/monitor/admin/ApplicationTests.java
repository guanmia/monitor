package com.anyun100.storage.monitor.admin;

import static org.junit.Assert.assertEquals;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.util.MultiValueMap;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import com.alibaba.fastjson.JSONObject;

import com.alibaba.fastjson.JSONObject;
import com.anyun100.storage.monitor.client_config_service.service.ClientConfigService;
import com.anyun100.storage.monitor.client_config_service.service.ClientConfigServiceConfiguration;
import com.anyun100.storage.monitor.user_service.domain.UsernameAuditorAware;
import com.anyun100.storage.monitor.user_service.service.UserServiceConfiguration;
@RunWith(SpringRunner.class)
@SpringBootTest(properties="security.user.password:foo", webEnvironment=WebEnvironment.RANDOM_PORT)
//@EntityScan({"com.anyun100.storage.monitor.user_service.domain", "com.anyun100.storage.monitor.audit_service.domain", "com.anyun100.storage.monitor.client_config_service.domain"})
@Import(AdminApplication.class)
//@EnableJpaRepositories({"com.anyun100.storage.monitor.user_service.repository", "com.anyun100.storage.monitor.audit_service.repository", "com.anyun100.storage.monitor.client_config_service.repository"})
@EnableAutoConfiguration
//@WebAppConfiguration
public class ApplicationTests {

	@LocalServerPort
	private int port;
	
	@Autowired
	private ClientConfigService clientConfigService;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void getRegionList() {
		TestRestTemplate template = new TestRestTemplate("user", "foo");
		ResponseEntity<String> response = template.postForEntity("http://localhost:" + port
				+ "/region/metadata/list", null, String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}
	
	@Test
	public void getUserList() {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("searchkey", "");
		map.add("page", "1");
		map.add("limit", "20");
		TestRestTemplate template = new TestRestTemplate("user", "foo");
		ResponseEntity<String> response = template.postForEntity("http://localhost:" + port
				+ "/user/list", map, String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}
	
	@Test
	public void getBackPassword() {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		
		map.add("email", "guanxd@anyun100.com");
		TestRestTemplate template = new TestRestTemplate();
		ResponseEntity<String> response = template.postForEntity("http://localhost:" + port
				+ "/user/forgotpasswordstep1", map, String.class);
		
		String ids = response.getBody();
		assertEquals(HttpStatus.OK, response.getStatusCode());
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
