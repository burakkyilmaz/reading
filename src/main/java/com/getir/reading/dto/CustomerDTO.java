package com.getir.reading.dto;

import java.util.ArrayList;
import java.util.List;

import com.getir.reading.entity.Customer;
import com.getir.reading.entity.Role;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

	@Id
	private Long id;

	private List<String> roleList = new ArrayList<>();

	private String username;

	private String email;


	public static CustomerDTO from(Customer customer) {

		return new CustomerDTO(customer.getId(), customer.getUser().getRoles().stream().map(Role::getName).toList(),
				customer.getUser().getUsername(),
				customer.getUser().getEmail());
	}

}
