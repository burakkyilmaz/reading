package com.getir.reading.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.getir.reading.entity.User;
import com.getir.reading.response.AuthRecordResponse.AuthenticationResponse;
import com.getir.reading.service.AuthenticateService;
import com.getir.reading.service.UserDetailGetirService;
import com.getir.reading.utils.JwtTokenUtil;
import com.getir.reading.utils.LogUtil;

@Service
public class AuthenticateServiceImpl implements AuthenticateService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailGetirService userDetailGetirService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public AuthenticationResponse authenticate(String username, String password) throws Exception {
		LogUtil.info("Authenticating user with username: {}", username);

		Authentication authentication = null;
		try {
			authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);

		User userDetails = userDetailGetirService.findByUsername(username);
		LogUtil.info("User with username {} is authenticated successfully.", username);


		return new AuthenticationResponse(jwtTokenUtil.generateToken(userDetails));
	}

}
