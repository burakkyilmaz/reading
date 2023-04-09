package com.getir.reading.payload;

import com.getir.reading.utils.RegexpUtils;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class AuthRecordPayload {

	public record AuthenticationRequest(
			@NotEmpty(message = "username cannot be empty") @Pattern(regexp = RegexpUtils.USERNAME_REGEXP, message = "check username") String username,
			@NotEmpty(message = "password cannot be empty") @Pattern(regexp = RegexpUtils.PASSWORD_REGEXP, message = "check password") String password) {
	}

}
