package com.getir.reading.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.getir.reading.dto.OrderDTO;
import com.getir.reading.entity.Book;
import com.getir.reading.entity.Customer;
import com.getir.reading.entity.Order;
import com.getir.reading.enums.OrderStatus;
import com.getir.reading.exception.ResourceNotFoundException;
import com.getir.reading.repository.OrderRepository;
import com.getir.reading.service.BookService;
import com.getir.reading.service.CustomerService;
import com.getir.reading.service.OrderService;
import com.getir.reading.utils.JwtTokenUtil;
import com.getir.reading.utils.LogUtil;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private BookService bookService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public List<Order> findCustomerOrderList(Customer customer, Pageable pageable) {
		LogUtil.info("Finding orders of customer: {}", customer.getId());
		return orderRepository.findByCustomer(customer, pageable);
	}

	@Override
	public Long countCustomerOrders(Customer customer) {
		LogUtil.info("Counting orders of customer: {}", customer.getId());
		return orderRepository.countByCustomer(customer);
	}
	
	@Override
	@Transactional
	public OrderDTO addOrder(Long bookId,Integer quantity) {
		Book book = bookService.findBookForAvailableStock(bookId);

		Order order = new Order();
		order.setBook(book);
		order.setCustomer(customerService.findCustomerByUsername(jwtTokenUtil.getUsernameFromToken()));
		order.setStatus(OrderStatus.PENDING);
		order.setTotalPrice(book.getPrice() * quantity);
		order.setQuantity(quantity);
		order = orderRepository.save(order);

		Integer newStock = book.getStock() - 1;
		bookService.updateBookStock(book, newStock);

		LogUtil.info("New order added with id: {}", order.getId());

		return OrderDTO.from(order);
	}

	@Override
	public OrderDTO findById(Long id)
	{
		LogUtil.info("Finding order by id: {}", id);
		Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
		return OrderDTO.from(order);
	}

	@Override
	public List<OrderDTO> findByCreatedDateBetween(LocalDate startDate, LocalDate endDate) {
		LogUtil.info("Finding orders created between {} and {}", startDate, endDate);
		List<Order> orderList = orderRepository.findByCreatedDateBetween(startDate.atStartOfDay(),
				endDate.atTime(LocalTime.MAX));

		List<OrderDTO> orderDTOList = new ArrayList<>();
		orderList.forEach(order -> orderDTOList.add(OrderDTO.from(order)));

		return orderDTOList;
	}

}
