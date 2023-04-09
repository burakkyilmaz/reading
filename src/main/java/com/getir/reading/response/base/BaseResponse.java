package com.getir.reading.response.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {
	private Object data;
	private String message;
	private boolean isSuccess;


	public static BaseResponse success() {
		return new BaseResponse(null, "success", true);
	}

	public static BaseResponse success(Object data) {
		return new BaseResponse(data, "success", true);
	}

	public static BaseResponse created(Object data) {
		return new BaseResponse(data, "success", true);
	}

}