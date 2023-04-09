package com.getir.reading.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class OrderRecordPayload {

	public record SaveOrder(
			@NotNull(message = "book id is required") @Positive(message = "id value must be greater than 0") Long bookId,
			@NotNull(message = "quantity id is required") @Positive(message = "quantity must be greater than 0") Integer quantity) {
	}

}
