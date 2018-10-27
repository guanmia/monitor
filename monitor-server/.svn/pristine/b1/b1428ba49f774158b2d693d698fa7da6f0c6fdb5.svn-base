package com.anyun100.storage.monitor.admin.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.anyun100.storage.monitor.client_config_service.domain.dto.BaseDto;
import com.anyun100.storage.monitor.client_config_service.service.ClientConfigService;

@RestController
public class LocationController {
	
	@Autowired
	private ClientConfigService clientConfigService;
	
	@RequestMapping(value = "/locations")
	@ResponseBody
	public JSONObject getlocations() {
		
		return clientConfigService.getLocations();
		
	}
	
	@RequestMapping(value = "/locations/detail")
	@ResponseBody
	public JSONObject getlocationDetail(@RequestParam(value = "location_id", required = true) String locationId) {
		
		return clientConfigService.getLocation(locationId);
		
	}
}
