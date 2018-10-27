package com.anyun100.storage.monitor.user_service.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Properties;

import javax.transaction.Transactional;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import com.anyun100.storage.monitor.user_service.domain.SysRole;
import com.anyun100.storage.monitor.user_service.domain.SysUser;
import com.anyun100.storage.monitor.user_service.redis.RedisConfig;
import com.anyun100.storage.monitor.user_service.repository.SysRoleRepository;
import com.anyun100.storage.monitor.user_service.repository.SysUserRepository;
import com.anyun100.storage.monitor.user_service.service.UserRoleService;
import com.anyun100.storage.monitor.user_service.service.UserServiceConfiguration;


//@RunWith(SpringJUnit4ClassRunner.class)

@RunWith(SpringRunner.class)
@DataJpaTest
@EnableAutoConfiguration
@EnableJpaRepositories({"com.anyun100.storage.monitor.user_service.repository", "com.anyun100.storage.monitor.audit_service.repository"})
@EntityScan({"com.anyun100.storage.monitor.user_service.domain", "com.anyun100.storage.monitor.audit_service.domain"})
@Import({UserServiceConfiguration.class, RedisConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
/*@ContextHierarchy({
	@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class,
		classes = {
				DataSourceConfig.class
		}),
})*/
//@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@Transactional
public class UserRoleRepositoryTest {

	@Autowired
	private SysUserRepository sysUserRepository;

	@Autowired
	private SysRoleRepository sysRoleRepository;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Rule
	public final ExpectedException thrown = ExpectedException.none();
	
	

	//private final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

	@Before
	public void init() {
		SysRole role1 = sysRoleRepository.findByName("admin");
		//SysRole role2 = sysRoleRepository.findByName("user");

		SysUser user1 = new SysUser();
		user1.setUsername("angang11");
		user1.setPassword("123456");
		SysUser user2 = new SysUser();
		user2.setUsername("angang31");
		user2.setPassword("123456");
		user1.getRoles().addAll(Arrays.asList(role1));
		user2.getRoles().addAll(Arrays.asList(role1));
		sysUserRepository.save(Arrays.asList(user1, user2));
		//sysRoleRepository.save(Arrays.asList(role1, role2));
	}

	@After
	public void deleteAll() {
		sysUserRepository.deleteAll();
		//sysRoleRepository.deleteAll();
	}

	@Test
	public void findAll() {
		assertThat(sysUserRepository.findAll()).hasSize(3);

		//assertThat(sysRoleRepository.findAll()).hasSize(3);
	}

	@Test
	public void findByNameContaining() {

		assertThat(sysUserRepository.findOneWithRolesByUsername("angang11").isPresent()).isTrue();

		assertThat(sysRoleRepository.findByValueContaining("ADMIN")).isNotNull();
	}
	
	@Test
	public void addUser() {

		userRoleService.addUser("username", "password", "ROLE_ADMIN", "lastname", "firstname", "email@email.com", "phone", true, "introduction");
	}
	
	@Test
	public void updateUser() {

		userRoleService.updateUser("angang11", "ROLE_ADMIN", "lastname", "firstname", "email@email.com", "phone", true, "introduction");
	}
	
	@Test
	public void getUser() {

		userRoleService.getUser("angang11");
	}
	
	@Test
	public void getUserList() {

		userRoleService.getUserList(null, 1, 2);
	}
	
	@Test
	public void getSMTP() {

		userRoleService.getSMTP();
	}
	
	@Ignore
	@Test
	public void sendMail() {

		SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("guanxd@anyun100.com");
        mailMessage.setReplyTo("guanxd@anyun100.com");
        mailMessage.setFrom("someone@localhost");
        mailMessage.setSubject("Lorem ipsum");
        mailMessage.setText("Lorem ipsum dolor sit amet [...]");
        javaMailSender.send(mailMessage);
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
	
	@Configuration
	@PropertySource("classpath:mail.properties")
	public static class MailConfiguration {

	    @Value("${mail.protocol}")
	    private String protocol;
	    @Value("${mail.host}")
	    private String host;
	    @Value("${mail.port}")
	    private int port;
	    @Value("${mail.smtp.auth}")
	    private boolean auth;
	    @Value("${mail.smtp.starttls.enable}")
	    private boolean starttls;
	    @Value("${mail.from}")
	    private String from;
	    @Value("${mail.username}")
	    private String username;
	    @Value("${mail.password}")
	    private String password;

	    @Bean
	    public JavaMailSender javaMailSender() {
	        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	        Properties mailProperties = new Properties();
	        mailProperties.put("mail.smtp.auth", auth);
	        mailProperties.put("mail.smtp.starttls.enable", starttls);
	        mailSender.setJavaMailProperties(mailProperties);
	        mailSender.setHost(host);
	        mailSender.setPort(port);
	        mailSender.setProtocol(protocol);
	        mailSender.setUsername(username);
	        mailSender.setPassword(password);
	        return mailSender;
	    }
	}

}
