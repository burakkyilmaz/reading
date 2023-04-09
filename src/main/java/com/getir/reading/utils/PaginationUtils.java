package com.getir.reading.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaginationUtils {

	public static Pageable getPageable(Direction direction, String orderColumn, Integer page, Integer limit) {

		if (page == null || page < 0 || limit == null) {

			return Pageable.unpaged();
		}


		if (direction != null && !Utils.isNullOrEmpty(orderColumn)) {

			Sort sortByColumn = Sort.by(direction, orderColumn);
			return PageRequest.of(page - 1, limit, sortByColumn);

		}

		return PageRequest.of(page - 1, limit);
	}

}
