package com.getir.reading.service;

import java.util.List;

import com.getir.reading.entity.Address;
import com.getir.reading.payload.AdressRecordPayload.CustomerAdressRequest;

public interface AdressService {

	Address convertCustomerAdressRequest(CustomerAdressRequest request);

	List<Address> convertAddressRequestToEntity(List<CustomerAdressRequest> adressList);

}
