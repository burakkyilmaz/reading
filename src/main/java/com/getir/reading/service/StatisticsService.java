package com.getir.reading.service;

import java.util.List;

import com.getir.reading.model.OrderStatistics;

public interface StatisticsService {

	List<OrderStatistics> findMonthlyOrderStatistics();

	void clearOrderStatislicsInRedis();

}
