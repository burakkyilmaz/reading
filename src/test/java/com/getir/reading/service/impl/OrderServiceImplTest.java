package com.getir.reading.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.getir.reading.dto.OrderDTO;
import com.getir.reading.entity.Book;
import com.getir.reading.entity.Customer;
import com.getir.reading.entity.Order;
import com.getir.reading.enums.OrderStatus;
import com.getir.reading.repository.OrderRepository;
import com.getir.reading.service.BookService;
import com.getir.reading.service.CustomerService;
import com.getir.reading.utils.JwtTokenUtil;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private BookService bookService;

	@Mock
	private CustomerService customerService;

	@Mock
	private JwtTokenUtil jwtTokenUtil;

	@InjectMocks
	private OrderServiceImpl orderService;

	private Customer customer;
	private Book book;

	@Before
	public void setUp() {
		customer = new Customer();
		customer.setId(1L);

		book = new Book();
		book.setId(1L);
		book.setPrice(10.0);
		book.setStock(5);
	}

	@Test
	public void testFindCustomerOrderList() {
		Pageable pageable = PageRequest.of(0, 10);
		List<Order> orders = Collections.singletonList(new Order());
		when(orderRepository.findByCustomer(customer, pageable)).thenReturn(orders);

		List<Order> result = orderService.findCustomerOrderList(customer, pageable);

		assertEquals(orders, result);
		verify(orderRepository).findByCustomer(customer, pageable);
	}

	@Test
	public void testCountCustomerOrders() {
		Long expected = 3L;
		when(orderRepository.countByCustomer(customer)).thenReturn(expected);

		Long result = orderService.countCustomerOrders(customer);

		assertEquals(expected, result);
		verify(orderRepository).countByCustomer(customer);
	}

	@Test
	public void testAddOrder() {
		String username = "testuser";
		String token = "testtoken";
		when(jwtTokenUtil.getUsernameFromToken()).thenReturn(username);
		when(customerService.findCustomerByUsername(username)).thenReturn(customer);
		when(bookService.findBookForAvailableStock(book.getId())).thenReturn(book);

		Integer quantity = 1;
		OrderDTO orderDTO = orderService.addOrder(book.getId(), quantity);

		Order expectedOrder = new Order();
		expectedOrder.setBook(book);
		expectedOrder.setCustomer(customer);
		expectedOrder.setStatus(OrderStatus.PENDING);
		expectedOrder.setTotalPrice(book.getPrice()*quantity);
		expectedOrder.setQuantity(quantity);
		when(orderRepository.save(expectedOrder)).thenReturn(expectedOrder);

		assertEquals(OrderDTO.from(expectedOrder), orderDTO);
		verify(jwtTokenUtil).getUsernameFromToken();
		verify(customerService).findCustomerByUsername(username);
		verify(bookService).findBookForAvailableStock(book.getId());
		verify(orderRepository).save(expectedOrder);
		verify(bookService).updateBookStock(book, book.getStock() - 1);
	}

	@Test
	public void testFindById() {
		Long orderId = 1L;
		Order order = new Order();
		order.setId(orderId);
		when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

		OrderDTO orderDTO = orderService.findById(orderId);

		assertEquals(OrderDTO.from(order), orderDTO);
		verify(orderRepository).findById(orderId);
	}


    @Test
    public void testFindByCreatedDateBetween() {
        LocalDateTime startDate = LocalDate.of(2023, 4, 1).atStartOfDay();
        LocalDateTime endDate = LocalDate.of(2023, 4, 9).atTime(LocalTime.MAX);

        Order order1 = new Order();
        order1.setId(1L);
        order1.setCreatedDate(LocalDateTime.of(2023, 4, 2, 12, 0));

        Order order2 = new Order();
        order2.setId(2L);
        order2.setCreatedDate(LocalDateTime.of(2023, 4, 6, 18, 0));

        List<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);

        when(orderRepository.findByCreatedDateBetween(startDate, endDate)).thenReturn(orderList);

        List<OrderDTO> orderDTOList = orderService.findByCreatedDateBetween(LocalDate.of(2023, 4, 1), LocalDate.of(2023, 4, 9));

        Assertions.assertEquals(2, orderDTOList.size());
        Assertions.assertEquals(1L, orderDTOList.get(0).getId());
        Assertions.assertEquals(2L, orderDTOList.get(1).getId());
    }
}
	

