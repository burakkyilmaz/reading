package com.getir.reading.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import com.getir.reading.dto.CustomerDTO;
import com.getir.reading.dto.OrderDTO;
import com.getir.reading.entity.Address;
import com.getir.reading.entity.Customer;
import com.getir.reading.entity.Order;
import com.getir.reading.entity.User;
import com.getir.reading.payload.AdressRecordPayload.CustomerAdressRequest;
import com.getir.reading.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

	@Mock
	private UserDetailGetirServiceImpl userDetailsService;

	@Mock
	private RoleServiceImpl roleService;

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private OrderServiceImpl orderService;

	@Mock
	private AdressServiceImpl adressService;

	@InjectMocks
	private CustomerServiceImpl customerService;

	private Customer testCustomer;

	@Before
	void setUp() {
		testCustomer = new Customer();
		User user = new User();
		user.setUsername("testuser");
		user.setEmail("testuser@test.com");
		user.setPassword("testpassword");
		testCustomer.setUser(user);
		testCustomer.setId(1L);

		Address address1 = new Address();
		address1.setCity("TestCity1");
		address1.setId(1L);
		address1.setStreet("TestStreet1");
		address1.setZipCode("123456");

		Address address2 = new Address();
		address2.setCity("TestCity2");
		address2.setId(2L);
		address2.setStreet("TestStreet2");
		address1.setZipCode("123456");

		List<Address> addressList = new ArrayList<>();
		addressList.add(address1);
		addressList.add(address2);
		testCustomer.setAddresses(addressList);
	}

	@Test
	void testSaveCustomer() {

		String username = "burak";
		String email = "burak@example.com";
		String password = "Burak84396+";

		List<CustomerAdressRequest> addressRequests = new ArrayList<>();
		addressRequests.add(new CustomerAdressRequest("Ankara", "Yenimahalle", "Ankara Cad.", "34100"));
		addressRequests.add(new CustomerAdressRequest("Ankara", "Yenimahalle", "Ankara2 Cad.", "34100"));

		List<Address> addresses = Arrays.asList(new Address(), new Address());
		List<CustomerAdressRequest> addressRequestsWithNullFields = Arrays
				.asList(new CustomerAdressRequest("Ankara", "Yenimahalle", "Ankara3 Cad.", "34100"));

		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		when(userDetailsService.saveUser(username, email, password, Arrays.asList(roleService.findCustomerRole()))).thenReturn(user);

		Customer savedCustomer = new Customer();
		savedCustomer.setUser(user);
		savedCustomer.setAddresses(addresses);

		when(adressService.convertAddressRequestToEntity(addressRequests)).thenReturn(addresses);
		when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

		CustomerDTO customerDTO = customerService.saveCustomer(username, email, password, addressRequests);

		assertNotNull(customerDTO);
		assertEquals(username, customerDTO.getUsername());
		assertEquals(email, customerDTO.getEmail());

		verify(userDetailsService, times(1)).saveUser(username, email, password, Arrays.asList(roleService.findCustomerRole()));
		verify(adressService, times(1)).convertAddressRequestToEntity(addressRequests);
		verify(customerRepository, times(1)).save(any(Customer.class));
		verifyNoMoreInteractions(userDetailsService, adressService, customerRepository);
		
		// Test for null fields in address requests
		when(adressService.convertAddressRequestToEntity(addressRequestsWithNullFields)).thenReturn(Arrays.asList(new Address(), new Address()));
		customerDTO = customerService.saveCustomer(username, email, password, addressRequestsWithNullFields);
		
		verify(adressService, times(1)).convertAddressRequestToEntity(addressRequestsWithNullFields);
		verify(customerRepository, times(1)).save(any(Customer.class));
		verifyNoMoreInteractions(adressService, customerRepository);
	}

	@Test
	void testFindOrderForCustomer() {

		String username = "burak";
		String email = "burak@example.com";
		String password = "Burak84396+";
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);

		Customer customer = new Customer();
		customer.setId(1L);
		customer.setUser(user);
		List<Order> orderList = new ArrayList<>();
		Order order1 = new Order();
		order1.setCustomer(customer);

		Order order2 = new Order();
		order2.setCustomer(customer);
		orderList.add(order1);
		orderList.add(order2);

		when(customerService.findCustomerByUsername(username)).thenReturn(customer);
		when(orderService.findCustomerOrderList(customer, PageRequest.of(0, 10))).thenReturn(orderList);

		List<OrderDTO> orderDTOList = customerService.findOrderForCustomer(username, PageRequest.of(0, 10));

		assertNotNull(orderDTOList);
		assertEquals(2, orderDTOList.size());

		verify(customerService, times(1)).findCustomerByUsername(username);
		verify(orderService, times(1)).findCustomerOrderList(customer, PageRequest.of(0, 10));
		verifyNoMoreInteractions(customerService, orderService);
	}

}
