package com.getir.reading.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.getir.reading.document.BookDetail;

@Repository
public interface BookDetailRepository extends MongoRepository<BookDetail, String> {

	Optional<BookDetail> findByBookId(Long bookId);
}
