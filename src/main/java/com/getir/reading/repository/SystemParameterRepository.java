package com.getir.reading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.getir.reading.entity.SystemParameter;

@Repository
public interface SystemParameterRepository extends JpaRepository<SystemParameter, Long> {

	SystemParameter findByKey(String parameter);

}
