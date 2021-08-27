package com.tour.guide.exception;

public class ThereIsNoSuchHikeTrailException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4740645588371451492L;
	
	public ThereIsNoSuchHikeTrailException (String message) {
		super(message);
	}
}
