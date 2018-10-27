package com.anyun100.storage.monitor.metadata_service.domain.dto;

import java.io.Serializable;
import java.util.Map;

public class BaseDto implements Serializable {
	
	private int error_code;
	private Map<String, Serializable> data;
	public BaseDto(int error_code, Map<String, Serializable> data) {
		this.error_code = error_code;
		this.data = data;
	}
	public int getError_code() {
		return error_code;
	}
	public void setError_code(int error_code) {
		this.error_code = error_code;
	}
	public Map<String, Serializable> getData() {
		return data;
	}
	public void setData(Map<String, Serializable> data) {
		this.data = data;
	}

}
