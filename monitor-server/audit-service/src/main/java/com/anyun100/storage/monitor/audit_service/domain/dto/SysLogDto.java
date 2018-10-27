package com.anyun100.storage.monitor.audit_service.domain.dto;

public class SysLogDto {
	
	private Long id;
	private String username;
	private String content;
	private String time;
	public SysLogDto(Long id, String username, String content, String time) {
		this.id = id;
		this.username = username;
		this.content = content;
		this.time = time;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

}
