package com.getir.reading.utils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {

	@Value("${redis.prefix}")
	private String redisPrefix;

	@Autowired
	private RedissonClient redissonClient;


	public String getParameterCacheName(String key) {
		return redisPrefix + ":parameter:" + key;
	}

	public String getOrderStatislicsCacheName() {
		return redisPrefix + ":ordertstat:";
	}

	public <T> void putListObject(String key, List<T> sourceList, long timeout, ChronoUnit unit) {
		if (sourceList != null && !sourceList.isEmpty()) {
			RList<T> list = redissonClient.getList(key, new JsonJacksonCodec());
			list.addAll(sourceList);
			list.expire(Duration.of(timeout, unit));
		}
	}

	public <T> List<T> getListObject(String key) {
		RList<T> list = redissonClient.getList(key, new JsonJacksonCodec());
		return list.readAll();
	}

	public void clearList(String key)
	{
		RList<Object> myList = redissonClient.getList(key);
		myList.delete();
	}

}
