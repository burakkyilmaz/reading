package com.getir.reading.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ParameterDTO implements Serializable {

	private static final long serialVersionUID = -4051679318758585871L;

	private String key;

	private String value;

}
