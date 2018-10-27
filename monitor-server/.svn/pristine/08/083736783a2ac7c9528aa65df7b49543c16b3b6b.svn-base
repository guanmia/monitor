package com.anyun100.storage.monitor.metadata_service.domain;


import java.time.LocalDateTime;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import javax.persistence.Table;

@Table(name = "metadata_status")
@Entity
public class MetaDataStatus implements Comparable<MetaDataStatus> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

	@NotNull
    @Column(name = "region_id", nullable = false)
    private Long regionId;
	
    @Column(name = "system_id", nullable = false)
    private Long systemId;
	
    @Column(name = "cpu_percent")
    private Long cpuPercent;
    
    @Column(name = "mem_used")
    private Long memUsed;
   
    @Column(name = "mem_free")
    private Long memFree;
    
    @Column(name = "disk_used")
    private Long diskUsed;
   
    @Column(name = "disk_free")
    private Long diskFree;
    
    @Column(name = "netio_input")
    private Long netioInput;
   
    @Column(name = "netio_output")
    private Long netioOutput;
    

    @Column (name = "timestamp" )
    private Long ldt;


	public Long getRegionId() {
		return regionId;
	}


	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}


	public Long getSystemId() {
		return systemId;
	}


	public void setSystemId(Long systemId) {
		this.systemId = systemId;
	}


	public Long getCpuPercent() {
		return cpuPercent;
	}


	public void setCpuPercent(Long cpuPercent) {
		this.cpuPercent = cpuPercent;
	}


	public Long getMemFree() {
		return memFree;
	}


	public void setMemFree(Long memFree) {
		this.memFree = memFree;
	}


	public Long getDiskFree() {
		return diskFree;
	}


	public void setDiskFree(Long diskFree) {
		this.diskFree = diskFree;
	}


	public Long getNetioInput() {
		return netioInput;
	}


	public void setNetioInput(Long netioInput) {
		this.netioInput = netioInput;
	}


	public Long getNetioOutput() {
		return netioOutput;
	}


	public void setNetioOutput(Long netioOutput) {
		this.netioOutput = netioOutput;
	}


	public Long getLdt() {
		return ldt;
	}


	public void setLdt(Long ldt) {
		this.ldt = ldt;
	}



	public Long getMemUsed() {
		return memUsed;
	}


	public void setMemUsed(Long memUsed) {
		this.memUsed = memUsed;
	}


	public Long getDiskUsed() {
		return diskUsed;
	}


	public void setDiskUsed(Long diskUsed) {
		this.diskUsed = diskUsed;
	}


	public int compareTo(MetaDataStatus other) {
		if(this.ldt > (other.ldt)) {
			return 1;
		} else if (this.ldt < (other.ldt)) {
			return -1;
		}
		return 0;
	}

}
