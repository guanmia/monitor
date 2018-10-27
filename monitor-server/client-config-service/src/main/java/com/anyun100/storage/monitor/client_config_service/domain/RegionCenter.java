package com.anyun100.storage.monitor.client_config_service.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name = "region_center")
@Entity
public class RegionCenter extends AbstractAuditingEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	@Size(min = 1, max = 45)
    @Column(name = "region_name", length = 45)
	private String regionName;
	
	@Size(min = 1, max = 45)
    @Column(name = "server_host_name", length = 45)
    private String serverHostName;

    @Column(name = "server_host_port")
    private Long serverHostPort;
	
   
    @Column(name = "server_host_ip", length = 45)
    private String serverHostIp;
    
    @Size(min = 1, max = 45)
    @Column(name = "db_server_host", length = 45)
    private String dbHostName;

    @Column(name = "db_server_port")
    private Long dbHostPort;
	
   
    @Column(name = "db_server_ip", length = 45)
    private String dbHostIp;
    

    @Column(name = "enabled")
    private boolean enabled;
    
    @Column(name = "comments", length = 2000)
    private String comments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private Location location;
    
    @OneToMany(mappedBy = "regionCenter", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ClientConfig> clientConfigs = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getServerHostPort() {
		return serverHostPort;
	}

	public void setServerHostPort(Long serverHostPort) {
		this.serverHostPort = serverHostPort;
	}

	public String getServerHostIp() {
		return serverHostIp;
	}

	public void setServerHostIp(String serverHostIp) {
		this.serverHostIp = serverHostIp;
	}

	public String getDbHostName() {
		return dbHostName;
	}

	public void setDbHostName(String dbHostName) {
		this.dbHostName = dbHostName;
	}

	public Long getDbHostPort() {
		return dbHostPort;
	}

	public void setDbHostPort(Long dbHostPort) {
		this.dbHostPort = dbHostPort;
	}

	public String getDbHostIp() {
		return dbHostIp;
	}

	public void setDbHostIp(String dbHostIp) {
		this.dbHostIp = dbHostIp;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<ClientConfig> getClientConfigs() {
		return clientConfigs;
	}

	public void setClientConfigs(List<ClientConfig> clientConfigs) {
		this.clientConfigs = clientConfigs;
	}
}
