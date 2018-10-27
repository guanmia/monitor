package com.anyun100.storage.monitor.client_config_service.service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.anyun100.storage.monitor.client_config_service.domain.ClientConfig;
import com.anyun100.storage.monitor.client_config_service.domain.Location;
import com.anyun100.storage.monitor.client_config_service.domain.RegionCenter;
import com.anyun100.storage.monitor.client_config_service.domain.dto.ClientConfigCityStatDto;
import com.anyun100.storage.monitor.client_config_service.domain.dto.ClientConfigStatDto;
import com.anyun100.storage.monitor.client_config_service.repository.ClientConfigRepository;
import com.anyun100.storage.monitor.client_config_service.repository.LocationRepository;
import com.anyun100.storage.monitor.client_config_service.repository.RegionCenterRepository;
import com.google.common.collect.ImmutableMap;


@Service
public class RegionCenterServiceImpl implements RegionCenterService {
	
	@Value("${region-center-id:-1}")
	private Long id;
	@Autowired
	private ClientConfigRepository clientConfigRepository;

	@Autowired
	private RegionCenterRepository regionCenterRepository;

	@Autowired
	private LocationRepository locationRepository;
	
	public List<ClientConfigCityStatDto> getRegionCenterStat() {
		if (id == -1L) {
			return null;
		}
		
		RegionCenter regionCenter = regionCenterRepository.findOne(id);
		List<ClientConfig> clientConfigs = clientConfigRepository.findClientConfigByRegionCenter(regionCenter);
		
		Map<Location, Long> clientConfigLocationCount = clientConfigs.stream().collect(Collectors.groupingBy(
													ClientConfig::getLocation,
													Collectors.counting()));
		Map<String, ClientConfigCityStatDto> clientConfigCityStatDtoMap = new HashMap<>();
		for (Location loc : clientConfigLocationCount.keySet()) {
			String cityName = locationRepository.findOne(loc.getParent_Id()).getLocationName();
			if (clientConfigCityStatDtoMap.get(cityName) == null) {
				List<ClientConfigStatDto> clientConfigStatDtos = new ArrayList<>();
				clientConfigStatDtos.add(new ClientConfigStatDto(clientConfigLocationCount.get(loc), loc.getLocationName()));
				clientConfigCityStatDtoMap.put(cityName, new ClientConfigCityStatDto(clientConfigLocationCount.get(loc),
																	cityName.substring(0, cityName.length() - 1), clientConfigStatDtos));
			} else {
				ClientConfigCityStatDto clientConfigCityStatDto = clientConfigCityStatDtoMap.get(cityName);
				clientConfigCityStatDto.setValue(clientConfigCityStatDto.getValue() + clientConfigLocationCount.get(loc));
				clientConfigCityStatDto.getChildren().add(new ClientConfigStatDto(clientConfigLocationCount.get(loc), loc.getLocationName()));
			}
		}
		/*JSONObject json = new JSONObject();
		json.put("error_code", 0);
		json.put("data", ImmutableMap.of("center_name", regionCenter.getRegionName(), "center_id", id, "client_city", clientConfigCityStatDtoMap.entrySet()));*/
		return Arrays.asList(clientConfigCityStatDtoMap.values().toArray(new ClientConfigCityStatDto[0]));
	}
	
	public RegionCenter getRegionCenter(Long id) {
		 return regionCenterRepository.findOne(id);
	}
	
}
