package com.getir.reading.enums;

import com.getir.reading.exception.ResourceNotFoundException;

public enum BookType {
    FICTION("fincition"),
    NON_FICTION("non finction"),
    SCIENCE_FICTION("science fincition"),
    MYSTERY("mystery"),
	THRILLER("thriller");
    

	private final String code;

	public static BookType getCode(String code) {
		for (BookType value : values()) {
			if (value.getCode().equalsIgnoreCase(code)) {
				return value;
			}
		}
		throw new ResourceNotFoundException("Book  Type Enum", "code", code);
	}

	BookType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}