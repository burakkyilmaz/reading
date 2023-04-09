package com.getir.reading.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.getir.reading.entity.SystemParameter;
import com.getir.reading.exception.ResourceNotFoundException;
import com.getir.reading.repository.SystemParameterRepository;
import com.getir.reading.service.ParameterService;
import com.getir.reading.utils.LogUtil;


@Service
public class ParameterServiceImpl implements ParameterService {

	@Autowired
	private SystemParameterRepository parameterRepository;

	@Override
	public SystemParameter findParameter(String key) {
		LogUtil.info("Finding SystemParameter by key: {}", key);
		SystemParameter parameter = parameterRepository.findByKey(key);
		if (parameter == null) {
			throw new ResourceNotFoundException("SystemParameeter ", "key", key);
		}
		return parameter;
	}

}
