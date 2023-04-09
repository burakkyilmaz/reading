package com.getir.reading.service;

import com.getir.reading.dto.BookDTO;
import com.getir.reading.dto.BookDetailDTO;
import com.getir.reading.entity.Book;
import com.getir.reading.payload.SaveBookRequest;

public interface BookService {

	BookDTO createBook(SaveBookRequest book);

	BookDTO updateBookStock(Long id, Integer stock);

	Book updateBookStock(Book book, Integer stock);

	BookDetailDTO findBookDetailById(Long id);

	Book findBookForAvailableStock(Long id);


}
