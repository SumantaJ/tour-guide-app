package com.tour.guide.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "ApiMessage", description = "Data object for response", oneOf = ApiMessage.class)
public class ApiMessage {

	private String message;
	private Long bookingRefNumber;
	private String[] error;
	
	public ApiMessage(String message) {
		this.message = message;
		this.error = new String[0];
	}
	
	public ApiMessage(String message, Long bookingRefNumber) {
		this.message = message;
		this.bookingRefNumber = bookingRefNumber;
	}
}
