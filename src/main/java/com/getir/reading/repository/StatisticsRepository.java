package com.getir.reading.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.getir.reading.model.OrderStatistics;

public interface StatisticsRepository {

//	@Query(value = "SELECT MONTHNAME(created_date) month_name"
//			+ "  YEAR(created_date) year_month, COUNT(*) total_order_count, "
//			+ "  SUM(total_price) total_purchased_amount, SUM(quantity) total_book_count "
//			+ "FROM orders "
//			+ "GROUP BY MONTHNAME(created_date) ,YEAR(created_date) "
//			+ "ORDER BY MONTHNAME(created_date) ,YEAR(created_date) ", nativeQuery = true)
//	List<OrderStatistics> findByOrderStatistics();

	List<OrderStatistics> findByOrderStatisticsBetween(LocalDateTime startDate, LocalDateTime endDate);

	List<OrderStatistics> findByOrderStatisticsBefore(LocalDateTime endDate);
}
