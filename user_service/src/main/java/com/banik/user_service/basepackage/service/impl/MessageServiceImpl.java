package com.banik.user_service.basepackage.service.impl;

import com.banik.user_service.basepackage.response.ResponseCode;
import com.banik.user_service.basepackage.service.IMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements IMessageService {

	private final MessageSource messageSource;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getMessage(final ResponseCode code, final String[] args) {
		return messageSource.getMessage(code.name(), args, Locale.getDefault());
	}
}