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

@Getter
@Setter
@Table(name = "system_parameters")
@Entity
public class SystemParameter extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(onMethod = @__({ @Deprecated }))
    private Long id;

	// only key and value were not accepted by h2

	@Column(name = "param_key")
	private String key;

	@Column(name = "param_value")
	private String value;

}
