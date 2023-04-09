package com.getir.reading.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.getir.reading.entity.Customer;
import com.getir.reading.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByCustomer(Customer customer, Pageable pageable);

	Long countByCustomer(Customer customer);

	@Modifying
	@Query("UPDATE Order o SET o.status = :status WHERE o.id = :id AND o.version = :version")
	void updateStatus(@Param("id") Long id, @Param("status") String status, @Param("version") Long version);

	List<Order> findByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

}
