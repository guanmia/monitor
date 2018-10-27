package com.anyun100.storage.monitor.user_service.domain.dto;

import java.io.Serializable;

public class SmtpDto implements Serializable{

	private String smtp_ip;
	private String smtp_port;
	private String username;
	private String password;
	private Long is_encrypt;
	private Boolean is_verify;
	public SmtpDto(String smtp_ip, String smtp_port, String username, String password, Long is_encrypt, Boolean is_verify) {
		this.smtp_ip = smtp_ip;
		this.smtp_port = smtp_port;
		this.username = username;
		this.password = password;
		this.is_encrypt = is_encrypt;
		this.is_verify = is_verify;
	}
	public String getSmtp_ip() {
		return smtp_ip;
	}
	public void setSmtp_ip(String smtp_ip) {
		this.smtp_ip = smtp_ip;
	}
	public String getSmtp_port() {
		return smtp_port;
	}
	public void setSmtp_port(String smtp_port) {
		this.smtp_port = smtp_port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long isIs_encrypt() {
		return is_encrypt;
	}
	public void setIs_encrypt(Long is_encrypt) {
		this.is_encrypt = is_encrypt;
	}
	public Boolean isIs_verify() {
		return is_verify;
	}
	public void setIs_verify(Boolean is_verify) {
		this.is_verify = is_verify;
	}
	
}
