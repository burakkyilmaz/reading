package com.getir.reading.payload;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.getir.reading.enums.BookType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveBookRequest {

	@NotEmpty(message = "username cannot be empty")
	private String name;

	@NotNull(message = "price is required")
	@Positive(message = "price must be positive")
	private Double price;

	@NotNull(message = "price is required")
	@PositiveOrZero(message = "stock cannot be less than 0")
	private Integer stock;

	private String description;

	private String publisher;

	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate publicationDate;

	private int pages;

	@NotNull(message = "Book Type is required")
	@Enumerated(EnumType.STRING)
	private BookType type;

}
