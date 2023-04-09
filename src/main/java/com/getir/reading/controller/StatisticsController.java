package com.getir.reading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.getir.reading.response.base.BaseResponse;
import com.getir.reading.service.StatisticsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/statistics")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Statistics Controller")
public class StatisticsController {

	@Autowired
	private StatisticsService statisticsService;

	@GetMapping("/order")
	@PreAuthorize("hasAuthority('ADMIN')")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Get monthly order statistics", description = "Returns monthly order statistics for the system, restricted to admin users")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Monthly order statistics retrieved successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "403", description = "Forbidden access"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<BaseResponse> findOrderForCustomer() {

		return new ResponseEntity<>(BaseResponse.success(statisticsService.findMonthlyOrderStatistics()),
				HttpStatus.OK);
	}

	@DeleteMapping("/order/cache")
	@PreAuthorize("hasAuthority('ADMIN')")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Clear order statistics cache", description = "Clears the cached monthly order statistics for the system, restricted to admin users")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Order statistics cache cleared successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "403", description = "Forbidden access"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<BaseResponse> clearOrderStatislicsInRedis() {
		statisticsService.clearOrderStatislicsInRedis();
		return new ResponseEntity<>(BaseResponse.success(), HttpStatus.OK);
	}

}
