package com.getir.reading.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.getir.reading.entity.Address;
import com.getir.reading.payload.AdressRecordPayload.CustomerAdressRequest;

@ExtendWith(MockitoExtension.class)
class AdressServiceImplTest {

	@InjectMocks
	private AdressServiceImpl adressService;

	@Test
	void shouldConvertCustomerAdressRequestToEntity() {
		CustomerAdressRequest request = new CustomerAdressRequest("Ankara", "Yenimahalle", "Ankara Cad.", "34100");

		Address result = adressService.convertCustomerAdressRequest(request);

		assertEquals(request.city(), result.getCity());
		assertEquals(request.state(), result.getState());
		assertEquals(request.street(), result.getStreet());
		assertEquals(request.zipCode(), result.getZipCode());
	}


	@Test
	void shouldConvertAddressRequestToEntity() {
		// given
		List<CustomerAdressRequest> adressList = new ArrayList<>();
		CustomerAdressRequest request1 = new CustomerAdressRequest("Ankara", "Yenimahalle", "Ankara Cad.", "34100");
		CustomerAdressRequest request2 = new CustomerAdressRequest("Ankara", "Yenimahalle", "Ankara Cad.", "34100");
		adressList.add(request1);
		adressList.add(request2);

		// when
		List<Address> result = adressService.convertAddressRequestToEntity(adressList);

		// then
		assertEquals(adressList.size(), result.size());
		assertEquals(adressList.get(0).city(), result.get(0).getCity());
		assertEquals(adressList.get(0).state(), result.get(0).getState());
		assertEquals(adressList.get(0).street(), result.get(0).getStreet());
		assertEquals(adressList.get(0).zipCode(), result.get(0).getZipCode());
		assertEquals(adressList.get(1).city(), result.get(1).getCity());
		assertEquals(adressList.get(1).state(), result.get(1).getState());
		assertEquals(adressList.get(1).street(), result.get(1).getStreet());
		assertEquals(adressList.get(1).zipCode(), result.get(1).getZipCode());
	}

}
