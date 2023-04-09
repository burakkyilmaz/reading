package com.getir.reading.utils;

public class RegexpUtils {

	private RegexpUtils() {
	}

	public static final String USERNAME_REGEXP = "^[a-zA-Z0-9._-]{3,}$";
	public static final String PASSWORD_REGEXP = "^(?=.*[0-9])(?=.*?[A-Z])(?=.*?[a-z]).{8,}$";

}
