package com.anyun100.storage.monitor.metadata_service.service;

import java.time.LocalDateTime;
import com.alibaba.fastjson.JSONObject;

public interface ClientStatusService {
	
	void doClientStatusTask();
	void doClientStatusTask(LocalDateTime now);
	JSONObject getLatestDataForDisk(Long siteId, Long numberOfreceived);
	JSONObject getInitialDataForDisk(Long siteId);
}
