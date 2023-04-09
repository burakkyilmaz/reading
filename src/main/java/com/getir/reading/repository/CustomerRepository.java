package com.getir.reading.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.getir.reading.entity.Customer;
import com.getir.reading.entity.User;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


	Optional<Customer> findByUser(User user);


}
