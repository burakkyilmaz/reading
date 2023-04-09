package com.getir.reading.service.impl;

import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.getir.reading.entity.SystemParameter;
import com.getir.reading.exception.ResourceNotFoundException;
import com.getir.reading.repository.SystemParameterRepository;

@ExtendWith(MockitoExtension.class)
class ParameterServiceTest {

	@Mock
	private SystemParameterRepository parameterRepository;

	@InjectMocks
	private ParameterServiceImpl parameterService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindParameter() {
		String key = "myParameter";
		SystemParameter parameter = new SystemParameter();
		parameter.setKey(key);
		parameter.setValue("12345");

		when(parameterRepository.findByKey(key)).thenReturn(parameter);

		SystemParameter result = parameterService.findParameter(key);

		Assertions.assertEquals(key, result.getKey());
		Assertions.assertEquals("12345", result.getValue());
	}

	@Test
	void testFindParameterNotFound() {
		String key = "myParameter";

		when(parameterRepository.findByKey(key)).thenReturn(null);

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			parameterService.findParameter(key);
		});
	}
}
