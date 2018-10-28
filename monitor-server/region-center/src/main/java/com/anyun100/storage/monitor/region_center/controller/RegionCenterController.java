package com.anyun100.storage.monitor.region_center.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.anyun100.storage.monitor.client_config_service.service.RegionCenterService;
import com.anyun100.storage.monitor.metadata_service.service.ClientStatusService;
import com.anyun100.storage.monitor.metadata_service.service.MetaDataStatusService;
import com.anyun100.storage.monitor.metadata_service.service.SGWStatusService;
import com.google.common.collect.ImmutableMap;;


@RestController
public class RegionCenterController {
	
	@Value("${region-center-id:-1}")
	private String id;

	/*@Autowired
	private ClientStatusService clientStatusService;*/
	
	@Autowired
	private RegionCenterService regionCenterService;
	
	@Autowired
	private MetaDataStatusService metaDataStatusService;
	
	@Autowired
	private SGWStatusService sgwStatusService;
	
	@RequestMapping(value = "/maps")
	@ResponseBody
	public JSONObject getMaps() {
		JSONObject json = new JSONObject();
		if (id.equals("-1")) {
			json.put("error/code", 1);
			return json;
		}
		String cityName = regionCenterService.getRegionCenter(Long.parseLong(id)).getLocation().getLocationName();
		json.put("error/code", 0);
		json.put("data", ImmutableMap.of("center/name", regionCenterService.getRegionCenter(Long.parseLong(id)).getRegionName(), "cityname", 
				cityName.substring(0, cityName.length() - 1),
				"center/id", id, "client/city", regionCenterService.getRegionCenterStat()));
		return json;
	}
	
	
	@RequestMapping(value = "/metadata")
	@ResponseBody
	public JSONObject getInitMetaDataStatus() {
		return metaDataStatusService.getInitialData(Long.parseLong(id));
	}
	
	@RequestMapping(value = "/metadata/recalc")
	@ResponseBody
	public JSONObject doMetadataRecalc() {
		metaDataStatusService.doRecalc(Long.parseLong(id));
		JSONObject json = new JSONObject();
		json.put("error/code", 0);
		return json;
	}
	
	
	
	@RequestMapping(value = "/metadata-update")
	@ResponseBody
	public JSONObject getLatestMetaDataStatus(@RequestParam(value = "length", required = true) Long numberOfreceived) {
		return metaDataStatusService.getLatestData(Long.parseLong(id), numberOfreceived);
	}
	
	@RequestMapping(value = "/monitor")
	@ResponseBody
	public JSONObject getInitMonitorDataStatus() {
		return metaDataStatusService.getInitialData(Long.parseLong(id));
	}
	
	@RequestMapping(value = "/monitor-update")
	@ResponseBody
	public JSONObject getLatestMonitorDataStatus(@RequestParam(value = "length", required = true) Long numberOfreceived) {
		return metaDataStatusService.getLatestData(Long.parseLong(id), numberOfreceived);
	}
	
	@RequestMapping(value = "/gateway")
	@ResponseBody
	public JSONObject getInitGatewayStatus(@RequestParam(value = "gatewayid", required = true) Long sgwId) {
		return sgwStatusService.getInitialData(sgwId);
	}
	
	@RequestMapping(value = "/gateway/recalc")
	@ResponseBody
	public JSONObject doGatewayRecalc() {
		sgwStatusService.doRecalc(Long.parseLong(id));
		JSONObject json = new JSONObject();
		json.put("error/code", 0);
		return json;
	}
	
	@RequestMapping(value = "/gateway-update")
	@ResponseBody
	public JSONObject getLatestGatewayStatus(@RequestParam(value = "gatewayid", required = true) Long sgwId,
														   @RequestParam(value = "length", required = true) Long numberOfreceived) {
		return sgwStatusService.getLatestData(sgwId, numberOfreceived);
	}
	
	
}
