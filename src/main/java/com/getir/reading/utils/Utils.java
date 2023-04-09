package com.getir.reading.utils;

import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

	public static boolean isNullOrEmpty(String value) {
		return value == null || value.isEmpty();
	}

	public static boolean isNullOrEmpty(Long value) {
		return value == null || value == 0;
	}

	public static boolean isNullOrEmpty(MultipartFile file) {
		return file == null || file.isEmpty();
	}

	public static boolean isNull(Object object) {
		return object == null;
	}

	public static boolean isNullOrEmpty(String... values) {
		for (String value : values) {
			if (isNullOrEmpty(value)) {
				return true;
			}
		}
		return false;

	}

}
