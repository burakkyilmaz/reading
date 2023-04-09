package com.getir.reading.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StockException extends RuntimeException {
	private static final long serialVersionUID = -1834259687601764592L;

	public StockException(String message) {
		super(message);
	}
}