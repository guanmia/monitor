package com.anyun100.storage.monitor.client_config_service.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.anyun100.storage.monitor.audit_service.service.AuditService;
import com.anyun100.storage.monitor.client_config_service.domain.ClientConfig;
import com.anyun100.storage.monitor.client_config_service.domain.Location;
import com.anyun100.storage.monitor.client_config_service.domain.RegionCenter;
import com.anyun100.storage.monitor.client_config_service.domain.StatusEnum;
import com.anyun100.storage.monitor.client_config_service.domain.dto.BaseDto;
import com.anyun100.storage.monitor.client_config_service.domain.dto.ClientConfigDto;
import com.anyun100.storage.monitor.client_config_service.domain.dto.LocationCityDto;
import com.anyun100.storage.monitor.client_config_service.domain.dto.LocationDto;
import com.anyun100.storage.monitor.client_config_service.domain.dto.LocationProvinceDto;
import com.anyun100.storage.monitor.client_config_service.domain.dto.RegionCenterDto;
import com.anyun100.storage.monitor.client_config_service.domain.dto.RegionCenterDto2;
import com.anyun100.storage.monitor.client_config_service.repository.ClientConfigRepository;
import com.anyun100.storage.monitor.client_config_service.repository.LocationRepository;
import com.anyun100.storage.monitor.client_config_service.repository.RegionCenterRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

@Service
public class ClientConfigServiceImpl implements ClientConfigService {

	@Autowired
	private ClientConfigRepository clientConfigRepository;

	@Autowired
	private RegionCenterRepository regionCenterRepository;

	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private AuditService auditService;
	
	Logger logger = LoggerFactory.getLogger(ClientConfigServiceImpl.class);


	public BaseDto getRegionsCenters(String searchkeys, int page, int limit) {
		Pageable pageable = new PageRequest(page - 1, limit, Sort.Direction.DESC,"id");
		Page<RegionCenter> _page =  regionCenterRepository.findAll(pageable);
		List<RegionCenterDto> regionCenterDtos = new ArrayList<>();
		_page.getContent().stream()
				.forEach(r -> regionCenterDtos.add(new RegionCenterDto(r.getId(), r.getRegionName(),
						r.getLocation().getLocationId(), r.getDbHostIp(), r.getDbHostPort(), r.getServerHostIp(),
						r.getServerHostPort(), r.getClientConfigs().size(), StatusEnum.getByDesc(!r.isEnabled() && r.getComments() == null ? "停用" : (r.isEnabled() && r.getComments() == null ? "使用" : r.getComments())).getStatus())));
		return new BaseDto(0, ImmutableMap.<String, Serializable>builder().put("total", _page.getNumberOfElements())
				.put("results", regionCenterDtos.toArray()).build());
	}
	
	public BaseDto getRegionsCenters() {
		List<RegionCenter> regionCenters =  regionCenterRepository.findAll();
		List<RegionCenterDto2> regionCenterDto2s = new ArrayList<>();
		regionCenters.stream()
				.forEach(r -> regionCenterDto2s.add(new RegionCenterDto2(r.getId(), r.getRegionName(),
						r.getLocation().getLocationId(), locationRepository.getCityName(r.getLocation().getLocationId()))));
		return new BaseDto(0, ImmutableMap.<String, Serializable>builder().put("total", regionCenterDto2s.size())
				.put("results", regionCenterDto2s.toArray()).build());
	}

	public JSONObject createCenter(String regionName, String location_id, String dbIp, Long dbPort, String msIp,
			Long msPort) {
		JSONObject json = new JSONObject();
		RegionCenter r = new RegionCenter();
		r.setRegionName(regionName);
		r.setLocation(locationRepository.findOne(location_id));
		r.setDbHostIp(dbIp);
		r.setDbHostPort(dbPort);
		r.setServerHostIp(msIp);
		r.setServerHostPort(msPort);
		regionCenterRepository.save(r);
		RegionCenterDto regionCenterDto = new RegionCenterDto(r.getId(), r.getRegionName(),
				r.getLocation().getLocationId(), r.getDbHostIp(), r.getDbHostPort(), r.getServerHostIp(),
				r.getServerHostPort(), r.getClientConfigs().size(), StatusEnum.getByDesc(!r.isEnabled() && r.getComments() == null ? "停用" : (r.isEnabled() && r.getComments() == null ? "使用" : r.getComments())).getStatus());
		auditService.auditLog("", regionCenterDto.toString(), "区域中心管理",
				"创建区域中心");
		json.put("error_code", 0);
		json.put("data", regionCenterDto);
		return json;
	}

	public JSONObject getCenter(Long id) {
		JSONObject json = new JSONObject();
		RegionCenter r = regionCenterRepository.findOne(id);
		RegionCenterDto regionCenterDto = new RegionCenterDto(r.getId(), r.getRegionName(),
				r.getLocation().getLocationId(), r.getDbHostIp(), r.getDbHostPort(), r.getServerHostIp(),
				r.getServerHostPort(), r.getClientConfigs().size(), StatusEnum.getByDesc(!r.isEnabled() && r.getComments() == null ? "停用" : (r.isEnabled() && r.getComments() == null ? "使用" : r.getComments())).getStatus());
		json.put("error_code", 0);
		json.put("data", regionCenterDto);
		return json;
	}
	
	public JSONObject deleteCenter(Long id) {
		JSONObject json = new JSONObject();
		RegionCenter r = regionCenterRepository.findOne(id);
		RegionCenterDto regionCenterDto = new RegionCenterDto(r.getId(), r.getRegionName(),
				r.getLocation().getLocationId(), r.getDbHostIp(), r.getDbHostPort(), r.getServerHostIp(),
				r.getServerHostPort(), r.getClientConfigs().size(), StatusEnum.getByDesc(!r.isEnabled() && r.getComments() == null ? "停用" : (r.isEnabled() && r.getComments() == null ? "使用" : r.getComments())).getStatus());
		regionCenterRepository.delete(id);
		auditService.auditLog(regionCenterDto.toString(), "", "区域中心管理", "删除区域中心");
		json.put("error_code", 0);
		return json;
	}
	
	public JSONObject updateCenter(Long id, String regionName, String locationId, String dbIp, Long dbPort, String msIp, Long msPort, Long status) {
		JSONObject json = new JSONObject();
		RegionCenter r = regionCenterRepository.findOne(id);
		if (r != null) {
			RegionCenterDto regionCenterDtoFrom = new RegionCenterDto(r.getId(), r.getRegionName(),
					r.getLocation().getLocationId(), r.getDbHostIp(), r.getDbHostPort(), r.getServerHostIp(),
					r.getServerHostPort(), r.getClientConfigs().size(), StatusEnum.getByDesc(!r.isEnabled() && r.getComments() == null ? "停用" : (r.isEnabled() && r.getComments() == null ? "使用" : r.getComments())).getStatus());
			r.setLocation(locationRepository.findOne(locationId));
			r.setDbHostIp(dbIp);
			r.setRegionName(regionName);
			r.setDbHostPort(dbPort);
			r.setServerHostIp(msIp);
			r.setServerHostPort(msPort);
			r.setEnabled(status == 1L);
			r.setComments(StatusEnum.getByStatus(status).getDesc());
			regionCenterRepository.save(r);
			RegionCenterDto regionCenterDto = new RegionCenterDto(r.getId(), r.getRegionName(),
					r.getLocation().getLocationId(), r.getDbHostIp(), r.getDbHostPort(), r.getServerHostIp(),
					r.getServerHostPort(), r.getClientConfigs().size(), StatusEnum.getByDesc(!r.isEnabled() && r.getComments() == null ? "停用" : (r.isEnabled() && r.getComments() == null ? "使用" : r.getComments())).getStatus());
			auditService.auditLog(regionCenterDtoFrom.toString(), regionCenterDto.toString(), "区域中心管理", "更新区域中心");
			json.put("error_code", 0);
			json.put("data", regionCenterDto);
			return json;
		}
		json.put("error_code", 1);
		return json;
	}
	
	public JSONObject getLocations() {
		List<LocationDto> locationDtos = new ArrayList<>();
		locationRepository.findAll().stream().filter(c -> c.getLevel() == 3).forEach(c -> locationDtos.add(new LocationDto(c.getLocationId(), c.getLocationName(), c.getLevel(), c.getParent_Id())));
		Map<String, List<LocationDto>> cityLocationIdMap = locationDtos.stream().collect(Collectors.groupingBy(LocationDto::getParent_id));
		List<LocationCityDto> cityLocations = new ArrayList<>();
		List<LocationProvinceDto> provinceLocations = new ArrayList<>();
		
		for (String locationId: cityLocationIdMap.keySet()) {
			Location loc = locationRepository.findOne(locationId);
			cityLocations.add(new LocationCityDto(loc.getLocationId(), loc.getLocationName(), loc.getLevel(), loc.getParent_Id(), cityLocationIdMap.get(locationId)));
		}
		Map<String, LocationProvinceDto> provinceLocationIdMap = new HashMap<>();
		for (LocationCityDto locationCityDto : cityLocations) {
			if (provinceLocationIdMap.get(locationCityDto.getParent_id()) == null) {
				Location parentLoc = locationRepository.findOne(locationCityDto.getParent_id());
				List<LocationCityDto> tempCityLocations = new ArrayList<>();
				tempCityLocations.add(locationCityDto);
				provinceLocationIdMap.put(locationCityDto.getParent_id(), new LocationProvinceDto(parentLoc.getLocationId(), parentLoc.getLocationName(), parentLoc.getLevel(), parentLoc.getParent_Id(), tempCityLocations));
			} else {
				provinceLocationIdMap.get(locationCityDto.getParent_id()).getChildren().add(locationCityDto);
			}
		}
		JSONObject json = new JSONObject();
		json.put("error_code", 0);
		json.put("data", provinceLocationIdMap.values());
		return json;
		
	}
	
	public JSONObject getLocation(String locationId) {
		Location loc = locationRepository.findOne(locationId);
		JSONObject json = new JSONObject();
		if (loc != null) {
			if (loc.getLevel() == 3L) {
				Location province = locationRepository.findOne(locationRepository.findOne(loc.getParent_Id()).getParent_Id());
				Location city = locationRepository.findOne(loc.getParent_Id());
				json.put("error_code", 0);
				json.put("data", ImmutableList.of(province.getLocationName(), city.getLocationName(), loc.getLocationName()));
				return json;
			} else if (loc.getLevel() == 2L) {
				
			}
		}
		
		return null;
	}
}
