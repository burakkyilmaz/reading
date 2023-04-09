package com.getir.reading.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class BookRecordPayload {

	public record UpdateStockBook(
			@NotNull(message = "stock is required") @PositiveOrZero(message = "stock cannot be less than 0") Integer stock) {
	}

}
