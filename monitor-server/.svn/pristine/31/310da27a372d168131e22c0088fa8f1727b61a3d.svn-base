package com.anyun100.storage.monitor.client_config_service.domain.dto;

import java.io.Serializable;
import java.util.List;

public class LocationProvinceDto implements Serializable {
	
	private String location_id;
	private String location;
	private Long level;
	private String parent_id;
	private List<LocationCityDto> children;
	public LocationProvinceDto(String location_id, String location, Long level, String parent_id, List<LocationCityDto> children) {
		this.location_id = location_id;
		this.location = location;
		this.level = level;
		this.parent_id = parent_id;
		this.children = children;
	}
	public List<LocationCityDto> getChildren() {
		return children;
	}
	public void setChildren(List<LocationCityDto> children) {
		this.children = children;
	}
	public String getLocation_id() {
		return location_id;
	}
	public void setLocation_id(String location_id) {
		this.location_id = location_id;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Long getLevel() {
		return level;
	}
	public void setLevel(Long level) {
		this.level = level;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
}
