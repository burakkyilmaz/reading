package com.getir.reading.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotEnoughStockException extends RuntimeException {

	private static final long serialVersionUID = -4906313519429341137L;

	public NotEnoughStockException(String resourceName, String fieldName, Object fieldValue) {
		super(String.format("%s stock not enough with %s : '%s'", resourceName, fieldName, fieldValue));
	}

}