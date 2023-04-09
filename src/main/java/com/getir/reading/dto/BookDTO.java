package com.getir.reading.dto;

import com.getir.reading.entity.Book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor

public class BookDTO {

	@NonNull
	private Long id;

	@NonNull
	private String name;

	private Double price;
	
	private Integer stock;

	

	public static BookDTO forOrder(Book book) {
		// If the book price is updated, we will have a problem with the order.
		return new BookDTO(book.getId(), book.getName());
	}

	
	public static BookDTO forBook(Book book) {
		return new BookDTO(book.getId(), book.getName(), book.getPrice(), book.getStock());
	}

}
