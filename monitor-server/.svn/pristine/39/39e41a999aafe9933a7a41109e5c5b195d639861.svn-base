package com.anyun100.storage.monitor.user_service.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

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

import com.anyun100.storage.monitor.audit_service.domain.AuditActionEnum;
import com.anyun100.storage.monitor.audit_service.domain.SysLog;
import com.anyun100.storage.monitor.audit_service.repository.SysLogRepository;
import com.anyun100.storage.monitor.audit_service.service.AuditService;
import com.anyun100.storage.monitor.audit_service.service.AuditServiceConfiguration;


//@RunWith(SpringJUnit4ClassRunner.class)

@RunWith(SpringRunner.class)
@DataJpaTest
@EnableAutoConfiguration
@EnableJpaRepositories("com.anyun100.storage.monitor.audit_service.repository")
@EntityScan("com.anyun100.storage.monitor.audit_service.domain")
@Import(AuditServiceConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
/*@ContextHierarchy({
	@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class,
		classes = {
				DataSourceConfig.class
		}),
})*/
//@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
//@Transactional
public class AuditLogRepositoryTest {

	@Autowired
	private SysLogRepository sysLogRepository;
	
	@Autowired
	private AuditService  auditService;

	//private final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

	@Before
	public void init() {
		
		SysLog sysLog = new SysLog();
		sysLog.setActionName(AuditActionEnum.ADMIN_USER.getName());
		sysLog.setActionType(AuditActionEnum.ADMIN_USER.getValue().get("create"));
		sysLog.setValueFrom("valueFrom");
		sysLog.setValueTo("valueTo");
		sysLog.setLastModifiedBy("user1");
		sysLogRepository.save(sysLog);
	}

	@After
	public void deleteAll() {
		sysLogRepository.deleteAll();
	}

	@Test
	public void findAll() {
		assertThat(sysLogRepository.findAll()).hasSize(2);

	}

	@Test
	public void findLogs() {
		auditService.getLogs("{\"username\": [\"admin\",\"user\"],\"time\": [\"2018-01-10 10:10:10\",\"2018-02-20 10:10:10\"]}", 1, 20);
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
