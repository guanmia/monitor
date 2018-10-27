package com.anyun100.storage.monitor.user_service.service;


import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;

import com.alibaba.fastjson.JSONObject;
import com.anyun100.storage.monitor.user_service.domain.dto.StatusDto;


public interface UserRoleService {
	
	@PreAuthorize("hasRole('ADMIN')")
	JSONObject addUser(String username, String password, String roles, String lastname, 
						String firstname, String email, String phone, boolean enabled, String introduction);
	@PreAuthorize("hasRole('ADMIN')")
	StatusDto disableUser(String username);
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	StatusDto changePassword(String newPassword, String password, Principal user);
	@PreAuthorize("hasRole('ADMIN')")
	StatusDto enableUser(String username);
	
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	JSONObject getUser(String username);
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	JSONObject getUserList(String searchkeys,  int page, int limit);;
	
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	JSONObject getUser(Long id);
	
	@PreAuthorize("hasRole('ADMIN')")
	JSONObject updateUser(String username, String roles, String lastname, 
			String firstname, String email, String phone, boolean enabled, String introduction);
	
	@PreAuthorize("hasRole('ADMIN')")
	StatusDto deleteUser(Long id);
	
	@PreAuthorize("hasRole('ADMIN')")
	StatusDto resetPassword(Long id, String password);
	
	StatusDto getBackPassword(String email);
	
	StatusDto getBackPassword(String email, String code, String password);
	
	@PreAuthorize("hasRole('ADMIN')")
	JSONObject setSMTP(String smtpIp, String smtpPort, String username, String password, Long isEncrypt, Boolean isVerify);
	
	@PreAuthorize("hasRole('ADMIN')")
	JSONObject getSMTP();
}
