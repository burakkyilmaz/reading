package com.getir.reading.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.getir.reading.document.BookDetail;
import com.getir.reading.dto.BookDTO;
import com.getir.reading.dto.BookDetailDTO;
import com.getir.reading.entity.Book;
import com.getir.reading.exception.NotEnoughStockException;
import com.getir.reading.exception.ResourceNotFoundException;
import com.getir.reading.exception.StockException;
import com.getir.reading.payload.SaveBookRequest;
import com.getir.reading.repository.BookDetailRepository;
import com.getir.reading.repository.BookRepository;
import com.getir.reading.service.BookService;
import com.getir.reading.utils.LogUtil;

import jakarta.transaction.Transactional;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BookDetailRepository bookDetailRepository;

	@Override
	@Transactional
	public BookDTO createBook(SaveBookRequest bookRequest) {
		Book book = new Book();
		book.setName(bookRequest.getName());
		book.setPrice(bookRequest.getPrice());
		book.setStock(bookRequest.getStock());

		book = bookRepository.save(book);

		BookDetail bookDetail = new BookDetail();
		bookDetail.setBookId(book.getId());
		bookDetail.setDescription(bookRequest.getDescription());
		bookDetail.setPublisher(bookRequest.getPublisher());
		bookDetail.setPublicationDate(bookRequest.getPublicationDate());
		bookDetail.setType(bookRequest.getType());
		bookDetailRepository.save(bookDetail);
		
		Book saveBook = bookRepository.save(book);

		LogUtil.info("Book created successfully with id {}", saveBook.getId());

		return BookDTO.forBook(saveBook);

	}

	@Override
	public BookDTO updateBookStock(Long id, Integer stock) {
		Optional<Book> optionalBook = bookRepository.findById(id);
		if (optionalBook.isPresent()) {
			Book book = optionalBook.get();
			book.setStock(stock);
			Book updateBook = bookRepository.save(book);

			LogUtil.info("Stock for book with id {} updated to {}", updateBook.getId(), stock);
			return BookDTO.forBook(updateBook);
		} else {
			throw new ResourceNotFoundException("Book ", "id", id);
		}
	}

	@Override
	public Book updateBookStock(Book book, Integer stock) {
		if(stock<0)
		{
			throw new StockException("Stock can not be negative");

		}
		book.setStock(stock);

		Book saveBook = bookRepository.save(book);
		LogUtil.info("Stock for book with id {} updated to {}", saveBook.getId(), stock);

		return saveBook;

	}

	@Override
	public BookDetailDTO findBookDetailById(Long id) {
		BookDetail bookDetail = bookDetailRepository.findByBookId(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));

		LogUtil.info("Book detail retrieved successfully for book with id {}", id);

		return BookDetailDTO.from(bookDetail);
	}

	@Override
	public Book findBookForAvailableStock(Long id) {

		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
		if (book.getStock() < 1) {
			throw new NotEnoughStockException("Book", "id", id);
		}

		LogUtil.info("Book retrieved successfully for checking stock availability with id {}", id);

		return book;

	}



}
