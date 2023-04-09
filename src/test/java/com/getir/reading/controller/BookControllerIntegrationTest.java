package com.getir.reading.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.LinkedHashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.reading.dto.BookDetailDTO;
import com.getir.reading.enums.BookType;
import com.getir.reading.payload.BookRecordPayload.UpdateStockBook;
import com.getir.reading.payload.SaveBookRequest;
import com.getir.reading.response.AuthRecordResponse.AuthenticationResponse;
import com.getir.reading.response.base.BaseResponse;
import com.getir.reading.service.impl.AuthenticateServiceImpl;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private AuthenticateServiceImpl authenticateService;

	private Long bookId;

	@SuppressWarnings("unchecked")
	@Test
	@WithMockUser(authorities = "ADMIN")
	void createBookAndGetDetailsAndUpdateStock() throws Exception {
		// Create a book
		
		AuthenticationResponse authenticate = authenticateService.authenticate("admin", "Admin123+");
		
		
		SaveBookRequest saveBookRequest = new SaveBookRequest();
		saveBookRequest.setName("Test Book");
		saveBookRequest.setPrice(20.0);
		saveBookRequest.setStock(5);
		saveBookRequest.setType(BookType.FICTION);
		saveBookRequest.setDescription("test");
		saveBookRequest.setPublisher("test");
		saveBookRequest.setPublicationDate(LocalDate.now());
		saveBookRequest.setPages(25);

		MvcResult createResult = mockMvc.perform(MockMvcRequestBuilders
				.post("/books")
		        .header("Authorization", "Bearer " +  authenticate.token())
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(saveBookRequest)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
		        .andReturn();
		
		BaseResponse createResponse = objectMapper.readValue(createResult.getResponse().getContentAsString(),
				BaseResponse.class);
		bookId = Long.valueOf(((LinkedHashMap) createResponse.getData()).get("id").toString());
		assertNotNull(bookId);

		System.out.println(bookId);

		// Get book details

		MvcResult getResult = mockMvc.perform(MockMvcRequestBuilders.get("/books/detail/{id}", bookId)
				.header("Authorization", "Bearer " + authenticate.token()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		BaseResponse getResponse = objectMapper.readValue(getResult.getResponse().getContentAsString(),
				BaseResponse.class);
		BookDetailDTO bookDetail = objectMapper.convertValue(getResponse.getData(), BookDetailDTO.class);
		assertEquals(bookDetail.getBookId(), bookId);




		// Update book stock
		UpdateStockBook updateStockBook = new UpdateStockBook(10);

		MvcResult updateResult = mockMvc.perform(MockMvcRequestBuilders.put("/books/{id}", bookId)
				.header("Authorization", "Bearer " + authenticate.token()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateStockBook)))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		BaseResponse updateResponse = objectMapper.readValue(updateResult.getResponse().getContentAsString(),
				BaseResponse.class);
		assertNotNull(updateResponse.getData());
	}
}
