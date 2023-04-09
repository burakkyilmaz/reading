package com.getir.reading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.getir.reading.dto.BookDTO;
import com.getir.reading.dto.BookDetailDTO;
import com.getir.reading.payload.BookRecordPayload.UpdateStockBook;
import com.getir.reading.payload.SaveBookRequest;
import com.getir.reading.response.base.BaseResponse;
import com.getir.reading.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/books")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Book Controller")
public class BookController {

	@Autowired
	private BookService bookService;

	@Operation(summary = "Create a book", description = "Create a new book with the given details.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Book created successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "422", description = "Validation errors in the request body") })
	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<BaseResponse> createBook(@Valid @RequestBody SaveBookRequest book) {
		BookDTO createBook = bookService.createBook(book);
		return new ResponseEntity<>(BaseResponse.success(createBook), HttpStatus.CREATED);
	}

	@Operation(summary = "Update book stock", description = "Update the stock of an existing book with the given ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Book stock updated successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "404", description = "Book not found"),
			@ApiResponse(responseCode = "422", description = "Validation errors in the request body") })
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<BaseResponse> updateBookStock(
			@Valid @PathVariable @Positive(message = "ID must be positive") Long id,
			@Valid @RequestBody(required = true) UpdateStockBook updateStockBook) {
		BookDTO updateBookStock = bookService.updateBookStock(id, updateStockBook.stock());
		return new ResponseEntity<>(BaseResponse.success(updateBookStock), HttpStatus.OK);

	}

	@Operation(summary = "Get book details", description = "Get the details of an existing book with the given ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Book details retrieved successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "404", description = "Book not found") })
	@GetMapping("/detail/{id}")
	public ResponseEntity<BaseResponse> getBookById(
			@Valid @PathVariable("id") @Positive(message = "ID must be positive") Long id) {
		BookDetailDTO bookDetail = bookService.findBookDetailById(id);
		return new ResponseEntity<>(BaseResponse.success(bookDetail), HttpStatus.OK);
	}
}
