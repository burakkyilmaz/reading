package com.getir.reading.service;

import com.getir.reading.response.AuthRecordResponse.AuthenticationResponse;

public interface AuthenticateService {

	AuthenticationResponse authenticate(String username, String password) throws Exception;

}
