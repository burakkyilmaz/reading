package com.getir.reading.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
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

import com.getir.reading.dto.CustomerDTO;
import com.getir.reading.dto.OrderDTO;
import com.getir.reading.payload.CustomerRecordPayload.CreateCustomerRequest;
import com.getir.reading.response.base.BasePaginationResponse;
import com.getir.reading.response.base.BaseResponse;
import com.getir.reading.service.CustomerService;
import com.getir.reading.utils.JwtTokenUtil;
import com.getir.reading.utils.PaginationUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/customers")
@Tag(name = "Customer Controller")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@PostMapping
	@Operation(summary = "Create a new customer", tags = { "Customers" })
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Customer created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid request"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<BaseResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest customer) {
		CustomerDTO saveCustomer = customerService.saveCustomer(customer.username(), customer.email(),
				customer.password(), customer.adress());

		return new ResponseEntity<>(BaseResponse.success(saveCustomer), HttpStatus.CREATED);
	}

	@GetMapping("/list/{customerId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Get orders for a customer by ID (admin only)", tags = { "Customers" })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Orders fetched successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid request"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "404", description = "Customer not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<BasePaginationResponse> findOrderForCustomer(
			@PathVariable @Positive(message = "ID must be positive") Long customerId,
			@RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer limit,
			@RequestParam(required = false) Direction direction, @RequestParam(required = false) String orderColumn) {
		Pageable pageable = PaginationUtils.getPageable(direction, orderColumn, pageNumber, limit);

		List<OrderDTO> orderDTOList = customerService.findOrderForCustomer(customerId, pageable);

		BasePaginationResponse response = new BasePaginationResponse();
		response.setPage(pageNumber);
		response.setLimit(limit);
		response.setData(orderDTOList);
		response.setCount(customerService.countCustomerOrders(customerId));
		response.setSuccess(true);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/list/me")
	@PreAuthorize("hasAuthority('CUSTOMER')")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Get orders for the current customer", tags = { "Customers" })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Orders fetched successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid request"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "404", description = "Customer not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<BasePaginationResponse> findOrderForCurrentCustomer(
			@RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer limit,
			@RequestParam(required = false) Direction direction, @RequestParam(required = false) String orderColumn) {
		Pageable pageable = PaginationUtils.getPageable(direction, orderColumn, pageNumber, limit);

		List<OrderDTO> orderDTOList = customerService.findOrderForCustomer(jwtTokenUtil.getUsernameFromToken(),
				pageable);

		BasePaginationResponse response = new BasePaginationResponse();
		response.setPage(pageNumber);
		response.setLimit(limit);
		response.setData(orderDTOList);
		response.setCount(customerService.countCustomerOrders(jwtTokenUtil.getUsernameFromToken()));
		response.setSuccess(true);
		return ResponseEntity.ok().body(response);
	}

}
