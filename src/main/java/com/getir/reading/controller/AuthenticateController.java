package com.getir.reading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.getir.reading.payload.AuthRecordPayload.AuthenticationRequest;
import com.getir.reading.response.AuthRecordResponse.AuthenticationResponse;
import com.getir.reading.response.base.BaseResponse;
import com.getir.reading.service.AuthenticateService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Authenticate Controller")
public class AuthenticateController {

	@Autowired
	private AuthenticateService authenticateService;

	// post for ssl body
	@PostMapping("/authenticate")
	@Operation(summary = "Authenticate a user", description = "Authenticate a user with the given username and password.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Authentication successful"),
			@ApiResponse(responseCode = "401", description = "Invalid credentials"),
			@ApiResponse(responseCode = "403", description = "User disabled") })
	public ResponseEntity<BaseResponse> createAuthenticationToken(
			@Valid @RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {

		AuthenticationResponse response = authenticateService.authenticate(authenticationRequest.username(),
				authenticationRequest.password());

		return new ResponseEntity<>(BaseResponse.success(response), HttpStatus.OK);


	}


}