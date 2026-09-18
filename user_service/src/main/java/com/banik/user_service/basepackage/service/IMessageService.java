package com.banik.user_service.basepackage.service;


import com.banik.user_service.basepackage.response.ResponseCode;

public interface IMessageService {

	/**
	 * This method is responsible for get the message from the message bundle using
	 * provided code.
	 *
	 * @param code
	 * @param params
	 * @return
	 */
	String getMessage(final ResponseCode code, final String... params);

}
