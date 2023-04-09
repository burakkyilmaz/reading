package com.getir.reading.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.getir.reading.document.BookDetail;
import com.getir.reading.dto.BookDTO;
import com.getir.reading.dto.BookDetailDTO;
import com.getir.reading.entity.Book;
import com.getir.reading.enums.BookType;
import com.getir.reading.exception.ResourceNotFoundException;
import com.getir.reading.payload.SaveBookRequest;
import com.getir.reading.repository.BookDetailRepository;
import com.getir.reading.repository.BookRepository;


@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

	@Mock
	private BookRepository bookRepository;

	@Mock
	private BookDetailRepository bookDetailRepository;

	@InjectMocks
	private BookServiceImpl bookService;

	private static final Long BOOK_ID = 1L;

	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateBook() {
		// Given
		SaveBookRequest bookRequest = new SaveBookRequest();
		bookRequest.setName("Book Name");
		bookRequest.setPrice(19.99);
		bookRequest.setStock(10);
		bookRequest.setDescription("Book Description");
		bookRequest.setPublisher("Book Publisher");
		bookRequest.setPublicationDate(LocalDate.of(2022, 4, 9));
		bookRequest.setType(BookType.FICTION);

		Book book = new Book();
		book.setId(BOOK_ID);
		book.setName(bookRequest.getName());
		book.setPrice(bookRequest.getPrice());
		book.setStock(bookRequest.getStock());

		BookDetail bookDetail = new BookDetail();
		bookDetail.setBookId(book.getId());
		bookDetail.setDescription(bookRequest.getDescription());
		bookDetail.setPublisher(bookRequest.getPublisher());
		bookDetail.setPublicationDate(bookRequest.getPublicationDate());
		bookDetail.setType(bookRequest.getType());

		when(bookRepository.save(any(Book.class))).thenReturn(book);
		when(bookDetailRepository.save(any(BookDetail.class))).thenReturn(bookDetail);

		BookDTO createdBook = bookService.createBook(bookRequest);

		assertNotNull(createdBook);
		assertEquals(BOOK_ID, createdBook.getId());
		assertEquals(bookRequest.getName(), createdBook.getName());
		assertEquals(bookRequest.getPrice(), createdBook.getPrice());
		assertEquals(bookRequest.getStock(), createdBook.getStock());

	}

	@Test
	void testUpdateBookStock() {
		// Given
		Integer newStock = 15;

		Book existingBook = new Book();
		existingBook.setId(BOOK_ID);
		existingBook.setName("Book Name");
		existingBook.setPrice(19.99);
		existingBook.setStock(10);

		when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(existingBook));
		when(bookRepository.save(any(Book.class))).thenReturn(existingBook);

		// When
		BookDTO updatedBook = bookService.updateBookStock(BOOK_ID, newStock);

		// Then
		assertNotNull(updatedBook);
		assertEquals(BOOK_ID, updatedBook.getId());
		assertEquals(existingBook.getName(), updatedBook.getName());
		assertEquals(existingBook.getPrice(), updatedBook.getPrice());
		assertEquals(newStock, updatedBook.getStock());
	}

	@Test
	void testUpdateBookStockForNonExistingBook() {
                when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.empty());

      

        assertThrows(ResourceNotFoundException.class, () ->   bookService.updateBookStock(BOOK_ID, 10));

    }
	
	 @Test
	    public void findBookDetailById_WithValidId_ReturnsBookDetailDTO() {
	        // arrange
	        Long id = 1L;
	        BookDetail bookDetail = new BookDetail();
			bookDetail.setId("deneme");
	        bookDetail.setBookId(id);
	        bookDetail.setDescription("Description");
	        bookDetail.setPublisher("Publisher");
	        bookDetail.setPublicationDate(LocalDate.of(2021, 1, 1));
			bookDetail.setType(BookType.FICTION);

	        when(bookDetailRepository.findByBookId(id)).thenReturn(Optional.of(bookDetail));

	        // act
	        BookDetailDTO result = bookService.findBookDetailById(id);

	        // assert
	        assertNotNull(result);
	        assertEquals(bookDetail.getId(), result.getId());
	        assertEquals(bookDetail.getBookId(), result.getBookId());
	        assertEquals(bookDetail.getDescription(), result.getDescription());
	        assertEquals(bookDetail.getPublisher(), result.getPublisher());
	        assertEquals(bookDetail.getPublicationDate(), result.getPublicationDate());
			assertEquals(bookDetail.getType().getCode(), result.getType());

	        verify(bookDetailRepository, times(1)).findByBookId(id);
	        verifyNoMoreInteractions(bookDetailRepository);
	    }

		@Test
	    public void findBookDetailById_WithInvalidId_ThrowsResourceNotFoundException() {
	        // arrange
	        Long id = 1L;

	        when(bookDetailRepository.findByBookId(id)).thenReturn(Optional.empty());

			assertThrows(ResourceNotFoundException.class, () -> bookService.findBookDetailById(id));

	      
	    }

	    @Test
	    public void findBookForAvailableStock_WithAvailableStock_ReturnsBook() {
	        // arrange
	        Long id = 1L;
	        Book book = new Book();
	        book.setId(id);
	        book.setName("Book Name");
	        book.setPrice(10.0);
	        book.setStock(1);

	        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

	        // act
	        Book result = bookService.findBookForAvailableStock(id);

	        // assert
	        assertNotNull(result);
	        assertEquals(book.getId(), result.getId());
	        assertEquals(book.getName(), result.getName());
	        assertEquals(book.getPrice(), result.getPrice());
	        assertEquals(book.getStock(), result.getStock());

	        verify(bookRepository, times(1)).findById(id);
	        verifyNoMoreInteractions(bookRepository);
	    }


}