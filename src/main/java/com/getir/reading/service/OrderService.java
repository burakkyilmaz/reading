package com.getir.reading.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.getir.reading.dto.OrderDTO;
import com.getir.reading.entity.Customer;
import com.getir.reading.entity.Order;

public interface OrderService {

	List<Order> findCustomerOrderList(Customer customer, Pageable pageable);

	Long countCustomerOrders(Customer customer);

	OrderDTO addOrder(Long bookId, Integer quantity);

	OrderDTO findById(Long id);

	List<OrderDTO> findByCreatedDateBetween(LocalDate startDate, LocalDate endDate);



}
