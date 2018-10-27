package com.anyun100.storage.monitor.region_center;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.util.MultiValueMap;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import com.alibaba.fastjson.JSONObject;
import com.anyun100.storage.monitor.client_config_service.domain.dto.ClientConfigCityStatDto;
import com.anyun100.storage.monitor.client_config_service.service.RegionCenterService;
import com.alibaba.fastjson.JSONObject;
@RunWith(SpringRunner.class)
@SpringBootTest(properties="security.user.password:foo", webEnvironment=WebEnvironment.RANDOM_PORT)
@Import(RegionCenterApplication.class)
@ActiveProfiles("region-center-1")
public class ApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private RegionCenterService regionCenterService;
	
	@Test
	public void metadataUpdateTest() {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		
		map.add("site_id", "1");
		TestRestTemplate template = new TestRestTemplate("user", "foo");
		ResponseEntity<String> response = template.postForEntity("http://localhost:" + port
				+ "/metadata-update", map, String.class);
		
		String ids = response.getBody();
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}
	

}
