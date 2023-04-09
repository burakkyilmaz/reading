package com.getir.reading.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.getir.reading.dto.CustomerDTO;
import com.getir.reading.dto.OrderDTO;
import com.getir.reading.entity.Customer;
import com.getir.reading.payload.AdressRecordPayload.CustomerAdressRequest;

public interface CustomerService {

	CustomerDTO saveCustomer(String username, String email, String password, List<CustomerAdressRequest> adressList);

	List<OrderDTO> findOrderForCustomer(String userName, Pageable pageable);

	List<OrderDTO> findOrderForCustomer(Long customerId, Pageable pageable);

	Long countCustomerOrders(String userId);

	Customer findCustomer(Long id);

	Long countCustomerOrders(Long customerId);

	Customer findCustomerByUsername(String Username);

}
