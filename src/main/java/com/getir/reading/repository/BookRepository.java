package com.getir.reading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.getir.reading.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
