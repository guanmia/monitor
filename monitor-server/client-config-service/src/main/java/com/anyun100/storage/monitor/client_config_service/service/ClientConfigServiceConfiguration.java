package com.anyun100.storage.monitor.client_config_service.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfigServiceConfiguration {

	@Bean
	public ClientConfigService clientConfigService() {
		ClientConfigService clientConfigService = new ClientConfigServiceImpl();
		return clientConfigService;
	}
	
	@Bean
	public RegionCenterService regionCenterService() {
		RegionCenterService regionCenterService = new RegionCenterServiceImpl();
		return regionCenterService;
	}
}
