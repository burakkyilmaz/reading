package com.getir.reading.mapper;

import com.getir.reading.entity.Address;
import com.getir.reading.payload.AdressRecordPayload.CustomerAdressRequest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressMapper {

	public static Address toEntity(CustomerAdressRequest addressDTO) {
		Address adress = new Address();
		adress.setStreet(addressDTO.street());
		adress.setCity(addressDTO.city());
		adress.setState(addressDTO.state());
		adress.setZipCode(addressDTO.zipCode());
		return adress;
	}


}
