package com.getir.reading.service.impl;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.getir.reading.model.OrderStatistics;
import com.getir.reading.repository.StatisticsRepository;
import com.getir.reading.utils.RedisUtils;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceTest {

	@Mock
	private StatisticsRepository statisticsRepository;

	@Mock
	private RedisUtils redisUtils;

	@InjectMocks
	private StatisticsServiceImpl statisticsService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindMonthlyOrderStatisticsFromCache() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime startOfMonth = now.with(TemporalAdjusters.firstDayOfMonth());

		List<OrderStatistics> currentMonthStatistics = new ArrayList<>();

		List<OrderStatistics> orderStatisticsList = new ArrayList<>();
		orderStatisticsList.add(new OrderStatistics());
		orderStatisticsList.add(new OrderStatistics());

		List<OrderStatistics> orderStatisticsListRedis = redisUtils
				.getListObject(redisUtils.getOrderStatislicsCacheName());
		when(orderStatisticsListRedis).thenReturn(orderStatisticsList);
		when(statisticsRepository.findByOrderStatisticsBetween(startOfMonth, now)).thenReturn(currentMonthStatistics);

		List<OrderStatistics> result = statisticsService.findMonthlyOrderStatistics();

		Assertions.assertEquals(orderStatisticsList.size() + currentMonthStatistics.size(), result.size());
	}

	@Test
	void testFindMonthlyOrderStatisticsFromDb() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime startOfMonth = now.with(TemporalAdjusters.firstDayOfMonth());

		List<OrderStatistics> currentMonthStatistics = new ArrayList<>();
		currentMonthStatistics.add(new OrderStatistics());
		currentMonthStatistics.add(new OrderStatistics());

		List<OrderStatistics> orderStatisticsList = new ArrayList<>();

		List<OrderStatistics> orderStatisticsListRedis = redisUtils
				.getListObject(redisUtils.getOrderStatislicsCacheName());
		when(orderStatisticsListRedis).thenReturn(orderStatisticsList);
		when(statisticsRepository.findByOrderStatisticsBetween(startOfMonth, now)).thenReturn(currentMonthStatistics);
		when(statisticsRepository.findByOrderStatisticsBefore(startOfMonth)).thenReturn(orderStatisticsList);

		List<OrderStatistics> result = statisticsService.findMonthlyOrderStatistics();

		Assertions.assertEquals(currentMonthStatistics.size(), result.size());
	}
}
