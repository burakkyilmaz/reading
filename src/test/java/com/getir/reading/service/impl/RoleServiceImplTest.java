package com.getir.reading.service.impl;

import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.getir.reading.entity.Role;
import com.getir.reading.entity.SystemParameter;
import com.getir.reading.exception.ResourceNotFoundException;
import com.getir.reading.repository.RoleRepository;
import com.getir.reading.utils.SystemParameterUtils;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private SystemParameterUtils systemParameterUtils;

	@InjectMocks
	private RoleServiceImpl roleService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testFindCustomerRole() {
		Long customerId = 1L;
		Role customerRole = new Role();
		customerRole.setId(customerId);
		customerRole.setName("Customer");

		SystemParameter customerRoleIdParameter = new SystemParameter();
		customerRoleIdParameter.setKey(SystemParameterUtils.CUSTOMER_ROLE_ID);
		customerRoleIdParameter.setValue(String.valueOf(customerId));


		when(roleRepository.findById(customerId)).thenReturn(java.util.Optional.of(customerRole));

		Role result = roleService.findCustomerRole();

		Assertions.assertEquals(customerRole.getId(), result.getId());
		Assertions.assertEquals(customerRole.getName(), result.getName());
	}

	@Test
	public void testFindCustomerRoleNotFound() {
		Long customerId = 1L;
		SystemParameter customerRoleIdParameter = new SystemParameter();
		customerRoleIdParameter.setKey(SystemParameterUtils.CUSTOMER_ROLE_ID);
		customerRoleIdParameter.setValue(String.valueOf(customerId));


		when(roleRepository.findById(customerId)).thenReturn(java.util.Optional.empty());

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			roleService.findCustomerRole();
		});
	}

}
