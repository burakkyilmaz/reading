package com.getir.reading.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.getir.reading.entity.Address;
import com.getir.reading.mapper.AddressMapper;
import com.getir.reading.payload.AdressRecordPayload.CustomerAdressRequest;
import com.getir.reading.service.AdressService;

@Service
public class AdressServiceImpl implements AdressService {

	@Override
	public Address convertCustomerAdressRequest(CustomerAdressRequest request)
	{
		Address address = new Address();
		address.setCity(request.city());
		address.setState(request.state());
		address.setStreet(request.street());
		address.setZipCode(request.zipCode());
		return address;
	}
	
	@Override
	public List<Address> convertAddressRequestToEntity(List<CustomerAdressRequest> adressList) {
		List<Address> addressList = new ArrayList<>();
		adressList.forEach(adressRequest -> addressList.add(AddressMapper.toEntity(adressRequest)));
		return addressList;
	}

}
