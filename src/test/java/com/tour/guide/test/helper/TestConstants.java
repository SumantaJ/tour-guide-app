package com.tour.guide.test.helper;

public class TestConstants {
	
	public static final String URI_GET_ALL_HIKE_TRAILS = "/hike/trails";
	public static final String URI_BOOK_HIKE_TRAIL = "/hike/book";
	public static final Long VALID_HIKE_TRAIL_ID = 1L;
	public static final Long INVALID_HIKE_TRAIL_ID = 10L;
	public static final String STATUS_CANCELLED = "Cancelled";
	
	public static final String MESSAGE_BOOKED_SUCESSFULLY = "Booked sucessfully";
	public static final String MESSAGE_NO_HIKE_TRAIL = "Selected hike trail does not exist, please choose another trail";
	public static final String MESSAGE_INVALID_NAME = "Name cannot be null or empty";
	
	public static final String SAMPLE_BOOKING_REQUEST_PAYLOAD = "{\n" + "    \"name\" : \"Suman Kumar\",\n"
			+ "    \"hikeTrail\": {\n" + "        \"id\": \"2\"\n" + "    },\n" + "    \"age\": 50\n" + "}";

}
