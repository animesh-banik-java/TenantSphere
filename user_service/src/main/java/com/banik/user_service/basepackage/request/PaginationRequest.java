package com.banik.user_service.basepackage.request;


import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class PaginationRequest {

	private Integer pageNo = 0;

	private Integer pageSize = 10;

	private String sortBy = "id";

	private Sort.Direction sortDirection = Sort.Direction.DESC;

}