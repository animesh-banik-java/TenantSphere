package com.banik.user_service.basepackage.service.impl;

import com.banik.user_service.basepackage.exception.ApplicationException;
import com.banik.user_service.basepackage.response.CustomPageImpl;
import com.banik.user_service.basepackage.response.Response;
import com.banik.user_service.basepackage.response.ResponseCode;
import com.banik.user_service.basepackage.service.IMessageService;
import com.banik.user_service.basepackage.service.IResponseService;
import com.google.common.collect.Lists;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ResponseServiceImpl implements IResponseService {

	private final IMessageService messageService;

	@Autowired
	private HttpServletRequest httpServletRequest;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<Response> success(final ResponseCode code, final String... args) {
		final String message = messageService.getMessage(code, args);
		return new ResponseEntity<>(Response.builder().status(HttpStatus.OK.value()).code(code).data(message)
				.path(httpServletRequest.getRequestURI()).build(), HttpStatus.OK);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<Response> error(final ResponseCode code, final String... args) {
		final String errorMessage = messageService.getMessage(code, args);

		if (code == ResponseCode.NOT_FOUND) {
			return error(HttpStatus.NOT_FOUND, code, Lists.newArrayList(errorMessage));
		} else if (code == ResponseCode.ACCESS_DENIED) {
			return error(HttpStatus.FORBIDDEN, code, Lists.newArrayList(errorMessage));
		} else {
			return error(HttpStatus.BAD_REQUEST, code, Lists.newArrayList(errorMessage));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<Response> error(final List<String> errors) {
		return error(HttpStatus.BAD_REQUEST, ResponseCode.BAD_REQUEST, errors);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<Response> error(ApplicationException applicationException) {
		return error(applicationException.getErrorCode(), applicationException.getMessage());
	}

	private ResponseEntity<Response> error(HttpStatus status, ResponseCode code, List<String> errors) {
		return new ResponseEntity<>(Response.builder().status(status.value()).code(code).errors(errors)
				.path(httpServletRequest.getRequestURI()).build(), status);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<Response> data(final Object entity) {
		return new ResponseEntity<>(Response.builder().status(HttpStatus.OK.value()).date(new Date()).code(ResponseCode.ENTITY).data(entity)
				.path(httpServletRequest.getRequestURI()).build(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> data(final Page<?> page) {
		return new ResponseEntity<>(Response.builder().status(HttpStatus.OK.value()).code(ResponseCode.ENTITY)
				.data(CustomPageImpl.builder().content(Objects.nonNull(page) ? page.getContent() : new ArrayList<>())
						.totalPages(Objects.nonNull(page) ? page.getTotalPages() : 0)
						.size(Objects.nonNull(page) ? page.getSize() : 0).number(Objects.nonNull(page) ? page.getNumber() : 0)
						.numberOfElements(Objects.nonNull(page) ? page.getNumberOfElements() : 0)
						.totalElements(Objects.nonNull(page) ? page.getTotalElements() : 0).build())
				.path(httpServletRequest.getRequestURI()).build(), HttpStatus.OK);
	}

}
