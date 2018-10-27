package com.anyun100.storage.monitor.client_config_service.domain;


import java.io.Serializable;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import javax.persistence.Table;

@Table(name = "client_config_cur")
@Entity
public class ClientConfig implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

	@NotNull
    @Column(name = "site_id", nullable = false)
    private Long siteId;
	
    @Size(min = 1, max = 45)
    @Column(name = "client_host_name", length = 45)
    private String clientHostName;

   
    @Column(name = "client_host_ip", length = 45)
    private String clientHostIp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_center_id")
    private RegionCenter regionCenter;
    
    @Column(name = "comments", length = 2000)
    private String comments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private Location location;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public RegionCenter getRegionCenter() {
		return regionCenter;
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

	public void setRegionCenter(RegionCenter regionCenter) {
		this.regionCenter = regionCenter;
	}

}
