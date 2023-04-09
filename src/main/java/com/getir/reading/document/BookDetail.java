package com.getir.reading.document;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.getir.reading.enums.BookType;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "book_detail")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class BookDetail {

	@Id
	private String id;

	@Indexed(unique = true)
	private Long bookId;

	private String description;

	private String publisher;

	private LocalDate publicationDate;

	private int pages;

	@Enumerated(EnumType.STRING)
	private BookType type;

	@CreatedDate
	private LocalDateTime createdDate;

	@LastModifiedDate
	private LocalDateTime lastModifiedDate;

}
