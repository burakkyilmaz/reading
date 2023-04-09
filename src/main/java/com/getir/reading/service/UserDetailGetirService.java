package com.getir.reading.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.getir.reading.entity.Role;
import com.getir.reading.entity.User;

public interface UserDetailGetirService {

	User saveUser(String userName, String email, String password, List<Role> roleList);

	User findByUsername(String username) throws UsernameNotFoundException;

}
