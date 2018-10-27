package com.anyun100.storage.monitor.metadata_service.service;

import java.time.LocalDateTime;

import com.alibaba.fastjson.JSONObject;

public interface MetaDataStatusService {
	
	void doMetaDataStatusTask();
	void doMetaDataStatusTask(LocalDateTime now);
	
	JSONObject getInitialDataForDisk(Long regionId);
	JSONObject getLatestDataForDisk(Long regionId, Long numberOfreceived);
	JSONObject getInitialDataForMem(Long regionId);
	JSONObject getLatestDataForMem(Long regionId, Long numberOfreceived);
	JSONObject getInitialDataForCPU(Long regionId);
	JSONObject getLatestDataForCPU(Long regionId, Long numberOfreceived);
	
	JSONObject getInitialData(Long regionId);
	JSONObject getLatestData(Long regionId, Long numberOfreceived);
	
	void doRecalc(Long regionId);
}
