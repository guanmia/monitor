package com.anyun100.storage.monitor.metadata_service.domain.dto;


import java.io.Serializable;

public class ClientStatusDto implements Serializable, Comparable<ClientStatusDto>{
   
    private Long siteId;
	
    private Long[] diskFree;
    private Long[] diskTotal;
	
	public ClientStatusDto(Long siteId, Long[] diskFree, Long[] diskTotal) {
		this.siteId = siteId;
		this.diskFree = diskFree;
		this.diskTotal = diskTotal;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Long[] getDiskFree() {
		return diskFree;
	}

	public void setDiskFree(Long[] diskFree) {
		this.diskFree = diskFree;
	}

	public Long[] getDiskTotal() {
		return diskTotal;
	}

	public void setDiskTotal(Long[] diskTotal) {
		this.diskTotal = diskTotal;
	}

	public int compareTo(ClientStatusDto other) {
		return (int) (this.siteId - other.siteId);
	}

}
