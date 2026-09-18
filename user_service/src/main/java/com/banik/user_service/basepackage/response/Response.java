package com.banik.user_service.basepackage.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Generic Response object for all controller.
 */
@Value
@Builder
@AllArgsConstructor
public class Response implements Serializable {

	private static final long serialVersionUID = 6090126975288080876L;

	Integer status;

	Date date;

	Object data;

	ResponseCode code;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	List<String> errors;

	String path;

	public Response() {
		this.status = HttpStatus.OK.value();
		this.path = null;
		this.data = null;
		this.date = new Date();
		this.errors = null;
		code = ResponseCode.ENTITY;

	}

	public static Response parse(final String json) {
		try {
			return new ObjectMapper().readValue(json, Response.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
