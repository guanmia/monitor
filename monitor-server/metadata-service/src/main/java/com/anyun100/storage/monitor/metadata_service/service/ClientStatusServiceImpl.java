package com.anyun100.storage.monitor.metadata_service.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.anyun100.storage.monitor.metadata_service.domain.ClientStatus;
import com.anyun100.storage.monitor.metadata_service.domain.Constants;
import com.anyun100.storage.monitor.metadata_service.repository.ClientStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ClientStatusServiceImpl implements ClientStatusService {

	private static final Logger logger = LoggerFactory.getLogger(ClientStatusServiceImpl.class);
	
	@Autowired
	private ClientStatusRepository clientStatusRepository;

	@Autowired
	private RedisTemplate<String, Long[]> redisTemplate;

	private final int checkIntervalMinutes = 5;
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

	//@Scheduled(cron = "* 0/5 * * * ?")
	public void doClientStatusTask() {
		LocalDateTime dt = LocalDateTime.now();
		logger.info("Start client status job at " + formatter.format(dt));
		Set<Long> siteIds = clientStatusRepository.findAll().stream().map(c -> c.getSiteId()).collect(Collectors.toSet());
		if (siteIds != null) {
			calcClientStatusDisk(siteIds, dt);
			shiftCacheData(siteIds, dt);
		}
		logger.info("End client status job at " + formatter.format(LocalDateTime.now()));
	}
	
	public void doClientStatusTask(LocalDateTime now) {
		logger.info("Start client status job at " + formatter.format(now));
		Set<Long> siteIds = clientStatusRepository.findAll().stream().map(c -> c.getSiteId()).collect(Collectors.toSet());
		if (siteIds != null) {
			calcClientStatusDisk(siteIds, now);
			shiftCacheData(siteIds, now);
		}
		logger.info("End client status job at " + formatter.format(LocalDateTime.now()));
	}

	private void shiftCacheData(Set<Long> siteIds, LocalDateTime now) {
		if (now.getHour() == 0 && now.getMinute() == 0) {

			for (Long siteId : siteIds) {
				Long[] todayDiskFreeClientStatus = redisTemplate.opsForValue().get(Constants.CLIENT_STATUS_DISK_FREE_TODAY_KEY + siteId);
				Long[] todayDiskTotalClientStatus = redisTemplate.opsForValue().get(Constants.CLIENT_STATUS_DISK_TOTAL_TODAY_KEY + siteId);
				Long[] yesdayDiskFreeClientStatus = redisTemplate.opsForValue().get(Constants.CLIENT_STATUS_DISK_FREE_YESTERDAY_KEY + siteId);
				Long[] yesdayDiskTotalClientStatus = redisTemplate.opsForValue().get(Constants.CLIENT_STATUS_DISK_TOTAL_YESTERDAY_KEY + siteId);
				if (todayDiskFreeClientStatus != null) {
					redisTemplate.opsForValue().set(Constants.CLIENT_STATUS_DISK_FREE_YESTERDAY_KEY + siteId, todayDiskFreeClientStatus);
				}
				
				if (todayDiskTotalClientStatus != null) {
					redisTemplate.opsForValue().set(Constants.CLIENT_STATUS_DISK_TOTAL_YESTERDAY_KEY + siteId, todayDiskTotalClientStatus);
				}

				if (yesdayDiskFreeClientStatus != null) {
					redisTemplate.opsForValue().set(Constants.CLIENT_STATUS_DISK_FREE_BEFORE_YESTERDAY_KEY + siteId, yesdayDiskFreeClientStatus);
				}
				
				if (yesdayDiskTotalClientStatus != null) {
					redisTemplate.opsForValue().set(Constants.CLIENT_STATUS_DISK_TOTAL_BEFORE_YESTERDAY_KEY + siteId, yesdayDiskTotalClientStatus);
				}
				redisTemplate.opsForValue().set(Constants.CLIENT_STATUS_DISK_FREE_TODAY_KEY + siteId, new Long[287]);
				redisTemplate.opsForValue().set(Constants.CLIENT_STATUS_DISK_TOTAL_TODAY_KEY + siteId, new Long[287]);
			}
		}
	}

	private void calcClientStatusDisk(Set<Long> siteIds, LocalDateTime now) {
		int hoursInMinutes = now.getHour() * 60;
		int minutes = now.getMinute();
		for (Long siteId : siteIds) {
			long numOfCalcNeeded = (long) Math.floor((hoursInMinutes + minutes) / checkIntervalMinutes);
			Long[] clientStatusDiskFree = redisTemplate.opsForValue().get(Constants.CLIENT_STATUS_DISK_FREE_TODAY_KEY + siteId);
			Long[] clientStatusDiskTotal = redisTemplate.opsForValue().get(Constants.CLIENT_STATUS_DISK_TOTAL_TODAY_KEY + siteId);
			if (clientStatusDiskFree == null) {
				clientStatusDiskFree = new Long[287];
			}
			
			if (clientStatusDiskTotal == null) {
				clientStatusDiskTotal = new Long[287];
			}
			
			long notNullNum = Arrays.asList(clientStatusDiskFree).stream().filter(c -> c != null).count();
			long tempNotNullNum = notNullNum;
			while (numOfCalcNeeded >= notNullNum) {
				LocalDateTime start = now.minusMinutes(checkIntervalMinutes * numOfCalcNeeded);
				LocalDateTime end =  now.minusMinutes(checkIntervalMinutes * (numOfCalcNeeded - 1));
				Optional<ClientStatus> clientStatus = clientStatusRepository.findClientStatus(siteId, start, end)
																			.stream().max(new Comparator<ClientStatus>() {
																				@Override  
																	            public int compare(ClientStatus o1, ClientStatus o2) {  
																	                 return o1.compareTo(o2);  
																	            }  
																			});
				if (clientStatus.isPresent()) {
					clientStatusDiskFree[(int) tempNotNullNum] = clientStatus.get().getClientDiskFree();
					clientStatusDiskTotal[(int) tempNotNullNum] = clientStatus.get().getClientDiskTotal();
					
				}
				tempNotNullNum++;
				numOfCalcNeeded--;
			}
			redisTemplate.opsForValue().set(Constants.CLIENT_STATUS_DISK_FREE_TODAY_KEY + siteId, clientStatusDiskFree);
			redisTemplate.opsForValue().set(Constants.CLIENT_STATUS_DISK_TOTAL_TODAY_KEY + siteId, clientStatusDiskTotal);
		}
	}
	
	public JSONObject getInitialDataForDisk(Long siteId) {
		JSONObject json = new JSONObject();
		Long[] todayDiskFreeClientStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.CLIENT_STATUS_DISK_FREE_TODAY_KEY, siteId);
		Long[] todayDiskTotalClientStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.CLIENT_STATUS_DISK_TOTAL_TODAY_KEY , siteId);
		Long[] yesdayDiskFreeClientStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.CLIENT_STATUS_DISK_FREE_YESTERDAY_KEY , siteId);
		Long[] yesdayDiskTotalClientStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.CLIENT_STATUS_DISK_TOTAL_YESTERDAY_KEY , siteId);
		Long[] beforeYesdayDiskFreeClientStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.CLIENT_STATUS_DISK_FREE_BEFORE_YESTERDAY_KEY , siteId);
		Long[] beforeYesdayDiskTotalClientStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.CLIENT_STATUS_DISK_TOTAL_BEFORE_YESTERDAY_KEY , siteId);
		json.put(Constants.JSON_STATUS_DISK_FREE_TODAY_KEY, todayDiskFreeClientStatus);
		json.put(Constants.CLIENT_STATUS_DISK_TOTAL_TODAY_KEY, todayDiskTotalClientStatus);
		json.put(Constants.JSON_STATUS_DISK_FREE_YESTERDAY_KEY, yesdayDiskFreeClientStatus);
		json.put(Constants.JSON_STATUS_DISK_TOTAL_YESTERDAY_KEY, yesdayDiskTotalClientStatus);
		json.put(Constants.JSON_STATUS_DISK_FREE_BEFORE_YESTERDAY_KEY, beforeYesdayDiskFreeClientStatus);
		json.put(Constants.JSON_STATUS_DISK_TOTAL_BEFORE_YESTERDAY_KEY, beforeYesdayDiskTotalClientStatus);
		return json;
	}
	
	public JSONObject getLatestDataForDisk(Long siteId, Long numberOfreceived) {
		JSONObject json = new JSONObject();
		Long[] todayDiskFreeClientStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.CLIENT_STATUS_DISK_FREE_TODAY_KEY, siteId);
		Long[] todayDiskTotalClientStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.CLIENT_STATUS_DISK_TOTAL_TODAY_KEY , siteId);
		json.put(Constants.JSON_STATUS_DISK_FREE_TODAY_KEY, Arrays.asList(todayDiskFreeClientStatus).stream().skip(numberOfreceived).collect(Collectors.toList()));
		json.put(Constants.CLIENT_STATUS_DISK_TOTAL_TODAY_KEY, Arrays.asList(todayDiskTotalClientStatus).stream().skip(numberOfreceived).collect(Collectors.toList()));
		return json;
		
	}
}
