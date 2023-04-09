package com.getir.reading.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.getir.reading.entity.Role;
import com.getir.reading.entity.User;
import com.getir.reading.exception.UserConflictException;
import com.getir.reading.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserDetailGetirServiceImplTest {
	@Mock
	private UserRepository userRepository;

	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	@InjectMocks
	private UserDetailGetirServiceImpl userDetailGetirService;

	@Test(expected = UsernameNotFoundException.class)
	public void testFindByUsernameWithInvalidUsername() {
		String username = "invalidUsername";
		when(userRepository.findByUsername(username)).thenReturn(null);
		userDetailGetirService.findByUsername(username);
	}

	@Test
	public void testFindByUsernameWithValidUsername() {
		String username = "validUsername";
		User user = new User();
		user.setUsername(username);
		when(userRepository.findByUsername(username)).thenReturn(user);
		User foundUser = userDetailGetirService.findByUsername(username);
		assertNotNull(foundUser);
		assertEquals(username, foundUser.getUsername());
	}

	@Test(expected = UserConflictException.class)
	public void testSaveUserWithExistingUser() {
		String userName = "existingUserName";
		String email = "existingEmail";
		String password = "password";
		List<Role> roleList = new ArrayList<>();
		when(userRepository.existsByUsernameOrEmail(userName, email)).thenReturn(true);
		userDetailGetirService.saveUser(userName, email, password, roleList);
	}

	@Test
	public void testSaveUserWithNonExistingUser() {
		String userName = "nonExistingUserName";
		String email = "nonExistingEmail";
		String password = "password";
		List<Role> roleList = new ArrayList<>();
		User newUser = new User();
		newUser.setUsername(userName);
		newUser.setEmail(email);
		newUser.setPassword(password);
		newUser.setRoles(roleList);
		when(userRepository.existsByUsernameOrEmail(userName, email)).thenReturn(false);
		when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
		when(userRepository.save(newUser)).thenReturn(newUser);
		User savedUser = userDetailGetirService.saveUser(userName, email, password, roleList);
		assertNotNull(savedUser);
		assertEquals(userName, savedUser.getUsername());
		assertEquals(email, savedUser.getEmail());
		assertEquals("encodedPassword", savedUser.getPassword());
		assertEquals(roleList, savedUser.getRoles());
	}

}
