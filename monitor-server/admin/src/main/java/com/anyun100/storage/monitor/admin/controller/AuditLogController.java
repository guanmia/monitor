package com.anyun100.storage.monitor.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.anyun100.storage.monitor.audit_service.service.AuditService;

@RestController
@RequestMapping("/logs")
public class AuditLogController {
	
	@Autowired
	private AuditService auditService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getUserList(@RequestParam(value = "searchkeys", required = false) String searchkeys,
			  					  @RequestParam(value = "page", required = true) int page,
			  					  @RequestParam(value = "limit", required = true) int limit) {
		
		return auditService.getLogs(searchkeys, page, limit);
		
	}

}
