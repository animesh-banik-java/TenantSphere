package com.banik.user_service.basepackage.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {
	private HttpStatus status;

	private Date timestamp;

	private String message;

	private String details;


	public ErrorDetails(HttpStatus status) {
		this.status = status;
	}
}
