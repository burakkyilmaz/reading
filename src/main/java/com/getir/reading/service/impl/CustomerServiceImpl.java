package com.getir.reading.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.getir.reading.dto.CustomerDTO;
import com.getir.reading.dto.OrderDTO;
import com.getir.reading.entity.Address;
import com.getir.reading.entity.Customer;
import com.getir.reading.entity.Order;
import com.getir.reading.entity.User;
import com.getir.reading.exception.ResourceNotFoundException;
import com.getir.reading.payload.AdressRecordPayload.CustomerAdressRequest;
import com.getir.reading.repository.CustomerRepository;
import com.getir.reading.service.AdressService;
import com.getir.reading.service.CustomerService;
import com.getir.reading.service.OrderService;
import com.getir.reading.service.RoleService;
import com.getir.reading.service.UserDetailGetirService;
import com.getir.reading.utils.LogUtil;

import jakarta.transaction.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private UserDetailGetirService userDetailsService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private OrderService orderService;

	@Autowired
	private AdressService adressService;

	@Override
	@Transactional
	public CustomerDTO saveCustomer(String username, String email, String password,
			List<CustomerAdressRequest> adressList) {

		User user= userDetailsService.saveUser(username, email, password, Arrays.asList(roleService.findCustomerRole()));

		Customer customer=new Customer();
		customer.setUser(user);
		List<Address> adress = adressService.convertAddressRequestToEntity(adressList);
		adress.forEach(address -> address.setCustomer(customer));
		customer.setAddresses(adress);
		
		Customer saveCustomer = customerRepository.save(customer);
		LogUtil.info("Created customer with username {}", username);

		return CustomerDTO.from(saveCustomer);
	}


	@Override
	public List<OrderDTO> findOrderForCustomer(String userName, Pageable pageable) {
		Customer customer = findCustomerByUsername(userName);
		LogUtil.info("Fetching orders for customer with username {}", userName);
		return findOrderDTOForCustomer(pageable, customer);
	}

	@Override
	public List<OrderDTO> findOrderForCustomer(Long customerId, Pageable pageable) {
		Customer customer = findCustomer(customerId);
		LogUtil.info("Fetching orders for customer with ID {}", customerId);
		return findOrderDTOForCustomer(pageable, customer);
	}


	private List<OrderDTO> findOrderDTOForCustomer(Pageable pageable, Customer customer) {
		List<Order> orderList = orderService.findCustomerOrderList(customer, pageable);

		List<OrderDTO> orderDTOList = new ArrayList<>();
		orderList.forEach(order -> orderDTOList.add(OrderDTO.from(order)));
		return orderDTOList;
	}

	@Override
	public Long countCustomerOrders(String username) {
		LogUtil.info("Counting orders for customer with username {}", username);
		Customer customer = findCustomerByUsername(username);
		return orderService.countCustomerOrders(customer);
	}

	@Override
	public Long countCustomerOrders(Long customerId) {
		LogUtil.info("Counting orders for customer with ID {}", customerId);
		Customer customer = findCustomer(customerId);
		return orderService.countCustomerOrders(customer);
	}

	@Override
	public Customer findCustomer(Long id) {
		LogUtil.info("Fetching customer with ID {}", id);
		return customerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
	}

	@Override
	public Customer findCustomerByUsername(String username) {
		LogUtil.info("Fetching customer with username {}", username);
		User user = userDetailsService.findByUsername(username);
		
		return customerRepository.findByUser(user)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "username", username));
	}


}
