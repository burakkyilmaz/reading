package com.getir.reading.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserConflictException extends RuntimeException {

	private static final long serialVersionUID = -7289126262931407135L;

	public UserConflictException(String message) {
		super(message);
	}

	public UserConflictException(String message, Throwable cause) {
		super(message, cause);
	}
}