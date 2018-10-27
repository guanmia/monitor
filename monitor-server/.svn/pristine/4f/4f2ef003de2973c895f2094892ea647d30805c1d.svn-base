package com.anyun100.storage.monitor.audit_service.domain;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

public enum AuditActionEnum {

	
	ADMIN_USER("用户管理", ImmutableMap.of("create", "CREATE", "modify", "MODIFY"));
	
	private String name;
	
	private Map<String, String> actionTypes;
	
	AuditActionEnum(String name, Map<String, String> actionTypes) {
		this.name = name;
		this.actionTypes = actionTypes;
	}
	
	public String getName() {
		return name;
	}
	
	public Map<String, String> getValue() {
		return actionTypes;
	}
	
}
