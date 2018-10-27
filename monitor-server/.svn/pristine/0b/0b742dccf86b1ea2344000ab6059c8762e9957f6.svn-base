package com.anyun100.storage.monitor.admin.controller;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.anyun100.storage.monitor.user_service.domain.dto.StatusDto;
import com.anyun100.storage.monitor.user_service.service.UserRoleService;
import com.google.common.collect.ImmutableMap;

@RestController
@RequestMapping("/user")
public class UserRoleController {
	
	@Autowired
	@Qualifier("userRoleService")
	private UserRoleService userRoleService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getLoginUser(Principal user) {
		
		return userRoleService.getUser(user.getName());
		
	}
	
	@RequestMapping(value = "/getinfo", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getUser(@RequestParam(value = "id", required = true) Long id) {
		
		return userRoleService.getUser(id);
		
	}
	
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getUserInfo(Principal user) {
		
		return userRoleService.getUser(user.getName());
		
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getUserList(@RequestParam(value = "searchkeys", required = false) String searchkeys,
			  					  @RequestParam(value = "page", required = true) int page,
			  					  @RequestParam(value = "limit", required = true) int limit) {
		
		return userRoleService.getUserList(searchkeys,  page, limit);
		
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject logout() {
		JSONObject json = new JSONObject();
		json.put("error_code", 0);
		json.put("data", ImmutableMap.of());
		return json;
		
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject addUser(@RequestParam(value = "username", required = true) String username,
						     @RequestParam(value= "password", required = true) String password,
						     @RequestParam(value = "roles", required = true) String roles,
						     @RequestParam(value = "lastname", required = false) String lastname,
						     @RequestParam(value = "firstname", required = false) String firstname,
						     @RequestParam(value = "email", required = false) String email,
						     @RequestParam(value = "phone", required = false) String phone,
						     @RequestParam(value = "enabled", required = false, defaultValue = "true") boolean enabled,
						     @RequestParam(value = "introduction", required = false) String introduction) {
		
		return userRoleService.addUser(username, password, roles, lastname, firstname, email, phone, enabled, introduction);
		
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateUser(@RequestParam(value = "username", required = true) String username,
						     @RequestParam(value = "roles", required = true) String roles,
						     @RequestParam(value = "lastname", required = false) String lastname,
						     @RequestParam(value = "firstname", required = false) String firstname,
						     @RequestParam(value = "email", required = false) String email,
						     @RequestParam(value = "phone", required = false) String phone,
						     @RequestParam(value = "enabled", required = false, defaultValue = "true") boolean enabled,
						     @RequestParam(value = "introduction", required = false) String introduction) {
		
		return userRoleService.updateUser(username, roles, lastname, firstname, email, phone, enabled, introduction);
		
	}
	
	@RequestMapping(value = "/disableuser", method = RequestMethod.POST)
	@ResponseBody
	public StatusDto disableUser(@RequestParam(value = "username", required = true) String username) {
		
		return userRoleService.disableUser(username);
		
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public StatusDto deleteUser(@RequestParam(value = "id", required = true) Long id) {
		
		return userRoleService.deleteUser(id);
		
	}
	
	@RequestMapping(value = "/enableuser", method = RequestMethod.POST)
	@ResponseBody
	public StatusDto enableUser(@RequestParam(value = "username", required = true) String username) {
		
		return userRoleService.enableUser(username);
	}
	
	@RequestMapping(value = "/changepwd", method = RequestMethod.POST)
	@ResponseBody
	public StatusDto changePassword(@RequestParam(value = "newPwd", required = true) String newPassword,
			 				     @RequestParam(value= "password", required = false) String password,
			 				     Principal user) {
		
		return userRoleService.changePassword(newPassword, password, user);
		
	}
	
	@RequestMapping(value = "/resetpwd", method = RequestMethod.POST)
	@ResponseBody
	public StatusDto changePassword(@RequestParam(value = "id", required = true) Long id,
			 				     @RequestParam(value= "password", required = true) String password) {
		
		return userRoleService.resetPassword(id, password);
		
	}
	
	@RequestMapping(value = "/forgotpasswordstep1")
	@ResponseBody
	public StatusDto getBackPassword(@RequestParam(value= "email", required = true) String email) {
		
		return userRoleService.getBackPassword(email);
		
	}
	
	@RequestMapping(value = "/forgotpasswordstep2")
	@ResponseBody
	public StatusDto getBackPassword(@RequestParam(value= "email", required = true) String email,
									@RequestParam(value= "code", required = true) String code,
									@RequestParam(value= "password", required = true) String password) {
		
		return userRoleService.getBackPassword(email, code, password);
		
	}
}
