package com.anyun100.storage.monitor.metadata_service.service;

import org.springframework.data.redis.core.RedisTemplate;

public class ServiceUtil {
	
	private final static int arraySize = 288;
	
	public static Long[] getDataFromCache(RedisTemplate<String, Long[]> redisTemplate, String key, Long id) {
		Long[] data = redisTemplate.opsForValue().get(key + id) == null || redisTemplate.opsForValue().get(key + id).length == 0 ? new Long[arraySize] : redisTemplate.opsForValue().get(key + id);
		for (int i = 0; i < data.length; i++) {
			data[i] = data[i] == null ? 0L : data[i];
		}
		return data;
	}

}
