package com.getir.reading.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogUtil {

	private static final Logger logger = LoggerFactory.getLogger(LogUtil.class);

	public static void info(String message) {
		logger.info(message);
	}

	public static void info(String message, Object arg) {
		logger.info(message, arg);
	}

	public static void info(String message, Object arg1, Object arg2) {
		logger.info(message, arg1, arg2);
	}

	public static void debug(String message) {
		logger.debug(message);
	}

	public static void warn(String message) {
		logger.warn(message);
	}

	public static void warn(String message, Throwable throwable) {
		logger.warn(message, throwable);
	}

	public static void error(String message) {
		logger.error(message);
	}

	public static void error(String message, Throwable throwable) {
		logger.error(message, throwable);
	}
}
