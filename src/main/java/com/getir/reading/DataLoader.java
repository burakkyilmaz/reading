package com.getir.reading;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.getir.reading.entity.Role;
import com.getir.reading.entity.SystemParameter;
import com.getir.reading.entity.User;
import com.getir.reading.repository.CustomerRepository;
import com.getir.reading.repository.RoleRepository;
import com.getir.reading.repository.SystemParameterRepository;
import com.getir.reading.repository.UserRepository;
import com.getir.reading.utils.SystemParameterUtils;

@Component
public class DataLoader implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private SystemParameterRepository systemParameterRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;


	public void run(String... args) throws Exception {
		
		Role roleAdmin = new Role();
		roleAdmin.setName("ADMIN");
		roleRepository.save(roleAdmin);

		Role roleCustomer = new Role();
		roleCustomer.setName("CUSTOMER");
		roleRepository.save(roleCustomer);
		
		SystemParameter systemParameter=new SystemParameter();
		systemParameter.setKey(SystemParameterUtils.CUSTOMER_ROLE_ID);
		systemParameter.setValue(roleCustomer.getId().toString());
		systemParameterRepository.save(systemParameter);
		
		User userAdmin = new User();
		userAdmin.setUsername("admin");
		userAdmin.setPassword(passwordEncoder.encode("Admin123+"));
		userAdmin.setRoles(Arrays.asList(roleAdmin));
		userAdmin.setEmail("admin@admin.com");
		userRepository.save(userAdmin);

		User userTest = new User();
		userTest.setUsername("testuser");
		userTest.setPassword(passwordEncoder.encode("testpassworD789+"));
		userTest.setRoles(Arrays.asList(roleCustomer));
		userTest.setEmail("testuser@admin.com");
		userRepository.save(userTest);
		


    }
}