package com.anyun100.storage.monitor.user_service.domain;

import java.util.Arrays;
import java.util.Optional;

public enum RoleEnum {

	ADMIN_USER("admin", "ROLE_ADMIN"),
	NORMAL_USER("user", "ROLE_USER");
	
	private String name;
	private String value;
	RoleEnum(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
	
	public static Optional<RoleEnum> getByValue(String value) {
		return Arrays.asList(RoleEnum.values()).stream().filter(x -> x.getValue().equalsIgnoreCase(value.trim())).findFirst();
	}
}
