package com.getir.reading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.getir.reading.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {


}
