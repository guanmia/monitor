package com.anyun100.storage.monitor.metadata_service.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class MetaDataServiceConfiguration {

	@Bean
	public ClientStatusService clientStatusService() {
		ClientStatusService clientStatusService = new ClientStatusServiceImpl();
		return clientStatusService;
	}
	
	@Bean
	@Scope("singleton")
	public MetaDataStatusService metaDataStatusService() {
		MetaDataStatusService metaDataStatusService = new MetaDataStatusServiceImpl();
		return metaDataStatusService;
	}
	
	@Bean
	@Scope("singleton")
	public SGWStatusService sgwStatusService() {
		SGWStatusService sgwStatusService = new SGWStatusServiceImpl();
		return sgwStatusService;
	}
}
