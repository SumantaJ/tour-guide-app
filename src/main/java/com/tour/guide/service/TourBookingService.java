package com.tour.guide.service;

import java.util.List;

import com.tour.guide.domain.Booking;
import com.tour.guide.domain.HikeTrail;

public interface TourBookingService {

	List<HikeTrail> getAllHikeTrail();

	HikeTrail getHikeTrailById(Long id);

	Long saveBooking(Booking booking);

	List<Booking> getBookingDetailsByRefNo(Long bookingRefId);

	void cancelBooking(Long id);
}