package com.anyun100.storage.monitor.user_service.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.anyun100.storage.monitor.audit_service.service.AuditService;

import com.anyun100.storage.monitor.user_service.domain.Constants;
import com.anyun100.storage.monitor.user_service.domain.RoleEnum;
import com.anyun100.storage.monitor.user_service.domain.SysRole;
import com.anyun100.storage.monitor.user_service.domain.SysUser;
import com.anyun100.storage.monitor.user_service.domain.dto.SmtpDto;
import com.anyun100.storage.monitor.user_service.domain.dto.StatusDto;
import com.anyun100.storage.monitor.user_service.domain.dto.SysUserDto;
import com.anyun100.storage.monitor.user_service.repository.SysRoleRepository;
import com.anyun100.storage.monitor.user_service.repository.SysUserRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.alibaba.fastjson.JSONObject;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

	private static final String ROLES_DELIM = ",";

	private final Logger log = LoggerFactory.getLogger(UserRoleServiceImpl.class);
	@Autowired
	private SysUserRepository sysUserRepository;

	@Autowired
	private SysRoleRepository sysRoleRepository;

	@Autowired
	private AuditService auditService;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public JSONObject addUser(String username, String password, String roles, String lastname, String firstname,
			String email, String phone, boolean enabled, String introduction) {
		SysUser user = new SysUser();
		user.setUsername(username);
		user.setPassword(password);
		user.setLastname(lastname);
		user.setFirstname(firstname);
		user.setEmail(email);
		user.setPhone(phone);
		user.setEnabled(enabled);
		user.setIntroduction(introduction);

		Arrays.asList(roles.split(ROLES_DELIM)).stream().filter(r -> RoleEnum.getByValue(r).isPresent()).forEach(
				r -> user.getRoles().add(sysRoleRepository.findByName(RoleEnum.getByValue(r).get().getName())));
		sysUserRepository.save(user);
		
		SysUserDto sysUserDto = new SysUserDto(user.getId(), user.getUsername(), user.getEmail(), user.getIntroduction(), user.getPhone(), 
				user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toSet()));
		auditService.auditLog("", sysUserDto.toString(), "用户管理", "添加用户");
		JSONObject json = new JSONObject();
		json.put("error_code", 0);
		json.put("data", sysUserDto);
		return json;
	}

	public StatusDto disableUser(String username) {
		Optional<SysUser> sysUser = sysUserRepository.findOneWithRolesByUsername(username);
		if (sysUser.isPresent()) {
			sysUser.get().setEnabled(false);
			sysUserRepository.save(sysUser.get());
			SysUserDto sysUserDto = new SysUserDto(sysUser.get().getId(), sysUser.get().getUsername(), sysUser.get().getEmail(), sysUser.get().getIntroduction(), sysUser.get().getPhone(), 
					sysUser.get().getRoles().stream().map(r -> r.getName()).collect(Collectors.toSet()));
			auditService.auditLog(sysUserDto.toString(), "", "用户管理", "删除用户");
			return new StatusDto(0, ImmutableMap.of());
		}

		return new StatusDto(1, ImmutableMap.of());
	}

	public StatusDto changePassword(String newPassword, String password, Principal user) {
		Optional<SysUser> sysUser = sysUserRepository.findOneWithRolesByUsername(user.getName());
		if (sysUser.isPresent()) {
				if (sysUser.get().getPassword().equals(password)) {
				sysUser.get().setPassword(newPassword);
				sysUserRepository.save(sysUser.get());
				return new StatusDto(0, ImmutableMap.of());
			}
		}

		return new StatusDto(1, ImmutableMap.of());
	}

	public StatusDto enableUser(String username) {
		Optional<SysUser> sysUser = sysUserRepository.findOneWithRolesByUsername(username);
		if (sysUser.isPresent()) {
			String valueFrom = sysUser.get().toString();
			sysUser.get().setEnabled(true);
			sysUserRepository.save(sysUser.get());
			/*auditService.auditLog(valueFrom, sysUser.get().toString(), AuditActionEnum.ADMIN_USER.getName(),
					AuditActionEnum.ADMIN_USER.getValue().get("modify"));*/
			return new StatusDto(0, ImmutableMap.of());
		}

		return new StatusDto(1, ImmutableMap.of());
	}

	public JSONObject getUser(String username) {
		JSONObject json = new JSONObject();
		Optional<SysUser> sysUser = sysUserRepository.findOneWithRolesByUsername(username);
		if (sysUser.isPresent()) {
			SysUser user = sysUser.get();
			json.put("error_code", 0);
			json.put("data", new SysUserDto(user.getId(), user.getUsername(), user.getEmail(), user.getIntroduction(), user.getPhone(), 
					                        user.getRoles().stream().map(SysRole::getName).collect(Collectors.toSet())
					                        ));
			return json;
		}
		json.put("error_code", 1);
		return json;
	}

	public JSONObject getUserList(String searchkeys, int page, int limit) {
		log.info("getUserList: by searchkeys {} with page: {} and limit: {}", searchkeys, page, limit);
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			Pageable pageable = new PageRequest(page - 1, limit, Sort.Direction.DESC,"id");
			Page<SysUser> _page;
			if (searchkeys != null && !searchkeys.equals("")) {
				String[] users = mapper.readValue(searchkeys, String[].class);
				if (users != null && users.length > 0) {
					_page = sysUserRepository.findAllPagesByUsername(pageable, Arrays.asList(users));
				} else {
					_page =  sysUserRepository.findAllPages(pageable);
				}
			} else {
			
				_page =  sysUserRepository.findAllPages(pageable);
			}
			JSONObject json = new JSONObject();
			Long TotalNumberOfElements = _page.getTotalElements();
			List<SysUserDto> sysUserDtos = new ArrayList<>();
			_page.getContent().stream().filter(s -> s.isEnabled()).forEach(user -> sysUserDtos.add(new SysUserDto(user.getId(), user.getUsername(), user.getEmail(), user.getIntroduction(), user.getPhone(), 
	                											user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toSet()))));
			json.put("error_code", 0);
			json.put("data", ImmutableMap.of("total", TotalNumberOfElements, "results", sysUserDtos.toArray(new SysUserDto[0])));
			return json;
		} catch (Exception e) {
			log.error(e.getMessage());
			JSONObject json = new JSONObject();
			json.put("error_code", 1);
			return json;
		}
	}

	public JSONObject getUser(Long id) {
		JSONObject json = new JSONObject();
		SysUser sysUser = sysUserRepository.getOne(id);
		if (sysUser != null) {
			return getUser(sysUser.getUsername());
		}
		json.put("error_code", 1);
		return json;
	}

	public JSONObject updateUser(String username, String roles, String lastname, String firstname,
			String email, String phone, boolean enabled, String introduction) {
		Optional<SysUser> sysUser = sysUserRepository.findOneWithRolesByUsername(username);
		if (sysUser.isPresent()) {
			SysUser oldSysUser = sysUser.get();
			SysUserDto sysUserDtoFrom = new SysUserDto(oldSysUser.getId(), oldSysUser.getUsername(), oldSysUser.getEmail(), oldSysUser.getIntroduction(), oldSysUser.getPhone(), 
					oldSysUser.getRoles().stream().map(r -> r.getName()).collect(Collectors.toSet()));
			oldSysUser.setUsername(username);
			oldSysUser.setLastname(lastname);
			oldSysUser.setFirstname(firstname);
			oldSysUser.setEmail(email);
			oldSysUser.setPhone(phone);
			oldSysUser.setEnabled(enabled);
			oldSysUser.setIntroduction(introduction);
			oldSysUser.getRoles().clear();
			Arrays.asList(roles.split(ROLES_DELIM)).stream().filter(r -> RoleEnum.getByValue(r).isPresent())
													.forEach(r -> oldSysUser.getRoles().add(sysRoleRepository.findByName(RoleEnum.getByValue(r).get().getName())));
			sysUserRepository.save(oldSysUser);
			SysUserDto sysUserDtoTo = new SysUserDto(oldSysUser.getId(), oldSysUser.getUsername(), oldSysUser.getEmail(), oldSysUser.getIntroduction(), oldSysUser.getPhone(), 
					oldSysUser.getRoles().stream().map(r -> r.getName()).collect(Collectors.toSet()));
			auditService.auditLog(sysUserDtoFrom.toString(), sysUserDtoTo.toString(), "用户管理", "更新用户");
			JSONObject json = new JSONObject();
			json.put("error_code", 0);
			json.put("data", new SysUserDto(oldSysUser.getId(), oldSysUser.getUsername(), oldSysUser.getEmail(), oldSysUser.getIntroduction(), oldSysUser.getPhone(), 
					oldSysUser.getRoles().stream().map(r -> r.getName()).collect(Collectors.toSet())));
			return json;
		}
		JSONObject json = new JSONObject();
		json.put("error_code", 1);
		return json;
	}
	
	public StatusDto deleteUser(Long id) {
		SysUser sysUser = sysUserRepository.getOne(id);
		if (sysUser != null) {
			return disableUser(sysUser.getUsername());
		}
		
		return new StatusDto(1, ImmutableMap.of());
	}
	
	public StatusDto resetPassword(Long id, String password) {
		SysUser sysUser = sysUserRepository.findOne(id);
		if (sysUser != null) {
			String valueFrom = sysUser.toString();
			sysUser.setPassword(password);
			sysUserRepository.save(sysUser);
			/*auditService.auditLog(valueFrom, sysUser.toString(), AuditActionEnum.ADMIN_USER.getName(),
					AuditActionEnum.ADMIN_USER.getValue().get("modify"));*/
			return new StatusDto(0, ImmutableMap.of());
		}

		return new StatusDto(1, ImmutableMap.of());
	}
	
	public StatusDto getBackPassword(String email) {
		Optional<SysUser> sysUser = sysUserRepository.findOneByEmail(email);
		if (sysUser.isPresent()) {
			try {
					SimpleMailMessage mailMessage = new SimpleMailMessage();
			        mailMessage.setTo(email);
			        mailMessage.setSubject("新密码验证码 ");
			        int random = (int)((Math.random() * 9 + 1) * 1000);
			        mailMessage.setText("" + random);
			        redisTemplate.opsForValue().set(email, "" + random,  60*100, TimeUnit.SECONDS);
			        javaMailSender.send(mailMessage);
			        return new StatusDto(0, ImmutableMap.of());
			} catch(Exception e) {
				return new StatusDto(5, ImmutableMap.of());
			}
		}
		return new StatusDto(4, ImmutableMap.of());
	}
	
	public StatusDto getBackPassword(String email, String code, String password) {
		Optional<SysUser> sysUser = sysUserRepository.findOneByEmail(email);
		if (sysUser.isPresent()) {
			try {
			        if (redisTemplate.opsForValue().get(email).equals(code)) {
			        	sysUser.get().setPassword(password);
			        	sysUserRepository.save(sysUser.get());
			        	return new StatusDto(0, ImmutableMap.of());
			        }
			} catch(Exception e) {
				return new StatusDto(6, ImmutableMap.of());
			}
		}
		return new StatusDto(6, ImmutableMap.of());
	}
	
	public JSONObject getSMTP() {
		
		SmtpDto smtpDto = new SmtpDto(redisTemplate.opsForValue().get(Constants.SMTP_IP), redisTemplate.opsForValue().get(Constants.SMTP_PORT), 
				redisTemplate.opsForValue().get(Constants.SMTP_USERNMAE), redisTemplate.opsForValue().get(Constants.SMTP_PASSWORD),
				null == redisTemplate.opsForValue().get(Constants.SMTP_IS_ENCRYPT) || redisTemplate.opsForValue().get(Constants.SMTP_IS_ENCRYPT).equals("") 
				? 0L : Long.parseLong(redisTemplate.opsForValue().get(Constants.SMTP_IS_ENCRYPT)), 
				(null != redisTemplate.opsForValue().get(Constants.SMTP_IS_VERIFY) && redisTemplate.opsForValue().get(Constants.SMTP_IS_VERIFY).equals("1")));
		JSONObject json = new JSONObject();
		json.put("error_code", 0);
		json.put("data", smtpDto);
		return json;
	}
	
	public JSONObject setSMTP(String smtpIp, String smtpPort, String username, String password, Long isEncrypt, Boolean isVerify) {
		
		redisTemplate.opsForValue().set(Constants.SMTP_IP, smtpIp);
		redisTemplate.opsForValue().set(Constants.SMTP_PORT, smtpPort);
		
		redisTemplate.opsForValue().set(Constants.SMTP_USERNMAE, username);
		redisTemplate.opsForValue().set(Constants.SMTP_PASSWORD, password);
		
		redisTemplate.opsForValue().set(Constants.SMTP_IS_ENCRYPT, "" + isEncrypt);
		redisTemplate.opsForValue().set(Constants.SMTP_IS_VERIFY, isVerify ? "1" : "0");
		SmtpDto smtpDto = new SmtpDto(redisTemplate.opsForValue().get(Constants.SMTP_IP), redisTemplate.opsForValue().get(Constants.SMTP_PORT), 
				redisTemplate.opsForValue().get(Constants.SMTP_USERNMAE), redisTemplate.opsForValue().get(Constants.SMTP_PASSWORD),
				Long.parseLong(redisTemplate.opsForValue().get(Constants.SMTP_IS_ENCRYPT)), (null != redisTemplate.opsForValue().get(Constants.SMTP_IS_VERIFY) && redisTemplate.opsForValue().get(Constants.SMTP_IS_VERIFY).equals("1")));
		JSONObject json = new JSONObject();
		json.put("error_code", 0);
		json.put("data", smtpDto);
		return json;
	}
}
