package com.anyun100.storage.monitor.metadata_service.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.assertj.core.api.AbstractObjectArrayAssert;
import org.assertj.core.api.EnumerableAssert;
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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import com.anyun100.storage.monitor.metadata_service.domain.ClientStatus;
import com.anyun100.storage.monitor.metadata_service.domain.Constants;
import com.anyun100.storage.monitor.metadata_service.domain.dto.ClientStatusDto;
import com.anyun100.storage.monitor.metadata_service.redis.RedisConfig;
import com.anyun100.storage.monitor.metadata_service.repository.ClientStatusRepository;
import com.anyun100.storage.monitor.metadata_service.service.ClientStatusService;
import com.anyun100.storage.monitor.metadata_service.service.MetaDataServiceConfiguration;
import com.anyun100.storage.monitor.metadata_service.service.MetaDataStatusService;
import com.anyun100.storage.monitor.metadata_service.service.SGWStatusService;
import com.alibaba.fastjson.JSONObject;


//@RunWith(SpringJUnit4ClassRunner.class)

@RunWith(SpringRunner.class)
@DataJpaTest
@EnableJpaRepositories("com.anyun100.storage.monitor.metadata_service.repository")
@EntityScan("com.anyun100.storage.monitor.metadata_service.domain")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({MetaDataServiceConfiguration.class, RedisConfig.class})
/*@ContextHierarchy({
	@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class,
		classes = {
				DataSourceConfig.class
		}),
})*/
//@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
//@Transactional
public class ClientStatusServiceTest {

	@Autowired
	private ClientStatusService clientStatusService;
	
	@Autowired
	private ClientStatusRepository clientStatusRepository;
	
	@Autowired
	private MetaDataStatusRepository metaDataStatusRepository;
	@Autowired
	private RedisTemplate<String, Long[]> redisTemplate;
	
	@Autowired
	private MetaDataStatusService metaDataStatusService;
	
	@Autowired
	private SGWStatusService sgwStatusService;
	
	//private final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

	@Test
	public void findAllSiteIds() {
		List<Long> siteIds = clientStatusRepository.findAll().stream().map(c -> c.getSiteId()).collect(Collectors.toList());
		assertThat(siteIds).hasSize(6);

	}

	@Test
	public void findDataFromCache() {
		LocalDateTime dt = LocalDateTime.now().plusHours(11L);
		clientStatusService.doClientStatusTask(dt.minusDays(1L));
		List<Long> siteIds = clientStatusRepository.findAll().stream().map(c -> c.getSiteId()).collect(Collectors.toList());
		for (Long siteId : siteIds) {
			Long[] todayDiskTotalClientStatus = redisTemplate.opsForValue().get(Constants.CLIENT_STATUS_DISK_TOTAL_TODAY_KEY + siteId);
			assertThat(todayDiskTotalClientStatus).isNotNull();
		}

	}
	
	@Test
	public void findInitDataFromCache() {
		List<Long> siteIds = clientStatusRepository.findAll().stream().map(c -> c.getSiteId()).collect(Collectors.toList());
		for (Long siteId : siteIds) {
			JSONObject josn = clientStatusService.getInitialDataForDisk(siteId);
			assertThat(josn).isNotNull();
		}

	}
	
	@Test
	public void findLatestDataFromCache() {
		List<Long> siteIds = clientStatusRepository.findAll().stream().map(c -> c.getSiteId()).collect(Collectors.toList());
		for (Long siteId : siteIds) {
			JSONObject josn = clientStatusService.getLatestDataForDisk(siteId, 5L);
			assertThat(josn).isNotNull();
			assertThat((josn.get(Constants.JSON_STATUS_DISK_FREE_TODAY_KEY).toString().split(","))).hasSize(282);
		}

	}
	
	@Test
	public void findInitData() {
		JSONObject josn1 = metaDataStatusService.getInitialData(1L);
		assertThat(josn1).isNotNull();

	}
	
	@Test
	public void doCalc() {
		metaDataStatusService.doMetaDataStatusTask(LocalDateTime.now());

	}
	
	@Ignore
	@Test
	public void doReCalc() {
		metaDataStatusService.doRecalc(2L);
		sgwStatusService.doRecalc(2L);

	}
	
	@Test
	public void getLatestData() {
		metaDataStatusService.getLatestData(1L, 1L);

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
