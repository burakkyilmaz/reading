package com.getir.reading.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.getir.reading.document.BookDetail;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookDetailDTO {

	@Id
	private String id;

	private Long bookId;

	private String description;

	private String publisher;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate publicationDate;

	private String type;

	public static BookDetailDTO from(BookDetail bookDetail) {

		return new BookDetailDTO(bookDetail.getId(), bookDetail.getBookId(), bookDetail.getDescription(),
				bookDetail.getPublisher(), bookDetail.getPublicationDate(), bookDetail.getType().getCode());
	}

}
