package com.banik.user_service.basepackage.controller;

import com.banik.user_service.basepackage.exception.ApplicationException;
import com.banik.user_service.basepackage.response.Response;
import com.banik.user_service.basepackage.response.ResponseCode;
import com.banik.user_service.basepackage.service.IResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Slf4j
public abstract class BaseController {

	@Autowired
	protected IResponseService responseService;

	/**
	 * This method will return success response with provided object. (Use this
	 * method when we have to return data)
	 *
	 * @param entity
	 * @return
	 */
	protected ResponseEntity<Response> data(final Object entity) {
		return this.responseService.data(entity);
	}

	/**
	 * This method will return success response with provided message. (Use this
	 * method when we have to return any success message)
	 *
	 * @param code
	 * @param fields
	 * @return
	 */
	protected ResponseEntity<Response> success(final ResponseCode code, final String... fields) {
		return this.responseService.success(code, fields);
	}

	/**
	 * This method will return error message with 500 status code. (Use this method
	 * when we have unknown exception)
	 *
	 * @param ex
	 * @return
	 */
	protected ResponseEntity<Response> error(Throwable ex) {
		log.error("Something really wrong happened!", ex);
		return this.responseService.error(new ApplicationException(ex));
	}

	/**
	 * This method will return error message with 400 status code. (Use this method
	 * when we have known exception or if we want to throw BadRequestException)
	 *
	 * @param code
	 * @param args
	 * @return
	 */
	protected ResponseEntity<Response> error(final ResponseCode code, final String... args) {
		return this.responseService.error(code, args);
	}

	/**
	 * This method will return error messages with 400 status code.
	 *
	 * @param errors
	 * @return
	 */
	protected ResponseEntity<Response> error(final List<String> errors) {
		return this.responseService.error(errors);
	}

	protected ResponseEntity<Response> data(final Page<?> page) {
		return this.responseService.data(page);
	}

}
