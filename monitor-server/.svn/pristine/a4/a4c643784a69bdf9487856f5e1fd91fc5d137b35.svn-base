package com.anyun100.storage.monitor.client_config_service.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Table(name = "location")
@Entity
public class Location extends ProvinceAndCity{

	private static final long serialVersionUID = 1L;

	@NotNull
	@Id
	@Column(name = "location_id", length = 32, nullable = false, insertable = false,  updatable = false)
	private String locationId;

	@Size(min = 1, max = 50)
	@Column(name = "location_name", length = 50, nullable = false)
	private String locationName;

	@NotNull
	@Column(name = "parent_id", length = 32, nullable = false)
	private String parent_Id;

	@NotNull
	@Column(name = "level", nullable = false)
	private Long level;

	@OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ClientConfig> clientConfigs = new ArrayList<>();
	
	
	@OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<RegionCenter> regionCenters = new ArrayList<>();


	public String getLocationId() {
		return locationId;
	}


	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}


	public String getLocationName() {
		return locationName;
	}


	public void setLocatioName(String locationName) {
		this.locationName = locationName;
	}


	public String getParent_Id() {
		return parent_Id;
	}


	public void setParent_Id(String parent_Id) {
		this.parent_Id = parent_Id;
	}


	public Long getLevel() {
		return level;
	}


	public void setLevel(Long level) {
		this.level = level;
	}


	public List<ClientConfig> getClientConfigs() {
		return clientConfigs;
	}


	public void setClientConfigs(List<ClientConfig> clientConfigs) {
		this.clientConfigs = clientConfigs;
	}


	public List<RegionCenter> getRegionCenters() {
		return regionCenters;
	}


	public void setRegionCenters(List<RegionCenter> regionCenters) {
		this.regionCenters = regionCenters;
	}
}
