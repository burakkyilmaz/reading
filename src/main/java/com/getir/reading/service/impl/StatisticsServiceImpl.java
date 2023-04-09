package com.getir.reading.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.getir.reading.model.OrderStatistics;
import com.getir.reading.repository.StatisticsRepository;
import com.getir.reading.service.StatisticsService;
import com.getir.reading.utils.LogUtil;
import com.getir.reading.utils.RedisUtils;

@Service
public class StatisticsServiceImpl implements StatisticsService {

	private static final Integer REDIS_CACHE_DAYS = 30;

	@Autowired
	private StatisticsRepository statisticsRepository;

	@Autowired
	private RedisUtils redisUtils;

	@Override
	public List<OrderStatistics> findMonthlyOrderStatistics() {

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime startOfMonth = now.with(TemporalAdjusters.firstDayOfMonth());

		// in this way, this month's data will be retrieved from the redis of the
		// previous months from the db
		LogUtil.info("Retrieving monthly order statistics...");

		List<OrderStatistics> currentMonthStatistics = statisticsRepository.findByOrderStatisticsBetween(startOfMonth,
				now);

		List<OrderStatistics> orderStatisticsList = redisUtils.getListObject(redisUtils.getOrderStatislicsCacheName());

		// If cache is empty or does not contain last month statistics, get it from
		// database
		if (orderStatisticsList.isEmpty() || !isContainsLastMonth(orderStatisticsList)) {

			LogUtil.info("Cache is empty or does not contain last month statistics, getting from the database...");
			// let's update if not last month
			clearOrderStatislicsInRedis();
			List<OrderStatistics> findByOrderStatistics = statisticsRepository
					.findByOrderStatisticsBefore(startOfMonth);
			redisUtils.putListObject(redisUtils.getOrderStatislicsCacheName(), findByOrderStatistics, REDIS_CACHE_DAYS,
					ChronoUnit.DAYS);
			findByOrderStatistics.addAll(currentMonthStatistics);
			return findByOrderStatistics;

		}
		orderStatisticsList.addAll(currentMonthStatistics);
		LogUtil.info("Monthly order statistics successfully retrieved from Redis cache.");
		return orderStatisticsList;

	}

	private boolean isContainsLastMonth(List<OrderStatistics> orderStatisticsList) {
		LocalDate now = LocalDate.now();
		LocalDate startOfLastMonth = now.minusMonths(1).withDayOfMonth(1); // started date for last month
		for (OrderStatistics orderStatistics : orderStatisticsList) {
			if (orderStatistics.getYear().equals(startOfLastMonth.getYear())
					&& orderStatistics.getMonth().equals(startOfLastMonth.getMonth().name())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void clearOrderStatislicsInRedis()
	{
		redisUtils.clearList(redisUtils.getOrderStatislicsCacheName());
		LogUtil.info("Monthly order statistics successfully cleared from Redis cache.");
	}

}
