package com.anyun100.storage.monitor.client_config_service.domain;

public enum StatusEnum {
	
	ACTIVE(1L, "使用"),
	MAINTAIN(2L, "维护"),
	SUSPEND(3L, "停用");
	
	private Long status;
	private String desc;
	
	StatusEnum(Long status, String desc) {
		this.status = status;
		this.desc = desc;
	}

	public Long getStatus() {
		return status;
	}

	public String getDesc() {
		return desc;
	}
	
	public static StatusEnum getByStatus(Long status) {
		for (StatusEnum statusEnum : StatusEnum.values()) {
			if (statusEnum.getStatus() == status) {
				return statusEnum;
			}
		}
		return null;
	}
	
	public static StatusEnum getByDesc(String desc) {
		for (StatusEnum statusEnum : StatusEnum.values()) {
			if (statusEnum.getDesc().equals(desc)) {
				return statusEnum;
			}
		}
		return null;
	}

}
