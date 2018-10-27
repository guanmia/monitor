package com.anyun100.storage.monitor.audit_service.service;



import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.anyun100.storage.monitor.audit_service.domain.SysLog;
import com.anyun100.storage.monitor.audit_service.domain.dto.SearchkeyDto;
import com.anyun100.storage.monitor.audit_service.domain.dto.SysLogDto;
import com.anyun100.storage.monitor.audit_service.repository.SysLogRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;

@Service
public class AuditServiceImpl implements AuditService{

	@Autowired
	private SysLogRepository sysLogRepository;
	
	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	
	private final Logger log = LoggerFactory.getLogger(AuditServiceImpl.class);
	
	public void auditLog(String valueFrom, String valueTo, String actionName, String actionType) {
		SysLog sysLog = new SysLog();
		sysLog.setValueFrom(valueFrom != null && !valueFrom.equals("") ? "From " + valueFrom.substring(valueFrom.indexOf("[")) : "From []");
		sysLog.setValueTo(valueTo != null && !valueTo.equals("") ? "To " + valueTo.substring(valueTo.indexOf("[")) : "To []");
		sysLog.setActionName(actionName);
		sysLog.setActionType(actionType);
		sysLogRepository.save(sysLog);
	}
	
	public JSONObject getLogs(String searchkeys, int page, int limit) {
		log.info("getLogs: by searchkeys {} with page: {} and limit: {}", searchkeys, page, limit);
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			Pageable pageable = new PageRequest(page - 1, limit, Sort.Direction.DESC,"id");
			Page<SysLog> _page;
			if (searchkeys != null && !searchkeys.equals("")) {
				SearchkeyDto searchkeyDto = mapper.readValue(searchkeys, SearchkeyDto.class);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				_page = sysLogRepository.findAllPages(pageable, StringUtils.isEmpty(searchkeyDto.getTime()[0]) ? sdf.parse("1970-01-01 00:00:00") : sdf.parse(searchkeyDto.getTime()[0]), 
																StringUtils.isEmpty(searchkeyDto.getTime()[1]) ? new Date() : sdf.parse(searchkeyDto.getTime()[1]), Arrays.asList(searchkeyDto.getUsername()));
			} else {
			
				_page =  sysLogRepository.findAll(pageable);
			}
			Long TotalNumberOfElements = _page.getTotalElements();
			List<SysLogDto> sysLogDtos = new ArrayList<>();
			_page.getContent().stream().forEach(l -> sysLogDtos.add(new SysLogDto(l.getId(), l.getLastModifiedBy(),
												l.getActionName() + " " + l.getActionType() + " " + l.getValueFrom() + " " + l.getValueTo(), 
												format.format(l.getLastModifiedDate()))));
			JSONObject json = new JSONObject();
			json.put("error_code", 0);
			json.put("data", ImmutableMap.of("total", TotalNumberOfElements, "results", sysLogDtos.toArray(new SysLogDto[0])));
			return json;
		} catch (Exception e) {
			log.error(e.getMessage());
			JSONObject json = new JSONObject();
			json.put("error_code", 1);
			return json;
		}
	} 
}
