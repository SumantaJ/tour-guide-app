package com.tour.guide.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tour.guide.domain.ApiMessage;
import com.tour.guide.test.helper.TestConstants;


@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class TourServiceRestControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void getAllAvilableHikeTrails() throws Exception {
		mockMvc.perform(get(TestConstants.URI_GET_ALL_HIKE_TRAILS)).andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(3)));
	}
	
	@Test
	public void getAvilableHikeTrailById() throws Exception {
		mockMvc.perform(get(TestConstants.URI_GET_ALL_HIKE_TRAILS + "/" +TestConstants.VALID_HIKE_TRAIL_ID))
				.andExpect(status().isOk()).
				andExpect(jsonPath("$.id", is(1)));
	}
	
	@Test
	public void getAvilableHikeTrailById_whenIdNotFound() throws Exception {
		mockMvc.perform(get(TestConstants.URI_GET_ALL_HIKE_TRAILS + "/" +TestConstants.INVALID_HIKE_TRAIL_ID))
				.andExpect(status().isNotFound()).
				andExpect(jsonPath("$.message", containsString(TestConstants.MESSAGE_NO_HIKE_TRAIL)));
	}
	
	@Test
	public void saveBooking() throws Exception {
		mockMvc.perform(post(TestConstants.URI_BOOK_HIKE_TRAIL)
				.content(TestConstants.SAMPLE_BOOKING_REQUEST_PAYLOAD)
				.contentType(MediaType.APPLICATION_JSON))
		       	.andExpect(status().isCreated())
		       	.andExpect(jsonPath("$.message", containsString(TestConstants.MESSAGE_BOOKED_SUCESSFULLY)));
	}
	
	@Test
	public void viewBooking() throws Exception {
		final ObjectMapper objectMapper = new ObjectMapper();

		MvcResult mvcResult = mockMvc.perform(post(TestConstants.URI_BOOK_HIKE_TRAIL)
				.content(TestConstants.SAMPLE_BOOKING_REQUEST_PAYLOAD)
				.contentType(MediaType.APPLICATION_JSON))
		       	.andExpect(status().isCreated())
		       	.andReturn();
		
		String response = mvcResult.getResponse().getContentAsString();
		ApiMessage apiMessage = objectMapper.readValue(response, ApiMessage.class);
		
		mockMvc.perform(get(TestConstants.URI_BOOK_HIKE_TRAIL+ "/"+ apiMessage.getBookingRefNumber()))
				.andExpect(status().isOk())
		       	.andExpect(jsonPath("$.[0].bookingRefNumber", is(apiMessage.getBookingRefNumber())));
		
	}
	
	@Test
	public void cancelBooking() throws Exception {
		final ObjectMapper objectMapper = new ObjectMapper();

		MvcResult mvcResult = mockMvc.perform(post(TestConstants.URI_BOOK_HIKE_TRAIL)
				.content(TestConstants.SAMPLE_BOOKING_REQUEST_PAYLOAD)
				.contentType(MediaType.APPLICATION_JSON))
		       	.andExpect(status().isCreated())
		       	.andReturn();
		
		String response = mvcResult.getResponse().getContentAsString();
		ApiMessage apiMessage = objectMapper.readValue(response, ApiMessage.class);
		
		mockMvc.perform(put(TestConstants.URI_BOOK_HIKE_TRAIL+ "/"+ apiMessage.getBookingRefNumber()))
				.andExpect(status().isNoContent())
		       	.andExpect(jsonPath("$.bookingRefNumber", is(apiMessage.getBookingRefNumber())));
		
	}

}
