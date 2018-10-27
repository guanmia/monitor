package com.anyun100.storage.monitor.metadata_service.service;

import java.time.LocalDateTime;

import com.alibaba.fastjson.JSONObject;

public interface SGWStatusService {
	
	void doSGWStatusTask();
	void doSGWStatusTask(LocalDateTime now);
	
	JSONObject getInitialDataForDisk(Long sgwId);
	JSONObject getLatestDataForDisk(Long sgwId, Long numberOfreceived);
	JSONObject getInitialDataForMem(Long sgwId);
	JSONObject getLatestDataForMem(Long sgwId, Long numberOfreceived);
	JSONObject getInitialDataForCPU(Long sgwId);
	JSONObject getLatestDataForCPU(Long sgwId, Long numberOfreceived);
	
	JSONObject getInitialData(Long sgwId);
	JSONObject getLatestData(Long sgwId, Long numberOfreceived);
	void doRecalc(Long regionId);
}
