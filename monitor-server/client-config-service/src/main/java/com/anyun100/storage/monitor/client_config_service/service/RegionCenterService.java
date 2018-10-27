package com.anyun100.storage.monitor.client_config_service.service;


import java.util.List;

import com.anyun100.storage.monitor.client_config_service.domain.RegionCenter;
import com.anyun100.storage.monitor.client_config_service.domain.dto.ClientConfigCityStatDto;



public interface RegionCenterService {
	
	List<ClientConfigCityStatDto> getRegionCenterStat();
	RegionCenter getRegionCenter(Long id);
	
}
