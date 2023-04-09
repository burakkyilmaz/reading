package com.getir.reading.dto;

import com.getir.reading.entity.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderDTO {

	private Long id;
	private Integer quantity;
	private Double totalPrice;
	private BookDTO bookDTO;


	public static OrderDTO from(Order order) {

		return new OrderDTO(order.getId(), order.getQuantity(), order.getTotalPrice(),
				BookDTO.forOrder(order.getBook()));
	}

}
