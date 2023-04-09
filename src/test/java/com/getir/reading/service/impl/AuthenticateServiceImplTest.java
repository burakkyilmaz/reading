package com.getir.reading.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.getir.reading.entity.User;
import com.getir.reading.response.AuthRecordResponse.AuthenticationResponse;
import com.getir.reading.service.UserDetailGetirService;
import com.getir.reading.utils.JwtTokenUtil;

@ExtendWith(MockitoExtension.class)
class AuthenticateServiceImplTest {

	@InjectMocks
	private AuthenticateServiceImpl authenticateService;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private UserDetailGetirService userDetailGetirService;

	@Mock
	private JwtTokenUtil jwtTokenUtil;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void authenticate_ShouldReturnValidToken() throws Exception {
		// Given
		String username = "testuser";
		String password = "testpassword";
		String token = "validToken";

		User userDetails = new User();
		userDetails.setUsername(username);

		Authentication authentication = mock(Authentication.class);
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenReturn(authentication);
		when(userDetailGetirService.findByUsername(username)).thenReturn(userDetails);
		when(jwtTokenUtil.generateToken(userDetails)).thenReturn(token);

		// When
		AuthenticationResponse response = authenticateService.authenticate(username, password);

		// Then
		assertNotNull(response);
		assertEquals(token, response.token());
	}

	@Test
	void authenticate_ShouldThrowException_WhenAuthenticationFails() throws Exception {

		String username = "testuser";
		String password = "testpassword";

		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenThrow(new BadCredentialsException("Invalid credentials"));
		
		assertThrows(Exception.class,
				() -> authenticateService.authenticate(username, password));


	}

	@Test
	void authenticate_ShouldThrowException_WhenUserIsDisabled() throws Exception {
		exception.expect(DisabledException.class);

		// Given
		String username = "testuser";
		String password = "testpassword";

		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenThrow(new DisabledException("User is disabled"));

		assertThrows(Exception.class, () -> authenticateService.authenticate(username, password));

	}
}