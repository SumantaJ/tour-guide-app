package com.tour.guide.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.tour.guide.domain.ApiMessage;
import com.tour.guide.utility.Constants;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ThereIsNoSuchHikeTrailException.class)
	@ResponseBody
	protected ApiMessage handleThereIsNoSuchUserException() {
		return getApiErrorMessage(Constants.MESSAGE_NO_HIKE_TRAIL);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BookingException.class)
	@ResponseBody
	protected ApiMessage handleBookingValidationException(final BookingException e) {
		return getApiErrorMessage(e.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidFormatException.class)
	@ResponseBody
	protected ApiMessage handleBookingValidationException(final InvalidFormatException e) {
		return getApiErrorMessage(e.getMessage());
	}

	private ApiMessage getApiErrorMessage(String message) {
		return new ApiMessage(message);
	}
}
