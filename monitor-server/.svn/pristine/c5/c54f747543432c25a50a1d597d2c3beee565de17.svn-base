package com.anyun100.storage.monitor.metadata_service.domain;


import java.time.LocalDateTime;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import javax.persistence.Table;

@Table(name = "client_status")
@Entity
public class ClientStatus implements Comparable<ClientStatus> {
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
    @Column(name = "client_disk_total")
    private Long clientDiskTotal;

   
    @Column(name = "client_disk_free")
    private Long clientDiskFree;

    @Column (name = "timestamp" )
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime dt;

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Long getClientDiskTotal() {
		return clientDiskTotal;
	}

	public void setClientDiskTotal(Long clientDiskTotal) {
		this.clientDiskTotal = clientDiskTotal;
	}

	public Long getClientDiskFree() {
		return clientDiskFree;
	}

	public void setClientDiskFree(Long clientDiskFree) {
		this.clientDiskFree = clientDiskFree;
	}

	public LocalDateTime getDt() {
		return dt;
	}

	public void setDt(LocalDateTime dt) {
		this.dt = dt;
	}
	
	public int compareTo(ClientStatus other) {
		if(this.dt.isAfter(other.dt)) {
			return 1;
		} else if (this.dt.isBefore(other.dt)) {
			return -1;
		}
		return 0;
	}


}
