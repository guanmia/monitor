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
import com.anyun100.storage.monitor.metadata_service.repository.MetaDataStatusRepository;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;


@Service
public class MetaDataStatusServiceImpl implements MetaDataStatusService {

	private static final Logger logger = LoggerFactory.getLogger(MetaDataStatusServiceImpl.class);
	
	@Autowired
	private MetaDataStatusRepository metaDataStatusRepository;

	@Autowired
	private RedisTemplate<String, Long[]> redisTemplate;
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

	private final int checkIntervalMinutes = 5;
	
	@Value("${region-center-id:-1}")
	private Long id;
	
	private int[] jobWhichIsDone = new int[(24 * 60) / checkIntervalMinutes];

	@Scheduled(cron = "* 0/5 * * * ?")
	public void doMetaDataStatusTask() {
		if (id == -1L) return;
		LocalDateTime dt = LocalDateTime.now();
		int hoursInMinutes = dt.getHour() * 60;
		int minutes = dt.getMinute();
		int numOfJobNeeded = (int) Math.floor((hoursInMinutes + minutes) / checkIntervalMinutes);
		if (jobWhichIsDone[numOfJobNeeded] != 0 ) {
			logger.info("Metadata status job at {} with object {} is done", formatter.format(dt), this);
			return;
		}
		logger.info("Start MetaData status job at {} with object {}", formatter.format(dt), this);
		countJobRun(dt);
		shiftCacheDataForDisk(id, dt);
		shiftCacheDataForCPU(id, dt);
		shiftCacheDataForMem(id, dt);
		shiftCacheDataForNet(id, dt);
		calcMetaDataStatusDisk(id, dt);
		calcMetaDataStatusCPU(id, dt);
		calcMetaDataStatusMem(id, dt);
		calcMetaDataStatusNet(id, dt);
		logger.info("End MetaData status job at {} with object {} " , formatter.format(LocalDateTime.now()), this);
	}
	
	public void doMetaDataStatusTask(LocalDateTime now) {
		if (id == -1L) return;
		logger.info("Start MetaData status job at " + formatter.format(now));
		shiftCacheDataForDisk(id, now);
		shiftCacheDataForCPU(id, now);
		shiftCacheDataForMem(id, now);
		shiftCacheDataForNet(id, now);
		calcMetaDataStatusDisk(id, now);
		calcMetaDataStatusCPU(id, now);
		calcMetaDataStatusMem(id, now);
		calcMetaDataStatusNet(id, now);
		logger.info("End MetaData status job at " + formatter.format(LocalDateTime.now()));
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
				Long[] todayDiskFreeMetaDataStatus = redisTemplate.opsForValue().get(Constants.METADATA_STATUS_DISK_FREE_TODAY_KEY + regionId);
				Long[] todayDiskUsedMetaDataStatus = redisTemplate.opsForValue().get(Constants.METADATA_STATUS_DISK_USED_TODAY_KEY + regionId);
				Long[] yesdayDiskFreeMetaDataStatus = redisTemplate.opsForValue().get(Constants.METADATA_STATUS_DISK_FREE_YESTERDAY_KEY + regionId);
				Long[] yesdayDiskUsedMetaDataStatus = redisTemplate.opsForValue().get(Constants.METADATA_STATUS_DISK_USED_YESTERDAY_KEY + regionId);
				if (todayDiskFreeMetaDataStatus != null) {
					redisTemplate.opsForValue().set(Constants.METADATA_STATUS_DISK_FREE_YESTERDAY_KEY + regionId, todayDiskFreeMetaDataStatus);
					logger.info("shift todayDiskFreeMetaDataStatus for regionId {} at {} with data {}", regionId, formatter.format(now), todayDiskFreeMetaDataStatus);
				}
				
				if (todayDiskUsedMetaDataStatus != null) {
					redisTemplate.opsForValue().set(Constants.METADATA_STATUS_DISK_USED_YESTERDAY_KEY + regionId, todayDiskUsedMetaDataStatus);
					logger.info("shift todayDiskUsedMetaDataStatus for regionId {} at {} with data {}", regionId, formatter.format(now), todayDiskUsedMetaDataStatus);
				}

				if (yesdayDiskFreeMetaDataStatus != null) {
					redisTemplate.opsForValue().set(Constants.METADATA_STATUS_DISK_FREE_BEFORE_YESTERDAY_KEY + regionId, yesdayDiskFreeMetaDataStatus);
					logger.info("shift yesdayDiskFreeMetaDataStatus for regionId {} at {} with data {}", regionId, formatter.format(now), yesdayDiskFreeMetaDataStatus);
				}
				
				if (yesdayDiskUsedMetaDataStatus != null) {
					redisTemplate.opsForValue().set(Constants.METADATA_STATUS_DISK_USED_BEFORE_YESTERDAY_KEY + regionId, yesdayDiskUsedMetaDataStatus);
					logger.info("shift yesdayDiskUsedMetaDataStatus for regionId {} at {} with data {}", regionId, formatter.format(now), yesdayDiskUsedMetaDataStatus);
				}
				redisTemplate.opsForValue().set(Constants.METADATA_STATUS_DISK_FREE_TODAY_KEY + regionId, new Long[1]);
				redisTemplate.opsForValue().set(Constants.METADATA_STATUS_DISK_USED_TODAY_KEY + regionId, new Long[1]);
				logger.info("end shift disk status data for regionId {} at {} ", regionId, formatter.format(now));
		}
	}
	
	private void shiftCacheDataForNet(Long regionId, LocalDateTime now) {
		if (now.getHour() == 0 && now.getMinute() == 0) {
			logger.info("end shift disk status data for regionId {} at {} ", regionId, formatter.format(now));
				Long[] todayNetInMetaDataStatus = redisTemplate.opsForValue().get(Constants.METADATA_STATUS_NET_IN_TODAY_KEY + regionId);
				Long[] todayNetOutMetaDataStatus = redisTemplate.opsForValue().get(Constants.METADATA_STATUS_NET_OUT_TODAY_KEY + regionId);
				Long[] yesdayNetInMetaDataStatus = redisTemplate.opsForValue().get(Constants.METADATA_STATUS_NET_IN_YESTERDAY_KEY + regionId);
				Long[] yesdayNetOutMetaDataStatus = redisTemplate.opsForValue().get(Constants.METADATA_STATUS_NET_OUT_YESTERDAY_KEY + regionId);
				if (todayNetInMetaDataStatus != null) {
					redisTemplate.opsForValue().set(Constants.METADATA_STATUS_NET_IN_YESTERDAY_KEY + regionId, todayNetInMetaDataStatus);
				}
				
				if (todayNetOutMetaDataStatus != null) {
					redisTemplate.opsForValue().set(Constants.METADATA_STATUS_NET_OUT_YESTERDAY_KEY + regionId, todayNetOutMetaDataStatus);
				}

				if (yesdayNetInMetaDataStatus != null) {
					redisTemplate.opsForValue().set(Constants.METADATA_STATUS_NET_IN_BEFORE_YESTERDAY_KEY + regionId, yesdayNetInMetaDataStatus);
				}
				
				if (yesdayNetOutMetaDataStatus != null) {
					redisTemplate.opsForValue().set(Constants.METADATA_STATUS_NET_OUT_BEFORE_YESTERDAY_KEY + regionId, yesdayNetOutMetaDataStatus);
				}
				redisTemplate.opsForValue().set(Constants.METADATA_STATUS_NET_IN_TODAY_KEY + regionId, new Long[1]);
				redisTemplate.opsForValue().set(Constants.METADATA_STATUS_NET_OUT_TODAY_KEY + regionId, new Long[1]);
				logger.info("end shift net status data for regionId {} at {} ", regionId, formatter.format(now));
			}
	}
	
	private void shiftCacheDataForCPU(Long regionId, LocalDateTime now) {
		if (now.getHour() == 0 && now.getMinute() == 0) {
			logger.info("end shift cpu status data for regionId {} at {} ", regionId, formatter.format(now));
			Long[] todayCPUeMetaDataStatus = redisTemplate.opsForValue().get(Constants.METADATA_STATUS_CPU_TODAY_KEY + regionId);
			Long[] yesdayCPUMetaDataStatus = redisTemplate.opsForValue().get(Constants.METADATA_STATUS_CPU_YESTERDAY_KEY + regionId);
			if (todayCPUeMetaDataStatus != null) {
				redisTemplate.opsForValue().set(Constants.METADATA_STATUS_CPU_YESTERDAY_KEY + regionId, todayCPUeMetaDataStatus);
				logger.info("shift todayCPUeMetaDataStatus for regionId {} at {} with data {}", regionId, formatter.format(now), todayCPUeMetaDataStatus);
			}

			if (yesdayCPUMetaDataStatus != null) {
				redisTemplate.opsForValue().set(Constants.METADATA_STATUS_CPU_BEFORE_YESTERDAY_KEY + regionId, yesdayCPUMetaDataStatus);
				logger.info("shift yesdayCPUMetaDataStatus for regionId {} at {} with data {}", regionId, formatter.format(now), yesdayCPUMetaDataStatus);
			}
			
			redisTemplate.opsForValue().set(Constants.METADATA_STATUS_CPU_TODAY_KEY + regionId, new Long[1]);
			logger.info("end shift cpu status data for regionId {} at {} ", regionId, formatter.format(now));
		}
	}
	
	private void shiftCacheDataForMem(Long regionId, LocalDateTime now) {
		if (now.getHour() == 0 && now.getMinute() == 0) {
			logger.info("end shift mem status data for regionId {} at {} ", regionId, formatter.format(now));
			Long[] todayMemFreeMetaDataStatus = redisTemplate.opsForValue().get(Constants.METADATA_STATUS_MEM_FREE_TODAY_KEY + regionId);
			Long[] todayMemUsedMetaDataStatus = redisTemplate.opsForValue().get(Constants.METADATA_STATUS_MEM_USED_TODAY_KEY + regionId);
			Long[] yesdayMemFreeMetaDataStatus = redisTemplate.opsForValue().get(Constants.METADATA_STATUS_MEM_FREE_YESTERDAY_KEY + regionId);
			Long[] yesdayMemUsedMetaDataStatus = redisTemplate.opsForValue().get(Constants.METADATA_STATUS_MEM_USED_YESTERDAY_KEY + regionId);
			if (todayMemFreeMetaDataStatus != null) {
				redisTemplate.opsForValue().set(Constants.METADATA_STATUS_MEM_FREE_YESTERDAY_KEY + regionId, todayMemFreeMetaDataStatus);
				logger.info("shift todayMemFreeMetaDataStatus for regionId {} at {} with data {}", regionId, formatter.format(now), todayMemFreeMetaDataStatus);
			}
			
			if (todayMemUsedMetaDataStatus != null) {
				redisTemplate.opsForValue().set(Constants.METADATA_STATUS_MEM_USED_YESTERDAY_KEY + regionId, todayMemUsedMetaDataStatus);
				logger.info("shift todayMemUsedMetaDataStatus for regionId {} at {} with data {}", regionId, formatter.format(now), todayMemUsedMetaDataStatus);
			}

			if (yesdayMemFreeMetaDataStatus != null) {
				redisTemplate.opsForValue().set(Constants.METADATA_STATUS_MEM_FREE_BEFORE_YESTERDAY_KEY + regionId, yesdayMemFreeMetaDataStatus);
				logger.info("shift yesdayMemFreeMetaDataStatus for regionId {} at {} with data {}", regionId, formatter.format(now), yesdayMemFreeMetaDataStatus);
			}
			
			if (yesdayMemUsedMetaDataStatus != null) {
				redisTemplate.opsForValue().set(Constants.METADATA_STATUS_MEM_USED_BEFORE_YESTERDAY_KEY + regionId, yesdayMemUsedMetaDataStatus);
				logger.info("shift yesdayMemUsedMetaDataStatus for regionId {} at {} with data {}", regionId, formatter.format(now), yesdayMemUsedMetaDataStatus);
			}
			redisTemplate.opsForValue().set(Constants.METADATA_STATUS_MEM_FREE_TODAY_KEY + regionId, new Long[1]);
			redisTemplate.opsForValue().set(Constants.METADATA_STATUS_MEM_USED_TODAY_KEY + regionId, new Long[1]);
			logger.info("end shift mem status data for regionId {} at {} ", regionId, formatter.format(now));
		}
	}

	private void calcMetaDataStatusDisk(Long regionId, LocalDateTime now) {
		int hoursInMinutes = now.getHour() * 60;
		int minutes = now.getMinute();
		long numOfCalcNeeded = (long) Math.floor((hoursInMinutes + minutes) / checkIntervalMinutes);
		Long[] metaDataStatusDiskFree = redisTemplate.opsForValue().get(Constants.METADATA_STATUS_DISK_FREE_TODAY_KEY + regionId);
		Long[] metaDataStatusDiskUsed = redisTemplate.opsForValue().get(Constants.METADATA_STATUS_DISK_USED_TODAY_KEY + regionId);
		if (metaDataStatusDiskFree == null) {
			metaDataStatusDiskFree = new Long[(int)numOfCalcNeeded];
		} else {
			metaDataStatusDiskFree = Arrays.copyOf(metaDataStatusDiskFree, (int)numOfCalcNeeded);
		}
		
		if (metaDataStatusDiskUsed == null) {
			metaDataStatusDiskUsed = new Long[(int)numOfCalcNeeded];
		} else {
			metaDataStatusDiskUsed = Arrays.copyOf(metaDataStatusDiskUsed, (int)numOfCalcNeeded);
		}
		
		long notNullNum = Arrays.asList(metaDataStatusDiskFree).stream().filter(c -> c != null).count();
		long tempNotNullNum = notNullNum;
		while (numOfCalcNeeded > notNullNum) {
			LocalDateTime start = now.minusMinutes(checkIntervalMinutes * numOfCalcNeeded);
			LocalDateTime end =  now.minusMinutes(checkIntervalMinutes * (numOfCalcNeeded - 1));
			Long timestampStart = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(start).getTime());
			Long timestampEnd = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(end).getTime());
			Optional<MetaDataStatus> metddataStatus = metaDataStatusRepository.findMetadataStatus(regionId, timestampStart, timestampEnd)
																		.stream().max(new Comparator<MetaDataStatus>() {
																			@Override  
																            public int compare(MetaDataStatus o1, MetaDataStatus o2) {  
																                 return o1.compareTo(o2);  
																            }  
																		});
			if (metddataStatus.isPresent()) {
				metaDataStatusDiskFree[(int) tempNotNullNum] = (metddataStatus.get().getDiskFree() != 0L ? (long) Math.floor(metddataStatus.get().getDiskFree() / (1024 * 1024 * 1024)) : metddataStatus.get().getDiskFree());
				metaDataStatusDiskUsed[(int) tempNotNullNum] = (metddataStatus.get().getDiskUsed() != 0L ? (long) Math.floor(metddataStatus.get().getDiskUsed() / (1024 * 1024 * 1024)) : metddataStatus.get().getDiskUsed());
				
			}
			tempNotNullNum++;
			numOfCalcNeeded--;
		}
		redisTemplate.opsForValue().set(Constants.METADATA_STATUS_DISK_FREE_TODAY_KEY + regionId, metaDataStatusDiskFree);
		redisTemplate.opsForValue().set(Constants.METADATA_STATUS_DISK_USED_TODAY_KEY + regionId, metaDataStatusDiskUsed);
	
	}
	
	public void doRecalc(Long regionId) {
		logger.info("Start Recalc MetaData status with region id {} at {}", regionId, formatter.format(LocalDateTime.now()));
		
		recalcMetaDataStatusDisk(regionId);
		recalcMetaDataStatusNetCPUMem(regionId);
		logger.info("End Recalc MetaData status with region id {} at {}", regionId, formatter.format(LocalDateTime.now()));
	}
	
	private void recalcMetaDataStatusDisk(Long regionId) {
		Long[] metaDataStatusYesterdayDiskFree = new Long[288];
		Long[] metaDataStatusYesterdayDiskUsed = new Long[288];
		
		Long[] metaDataStatusBeforeYesterdayDiskFree = new Long[288];
		Long[] metaDataStatusBeforeYesterdayDiskUsed = new Long[288];
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
			Optional<MetaDataStatus> metddataStatus = metaDataStatusRepository.findMetadataStatus(regionId, timestampStart, timestampEnd)
																		.stream().max(new Comparator<MetaDataStatus>() {
																			@Override  
																            public int compare(MetaDataStatus o1, MetaDataStatus o2) {  
																                 return o1.compareTo(o2);  
																            }  
																		});
			if (metddataStatus.isPresent()) {
				metaDataStatusYesterdayDiskFree[(int) tempNotNullNum] = (metddataStatus.get().getDiskFree() != 0L ? (long) Math.floor(metddataStatus.get().getDiskFree() / (1024 * 1024 * 1024)) : metddataStatus.get().getDiskFree());
				metaDataStatusYesterdayDiskUsed[(int) tempNotNullNum] = (metddataStatus.get().getDiskUsed() != 0L ? (long) Math.floor(metddataStatus.get().getDiskUsed() / (1024 * 1024 * 1024)) : metddataStatus.get().getDiskUsed());
				
			}
			tempNotNullNum++;
			numOfCalcNeeded--;
		}
		redisTemplate.opsForValue().set(Constants.METADATA_STATUS_DISK_FREE_YESTERDAY_KEY + regionId, metaDataStatusYesterdayDiskFree);
		redisTemplate.opsForValue().set(Constants.METADATA_STATUS_DISK_USED_YESTERDAY_KEY + regionId, metaDataStatusYesterdayDiskUsed);
		
		now = yesterday.atStartOfDay();
		numOfCalcNeeded = 288;
		notNullNum = 0;
		tempNotNullNum = notNullNum;
		while (numOfCalcNeeded > notNullNum) {
			LocalDateTime start = now.minusMinutes(checkIntervalMinutes * numOfCalcNeeded);
			LocalDateTime end =  now.minusMinutes(checkIntervalMinutes * (numOfCalcNeeded - 1));
			Long timestampStart = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(start).getTime());
			Long timestampEnd = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(end).getTime());
			Optional<MetaDataStatus> metddataStatus = metaDataStatusRepository.findMetadataStatus(regionId, timestampStart, timestampEnd)
																		.stream().max(new Comparator<MetaDataStatus>() {
																			@Override  
																            public int compare(MetaDataStatus o1, MetaDataStatus o2) {  
																                 return o1.compareTo(o2);  
																            }  
																		});
			if (metddataStatus.isPresent()) {
				metaDataStatusBeforeYesterdayDiskFree[(int) tempNotNullNum] = (metddataStatus.get().getDiskFree() != 0L ? (long) Math.floor(metddataStatus.get().getDiskFree() / (1024 * 1024 * 1024)) : metddataStatus.get().getDiskFree());
				metaDataStatusBeforeYesterdayDiskUsed[(int) tempNotNullNum] = (metddataStatus.get().getDiskUsed() != 0L ? (long) Math.floor(metddataStatus.get().getDiskUsed() / (1024 * 1024 * 1024)) : metddataStatus.get().getDiskUsed());
				
			}
			tempNotNullNum++;
			numOfCalcNeeded--;
		}
		redisTemplate.opsForValue().set(Constants.METADATA_STATUS_DISK_FREE_BEFORE_YESTERDAY_KEY + regionId, metaDataStatusBeforeYesterdayDiskFree);
		redisTemplate.opsForValue().set(Constants.METADATA_STATUS_DISK_USED_BEFORE_YESTERDAY_KEY + regionId, metaDataStatusBeforeYesterdayDiskUsed);
	}
	
	private void recalcMetaDataStatusNetCPUMem(Long regionId) {
		Long[] metaDataStatusYesterdayNetIn = new Long[288];
		Long[] metaDataStatusYesterdayNetOut = new Long[288];
		
		Long[] metaDataStatusBeforeYesterdayNetIn = new Long[288];
		Long[] metaDataStatusBeforeYesterdayNetOut = new Long[288];
		
		Long[] metaDataStatusYesterdayMemUsed = new Long[288];
		Long[] metaDataStatusYesterdayMemFree = new Long[288];
		
		Long[] metaDataStatusBeforeYesterdayMemUsed = new Long[288];
		Long[] metaDataStatusBeforeYesterdayMemFree = new Long[288];
		
		Long[] metaDataStatusYesterdayCPU = new Long[288];
		Long[] metaDataStatusBeforeYesterdayCPU = new Long[288];
		long numOfCalcNeeded = 288;
		long notNullNum = 0;
		long tempNotNullNum = notNullNum;
		LocalDate today = LocalDate.now();
		LocalDate yesterday = LocalDate.now().minusDays(1);
		LocalDateTime now = today.atStartOfDay();
		logger.info("Recalc MetaData status with region id {} at {}", regionId, formatter.format(now));
		while (numOfCalcNeeded > notNullNum) {
			LocalDateTime start = now.minusMinutes(checkIntervalMinutes * numOfCalcNeeded);
			LocalDateTime end =  now.minusMinutes(checkIntervalMinutes * (numOfCalcNeeded - 1));
			Long timestampStart = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(start).getTime());
			Long timestampEnd = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(end).getTime());
			List<MetaDataStatus> metadataStatus = metaDataStatusRepository.findMetadataStatus(regionId, timestampStart, timestampEnd);
			if (!CollectionUtils.isEmpty(metadataStatus)) {
				Long cpu= (long) metadataStatus.stream().mapToLong(MetaDataStatus::getCpuPercent).average().getAsDouble();
				metaDataStatusYesterdayCPU[(int) tempNotNullNum] = cpu;
				Long memFree= (long) metadataStatus.stream().mapToLong(MetaDataStatus::getMemFree).average().getAsDouble();
				metaDataStatusYesterdayMemFree[(int) tempNotNullNum] = (memFree != 0L ? (long) Math.floor(memFree / (1024 * 1024)) :memFree);
				Long memUsed= (long) metadataStatus.stream().mapToLong(MetaDataStatus::getMemUsed).average().getAsDouble();
				metaDataStatusYesterdayMemUsed[(int) tempNotNullNum] = (memUsed != 0L ? (long) Math.floor(memUsed / (1024 * 1024)) :memUsed);
				
				Long netIn= (long) metadataStatus.stream().mapToLong(MetaDataStatus::getNetioInput).average().getAsDouble();
				metaDataStatusYesterdayNetIn[(int) tempNotNullNum] = (netIn != 0L ? (long) Math.floor(netIn / (1024 * 1024)) :netIn);
				Long netOut= (long) metadataStatus.stream().mapToLong(MetaDataStatus::getNetioOutput).average().getAsDouble();
				metaDataStatusYesterdayNetOut[(int) tempNotNullNum] = (netOut != 0L ? (long) Math.floor(netOut / (1024 * 1024)) :netOut);
			}
			tempNotNullNum++;
			numOfCalcNeeded--;
		}
		redisTemplate.opsForValue().set(Constants.METADATA_STATUS_CPU_YESTERDAY_KEY + regionId, metaDataStatusYesterdayCPU);
		redisTemplate.opsForValue().set(Constants.METADATA_STATUS_MEM_FREE_YESTERDAY_KEY + regionId, metaDataStatusYesterdayMemFree);
		redisTemplate.opsForValue().set(Constants.METADATA_STATUS_MEM_USED_YESTERDAY_KEY + regionId, metaDataStatusYesterdayMemUsed);
		redisTemplate.opsForValue().set(Constants.METADATA_STATUS_NET_IN_YESTERDAY_KEY + regionId, metaDataStatusYesterdayNetIn);
		redisTemplate.opsForValue().set(Constants.METADATA_STATUS_NET_OUT_YESTERDAY_KEY + regionId, metaDataStatusYesterdayNetOut);
		logger.info("Recalc MetaData metaDataStatusYesterdayCPU with region id {} for {}", regionId, metaDataStatusYesterdayCPU);
		logger.info("Recalc MetaData metaDataStatusYesterdayMemFree with region id {} for {}", regionId, metaDataStatusYesterdayMemFree);
		logger.info("Recalc MetaData metaDataStatusYesterdayNetIn with region id {} for {}", regionId, metaDataStatusYesterdayNetIn);
		logger.info("Recalc MetaData metaDataStatusYesterdayNetOut with region id {} for {}", regionId, metaDataStatusYesterdayNetOut);
		
		now = yesterday.atStartOfDay();
		numOfCalcNeeded = 288;
		notNullNum = 0;
		tempNotNullNum = notNullNum;
		while (numOfCalcNeeded > notNullNum) {
			LocalDateTime start = now.minusMinutes(checkIntervalMinutes * numOfCalcNeeded);
			LocalDateTime end =  now.minusMinutes(checkIntervalMinutes * (numOfCalcNeeded - 1));
			Long timestampStart = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(start).getTime());
			Long timestampEnd = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(end).getTime());
			List<MetaDataStatus> metadataStatus = metaDataStatusRepository.findMetadataStatus(regionId, timestampStart, timestampEnd);
			if (!CollectionUtils.isEmpty(metadataStatus)) {
				Long cpu= (long) metadataStatus.stream().mapToLong(MetaDataStatus::getCpuPercent).average().getAsDouble();
				metaDataStatusBeforeYesterdayCPU[(int) tempNotNullNum] = cpu;
				Long memFree= (long) metadataStatus.stream().mapToLong(MetaDataStatus::getMemFree).average().getAsDouble();
				metaDataStatusBeforeYesterdayMemFree[(int) tempNotNullNum] = (memFree != 0L ? (long) Math.floor(memFree / (1024 * 1024)) :memFree);
				Long memUsed= (long) metadataStatus.stream().mapToLong(MetaDataStatus::getMemUsed).average().getAsDouble();
				metaDataStatusBeforeYesterdayMemUsed[(int) tempNotNullNum] = (memUsed != 0L ? (long) Math.floor(memUsed / (1024 * 1024)) :memUsed);
				
				Long netIn= (long) metadataStatus.stream().mapToLong(MetaDataStatus::getNetioInput).average().getAsDouble();
				metaDataStatusBeforeYesterdayNetIn[(int) tempNotNullNum] = (netIn != 0L ? (long) Math.floor(netIn / (1024 * 1024)) :netIn);
				Long netOut= (long) metadataStatus.stream().mapToLong(MetaDataStatus::getNetioOutput).average().getAsDouble();
				metaDataStatusBeforeYesterdayNetOut[(int) tempNotNullNum] = (netOut != 0L ? (long) Math.floor(netOut / (1024 * 1024)) :netOut);
			}
			tempNotNullNum++;
			numOfCalcNeeded--;
		}
		redisTemplate.opsForValue().set(Constants.METADATA_STATUS_CPU_BEFORE_YESTERDAY_KEY + regionId, metaDataStatusBeforeYesterdayCPU);
		redisTemplate.opsForValue().set(Constants.METADATA_STATUS_MEM_FREE_BEFORE_YESTERDAY_KEY + regionId, metaDataStatusBeforeYesterdayMemFree);
		redisTemplate.opsForValue().set(Constants.METADATA_STATUS_MEM_USED_BEFORE_YESTERDAY_KEY + regionId, metaDataStatusBeforeYesterdayMemUsed);
		redisTemplate.opsForValue().set(Constants.METADATA_STATUS_NET_IN_BEFORE_YESTERDAY_KEY + regionId, metaDataStatusBeforeYesterdayNetIn);
		redisTemplate.opsForValue().set(Constants.METADATA_STATUS_NET_OUT_BEFORE_YESTERDAY_KEY + regionId, metaDataStatusBeforeYesterdayNetOut);
	}
	
	private void calcMetaDataStatusNet(Long regionId, LocalDateTime now) {
		int hoursInMinutes = now.getHour() * 60;
		int minutes = now.getMinute();
		long numOfCalcNeeded = (long) Math.floor((hoursInMinutes + minutes) / checkIntervalMinutes);
		Long[] metaDataStatusNetIn = redisTemplate.opsForValue().get(Constants.METADATA_STATUS_NET_IN_TODAY_KEY + regionId);
		Long[] metaDataStatusNetOut = redisTemplate.opsForValue().get(Constants.METADATA_STATUS_NET_OUT_TODAY_KEY + regionId);
		if (metaDataStatusNetIn == null) {
			metaDataStatusNetIn = new Long[(int)numOfCalcNeeded];
		} else {
			metaDataStatusNetIn = Arrays.copyOf(metaDataStatusNetIn, (int)numOfCalcNeeded);
		}
		
		if (metaDataStatusNetOut == null) {
			metaDataStatusNetOut = new Long[(int)numOfCalcNeeded];
		} else {
			metaDataStatusNetOut = Arrays.copyOf(metaDataStatusNetOut, (int)numOfCalcNeeded);
		}
		
		long notNullNum = Arrays.asList(metaDataStatusNetIn).stream().filter(c -> c != null).count();
		long tempNotNullNum = notNullNum;
		while (numOfCalcNeeded > notNullNum) {
			LocalDateTime start = now.minusMinutes(checkIntervalMinutes * numOfCalcNeeded);
			LocalDateTime end =  now.minusMinutes(checkIntervalMinutes * (numOfCalcNeeded - 1));
			Long timestampStart = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(start).getTime());
			Long timestampEnd = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(end).getTime());
			List<MetaDataStatus> metadataStatus = metaDataStatusRepository.findMetadataStatus(regionId, timestampStart, timestampEnd);
			if (!CollectionUtils.isEmpty(metadataStatus)) {
				Long netIn= (long) metadataStatus.stream().mapToLong(MetaDataStatus::getNetioInput).average().getAsDouble();
				metaDataStatusNetIn[(int) tempNotNullNum] = (netIn != 0L ? (long) Math.floor(netIn / (1024 * 1024)) :netIn);
				Long netOut= (long) metadataStatus.stream().mapToLong(MetaDataStatus::getNetioOutput).average().getAsDouble();
				metaDataStatusNetOut[(int) tempNotNullNum] = (netOut != 0L ? (long) Math.floor(netOut / (1024 * 1024)) :netOut);
				
			}
			tempNotNullNum++;
			numOfCalcNeeded--;
		}
		redisTemplate.opsForValue().set(Constants.METADATA_STATUS_NET_IN_TODAY_KEY + regionId, metaDataStatusNetIn);
		redisTemplate.opsForValue().set(Constants.METADATA_STATUS_NET_OUT_TODAY_KEY + regionId, metaDataStatusNetOut);
	}
	
	private void calcMetaDataStatusCPU(Long regionId, LocalDateTime now) {
		int hoursInMinutes = now.getHour() * 60;
		int minutes = now.getMinute();
		long numOfCalcNeeded = (long) Math.floor((hoursInMinutes + minutes) / checkIntervalMinutes);
		Long[] metaDataStatusCPU = redisTemplate.opsForValue().get(Constants.METADATA_STATUS_CPU_TODAY_KEY + regionId);
		if (metaDataStatusCPU == null) {
			metaDataStatusCPU = new Long[(int)numOfCalcNeeded];
		} else {
			metaDataStatusCPU = Arrays.copyOf(metaDataStatusCPU, (int)numOfCalcNeeded);
		}
		long notNullNum = Arrays.asList(metaDataStatusCPU).stream().filter(c -> c != null).count();
		long tempNotNullNum = notNullNum;
		while (numOfCalcNeeded > notNullNum) {
			LocalDateTime start = now.minusMinutes(checkIntervalMinutes * numOfCalcNeeded);
			LocalDateTime end =  now.minusMinutes(checkIntervalMinutes * (numOfCalcNeeded - 1));
			Long timestampStart = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(start).getTime());
			Long timestampEnd = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(end).getTime());
			List<MetaDataStatus> metadataStatus = metaDataStatusRepository.findMetadataStatus(regionId, timestampStart, timestampEnd);
			if (!CollectionUtils.isEmpty(metadataStatus)) {
				Long cpu= (long) metadataStatus.stream().mapToLong(MetaDataStatus::getCpuPercent).average().getAsDouble();
				metaDataStatusCPU[(int) tempNotNullNum] = cpu;
			}
			tempNotNullNum++;
			numOfCalcNeeded--;
		}
		redisTemplate.opsForValue().set(Constants.METADATA_STATUS_CPU_TODAY_KEY + regionId, metaDataStatusCPU);
		
	}
	
	private void calcMetaDataStatusMem(Long regionId, LocalDateTime now) {
		int hoursInMinutes = now.getHour() * 60;
		int minutes = now.getMinute();
		long numOfCalcNeeded = (long) Math.floor((hoursInMinutes + minutes) / checkIntervalMinutes);
		Long[] metaDataStatusMemFree = redisTemplate.opsForValue().get(Constants.METADATA_STATUS_MEM_FREE_TODAY_KEY + regionId);
		Long[] metaDataStatusMemUsed = redisTemplate.opsForValue().get(Constants.METADATA_STATUS_MEM_USED_TODAY_KEY + regionId);
		if (metaDataStatusMemFree == null) {
			metaDataStatusMemFree = new Long[(int)numOfCalcNeeded];
		} else {
			metaDataStatusMemFree = Arrays.copyOf(metaDataStatusMemFree, (int)numOfCalcNeeded);
		}
		
		if (metaDataStatusMemUsed == null) {
			metaDataStatusMemUsed = new Long[(int)numOfCalcNeeded];
		} else {
			metaDataStatusMemUsed = Arrays.copyOf(metaDataStatusMemUsed, (int)numOfCalcNeeded);
		}
		long notNullNum = Arrays.asList(metaDataStatusMemFree).stream().filter(c -> c != null).count();
		long tempNotNullNum = notNullNum;
		while (numOfCalcNeeded > notNullNum) {
			LocalDateTime start = now.minusMinutes(checkIntervalMinutes * numOfCalcNeeded);
			LocalDateTime end =  now.minusMinutes(checkIntervalMinutes * (numOfCalcNeeded - 1));
			Long timestampStart = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(start).getTime());
			Long timestampEnd = TimeUnit.MILLISECONDS.toSeconds(Timestamp.valueOf(end).getTime());
			List<MetaDataStatus> metadataStatus = metaDataStatusRepository.findMetadataStatus(regionId, timestampStart, timestampEnd);
			if (!CollectionUtils.isEmpty(metadataStatus)) {
				Long memFree= (long) metadataStatus.stream().mapToLong(MetaDataStatus::getMemFree).average().getAsDouble();
				metaDataStatusMemFree[(int) tempNotNullNum] = (memFree != 0L ? (long) Math.floor(memFree / (1024 * 1024)) :memFree);
				Long memUsed= (long) metadataStatus.stream().mapToLong(MetaDataStatus::getMemUsed).average().getAsDouble();
				metaDataStatusMemUsed[(int) tempNotNullNum] = (memUsed != 0L ? (long) Math.floor(memUsed / (1024 * 1024)) :memUsed);;
			}
			tempNotNullNum++;
			numOfCalcNeeded--;
		}
		redisTemplate.opsForValue().set(Constants.METADATA_STATUS_MEM_FREE_TODAY_KEY + regionId, metaDataStatusMemFree);
		redisTemplate.opsForValue().set(Constants.METADATA_STATUS_MEM_USED_TODAY_KEY + regionId, metaDataStatusMemUsed);
		
	}
	
	public JSONObject getInitialDataForDisk(Long regionId) {
		JSONObject json = new JSONObject();
		Long[] todayDiskFreeStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_FREE_TODAY_KEY, regionId);
		Long[] todayDiskUsedStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_USED_TODAY_KEY , regionId);
		Long[] yesdayDiskFreeStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_FREE_YESTERDAY_KEY , regionId);
		Long[] yesdayDiskUsedStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_USED_YESTERDAY_KEY , regionId);
		Long[] beforeYesdayDiskFreeStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_FREE_BEFORE_YESTERDAY_KEY , regionId);
		Long[] beforeYesdayDiskUsedStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_USED_BEFORE_YESTERDAY_KEY , regionId);
		json.put(Constants.JSON_STATUS_DISK_FREE_TODAY_KEY, todayDiskFreeStatus);
		json.put(Constants.JSON_STATUS_DISK_USED_TODAY_KEY, todayDiskUsedStatus);
		json.put(Constants.JSON_STATUS_DISK_FREE_YESTERDAY_KEY, yesdayDiskFreeStatus);
		json.put(Constants.JSON_STATUS_DISK_USED_YESTERDAY_KEY, yesdayDiskUsedStatus);
		json.put(Constants.JSON_STATUS_DISK_FREE_BEFORE_YESTERDAY_KEY, beforeYesdayDiskFreeStatus);
		json.put(Constants.JSON_STATUS_DISK_USED_BEFORE_YESTERDAY_KEY, beforeYesdayDiskUsedStatus);
		return json;
	}
	
	public JSONObject getLatestDataForDisk(Long regionId, Long numberOfreceived) {
		JSONObject json = new JSONObject();
		Long[] todayDiskFreeStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_FREE_TODAY_KEY, regionId);
		Long[] todayDiskUsedStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_USED_TODAY_KEY , regionId);
		json.put(Constants.JSON_STATUS_DISK_FREE_TODAY_KEY, Arrays.asList(todayDiskFreeStatus).stream().skip(numberOfreceived).collect(Collectors.toList()));
		json.put(Constants.JSON_STATUS_DISK_USED_TODAY_KEY, Arrays.asList(todayDiskUsedStatus).stream().skip(numberOfreceived).collect(Collectors.toList()));
		return json;
		
	}
	
	public JSONObject getInitialDataForMem(Long regionId) {
		JSONObject json = new JSONObject();
		Long[] todayMemFreeStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_MEM_FREE_TODAY_KEY, regionId);
		Long[] todayMemUsedStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_MEM_USED_TODAY_KEY , regionId);
		Long[] yesdayMemFreeStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_MEM_FREE_YESTERDAY_KEY , regionId);
		Long[] yesdayMemUsedStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_MEM_USED_YESTERDAY_KEY , regionId);
		Long[] beforeYesdayMemFreeStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_MEM_FREE_BEFORE_YESTERDAY_KEY , regionId);
		Long[] beforeYesdayMemUsedStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_MEM_USED_BEFORE_YESTERDAY_KEY , regionId);
		json.put(Constants.JSON_STATUS_MEM_FREE_TODAY_KEY, todayMemFreeStatus);
		json.put(Constants.JSON_STATUS_MEM_USED_TODAY_KEY, todayMemUsedStatus);
		json.put(Constants.JSON_STATUS_MEM_FREE_YESTERDAY_KEY, yesdayMemFreeStatus);
		json.put(Constants.JSON_STATUS_MEM_USED_YESTERDAY_KEY, yesdayMemUsedStatus);
		json.put(Constants.JSON_STATUS_MEM_FREE_BEFORE_YESTERDAY_KEY, beforeYesdayMemFreeStatus);
		json.put(Constants.JSON_STATUS_MEM_USED_BEFORE_YESTERDAY_KEY, beforeYesdayMemUsedStatus);
		return json;
	}
	
	public JSONObject getLatestDataForMem(Long regionId, Long numberOfreceived) {
		JSONObject json = new JSONObject();
		Long[] todayMemFreeStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_MEM_FREE_TODAY_KEY, regionId);
		Long[] todayMemUsedStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_MEM_USED_TODAY_KEY , regionId);
		json.put(Constants.JSON_STATUS_MEM_FREE_TODAY_KEY, Arrays.asList(todayMemFreeStatus).stream().skip(numberOfreceived).collect(Collectors.toList()));
		json.put(Constants.JSON_STATUS_MEM_USED_TODAY_KEY, Arrays.asList(todayMemUsedStatus).stream().skip(numberOfreceived).collect(Collectors.toList()));
		return json;
		
	}
	
	public JSONObject getInitialDataForNet(Long regionId) {
		JSONObject json = new JSONObject();
		Long[] todayMemFreeStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_NET_IN_TODAY_KEY, regionId);
		Long[] todayMemUsedStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_NET_OUT_TODAY_KEY , regionId);
		Long[] yesdayMemFreeStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_NET_IN_YESTERDAY_KEY , regionId);
		Long[] yesdayMemUsedStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_NET_OUT_YESTERDAY_KEY , regionId);
		Long[] beforeYesdayMemFreeStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_NET_IN_BEFORE_YESTERDAY_KEY , regionId);
		Long[] beforeYesdayMemUsedStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_NET_OUT_BEFORE_YESTERDAY_KEY , regionId);
		json.put(Constants.JSON_STATUS_NET_IN_TODAY_KEY, todayMemFreeStatus);
		json.put(Constants.JSON_STATUS_NET_OUT_TODAY_KEY, todayMemUsedStatus);
		json.put(Constants.JSON_STATUS_NET_IN_YESTERDAY_KEY, yesdayMemFreeStatus);
		json.put(Constants.JSON_STATUS_NET_OUT_YESTERDAY_KEY, yesdayMemUsedStatus);
		json.put(Constants.JSON_STATUS_NET_IN_BEFORE_YESTERDAY_KEY, beforeYesdayMemFreeStatus);
		json.put(Constants.JSON_STATUS_NET_OUT_BEFORE_YESTERDAY_KEY, beforeYesdayMemUsedStatus);
		return json;
	}
	
	public JSONObject getLatestDataForNet(Long regionId, Long numberOfreceived) {
		JSONObject json = new JSONObject();
		Long[] todayMemFreeStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_NET_IN_TODAY_KEY, regionId);
		Long[] todayMemUsedStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_NET_OUT_TODAY_KEY , regionId);
		json.put(Constants.JSON_STATUS_NET_IN_TODAY_KEY, Arrays.asList(todayMemFreeStatus).stream().skip(numberOfreceived).collect(Collectors.toList()));
		json.put(Constants.JSON_STATUS_NET_OUT_TODAY_KEY, Arrays.asList(todayMemUsedStatus).stream().skip(numberOfreceived).collect(Collectors.toList()));
		return json;
		
	}
	
	public JSONObject getInitialDataForCPU(Long regionId) {
		JSONObject json = new JSONObject();
		Long[] todayCPUStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_CPU_TODAY_KEY, regionId);
		Long[] yesdayCPUStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_CPU_YESTERDAY_KEY , regionId);
		Long[] beforeYesdayCPUStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_CPU_BEFORE_YESTERDAY_KEY , regionId);
		json.put(Constants.JSON_STATUS_CPU_TODAY_KEY, todayCPUStatus);
		json.put(Constants.JSON_STATUS_CPU_YESTERDAY_KEY, yesdayCPUStatus);
		json.put(Constants.JSON_STATUS_CPU_BEFORE_YESTERDAY_KEY, beforeYesdayCPUStatus);
		return json;
	}
	
	public JSONObject getLatestDataForCPU(Long regionId, Long numberOfreceived) {
		JSONObject json = new JSONObject();
		Long[] todayCPUStatus = ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_CPU_TODAY_KEY, regionId);
		json.put(Constants.JSON_STATUS_CPU_TODAY_KEY, Arrays.asList(todayCPUStatus).stream().skip(numberOfreceived).collect(Collectors.toList()));
		return json;
		
	}
	
	public JSONObject getInitialData(Long regionId) {
		Long [] diskArray = Arrays.copyOfRange(ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_USED_YESTERDAY_KEY , regionId), 
				(ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_USED_YESTERDAY_KEY , regionId).length - 100) < 0 ? 0 : (ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_USED_YESTERDAY_KEY , regionId).length - 100), 
				ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_USED_YESTERDAY_KEY , regionId).length);
		System.arraycopy(ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_USED_TODAY_KEY , regionId),
						((ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_USED_TODAY_KEY , regionId).length - 100) < 0 ? 0 : (ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_USED_TODAY_KEY , regionId).length - 100)),
						diskArray,
						((ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_USED_TODAY_KEY , regionId).length - 100) < 0 ? (100 - ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_USED_TODAY_KEY , regionId).length) : 0),
						((ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_USED_TODAY_KEY , regionId).length - 100) > 0 ? 100 : (ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_USED_TODAY_KEY , regionId).length)));
		redisTemplate.opsForValue().set(Constants.METADATA_STATUS_DISK_USED_LATEST_KEY + regionId, diskArray);				
		JSONObject json = new JSONObject();
		json.put("error_code", 0);
		json.put("data", ImmutableMap.<String, Serializable>builder().put("out", ImmutableList.of(ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_NET_OUT_BEFORE_YESTERDAY_KEY , regionId),
																								ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_NET_OUT_YESTERDAY_KEY , regionId),
																								ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_NET_OUT_TODAY_KEY , regionId)))
																	  .put("in", ImmutableList.of(ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_NET_IN_BEFORE_YESTERDAY_KEY , regionId),
																				ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_NET_IN_YESTERDAY_KEY , regionId),
																				ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_NET_IN_TODAY_KEY , regionId)))
																	  .put("cpu", ImmutableList.of(ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_CPU_BEFORE_YESTERDAY_KEY , regionId),
																				ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_CPU_YESTERDAY_KEY , regionId),
																				ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_CPU_TODAY_KEY , regionId)))
																	  .put("memory", ImmutableList.of(ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_MEM_USED_BEFORE_YESTERDAY_KEY , regionId),
																				ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_MEM_USED_YESTERDAY_KEY , regionId),
																				ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_MEM_USED_TODAY_KEY , regionId)))
																	  .put("disk", ImmutableList.of(ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_USED_LATEST_KEY , regionId))).build());
		return json;
	}
	
	public JSONObject getLatestData(Long regionId, Long numberOfreceived) {
		Long [] diskArray = Arrays.copyOfRange(ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_USED_YESTERDAY_KEY , regionId), 
				(ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_USED_YESTERDAY_KEY , regionId).length - 100) < 0 ? 0 : (ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_USED_YESTERDAY_KEY , regionId).length - 100), 
				ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_USED_YESTERDAY_KEY , regionId).length);
		System.arraycopy(ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_USED_TODAY_KEY , regionId),
						((ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_USED_TODAY_KEY , regionId).length - 100) < 0 ? 0 : (ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_USED_TODAY_KEY , regionId).length - 100)),
						diskArray,
						((ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_USED_TODAY_KEY , regionId).length - 100) < 0 ? (100 - ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_USED_TODAY_KEY , regionId).length) : 0),
						((ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_USED_TODAY_KEY , regionId).length - 100) > 0 ? 100 : (ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_USED_TODAY_KEY , regionId).length)));
		redisTemplate.opsForValue().set(Constants.METADATA_STATUS_DISK_USED_LATEST_KEY + regionId, diskArray);				
		JSONObject json = new JSONObject();
		json.put("error_code", 0);
		json.put("data", ImmutableMap.<String, Serializable>builder().put("out", ImmutableList.of(getSkipArray(ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_NET_OUT_TODAY_KEY , regionId), numberOfreceived)))
																								
																	  .put("in", ImmutableList.of(getSkipArray(ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_NET_IN_TODAY_KEY , regionId), numberOfreceived)))
																	  .put("cpu", ImmutableList.of(getSkipArray(ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_CPU_TODAY_KEY , regionId), numberOfreceived)))
																	  .put("memory", ImmutableList.of(getSkipArray(ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_MEM_USED_TODAY_KEY , regionId), numberOfreceived)))
																	  .put("disk", ImmutableList.of(getSkipArray(ServiceUtil.getDataFromCache(redisTemplate, Constants.METADATA_STATUS_DISK_USED_LATEST_KEY , regionId), numberOfreceived))).build());
		return json;
		
	}
	
	private Long[] getSkipArray(Long[] src, Long skip) {
		return Arrays.asList(src).stream().skip(skip).collect(Collectors.toList()).toArray(new Long[0]);
	}
}
