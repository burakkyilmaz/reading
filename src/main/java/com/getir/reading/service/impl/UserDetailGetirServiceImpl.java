package com.getir.reading.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.getir.reading.entity.Role;
import com.getir.reading.entity.User;
import com.getir.reading.exception.UserConflictException;
import com.getir.reading.repository.UserRepository;
import com.getir.reading.service.UserDetailGetirService;
import com.getir.reading.utils.LogUtil;

@Service
public class UserDetailGetirServiceImpl implements UserDetailGetirService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public User findByUsername(String username) throws UsernameNotFoundException {
		LogUtil.info("Finding user by username: {}", username);
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}

		return user;
	}

	@Override
	public User saveUser(String userName, String email, String password, List<Role> roleList) {

		if (userRepository.existsByUsernameOrEmail(userName, email)) {
			throw new UserConflictException("A user with the same username or email already exists.");
		}

		User user = new User();
		user.setUsername(userName);
		user.setEmail(email);
		user.setRoles(roleList);
		String encodedPasswod = passwordEncoder.encode(password);
		user.setPassword(encodedPasswod);
		user = userRepository.save(user);
		LogUtil.info("User saved: {}", user.getUsername());
		return user;
	}

}
