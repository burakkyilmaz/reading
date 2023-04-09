package com.getir.reading.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.getir.reading.entity.Role;
import com.getir.reading.exception.ResourceNotFoundException;
import com.getir.reading.repository.RoleRepository;
import com.getir.reading.service.RoleService;
import com.getir.reading.utils.LogUtil;
import com.getir.reading.utils.SystemParameterUtils;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private SystemParameterUtils systemParameterUtils;

	@Override
	public Role findCustomerRole() {
		return findById(
				Long.valueOf(systemParameterUtils.getParameter(SystemParameterUtils.CUSTOMER_ROLE_ID).getValue()));
	}

	private Role findById(Long id) {
		LogUtil.info("Finding Role by id: {}", id);
		return roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role ", "id", id));
	}

}
