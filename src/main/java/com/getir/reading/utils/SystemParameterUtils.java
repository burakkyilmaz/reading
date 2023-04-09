package com.getir.reading.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.getir.reading.entity.SystemParameter;
import com.getir.reading.model.ParameterDTO;
import com.getir.reading.service.ParameterService;

@Component
public class SystemParameterUtils {

	public static final String CUSTOMER_ROLE_ID = "CustomerRoleId";

	@Autowired
	private RedisUtils redisCacheNameUtils;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private ParameterService parameterService;


	public ParameterDTO getParameter(String key) {
		String value = redisTemplate.opsForValue().get(redisCacheNameUtils.getParameterCacheName(key));
		if (value != null) {
			return ParameterDTO.builder().key(key).value(value).build();
		}

		try {
			// check message-service
			return toDto(parameterService.findParameter(key));
		} catch (Exception e) {
			LogUtil.error("An error occured while connecting the message-service!", e);
			return null;
		}
	}

	private ParameterDTO toDto(SystemParameter entity) {
		return ParameterDTO.builder().key(entity.getKey()).value(entity.getValue()).build();
	}


}
