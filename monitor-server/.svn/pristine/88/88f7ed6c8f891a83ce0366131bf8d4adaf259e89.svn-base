package com.anyun100.storage.monitor.client_config_service.domain.dto;


import java.io.Serializable;

public class ClientConfigDto implements Serializable, Comparable<ClientConfigDto>{
   
    private Long siteId;
	
    private String clientHostName;

    private String clientHostIp;

    private String regionName;
	
    private String serverHostName;

    private String serverHostIp;
    
    private Long serverHostPort;
	
    private boolean enabled;
    
    private String locationName;
    
    private String cityName;
    
    private String provinceName;

	public ClientConfigDto(Long siteId, String clientHostName, String clientHostIp, String regionName,
			String serverHostName, String serverHostIp, Long serverHostPort, boolean enabled, String locationName,
			String cityName, String provinceName) {
		this.siteId = siteId;
		this.clientHostName = clientHostName;
		this.clientHostIp = clientHostIp;
		this.regionName = regionName;
		this.serverHostName = serverHostName;
		this.serverHostIp = serverHostIp;
		this.serverHostPort = serverHostPort;
		this.enabled = enabled;
		this.locationName = locationName;
		this.cityName = cityName;
		this.provinceName = provinceName;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getClientHostName() {
		return clientHostName;
	}

	public void setClientHostName(String clientHostName) {
		this.clientHostName = clientHostName;
	}

	public String getClientHostIp() {
		return clientHostIp;
	}

	public void setClientHostIp(String clientHostIp) {
		this.clientHostIp = clientHostIp;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getServerHostName() {
		return serverHostName;
	}

	public void setServerHostName(String serverHostName) {
		this.serverHostName = serverHostName;
	}

	public String getServerHostIp() {
		return serverHostIp;
	}

	public void setServerHostIp(String serverHostIp) {
		this.serverHostIp = serverHostIp;
	}

	public Long getServerHostPort() {
		return serverHostPort;
	}

	public void setServerHostPort(Long serverHostPort) {
		this.serverHostPort = serverHostPort;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public int compareTo(ClientConfigDto other) {
		return (int) (this.siteId - other.siteId);
	}

}
