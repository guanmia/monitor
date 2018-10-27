package com.anyun100.storage.monitor.metadata_service.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.anyun100.storage.monitor.metadata_service.domain.Constants;
import com.anyun100.storage.monitor.metadata_service.domain.MetaDataStatus;
import com.anyun100.storage.monitor.metadata_service.domain.SGWStatus;
import com.anyun100.storage.monitor.metadata_service.repository.SGWStatusRepository;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;


@Service
public class SGWStatusServiceImpl implements SGWStatusService {

private static final Logger logger = LoggerFactory.getLogger(SGWStatusServiceImpl.class);
	
	@Autowired
	private SGWStatusRepository sgwStatusRepository;

	@Autowired
	private RedisTemplate<String, Long[]> redisTemplate;
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

	private final int checkIntervalMinutes = 5;
	
	private int[] jobWhichIsDone = new int[(24 * 60) / checkIntervalMinutes];
	
	@Value("${region-center-id:-1}")
	private Long id;

	@Scheduled(cron = "* 0/5 * * * ?")
	public void doSGWStatusTask() {
		if (id == -1L) return;
		LocalDateTime dt = LocalDateTime.now();
		int hoursInMinutes = dt.getHour() * 60;
		int minutes = dt.getMinute();
		int numOfJobNeeded = (int) Math.floor((hoursInMinutes + minutes) / checkIntervalMinutes);
		if (jobWhichIsDone[numOfJobNeeded] != 0 ) {
			logger.info("SGW status job at {} with object {} is done", formatter.format(dt), this);
			return;
		}
		logger.info("Start SGW status job at {} with object {} ", formatter.format(dt), this);
		countJobRun(dt);
		shiftCacheDataForDisk(id, dt);
		shiftCacheDataForCPU(id, dt);
		shiftCacheDataForMem(id, dt);
		shiftCacheDataForNet(id, dt);
		//calcSGWStatusDisk(id, dt);
		calcSGWStatusCPUNETMem(id, dt);
		//calcSGWStatusMem(id, dt);
		//calcSGWStatusNet(id, dt);
		logger.info("End SGW status job at {} with object {}", formatter.format(LocalDateTime.now()), this);
	}
	
	public void doSGWStatusTask(LocalDateTime now) {
		if (id == -1L) return;
		logger.info("Start SGW status job at " + formatter.format(now));
		shiftCacheDataForDisk(id, now);
		shiftCacheDataForCPU(id, now);
		shiftCacheDataForMem(id, now);
		shiftCacheDataForNet(id, now);
		//calcSGWStatusDisk(id, now);
		calcSGWStatusCPUNETMem(id, now);
		//calcSGWStatusMem(id, now);
		//calcSGWStatusNet(id, now);
		logger.info("End SGW status job at " + formatter.format(LocalDateTime.now()));
	}

	private void countJobRun(LocalDateTime now) {
		int hoursInMinutes = now.getHour() * 60;
		int minutes = now.getMinute();
		int numOfJobNeeded = (int) Math.floor((hoursInMinutes + minutes) / checkIntervalMinutes);
		if (now.getHour() == 0 && now.getMinute() == 0) {
			jobWhichIsDone = new int[(24 * 60) / checkIntervalMinutes];
			jobWhichIsDone[numOfJobNeeded] = 1;
		} else {
			jobWhichIsDone[numOfJobNeeded] = 1;
		}
		
	}
	private void shiftCacheDataForDisk(Long regionId, LocalDateTime now) {
		if (now.getHour() == 0 && now.getMinute() == 0) {
			logger.info("Start shift disk status data for regionId {} at {} ", regionId, formatter.format(now));
			Set<Long> sgwIds = sgwStatusRepository.findAllSGWSIds(regionId);
			for (Long sgwId : sgwIds) {
				Long[] todayDiskFreeSGWStatus = redisTemplate.opsForValue().get(Constants.SGW_STATUS_DISK_FREE_TODAY_KEY + sgwId);
				Long[] todayDiskUsedSGWStatus = redisTemplate.opsForValue().get(Constants.SGW_STATUS_DISK_USED_TODAY_KEY + sgwId);
				Long[] yesdayDiskFreeSGWStatus = redisTemplate.opsForValue().get(Constants.SGW_STATUS_DISK_FREE_YESTERDAY_KEY + sgwId);
				Long[] yesdayDiskUsedSGWStatus = redisTemplate.opsForValue().get(Constants.SGW_STATUS_DISK_USED_YESTERDAY_KEY + sgwId);
				if (todayDiskFreeSGWStatus != null) {
					redisTemplate.opsForValue().set(Constants.SGW_STATUS_DISK_FREE_YESTERDAY_KEY + sgwId, todayDiskFreeSGWStatus);
					logger.info("shift todayDiskFreeMetaDataStatus for sgwId {} at {} with data {}", sgwId, formatter.format(now), todayDiskFreeSGWStatus);
				}
				
				if (todayDiskUsedSGWStatus != null) {
					redisTemplate.opsForValue().set(Constants.SGW_STATUS_DISK_USED_YESTERDAY_KEY + sgwId, todayDiskUsedSGWStatus);
				}

				if (yesdayDiskFreeSGWStatus != null) {
					redisTemplate.opsForValue().set(Constants.SGW_STATUS_DISK_FREE_BEFORE_YESTERDAY_KEY + sgwId, yesdayDiskFreeSGWStatus);
					logger.info("shift yesdayDiskFreeSGWStatus for sgwId {} at {} with data {}", sgwId, formatter.format(now), yesdayDiskFreeSGWStatus);
				}
				
				if (yesdayDiskUsedSGWStatus != null) {
					redisTemplate.opsForValue().set(Constants.SGW_STATUS_DISK_USED_BEFORE_YESTERDAY_KEY + sgwId, yesdayDiskUsedSGWStatus);
				}
				redisTemplate.opsForValue().set(Constants.SGW_STATUS_DISK_FREE_TODAY_KEY + sgwId, new Long[1]);
				redisTemplate.opsForValue().set(Constants.SGW_STATUS_DISK_USED_TODAY_KEY + sgwId, new Long[1]);
			}
			logger.info("end shift disk status data for regionId {} at {} ", regionId, formatter.format(now));
		}
	}
	
	private void shiftCacheDataForNet(Long regionId, LocalDateTime now) {
		if (now.getHour() == 0 && now.getMinute() == 0) {
			logger.info("Start shift net status data for regionId {} at {} ", regionId, formatter.format(now));
			Set<Long> sgwIds = sgwStatusRepository.findAllSGWSIds(regionId);
			for (Long sgwId : sgwIds) {
				Long[] todayNetInSGWStatus = redisTemplate.opsForValue().get(Constants.SGW_STATUS_NET_IN_TODAY_KEY + sgwId);
				Long[] todayNetOutSGWStatus = redisTemplate.opsForValue().get(Constants.SGW_STATUS_NET_OUT_TODAY_KEY + sgwId);
				Long[] yesdayNetInSGWStatus = redisTemplate.opsForValue().get(Constants.SGW_STATUS_NET_IN_YESTERDAY_KEY + sgwId);
				Long[] yesdayNetOutSGWStatus = redisTemplate.opsForValue().get(Constants.SGW_STATUS_NET_OUT_YESTERDAY_KEY + sgwId);
				if (todayNetInSGWStatus != null) {
					redisTemplate.opsForValue().set(Constants.SGW_STATUS_NET_IN_YESTERDAY_KEY + sgwId, todayNetInSGWStatus);
					logger.info("shift todayNetInSGWStatus for sgwId {} at {} with data {}", sgwId, formatter.format(now), todayNetInSGWStatus);
				}
				
				if (todayNetOutSGWStatus != null) {
					redisTemplate.opsForValue().set(Constants.SGW_STATUS_NET_OUT_YESTERDAY_KEY + sgwId, todayNetOutSGWStatus);
				}

				if (yesdayNetInSGWStatus != null) {
					redisTemplate.opsForValue().set(Constants.SGW_STATUS_NET_IN_BEFORE_YESTERDAY_KEY + sgwId, yesdayNetInSGWStatus);
					logger.info("shift yesdayNetInSGWStatus for sgwId {} at {} with data {}", sgwId, formatter.format(now), yesdayNetInSGWStatus);
				}
				
				if (yesdayNetOutSGWStatus != null) {
					redisTemplate.opsForValue().set(Constants.SGW_STATUS_NET_OUT_BEFORE_YESTERDAY_KEY + sgwId, yesdayNetOutSGWStatus);
				}
				redisTemplate.opsForValue().set(Constants.SGW_STATUS_NET_IN_TODAY_KEY + sgwId, new Long[1]);
				redisTemplate.opsForValue().set(Constants.SGW_STATUS_NET_OUT_TODAY_KEY + sgwId, new Long[1]);
			}
			logger.info("end shift net status data for regionId {} at {} ", regionId, formatter.format(now));
		}
	}
	
	private void shiftCacheDataForCPU(Long regionId, LocalDateTime now) {
		if (now.getHour() == 0 && now.getMinute() == 0) {
			logger.info("Start shift cpu status data for regionId {} at {} ", regionId, formatter.format(now));
			Set<Long> sgwIds = sgwStatusRepository.findAllSGWSIds(regionId);
			for (Long sgwId : sgwIds) {
				Long[] todayCPUSGWStatus = redisTemplate.opsForValue().get(Constants.SGW_STATUS_CPU_TODAY_KEY + sgwId);
				Long[] yesdayCPUSGWStatus = redisTemplate.opsForValue().get(Constants.SGW_STATUS_CPU_YESTERDAY_KEY + sgwId);
				if (todayCPUSGWStatus != null) {
					redisTemplate.opsForValue().set(Constants.SGW_STATUS_CPU_YESTERDAY_KEY + sgwId, todayCPUSGWStatus);
				}

				if (yesdayCPUSGWStatus != null) {
					redisTemplate.opsForValue().set(Constants.SGW_STATUS_CPU_BEFORE_YESTERDAY_KEY + sgwId, yesdayCPUSGWStatus);
				}
				
				redisTemplate.opsForValue().set(Constants.SGW_STATUS_CPU_TODAY_KEY + sgwId, new Long[1]);
			}
			logger.info("end shift cpu status data for regionId {} at {} ", regionId, formatter.format(now));
		}
	}
	
	private void shiftCacheDataForMem(Long regionId, LocalDateTime now) {
		if (now.getHour() == 0 && now.getMinute() == 0) {
			Set<Long> sgwIds = sgwStatusRepository.findAllSGWSIds(regionId);
			logger.info("Start shift mem status data for regionId {} at {} ", regionId, formatter.format(now));
			for (Long sgwId : sgwIds) {
				Long[] todayMemFreeSGWStatus = redisTemplate.opsForValue().get(Constants.SGW_STATUS_MEM_FREE_TODAY_KEY + sgwId);
				Long[] todayMemUsedSGWStatus = redisTemplate.opsForValue().get(Constants.SGW_STATUS_MEM_USED_TODAY_KEY + sgwId);
				Long[] yesdayMemFreeSGWStatus = redisTemplate.opsForValue().get(Constants.SGW_STATUS_MEM_FREE_YESTERDAY_KEY + sgwId);
				Long[] yesdayMemUsedSGWStatus = redisTemplate.opsForValue().get(Constants.SGW_STATUS_MEM_USED_YESTERDAY_KEY + sgwId);
				if (todayMemFreeSGWStatus != null) {
					redisTemplate.opsForValue().set(Constants.SGW_STATUS_MEM_FREE_YESTERDAY_KEY + sgwId, todayMemFreeSGWStatus);
					logger.info("shift todayMemFreeSGWStatus for sgwId {} at {} with data {}", sgwId, formatter.format(now), todayMemFreeSGWStatus);
				}
				
				if (todayMemUsedSGWStatus != null) {
					redisTemplate.opsForValue().set(Constants.SGW_STATUS_MEM_USED_YESTERDAY_KEY + sgwId, todayMemUsedSGWStatus);
					logger.info("shift todayMemUsedSGWStatus for sgwId {} at {} with data {}", sgwId, formatter.format(now), todayMemUsedSGWStatus);
				}

				if (yesdayMemFreeSGWStatus != null) {
					redisTemplate.opsForValue().set(Constants.SGW_STATUS_MEM_FREE_BEFORE_YESTERDAY_KEY + sgwId, yesdayMemFreeSGWStatus);
					logger.info("shift yesdayMemFreeSGWStatus for sgwId {} at {} with data {}", sgwId, formatter.format(now), yesdayMemFreeSGWStatus);
				}
				
				if (yesdayMemUsedSGWStatus != null) {
					redisTemplate.opsForValue().set(Constants.SGW_STATUS_MEM_USED_BEFORE_YESTERDAY_KEY + sgwId, yesdayMemUsedSGWStatus);
					logger.info("shift yesdayMemUsedSGWStatus for sgwId {} at {} with data {}", sgwId, formatter.format(now), yesdayMemUsedSGWStatus);
				}
				redisTemplate.opsForValue().set(Constants.SGW_STATUS_MEM_FREE_TODAY_KEY + sgwId, new Long[1]);
				redisTemplate.opsForValue().set(Constants.SGW_STATUS_MEM_USED_TODAY_KEY + sgwId, new Long[1]);
			}
			logger.info("end shift mem status data for regionId {} at {} ", regionId, formatter.format(now));
		}
	}

	private void calcSGWStatusDisk(Long regionId, LocalDateTime now) {
		int hoursInMinutes = now.getHour() * 60;
		int minutes = now.getMinute();
		Set<Long> sgwIds = sgwStatusRepository.findAllSGWSIds(regionId);
		for (Long sgwId : sgwIds) {
			long numOfCalcNeeded = (long) Math.floor((hoursInMinutes + minutes) / checkIntervalMinutes);
			Long[] sgwStatusDiskFree = redisTemplate.opsForValue().get(Constants.SGW_STATUS_DISK_FREE_TODAY_KEY + sgwId);
			Long[] sgwStatusDiskUsed = redisTemplate.opsForValue().get(Constants.SGW_STATUS_DISK_USED_TODAY_KEY + sgwId);
			if (sgwStatusDiskFree == null) {
				sgwStatusDiskFree = new Long[(int)numOfCalcNeeded];
			} else {
				sgwStatusDiskFree = Arrays.copyOf(sgwStatusDiskFree, (int)numOfCalcNeeded);
			}
			
			if (sgwStatusDiskUsed == null) {
				sgwStatusDiskUsed = new Long[(int)numOfCalcNeeded];
			} else {
				sgwStatusDiskUsed = Arrays.copyOf(sgwStatusDiskUsed, (int)numOfCalcNeeded);
			}
			
			long notNullNum = Arrays.asList(sgwStatusDiskFree).stream().filter(c -> c != null).count();
			long tempNotNullNum = notNullNum;
			while (numOfCalcNeeded > notNullNum) {
				LocalDateTime start = now.minusMinutes(checkIntervalMinutes * numOfCalcNeeded);
				LocalDateTime end =  now.minusMinutes(checkIntervalMinutes * (numOfCalcNeeded - 1));
				Long timestampStart = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(start).getTime());
				Long timestampEnd = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(end).getTime());
				Optional<SGWStatus> sgwStatus = sgwStatusRepository.findSGWStatus(sgwId, timestampStart, timestampEnd)
																			.stream().max(new Comparator<SGWStatus>() {
																				@Override  
																	            public int compare(SGWStatus o1, SGWStatus o2) {  
																	                 return o1.compareTo(o2);  
																	            }  
																			});
				if (sgwStatus.isPresent()) {
					sgwStatusDiskFree[(int) tempNotNullNum] = (sgwStatus.get().getDiskFree() != 0L ? (long) Math.floor(sgwStatus.get().getDiskFree() / (1024 * 1024 * 1024)) : sgwStatus.get().getDiskFree());
					sgwStatusDiskUsed[(int) tempNotNullNum] = (sgwStatus.get().getDiskUsed() != 0L ? (long) Math.floor(sgwStatus.get().getDiskUsed() / (1024 * 1024 * 1024)) : sgwStatus.get().getDiskUsed());
					
				}
				tempNotNullNum++;
				numOfCalcNeeded--;
			}
			redisTemplate.opsForValue().set(Constants.SGW_STATUS_DISK_FREE_TODAY_KEY + sgwId, sgwStatusDiskFree);
			redisTemplate.opsForValue().set(Constants.SGW_STATUS_DISK_USED_TODAY_KEY + sgwId, sgwStatusDiskUsed);
		}
	}
	
	private void recalcSGWStatusDisk(Long regionId) {
		Set<Long> sgwIds = sgwStatusRepository.findAllSGWSIds(regionId);
		for (Long sgwId : sgwIds) {
			Long[] sgwStatusYesterdayDiskFree = new Long[288];
			Long[] sgwStatusYesterdayDiskUsed = new Long[288];
			
			Long[] sgwStatusBeforeYesterdayDiskFree = new Long[288];
			Long[] sgwStatusBeforeYesterdayDiskUsed = new Long[288];
			long numOfCalcNeeded = 288;
			long notNullNum = 0;
			long tempNotNullNum = notNullNum;
			LocalDate today = LocalDate.now();
			LocalDate yesterday = LocalDate.now().minusDays(1);
			LocalDateTime now = today.atStartOfDay();
			while (numOfCalcNeeded > notNullNum) {
				LocalDateTime start = now.minusMinutes(checkIntervalMinutes * numOfCalcNeeded);
				LocalDateTime end =  now.minusMinutes(checkIntervalMinutes * (numOfCalcNeeded - 1));
				Long timestampStart = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(start).getTime());
				Long timestampEnd = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(end).getTime());
				Optional<SGWStatus> sgwStatus = sgwStatusRepository.findSGWStatus(sgwId, timestampStart, timestampEnd)
																			.stream().max(new Comparator<SGWStatus>() {
																				@Override  
																	            public int compare(SGWStatus o1, SGWStatus o2) {  
																	                 return o1.compareTo(o2);  
																	            }  
																			});
				if (sgwStatus.isPresent()) {
					sgwStatusYesterdayDiskFree[(int) tempNotNullNum] = (sgwStatus.get().getDiskFree() != 0L ? (long) Math.floor(sgwStatus.get().getDiskFree() / (1024 * 1024 * 1024)) : sgwStatus.get().getDiskFree());
					sgwStatusYesterdayDiskUsed[(int) tempNotNullNum] = (sgwStatus.get().getDiskUsed() != 0L ? (long) Math.floor(sgwStatus.get().getDiskUsed() / (1024 * 1024 * 1024)) : sgwStatus.get().getDiskUsed());
					
				}
				tempNotNullNum++;
				numOfCalcNeeded--;
			}
			redisTemplate.opsForValue().set(Constants.SGW_STATUS_DISK_FREE_YESTERDAY_KEY + sgwId, sgwStatusYesterdayDiskFree);
			redisTemplate.opsForValue().set(Constants.SGW_STATUS_DISK_USED_YESTERDAY_KEY + sgwId, sgwStatusYesterdayDiskUsed);
			
			numOfCalcNeeded = 288;
			notNullNum = 0;
			tempNotNullNum = notNullNum;
			now = yesterday.atStartOfDay();
			while (numOfCalcNeeded > notNullNum) {
				LocalDateTime start = now.minusMinutes(checkIntervalMinutes * numOfCalcNeeded);
				LocalDateTime end =  now.minusMinutes(checkIntervalMinutes * (numOfCalcNeeded - 1));
				Long timestampStart = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(start).getTime());
				Long timestampEnd = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(end).getTime());
				Optional<SGWStatus> sgwStatus = sgwStatusRepository.findSGWStatus(sgwId, timestampStart, timestampEnd)
																			.stream().max(new Comparator<SGWStatus>() {
																				@Override  
																	            public int compare(SGWStatus o1, SGWStatus o2) {  
																	                 return o1.compareTo(o2);  
																	            }  
																			});
				if (sgwStatus.isPresent()) {
					sgwStatusBeforeYesterdayDiskFree[(int) tempNotNullNum] = (sgwStatus.get().getDiskFree() != 0L ? (long) Math.floor(sgwStatus.get().getDiskFree() / (1024 * 1024 * 1024)) : sgwStatus.get().getDiskFree());
					sgwStatusBeforeYesterdayDiskUsed[(int) tempNotNullNum] = (sgwStatus.get().getDiskUsed() != 0L ? (long) Math.floor(sgwStatus.get().getDiskUsed() / (1024 * 1024 * 1024)) : sgwStatus.get().getDiskUsed());
					
				}
				tempNotNullNum++;
				numOfCalcNeeded--;
			}
			redisTemplate.opsForValue().set(Constants.SGW_STATUS_DISK_FREE_BEFORE_YESTERDAY_KEY + sgwId, sgwStatusBeforeYesterdayDiskFree);
			redisTemplate.opsForValue().set(Constants.SGW_STATUS_DISK_USED_BEFORE_YESTERDAY_KEY + sgwId, sgwStatusBeforeYesterdayDiskUsed);
		}
	}
	
	private void calcSGWStatusNet(Long regionId, LocalDateTime now) {
		int hoursInMinutes = now.getHour() * 60;
		int minutes = now.getMinute();
		Set<Long> sgwIds = sgwStatusRepository.findAllSGWSIds(regionId);
		for (Long sgwId : sgwIds) {
			long numOfCalcNeeded = (long) Math.floor((hoursInMinutes + minutes) / checkIntervalMinutes);
			Long[] sgwStatusNetIn = redisTemplate.opsForValue().get(Constants.SGW_STATUS_NET_IN_TODAY_KEY + sgwId);
			Long[] sgwStatusNetOut = redisTemplate.opsForValue().get(Constants.SGW_STATUS_NET_OUT_TODAY_KEY + sgwId);
			if (sgwStatusNetIn == null) {
				sgwStatusNetIn = new Long[(int)numOfCalcNeeded];
			} else {
				sgwStatusNetIn = Arrays.copyOf(sgwStatusNetIn, (int)numOfCalcNeeded);
			}
			
			if (sgwStatusNetOut == null) {
				sgwStatusNetOut = new Long[(int)numOfCalcNeeded];
			} else {
				sgwStatusNetOut = Arrays.copyOf(sgwStatusNetOut, (int)numOfCalcNeeded);
			}
			
			long notNullNum = Arrays.asList(sgwStatusNetIn).stream().filter(c -> c != null).count();
			long tempNotNullNum = notNullNum;
			while (numOfCalcNeeded > notNullNum) {
				LocalDateTime start = now.minusMinutes(checkIntervalMinutes * numOfCalcNeeded);
				LocalDateTime end =  now.minusMinutes(checkIntervalMinutes * (numOfCalcNeeded - 1));
				Long timestampStart = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(start).getTime());
				Long timestampEnd = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(end).getTime());
				Optional<SGWStatus> sgwStatus = sgwStatusRepository.findSGWStatus(sgwId, timestampStart, timestampEnd)
																			.stream().max(new Comparator<SGWStatus>() {
																				@Override  
																	            public int compare(SGWStatus o1, SGWStatus o2) {  
																	                 return o1.compareTo(o2);  
																	            }  
																			});
				if (sgwStatus.isPresent()) {
					sgwStatusNetIn[(int) tempNotNullNum] = (sgwStatus.get().getNetioInput() != 0L ? (long) Math.floor(sgwStatus.get().getNetioInput() / (1024 * 1024)) : sgwStatus.get().getNetioInput());
					sgwStatusNetOut[(int) tempNotNullNum] = (sgwStatus.get().getNetioOutput() != 0L ? (long) Math.floor(sgwStatus.get().getNetioOutput() / (1024 * 1024)) : sgwStatus.get().getNetioOutput());
					
				}
				tempNotNullNum++;
				numOfCalcNeeded--;
			}
			redisTemplate.opsForValue().set(Constants.SGW_STATUS_NET_IN_TODAY_KEY + sgwId, sgwStatusNetIn);
			redisTemplate.opsForValue().set(Constants.SGW_STATUS_NET_OUT_TODAY_KEY + sgwId, sgwStatusNetOut);
		}
	}
	
	public void doRecalc(Long regionId) {
		logger.info("Start Recalc SGWData status with region id {} job at {} ", regionId,  formatter.format(LocalDateTime.now()));
		recalcSGWStatusDisk(regionId);
		recalcSGWStatusNETCPUMem(regionId);
		logger.info("End Recalc SGWData status with region id {} at {} ", regionId, formatter.format(LocalDateTime.now()));
	}
	
	private void recalcSGWStatusNETCPUMem(Long regionId) {
		Set<Long> sgwIds = sgwStatusRepository.findAllSGWSIds(regionId);
		for (Long sgwId : sgwIds) {
			Long[] sgwStatusYesterdayNetIn = new Long[288];
			Long[] sgwStatusYesterdayNetOut = new Long[288];
			
			Long[] sgwStatusBeforeYesterdayNetIn = new Long[288];
			Long[] sgwStatusBeforeYesterdayNetOut = new Long[288];
			
			Long[] sgwStatusYesterdayMemUsed = new Long[288];
			Long[] sgwStatusYesterdayMemFree = new Long[288];
			
			Long[] sgwStatusBeforeYesterdayMemUsed = new Long[288];
			Long[] sgwStatusBeforeYesterdayMemFree = new Long[288];
			
			Long[] sgwStatusYesterdayCPU = new Long[288];
			Long[] sgwStatusBeforeYesterdayCPU = new Long[288];
			long numOfCalcNeeded = 288;
			long notNullNum = 0;
			long tempNotNullNum = notNullNum;
			LocalDate today = LocalDate.now();
			LocalDate yesterday = LocalDate.now().minusDays(1);
			LocalDateTime now = today.atStartOfDay();
			while (numOfCalcNeeded > notNullNum) {
				LocalDateTime start = now.minusMinutes(checkIntervalMinutes * numOfCalcNeeded);
				LocalDateTime end =  now.minusMinutes(checkIntervalMinutes * (numOfCalcNeeded - 1));
				Long timestampStart = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(start).getTime());
				Long timestampEnd = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(end).getTime());
				List<SGWStatus> sgwStatus = sgwStatusRepository.findSGWStatus(sgwId, timestampStart, timestampEnd);
				if (!CollectionUtils.isEmpty(sgwStatus)) {
					Long cpu= (long) sgwStatus.stream().mapToLong(SGWStatus::getCpuPercent).average().getAsDouble();
					sgwStatusYesterdayCPU[(int) tempNotNullNum] = cpu;
					Long memFree= (long) sgwStatus.stream().mapToLong(SGWStatus::getMemFree).average().getAsDouble();
					sgwStatusYesterdayMemFree[(int) tempNotNullNum] = (memFree != 0L ? (long) Math.floor(memFree / (1024 * 1024)) :memFree);
					Long memUsed= (long) sgwStatus.stream().mapToLong(SGWStatus::getMemUsed).average().getAsDouble();
					sgwStatusYesterdayMemUsed[(int) tempNotNullNum] = (memUsed != 0L ? (long) Math.floor(memUsed / (1024 * 1024)) :memUsed);
					
					Long netIn= (long) sgwStatus.stream().mapToLong(SGWStatus::getNetioInput).average().getAsDouble();
					sgwStatusYesterdayNetIn[(int) tempNotNullNum] = (netIn != 0L ? (long) Math.floor(netIn / (1024 * 1024)) :netIn);
					Long netOut= (long) sgwStatus.stream().mapToLong(SGWStatus::getNetioOutput).average().getAsDouble();
					sgwStatusYesterdayNetOut[(int) tempNotNullNum] = (netOut != 0L ? (long) Math.floor(netOut / (1024 * 1024)) :netOut);
				}
				tempNotNullNum++;
				numOfCalcNeeded--;
			}
			redisTemplate.opsForValue().set(Constants.SGW_STATUS_CPU_YESTERDAY_KEY + sgwId, sgwStatusYesterdayCPU);
			redisTemplate.opsForValue().set(Constants.SGW_STATUS_MEM_FREE_YESTERDAY_KEY + sgwId, sgwStatusYesterdayMemFree);
			redisTemplate.opsForValue().set(Constants.SGW_STATUS_MEM_USED_YESTERDAY_KEY + sgwId, sgwStatusYesterdayMemUsed);
			redisTemplate.opsForValue().set(Constants.SGW_STATUS_NET_IN_YESTERDAY_KEY + sgwId, sgwStatusYesterdayNetIn);
			redisTemplate.opsForValue().set(Constants.SGW_STATUS_NET_OUT_YESTERDAY_KEY + sgwId, sgwStatusYesterdayNetOut);
			
			now = yesterday.atStartOfDay();
			numOfCalcNeeded = 288;
			notNullNum = 0;
			tempNotNullNum = notNullNum;
			while (numOfCalcNeeded > notNullNum) {
				LocalDateTime start = now.minusMinutes(checkIntervalMinutes * numOfCalcNeeded);
				LocalDateTime end =  now.minusMinutes(checkIntervalMinutes * (numOfCalcNeeded - 1));
				Long timestampStart = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(start).getTime());
				Long timestampEnd = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(end).getTime());
				List<SGWStatus> sgwStatus = sgwStatusRepository.findSGWStatus(sgwId, timestampStart, timestampEnd);
				if (!CollectionUtils.isEmpty(sgwStatus)) {
					Long cpu= (long) sgwStatus.stream().mapToLong(SGWStatus::getCpuPercent).average().getAsDouble();
					sgwStatusBeforeYesterdayCPU[(int) tempNotNullNum] = cpu;
					Long memFree= (long) sgwStatus.stream().mapToLong(SGWStatus::getMemFree).average().getAsDouble();
					sgwStatusBeforeYesterdayMemFree[(int) tempNotNullNum] = (memFree != 0L ? (long) Math.floor(memFree / (1024 * 1024)) :memFree);
					Long memUsed= (long) sgwStatus.stream().mapToLong(SGWStatus::getMemUsed).average().getAsDouble();
					sgwStatusBeforeYesterdayMemUsed[(int) tempNotNullNum] = (memUsed != 0L ? (long) Math.floor(memUsed / (1024 * 1024)) :memUsed);
					
					Long netIn= (long) sgwStatus.stream().mapToLong(SGWStatus::getNetioInput).average().getAsDouble();
					sgwStatusBeforeYesterdayNetIn[(int) tempNotNullNum] = (netIn != 0L ? (long) Math.floor(netIn / (1024 * 1024)) :netIn);
					Long netOut= (long) sgwStatus.stream().mapToLong(SGWStatus::getNetioOutput).average().getAsDouble();
					sgwStatusBeforeYesterdayNetOut[(int) tempNotNullNum] = (netOut != 0L ? (long) Math.floor(netOut / (1024 * 1024)) :netOut);
				}
				tempNotNullNum++;
				numOfCalcNeeded--;
			}
			redisTemplate.opsForValue().set(Constants.SGW_STATUS_CPU_BEFORE_YESTERDAY_KEY + sgwId, sgwStatusBeforeYesterdayCPU);
			redisTemplate.opsForValue().set(Constants.SGW_STATUS_MEM_FREE_BEFORE_YESTERDAY_KEY + sgwId, sgwStatusBeforeYesterdayMemFree);
			redisTemplate.opsForValue().set(Constants.SGW_STATUS_MEM_USED_BEFORE_YESTERDAY_KEY + sgwId, sgwStatusBeforeYesterdayMemUsed);
			redisTemplate.opsForValue().set(Constants.SGW_STATUS_NET_IN_BEFORE_YESTERDAY_KEY + sgwId, sgwStatusBeforeYesterdayNetIn);
			redisTemplate.opsForValue().set(Constants.SGW_STATUS_NET_OUT_BEFORE_YESTERDAY_KEY + sgwId, sgwStatusBeforeYesterdayNetOut);
		}
	}
	
	private void calcSGWStatusCPUNETMem(Long regionId, LocalDateTime now) {
		int hoursInMinutes = now.getHour() * 60;
		int minutes = now.getMinute();
		Set<Long> sgwIds = sgwStatusRepository.findAllSGWSIds(regionId);
		for (Long sgwId : sgwIds) {
			long numOfCalcNeeded = (long) Math.floor((hoursInMinutes + minutes) / checkIntervalMinutes) + 1L;
			Long[] sgwStatusCPU = redisTemplate.opsForValue().get(Constants.SGW_STATUS_CPU_TODAY_KEY + sgwId);
			if (sgwStatusCPU == null) {
				sgwStatusCPU = new Long[(int)numOfCalcNeeded];
			} else {
				sgwStatusCPU = Arrays.copyOf(sgwStatusCPU, (int)numOfCalcNeeded);
			}
			Long[] sgwStatusNetIn = redisTemplate.opsForValue().get(Constants.SGW_STATUS_NET_IN_TODAY_KEY + sgwId);
			Long[] sgwStatusNetOut = redisTemplate.opsForValue().get(Constants.SGW_STATUS_NET_OUT_TODAY_KEY + sgwId);
			if (sgwStatusNetIn == null) {
				sgwStatusNetIn = new Long[(int)numOfCalcNeeded];
			} else {
				sgwStatusNetIn = Arrays.copyOf(sgwStatusNetIn, (int)numOfCalcNeeded);
			}
			
			if (sgwStatusNetOut == null) {
				sgwStatusNetOut = new Long[(int)numOfCalcNeeded];
			} else {
				sgwStatusNetOut = Arrays.copyOf(sgwStatusNetOut, (int)numOfCalcNeeded);
			}
			Long[] sgwStatusMemFree = redisTemplate.opsForValue().get(Constants.SGW_STATUS_MEM_FREE_TODAY_KEY + sgwId);
			Long[] sgwStatusMemUsed = redisTemplate.opsForValue().get(Constants.SGW_STATUS_MEM_USED_TODAY_KEY + sgwId);
			if (sgwStatusMemFree == null) {
				sgwStatusMemFree = new Long[(int)numOfCalcNeeded];
			} else {
				sgwStatusMemFree = Arrays.copyOf(sgwStatusMemFree, (int)numOfCalcNeeded);
			}
			
			if (sgwStatusMemUsed == null) {
				sgwStatusMemUsed = new Long[(int)numOfCalcNeeded];
			} else {
				sgwStatusMemUsed = Arrays.copyOf(sgwStatusMemUsed, (int)numOfCalcNeeded);
			}
			long notNullNum = Arrays.asList(sgwStatusCPU).stream().filter(c -> c != null).count();
			long tempNotNullNum = notNullNum;
			while (numOfCalcNeeded >= notNullNum) {
				LocalDateTime start = now.minusMinutes(checkIntervalMinutes * numOfCalcNeeded);
				LocalDateTime end =  now.minusMinutes(checkIntervalMinutes * (numOfCalcNeeded - 1));
				Long timestampStart = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(start).getTime());
				Long timestampEnd = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(end).getTime());
				List<SGWStatus> sgwStatus = sgwStatusRepository.findSGWStatus(sgwId, timestampStart, timestampEnd);
				if (!CollectionUtils.isEmpty(sgwStatus)) {
					Long cpu= (long) sgwStatus.stream().mapToLong(SGWStatus::getCpuPercent).average().getAsDouble();
					sgwStatusCPU[(int) tempNotNullNum] = cpu;
					Long memFree= (long) sgwStatus.stream().mapToLong(SGWStatus::getMemFree).average().getAsDouble();
					sgwStatusMemFree[(int) tempNotNullNum] = (memFree != 0L ? (long) Math.floor(memFree / (1024 * 1024)) :memFree);
					Long memUsed= (long) sgwStatus.stream().mapToLong(SGWStatus::getMemUsed).average().getAsDouble();
					sgwStatusMemUsed[(int) tempNotNullNum] = (memUsed != 0L ? (long) Math.floor(memUsed / (1024 * 1024)) :memUsed);
					
					Long netIn= (long) sgwStatus.stream().mapToLong(SGWStatus::getNetioInput).average().getAsDouble();
					sgwStatusNetIn[(int) tempNotNullNum] = (netIn != 0L ? (long) Math.floor(netIn / (1024 * 1024)) :netIn);
					Long netOut= (long) sgwStatus.stream().mapToLong(SGWStatus::getNetioOutput).average().getAsDouble();
					sgwStatusNetOut[(int) tempNotNullNum] = (netOut != 0L ? (long) Math.floor(netOut / (1024 * 1024)) :netOut);
				}
				tempNotNullNum++;
				numOfCalcNeeded--;
			}
			redisTemplate.opsForValue().set(Constants.SGW_STATUS_CPU_TODAY_KEY + sgwId, sgwStatusCPU);
			redisTemplate.opsForValue().set(Constants.SGW_STATUS_MEM_FREE_TODAY_KEY + sgwId, sgwStatusMemFree);
			redisTemplate.opsForValue().set(Constants.SGW_STATUS_MEM_USED_TODAY_KEY + sgwId, sgwStatusMemUsed);
			redisTemplate.opsForValue().set(Constants.SGW_STATUS_NET_IN_TODAY_KEY + sgwId, sgwStatusNetIn);
			redisTemplate.opsForValue().set(Constants.SGW_STATUS_NET_OUT_TODAY_KEY + sgwId, sgwStatusNetOut);
		}
	}
	
	private void calcSGWStatusMem(Long regionId, LocalDateTime now) {
		int hoursInMinutes = now.getHour() * 60;
		int minutes = now.getMinute();
		Set<Long> sgwIds = sgwStatusRepository.findAllSGWSIds(regionId);
		for (Long sgwId : sgwIds) {
			long numOfCalcNeeded = (long) Math.floor((hoursInMinutes + minutes) / checkIntervalMinutes);
			Long[] sgwStatusMemFree = redisTemplate.opsForValue().get(Constants.SGW_STATUS_MEM_FREE_TODAY_KEY + sgwId);
			Long[] sgwStatusMemUsed = redisTemplate.opsForValue().get(Constants.SGW_STATUS_MEM_USED_TODAY_KEY + sgwId);
			if (sgwStatusMemFree == null) {
				sgwStatusMemFree = new Long[(int)numOfCalcNeeded];
			} else {
				sgwStatusMemFree = Arrays.copyOf(sgwStatusMemFree, (int)numOfCalcNeeded);
			}
			
			if (sgwStatusMemUsed == null) {
				sgwStatusMemUsed = new Long[(int)numOfCalcNeeded];
			} else {
				sgwStatusMemUsed = Arrays.copyOf(sgwStatusMemUsed, (int)numOfCalcNeeded);
			}
			long notNullNum = Arrays.asList(sgwStatusMemFree).stream().filter(c -> c != null).count();
			long tempNotNullNum = notNullNum;
			while (numOfCalcNeeded > notNullNum) {
				LocalDateTime start = now.minusMinutes(checkIntervalMinutes * numOfCalcNeeded);
				LocalDateTime end =  now.minusMinutes(checkIntervalMinutes * (numOfCalcNeeded - 1));
				Long timestampStart = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(start).getTime());
				Long timestampEnd = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(end).getTime());
				List<SGWStatus> sgwStatus = sgwStatusRepository.findSGWStatus(sgwId, timestampStart, timestampEnd);
				if (!CollectionUtils.isEmpty(sgwStatus)) {
					Long memFree= (long) sgwStatus.stream().mapToLong(SGWStatus::getMemFree).average().getAsDouble();
					sgwStatusMemFree[(int) tempNotNullNum] = (memFree != 0L ? (long) Math.floor(memFree / (1024 * 1024)) :memFree);
					Long memUsed= (long) sgwStatus.stream().mapToLong(SGWStatus::getMemUsed).average().getAsDouble();
					sgwStatusMemUsed[(int) tempNotNullNum] = (memUsed != 0L ? (long) Math.floor(memUsed / (1024 * 1024)) :memUsed);;
				}
				tempNotNullNum++;
				numOfCalcNeeded--;
			}
			redisTemplate.opsForValue().set(Constants.SGW_STATUS_MEM_FREE_TODAY_KEY + sgwId, sgwStatusMemFree);
			redisTemplate.opsForValue().set(Constants.SGW_STATUS_MEM_USED_TODAY_KEY + sgwId, sgwStatusMemUsed);
		}
	}
	
	public JSONObject getInitialDataForDisk(Long sgwId) {
		JSONObject json = new JSONObject();
		Long[] todayDiskFreeStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_FREE_TODAY_KEY, sgwId);
		Long[] todayDiskUsedStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_USED_TODAY_KEY , sgwId);
		Long[] yesdayDiskFreeStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_FREE_YESTERDAY_KEY , sgwId);
		Long[] yesdayDiskUsedStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_USED_YESTERDAY_KEY , sgwId);
		Long[] beforeYesdayDiskFreeStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_FREE_BEFORE_YESTERDAY_KEY , sgwId);
		Long[] beforeYesdayDiskUsedStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_USED_BEFORE_YESTERDAY_KEY , sgwId);
		json.put(Constants.JSON_STATUS_DISK_FREE_TODAY_KEY, todayDiskFreeStatus);
		json.put(Constants.JSON_STATUS_DISK_USED_TODAY_KEY, todayDiskUsedStatus);
		json.put(Constants.JSON_STATUS_DISK_FREE_YESTERDAY_KEY, yesdayDiskFreeStatus);
		json.put(Constants.JSON_STATUS_DISK_USED_YESTERDAY_KEY, yesdayDiskUsedStatus);
		json.put(Constants.JSON_STATUS_DISK_FREE_BEFORE_YESTERDAY_KEY, beforeYesdayDiskFreeStatus);
		json.put(Constants.JSON_STATUS_DISK_USED_BEFORE_YESTERDAY_KEY, beforeYesdayDiskUsedStatus);
		return json;
	}
	
	public JSONObject getLatestDataForDisk(Long sgwId, Long numberOfreceived) {
		JSONObject json = new JSONObject();
		Long[] todayDiskFreeStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_FREE_TODAY_KEY, sgwId);
		Long[] todayDiskUsedStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_USED_TODAY_KEY , sgwId);
		json.put(Constants.JSON_STATUS_DISK_FREE_TODAY_KEY, Arrays.asList(todayDiskFreeStatus).stream().skip(numberOfreceived).collect(Collectors.toList()));
		json.put(Constants.JSON_STATUS_DISK_USED_TODAY_KEY, Arrays.asList(todayDiskUsedStatus).stream().skip(numberOfreceived).collect(Collectors.toList()));
		return json;
		
	}
	
	public JSONObject getInitialDataForMem(Long sgwId) {
		JSONObject json = new JSONObject();
		Long[] todayMemFreeStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_MEM_FREE_TODAY_KEY, sgwId);
		Long[] todayMemUsedStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_MEM_USED_TODAY_KEY , sgwId);
		Long[] yesdayMemFreeStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_MEM_FREE_YESTERDAY_KEY , sgwId);
		Long[] yesdayMemUsedStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_MEM_USED_YESTERDAY_KEY , sgwId);
		Long[] beforeYesdayMemFreeStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_MEM_FREE_BEFORE_YESTERDAY_KEY , sgwId);
		Long[] beforeYesdayMemUsedStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_MEM_USED_BEFORE_YESTERDAY_KEY , sgwId);
		json.put(Constants.JSON_STATUS_MEM_FREE_TODAY_KEY, todayMemFreeStatus);
		json.put(Constants.JSON_STATUS_MEM_USED_TODAY_KEY, todayMemUsedStatus);
		json.put(Constants.JSON_STATUS_MEM_FREE_YESTERDAY_KEY, yesdayMemFreeStatus);
		json.put(Constants.JSON_STATUS_MEM_USED_YESTERDAY_KEY, yesdayMemUsedStatus);
		json.put(Constants.JSON_STATUS_MEM_FREE_BEFORE_YESTERDAY_KEY, beforeYesdayMemFreeStatus);
		json.put(Constants.JSON_STATUS_MEM_USED_BEFORE_YESTERDAY_KEY, beforeYesdayMemUsedStatus);
		return json;
	}
	
	public JSONObject getLatestDataForMem(Long sgwId, Long numberOfreceived) {
		JSONObject json = new JSONObject();
		Long[] todayMemFreeStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_MEM_FREE_TODAY_KEY, sgwId);
		Long[] todayMemUsedStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_MEM_USED_TODAY_KEY , sgwId);
		json.put(Constants.JSON_STATUS_MEM_FREE_TODAY_KEY, Arrays.asList(todayMemFreeStatus).stream().skip(numberOfreceived).collect(Collectors.toList()));
		json.put(Constants.JSON_STATUS_MEM_USED_TODAY_KEY, Arrays.asList(todayMemUsedStatus).stream().skip(numberOfreceived).collect(Collectors.toList()));
		return json;
		
	}
	
	public JSONObject getInitialDataForNet(Long sgwId) {
		JSONObject json = new JSONObject();
		Long[] todayMemFreeStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_NET_IN_TODAY_KEY, sgwId);
		Long[] todayMemUsedStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_NET_OUT_TODAY_KEY , sgwId);
		Long[] yesdayMemFreeStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_NET_IN_YESTERDAY_KEY , sgwId);
		Long[] yesdayMemUsedStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_NET_OUT_YESTERDAY_KEY , sgwId);
		Long[] beforeYesdayMemFreeStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_NET_IN_BEFORE_YESTERDAY_KEY , sgwId);
		Long[] beforeYesdayMemUsedStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_NET_OUT_BEFORE_YESTERDAY_KEY , sgwId);
		json.put(Constants.JSON_STATUS_NET_IN_TODAY_KEY, todayMemFreeStatus);
		json.put(Constants.JSON_STATUS_NET_OUT_TODAY_KEY, todayMemUsedStatus);
		json.put(Constants.JSON_STATUS_NET_IN_YESTERDAY_KEY, yesdayMemFreeStatus);
		json.put(Constants.JSON_STATUS_NET_OUT_YESTERDAY_KEY, yesdayMemUsedStatus);
		json.put(Constants.JSON_STATUS_NET_IN_BEFORE_YESTERDAY_KEY, beforeYesdayMemFreeStatus);
		json.put(Constants.JSON_STATUS_NET_OUT_BEFORE_YESTERDAY_KEY, beforeYesdayMemUsedStatus);
		return json;
	}
	
	public JSONObject getLatestDataForNet(Long sgwId, Long numberOfreceived) {
		JSONObject json = new JSONObject();
		Long[] todayMemFreeStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_NET_IN_TODAY_KEY, sgwId);
		Long[] todayMemUsedStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_NET_OUT_TODAY_KEY , sgwId);
		json.put(Constants.JSON_STATUS_NET_IN_TODAY_KEY, Arrays.asList(todayMemFreeStatus).stream().skip(numberOfreceived).collect(Collectors.toList()));
		json.put(Constants.JSON_STATUS_NET_OUT_TODAY_KEY, Arrays.asList(todayMemUsedStatus).stream().skip(numberOfreceived).collect(Collectors.toList()));
		return json;
		
	}
	
	public JSONObject getInitialDataForCPU(Long sgwId) {
		JSONObject json = new JSONObject();
		Long[] todayCPUStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_CPU_TODAY_KEY, sgwId);
		Long[] yesdayCPUStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_CPU_YESTERDAY_KEY , sgwId);
		Long[] beforeYesdayCPUStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_CPU_BEFORE_YESTERDAY_KEY , sgwId);
		json.put(Constants.JSON_STATUS_CPU_TODAY_KEY, todayCPUStatus);
		json.put(Constants.JSON_STATUS_CPU_YESTERDAY_KEY, yesdayCPUStatus);
		json.put(Constants.JSON_STATUS_CPU_BEFORE_YESTERDAY_KEY, beforeYesdayCPUStatus);
		return json;
	}
	
	public JSONObject getLatestDataForCPU(Long sgwId, Long numberOfreceived) {
		JSONObject json = new JSONObject();
		Long[] todayCPUStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_CPU_TODAY_KEY, sgwId);
		json.put(Constants.JSON_STATUS_CPU_TODAY_KEY, Arrays.asList(todayCPUStatus).stream().skip(numberOfreceived).collect(Collectors.toList()));
		return json;
		
	}
	
	public JSONObject getInitialData(Long sgwId) {
		Long [] diskArray = Arrays.copyOfRange(ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_USED_YESTERDAY_KEY , sgwId), 
				(ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_USED_YESTERDAY_KEY , sgwId).length - 100) < 0 ? 0 : (ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_USED_YESTERDAY_KEY , sgwId).length - 100), 
				ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_USED_YESTERDAY_KEY , sgwId).length);
		System.arraycopy(ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_USED_TODAY_KEY , sgwId),
						((ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_USED_TODAY_KEY , sgwId).length - 100) < 0 ? 0 : (ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_USED_TODAY_KEY , sgwId).length - 100)),
						diskArray,
						((ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_USED_TODAY_KEY , sgwId).length - 100) < 0 ? (100 - ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_USED_TODAY_KEY , sgwId).length) : 0),
						((ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_USED_TODAY_KEY , sgwId).length - 100) > 0 ? 100 : (ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_USED_TODAY_KEY , sgwId).length)));
		redisTemplate.opsForValue().set(Constants.SGW_STATUS_DISK_USED_LATEST_KEY + sgwId, diskArray);				
		JSONObject json = new JSONObject();
		json.put("error_code", 0);
		json.put("data", ImmutableMap.<String, Serializable>builder().put("out", ImmutableList.of(ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_NET_OUT_BEFORE_YESTERDAY_KEY , sgwId),
																								ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_NET_OUT_YESTERDAY_KEY , sgwId),
																								ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_NET_OUT_TODAY_KEY , sgwId)))
																	  .put("in", ImmutableList.of(ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_NET_IN_BEFORE_YESTERDAY_KEY , sgwId),
																				ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_NET_IN_YESTERDAY_KEY , sgwId),
																				ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_NET_IN_TODAY_KEY , sgwId)))
																	  .put("cpu", ImmutableList.of(ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_CPU_BEFORE_YESTERDAY_KEY , sgwId),
																				ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_CPU_YESTERDAY_KEY , sgwId),
																				ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_CPU_TODAY_KEY , sgwId)))
																	  .put("memory", ImmutableList.of(ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_MEM_USED_BEFORE_YESTERDAY_KEY , sgwId),
																				ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_MEM_USED_YESTERDAY_KEY , sgwId),
																				ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_MEM_USED_TODAY_KEY , sgwId)))
																	  .put("disk", ImmutableList.of(ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_USED_LATEST_KEY , sgwId))).build());
		return json;
	}
	
	public JSONObject getLatestData(Long sgwId, Long numberOfreceived) {
		Long [] diskArray = Arrays.copyOfRange(ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_USED_YESTERDAY_KEY , sgwId), 
				(ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_USED_YESTERDAY_KEY , sgwId).length - 100) < 0 ? 0 : (ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_USED_YESTERDAY_KEY , sgwId).length - 100), 
				ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_USED_YESTERDAY_KEY , sgwId).length);
		System.arraycopy(ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_USED_TODAY_KEY , sgwId),
						((ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_USED_TODAY_KEY , sgwId).length - 100) < 0 ? 0 : (ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_USED_TODAY_KEY , sgwId).length - 100)),
						diskArray,
						((ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_USED_TODAY_KEY , sgwId).length - 100) < 0 ? (100 - ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_USED_TODAY_KEY , sgwId).length) : 0),
						((ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_USED_TODAY_KEY , sgwId).length - 100) > 0 ? 100 : (ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_USED_TODAY_KEY , sgwId).length)));
		redisTemplate.opsForValue().set(Constants.SGW_STATUS_DISK_USED_LATEST_KEY + sgwId, diskArray);				
		JSONObject json = new JSONObject();
		json.put("error_code", 0);
		json.put("data", ImmutableMap.<String, Serializable>builder().put("out", ImmutableList.of(getSkipArray(ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_NET_OUT_TODAY_KEY , sgwId), numberOfreceived)))
																								
																	  .put("in", ImmutableList.of(getSkipArray(ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_NET_IN_TODAY_KEY , sgwId), numberOfreceived)))
																	  .put("cpu", ImmutableList.of(getSkipArray(ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_CPU_TODAY_KEY , sgwId), numberOfreceived)))
																	  .put("memory", ImmutableList.of(getSkipArray(ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_MEM_USED_TODAY_KEY , sgwId), numberOfreceived)))
																	  .put("disk", ImmutableList.of(getSkipArray(ServiceUtil.getDataFromCache(redisTemplate, Constants.SGW_STATUS_DISK_USED_LATEST_KEY , sgwId), numberOfreceived))).build());
		return json;
		
	}
	
	private Long[] getSkipArray(Long[] src, Long skip) {
		return Arrays.asList(src).stream().skip(skip).collect(Collectors.toList()).toArray(new Long[0]);
	}
}
