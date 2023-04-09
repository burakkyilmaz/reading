package com.getir.reading.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
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
import com.getir.reading.response.AuthRecordResponse.AuthenticationResponse;
import com.getir.reading.response.base.BaseResponse;
import com.getir.reading.service.impl.AuthenticateServiceImpl;
import com.getir.reading.service.impl.StatisticsServiceImpl;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StatisticsControllerIntegrationTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private AuthenticateServiceImpl authenticateService;

	@Autowired
	private StatisticsServiceImpl statisticsService;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setUp() {
		// Redis cache cleanup before each test
		statisticsService.clearOrderStatislicsInRedis();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void findOrderForCustomer_returnsMonthlyOrderStatistics() throws Exception {
		// Mock monthly order statistics data
		AuthenticationResponse authenticate = authenticateService.authenticate("admin", "Admin123+");

		MvcResult getResult = mockMvc.perform(MockMvcRequestBuilders.get("/statistics/order")
				.header("Authorization", "Bearer " + authenticate.token()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		BaseResponse updateResponse = objectMapper.readValue(getResult.getResponse().getContentAsString(),
				BaseResponse.class);

		assertNotNull(updateResponse.getData());

	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void clearOrderStatislicsInRedis_clearsMonthlyOrderStatisticsCache() throws Exception {
		AuthenticationResponse authenticate = authenticateService.authenticate("admin", "Admin123+");

		MvcResult getResult = mockMvc.perform(MockMvcRequestBuilders.delete("/statistics/order/cache")
				.header("Authorization", "Bearer " + authenticate.token()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		BaseResponse updateResponse = objectMapper.readValue(getResult.getResponse().getContentAsString(),
				BaseResponse.class);

		assertNotNull(updateResponse.getMessage());


	}

	@Test
	@WithMockUser(authorities = "USER")
	void findOrderForCustomer_returnsForbiddenStatus() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/statistics/order")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}



}
