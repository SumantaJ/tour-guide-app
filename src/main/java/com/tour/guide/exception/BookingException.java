package com.tour.guide.exception;

public class BookingException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7556264325553546518L;

	public BookingException (String message) {
		super(message);
	}
}
