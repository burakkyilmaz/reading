package com.getir.reading.repository.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.getir.reading.model.OrderStatistics;
import com.getir.reading.repository.StatisticsRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class StatisticsRepositoryImpl implements StatisticsRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<OrderStatistics> findByOrderStatisticsBefore(LocalDateTime endDate) {
		String hql = getOrderSelectSql();

		if (endDate != null) {
			hql += "WHERE o.createdDate < :endDate ";

		}
		hql += getOrderGroupSql();


		return entityManager.createQuery(hql, OrderStatistics.class).setParameter("endDate", endDate).getResultList();

	}

	@Override
	public List<OrderStatistics> findByOrderStatisticsBetween(LocalDateTime startDate, LocalDateTime endDate) {
		String hql = getOrderSelectSql();

		if (startDate != null && endDate != null) {
			hql += "WHERE o.createdDate BETWEEN :startDate AND :endDate ";

		}

		hql += getOrderGroupSql();

		return entityManager.createQuery(hql, OrderStatistics.class)
				.setParameter("startDate", startDate).setParameter("endDate", endDate).getResultList();

	}
	
	private String getOrderSelectSql() {
		//@formatter:off
	    return "SELECT NEW com.getir.reading.model.OrderStatistics(MONTHNAME(o.createdDate) month_name, "
	            + "YEAR(o.createdDate) year_month, "
	            + "COUNT(*) total_order_count, "
	            + "SUM(o.totalPrice)  total_purchased_amount, "
	            + "SUM(o.quantity) total_book_count) "
	            + "FROM Order o ";
	    //@formatter:on
	}

	private String getOrderGroupSql() {
		return "GROUP BY MONTHNAME(o.createdDate), YEAR(o.createdDate) "
				+ "ORDER BY MONTHNAME(o.createdDate), YEAR(o.createdDate)";
	}

}
