package com.anyun100.storage.monitor.client_config_service.service;



import com.alibaba.fastjson.JSONObject;
import com.anyun100.storage.monitor.client_config_service.domain.dto.BaseDto;


public interface ClientConfigService {
	
	BaseDto getRegionsCenters(String searchkeys, int page, int limit);
	BaseDto getRegionsCenters();
	JSONObject createCenter(String regionName, String location_id, String dbIp, Long dbPort, String msIp, Long msPort);
	JSONObject getCenter(Long id);
	JSONObject deleteCenter(Long id);
	JSONObject updateCenter(Long id, String regionName, String locationId, String dbIp, Long dbPort, String msIp, Long msPort, Long status);
	
	JSONObject getLocations();
	
	JSONObject getLocation(String locationId);
}
