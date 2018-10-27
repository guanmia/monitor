package com.anyun100.storage.monitor.client_config_service.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.fastjson.JSONObject;
import com.anyun100.storage.monitor.audit_service.service.AuditServiceConfiguration;
import com.anyun100.storage.monitor.client_config_service.domain.ClientConfig;
import com.anyun100.storage.monitor.client_config_service.domain.Location;
import com.anyun100.storage.monitor.client_config_service.domain.RegionCenter;
import com.anyun100.storage.monitor.client_config_service.repository.ClientConfigRepository;
import com.anyun100.storage.monitor.client_config_service.service.ClientConfigService;
import com.anyun100.storage.monitor.client_config_service.service.ClientConfigServiceConfiguration;


//@RunWith(SpringJUnit4ClassRunner.class)

@RunWith(SpringRunner.class)
@DataJpaTest
@EnableAutoConfiguration
@EnableJpaRepositories({"com.anyun100.storage.monitor.client_config_service.repository", "com.anyun100.storage.monitor.audit_service.repository"})
@EntityScan({"com.anyun100.storage.monitor.client_config_service.domain", "com.anyun100.storage.monitor.audit_service.domain"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({ClientConfigServiceConfiguration.class, AuditServiceConfiguration.class})
/*@ContextHierarchy({
	@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class,
		classes = {
				DataSourceConfig.class
		}),
})*/
//@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
//@Transactional
public class ClientConfigTest {

	@Autowired
	private ClientConfigRepository clientConfigRepository;
	
	@Autowired
	private ClientConfigService clientConfigService;
	
	@Autowired
	private RegionCenterRepository regionCenterRepository;
	
	@Autowired
	private LocationRepository locationRepository;

	//private final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

	@Test
	public void findRegionCenter() {
		clientConfigService.getRegionsCenters(null, 1, 20);
	}
	
	@Test
	public void getLocations() {
 		JSONObject json = clientConfigService.getLocations();
		//System.out.println(json);
		assertThat(json).isNotNull();

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
