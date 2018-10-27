package com.anyun100.storage.monitor.admin.controller;


import java.security.Principal;

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
@RequestMapping(value = "/region")
public class RegionConfigController {

	@Autowired
	private ClientConfigService clientConfigService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public BaseDto getRegions(@RequestParam(value = "searchkeys", required = false) String searchkeys,
							  @RequestParam(value = "page", required = true) int page,
							  @RequestParam(value = "limit", required = true) int limit) {
		return clientConfigService.getRegionsCenters(searchkeys, page, limit);
	}
	
	@RequestMapping(value = "/metadata/list", method = RequestMethod.POST)
	@ResponseBody
	public BaseDto getRegions() {
		return clientConfigService.getRegionsCenters();
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject createRegions(@RequestParam(value = "regionName", required = true) String regionName,
								@RequestParam(value = "location_id", required = true) String locationId,
								@RequestParam(value = "dbIp", required = true) String dbIp,
								@RequestParam(value = "dbPort", required = true) Long dbPort,
								@RequestParam(value = "msIp", required = true) String msIp,
								@RequestParam(value = "msPort", required = true) Long msPort) {
	
		return clientConfigService.createCenter(regionName, locationId, dbIp, dbPort, msIp, msPort);
	}
	
	@RequestMapping(value = "/getinfo", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getRegion(@RequestParam(value = "id", required = true) Long id) {
		return clientConfigService.getCenter(id);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteRegion(@RequestParam(value = "id", required = true) Long id) {
		return clientConfigService.deleteCenter(id);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateRegions(@RequestParam(value = "id", required = true) Long id,
								@RequestParam(value = "regionName", required = true) String regionName,
								@RequestParam(value = "location_id", required = true) String locationId,
								@RequestParam(value = "dbIp", required = true) String dbIp,
								@RequestParam(value = "dbPort", required = true) Long dbPort,
								@RequestParam(value = "msIp", required = true) String msIp,
								@RequestParam(value = "msPort", required = true) Long msPort,
								@RequestParam(value = "status", required = true) Long status) {
		return clientConfigService.updateCenter(id, regionName, locationId, dbIp, dbPort, msIp, msPort, status);
	}
}
