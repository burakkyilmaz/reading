package com.getir.reading.payload;

import java.util.ArrayList;

import com.getir.reading.payload.AdressRecordPayload.CustomerAdressRequest;
import com.getir.reading.utils.RegexpUtils;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class CustomerRecordPayload {

	public record CreateCustomerRequest(
			@NotEmpty(message = "Name is required") @Pattern(regexp = RegexpUtils.USERNAME_REGEXP, message = "check username") String username,
			@NotEmpty(message = "password is required") @Pattern(regexp = RegexpUtils.PASSWORD_REGEXP, message = "check password") String password,
			@Email(message = "Invalid email address") @NotBlank(message = "Email is required") String email,
			@NotEmpty(message = "Adress is required") ArrayList<CustomerAdressRequest> adress) {
	}
	

}
