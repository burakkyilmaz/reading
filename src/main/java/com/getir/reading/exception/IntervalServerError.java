package com.getir.reading.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class IntervalServerError extends RuntimeException {
	private static final long serialVersionUID = -204781759742130730L;

	public IntervalServerError(String message) {
		super(message);
	}
}
