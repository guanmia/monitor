package com.anyun100.storage.monitor.admin.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.anyun100.storage.monitor.user_service.service.UserRoleService;

@RestController
@RequestMapping("/system")
public class SystemConfigController {
	@Autowired
	@Qualifier("userRoleService")
	private UserRoleService userRoleService; 

	@RequestMapping(value = "/smtpdata")
	@ResponseBody
	public JSONObject getSmtp() {
		
		return userRoleService.getSMTP();
		
	}
	
	@RequestMapping(value = "/smtp")
	@ResponseBody
	public JSONObject setSmtp(@RequestParam(value = "username", required = true) String username,
		     				@RequestParam(value= "password", required = true) String password,
		     				@RequestParam(value= "smtp_ip", required = true) String smtpIp,
		     				@RequestParam(value= "smtp_port", required = true) String smtpPort,
		     				@RequestParam(value= "is_encrypt", required = true) Long isEncrypt,
		     				@RequestParam(value= "is_verify", required = true) Boolean isVerify
		     ) {
		
		return userRoleService.setSMTP(smtpIp, smtpPort, username, password, isEncrypt, isVerify);
		
	}
}
