package com.anyun100.storage.monitor.user_service.service;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.anyun100.storage.monitor.audit_service.service.AuditService;
import com.anyun100.storage.monitor.audit_service.service.AuditServiceImpl;
import com.anyun100.storage.monitor.user_service.domain.Constants;

@Configuration
@PropertySource("classpath:mail.properties")
public class UserServiceConfiguration {
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
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
    @Scope("prototype")
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", redisTemplate.opsForValue().get(Constants.SMTP_IS_VERIFY) == null ? auth : redisTemplate.opsForValue().get(Constants.SMTP_IS_VERIFY).equals("1"));
        mailProperties.put("mail.smtp.starttls.enable", redisTemplate.opsForValue().get(Constants.SMTP_IS_ENCRYPT) == null ? starttls : redisTemplate.opsForValue().get(Constants.SMTP_IS_ENCRYPT).equals("1"));
        mailSender.setJavaMailProperties(mailProperties);
        mailSender.setHost(redisTemplate.opsForValue().get(Constants.SMTP_IP) == null ? host : redisTemplate.opsForValue().get(Constants.SMTP_IP));
        if (redisTemplate.opsForValue().get(Constants.SMTP_PORT) != null) {
        	try {
        		port = Integer.parseInt(redisTemplate.opsForValue().get(Constants.SMTP_PORT));
        		
        	} catch (Exception e) {
        		
        	}
        }
        mailSender.setPort(port);
        mailSender.setProtocol(protocol);
        mailSender.setUsername(redisTemplate.opsForValue().get(Constants.SMTP_USERNMAE) == null ? username : redisTemplate.opsForValue().get(Constants.SMTP_USERNMAE));
        mailSender.setPassword(redisTemplate.opsForValue().get(Constants.SMTP_PASSWORD) == null ? password : redisTemplate.opsForValue().get(Constants.SMTP_PASSWORD));
        return mailSender;
    }

	@Bean
	public UserRoleService userRoleService() {
		UserRoleService userRoleService = new UserRoleServiceImpl();
		return userRoleService;
	}
	
	@Bean
	public AuditService auditService() {
		AuditService auditService = new AuditServiceImpl();
		return auditService;
	}
}
