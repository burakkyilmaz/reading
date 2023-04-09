package com.getir.reading.entity;


import com.getir.reading.entity.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(onMethod = @__({ @Deprecated }))
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Double price;

	private boolean isActive = true;

	@Column(nullable = false)
	private Integer stock;


}
