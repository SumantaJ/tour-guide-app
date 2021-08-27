package com.tour.guide.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tour.guide.domain.ApiMessage;
import com.tour.guide.domain.Booking;
import com.tour.guide.domain.HikeTrail;
import com.tour.guide.service.TourBookingService;
import com.tour.guide.utility.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/hike", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name = "Hike Tour Booking", description = "The Hike Tour Booking API")
public class TourServiceRestController {

	@Autowired
	private TourBookingService tourBookingService;

	public static double getTiming(Instant start, Instant end) {
		return Duration.between(start, end).toMillis();
	}

	@Operation(summary = "Fetch All Avilable HikeTrail", description = "endpoint for fetching all hike trails")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "HikeTrail fetched"),
			@ApiResponse(responseCode = "404", description = "Unable to find hike trails") })
	@GetMapping("/trails")
	@ResponseStatus(HttpStatus.OK)
	public List<HikeTrail> getAllHikeTrails() {
		log.info("getAllHikeTrails() - start");
		List<HikeTrail> hikeTrailsList = tourBookingService.getAllHikeTrail();
		log.info("getAllHikeTrails() - end");
		return hikeTrailsList;
	}

	@Operation(summary = "Fetch HikeTrail By ID", description = "endpoint for fetching hike trail by id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "HikeTrail fetched"),
			@ApiResponse(responseCode = "404", description = "Unable to find hike trail") })
	@GetMapping("/trails/{id}")
	@ResponseStatus(HttpStatus.OK)
	public HikeTrail getHikeTrailById(
			@Parameter(description = "Id of the HikeTrail to be obtained. Cannot be empty.", required = true) @NotNull @PathVariable Long id) {
		log.info("getHikeTrailById() - start: id = {}" + id);
		HikeTrail hikeTrail = tourBookingService.getHikeTrailById(id);
		log.info("getHikeTrailById() - end: id = {}" + id);
		return hikeTrail;
	}

	@Operation(summary = "Add a new booking", description = "endpoint for creating an booking")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Booking created"),
			@ApiResponse(responseCode = "403", description = "Invalid input") })
	@PostMapping("/book")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiMessage saveBooking(
			@Parameter(description = "Booking", required = true) @NotNull @RequestBody Booking booking) {
		log.info("saveBooking() - start: booking = {}", booking);
		Long savedBookingRefNumber = tourBookingService.saveBooking(booking);

		log.info("saveBooking() - end: savedBookingRefNumber = {}", savedBookingRefNumber);
		return new ApiMessage(Constants.MESSAGE_BOOKED_SUCESSFULLY, savedBookingRefNumber);
	}

	@Operation(summary = "Fetch Booking By Reference Number", description = "endpoint for fetching booking details by reference number")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Booking details fetched"),
			@ApiResponse(responseCode = "404", description = "Unable to find booking with the given information") })
	@GetMapping("/book/{bookingRefNumber}")
	@ResponseStatus(HttpStatus.OK)
	public List<Booking> getBookingDetailsByRefNo(
			@Parameter(description = "Booking reference number of the booking details to be obtained. Cannot be empty.", required = true) @NotNull @PathVariable Long bookingRefNumber) {
		log.info("getBookingDetailsByRefNo() - start");
		List<Booking> bookingList = tourBookingService.getBookingDetailsByRefNo(bookingRefNumber);
		log.info("getBookingDetailsByRefNo() - end");
		return bookingList;
	}

	@Operation(summary = "Cancel a Booking", description = "need to cancel a hike trail booking")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Cancellation sucessfull"),
			@ApiResponse(responseCode = "404", description = "Booking not found") })
	@PutMapping("/book/{bookingRefNumber}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ApiMessage cancelBooking(
			@Parameter(description = "bookingRefNumber of the Booking to be cancelled. Cannot be empty.", required = true) @NotNull @PathVariable Long bookingRefNumber) {
		log.info("cancelBooking() - start: bookingRefNumber = {}", bookingRefNumber);
		tourBookingService.cancelBooking(bookingRefNumber);
		log.info("cancelBooking() - end: bookingRefNumber = {}", bookingRefNumber);
		return new ApiMessage(Constants.MESSAGE_CANCELLED_SUCESSFULLY, bookingRefNumber);
	}
}
