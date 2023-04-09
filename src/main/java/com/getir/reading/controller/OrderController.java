package com.getir.reading.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.getir.reading.dto.OrderDTO;
import com.getir.reading.payload.OrderRecordPayload.SaveOrder;
import com.getir.reading.response.base.BaseResponse;
import com.getir.reading.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/orders")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Order Controller")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping
	@Operation(summary = "Add a new order")
	//@formatter:off
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "201", description = "Order added successfully"),
	        @ApiResponse(responseCode = "400", description = "Invalid input")
	})
	//@formatter:on
	public ResponseEntity<BaseResponse> addOrder(@Valid @RequestBody SaveOrder saveOrder) {

		return new ResponseEntity<>(
				BaseResponse.success(orderService.addOrder(saveOrder.bookId(), saveOrder.quantity())),
				HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	//@formatter:off
	@Operation(summary = "Get order")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Order found"),
	        @ApiResponse(responseCode = "404", description = "Order not found")
	})
	//@formatter:on
	public ResponseEntity<BaseResponse> getOrder(@PathVariable @Positive(message = "ID must be positive") Long id) {
		return new ResponseEntity<>(BaseResponse.success(orderService.findById(id)), HttpStatus.OK);

	}

	@GetMapping("/list")
	@PreAuthorize("hasAuthority('ADMIN')")
	@Operation(summary = "Get orders by date range")
	//@formatter:off
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Orders found"),
	        @ApiResponse(responseCode = "400", description = "Invalid input")
	})
	//@formatter:on
	public ResponseEntity<BaseResponse> getOrdersByDateRange(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		List<OrderDTO> orderDTOlist = orderService.findByCreatedDateBetween(startDate, endDate);
		return new ResponseEntity<>(BaseResponse.success(orderDTOlist), HttpStatus.OK);
	}

}
