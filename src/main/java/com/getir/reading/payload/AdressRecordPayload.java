package com.getir.reading.payload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;

public class AdressRecordPayload {

	public record CustomerAdressRequest(
			@NotEmpty(message = "Street is required") @Max(value = 255, message = "street max size is 255") String street,
			@NotEmpty(message = "password is required") @Max(value = 255, message = "city max size is 255") String city,
			@NotEmpty(message = "state is required") @Max(value = 255, message = "state max size is 255") String state,
			@NotEmpty(message = "zip code is required") @Max(value = 255, message = "zip code max size is 255") String zipCode) {
	}

}
