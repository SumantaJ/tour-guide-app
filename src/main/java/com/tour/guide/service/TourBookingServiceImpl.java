package com.tour.guide.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tour.guide.dao.TourBookingRepository;
import com.tour.guide.dao.TourBookingStatusRepository;
import com.tour.guide.dao.TourHikeTrailsRepository;
import com.tour.guide.domain.Booking;
import com.tour.guide.domain.BookingStatus;
import com.tour.guide.domain.Companion;
import com.tour.guide.domain.HikeTrail;
import com.tour.guide.exception.BookingException;
import com.tour.guide.exception.ThereIsNoSuchHikeTrailException;
import com.tour.guide.utility.Constants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class TourBookingServiceImpl implements TourBookingService {

	@Autowired
	private TourHikeTrailsRepository tourHikeTrailsRepository;

	@Autowired
	private TourBookingStatusRepository tourBookingStatusRepository;

	@Autowired
	private TourBookingRepository tourBookingRepository;

	@Override
	public List<HikeTrail> getAllHikeTrail() {
		return tourHikeTrailsRepository.findAll();
	}

	@Override
	public HikeTrail getHikeTrailById(Long id) {
		return tourHikeTrailsRepository.findById(id)
				.orElseThrow(() -> new ThereIsNoSuchHikeTrailException(Constants.MESSAGE_NO_HIKE_TRAIL));
	}

	@Override
	public Long saveBooking(Booking bookingDetails) {

		Optional<HikeTrail> hikeTrail = tourHikeTrailsRepository.findById(bookingDetails.getHikeTrail().getId());

		if (!hikeTrail.isPresent()) {
			throw new BookingException(Constants.MESSAGE_NO_HIKE_TRAIL);
		}

		if (StringUtils.isBlank(bookingDetails.getName())) {
			throw new BookingException(Constants.MESSAGE_INVALID_NAME);
		}

		HikeTrail trail = hikeTrail.get();
		checkIfAgeWithinLimitAndReturn(bookingDetails.getAge(), trail.getMinimumAge(), trail.getMaximumAge(),
				bookingDetails.getName());

		Long refId = generateBookingRefNumber(bookingDetails);
		log.info("saveBooking() :: generated booking reference number : " + refId);

		bookingDetails.setBookingRefNumber(refId);
		bookingDetails.setBookingStatus(getBookingStatus(Constants.STATUS_BOOKED));
		bookingDetails.setCreationDate(Instant.now());
		bookingDetails.setUpdateDate(Instant.now());

		List<Booking> bookingList = new ArrayList<>();
		bookingList.add(bookingDetails);

		// Check if hiker has any companion for this booking
		if (bookingDetails.getCompanion().size() != 0) {
			for (Companion companion : bookingDetails.getCompanion()) {
				Booking booking = new Booking();
				booking.setBookingRefNumber(refId);
				booking.setBookingStatus(getBookingStatus(Constants.STATUS_BOOKED));
				booking.setAge(checkIfAgeWithinLimitAndReturn(companion.getAge(), trail.getMinimumAge(),
						trail.getMaximumAge(), companion.getHikerName()));

				if (StringUtils.isBlank(companion.getHikerName())) {
					throw new BookingException(Constants.MESSAGE_INVALID_NAME);
				}

				booking.setName(companion.getHikerName());
				booking.setHikeTrail(bookingDetails.getHikeTrail());
				bookingDetails.setCreationDate(Instant.now());
				bookingDetails.setUpdateDate(Instant.now());
				bookingList.add(booking);
			}
		}
		log.info("saveBooking() :: booking list to be saved - " + bookingList.toString());
		List<Booking> savedBooking = tourBookingRepository.saveAll(bookingList);
		log.info("saveBooking() :: savedBooking - " + savedBooking.toString());

		// Return first reference number as it should be same for all in same booking
		return savedBooking.get(0).getBookingRefNumber();
	}

	@Override
	public List<Booking> getBookingDetailsByRefNo(Long id) {
		List<Booking> bookingDetails = tourBookingRepository.findBybookingRefNumber(id);

		if (bookingDetails.size() == 0) {
			throw new BookingException(Constants.MESSAGE_NO_BOOKING_FOUND);
		}
		return bookingDetails;
	}

	@Override
	public void cancelBooking(Long id) {
		List<Booking> bookingDetails = tourBookingRepository.findBybookingRefNumber(id);

		if (bookingDetails.size() == 0) {
			throw new BookingException(Constants.MESSAGE_NO_BOOKING_FOUND);
		}

		for (Booking booking : bookingDetails) {
			booking.setBookingStatus(getBookingStatus(Constants.STATUS_CANCELLED));
			booking.setUpdateDate(Instant.now());
		}

		tourBookingRepository.saveAll(bookingDetails);
	}

	// setting current timestamp in millisecond as booking reference number
	private Long generateBookingRefNumber(Booking bookingDetails) {
		return System.currentTimeMillis();
	}

	private int checkIfAgeWithinLimitAndReturn(int age, int min, int max, String name) {
		if (!(age >= min && age <= max)) {
			throw new BookingException(new StringBuilder().append("Hiker ").append(name)
					.append(" is not eligable for this trail. Allowed age limit is: ").append(min).append(" - ")
					.append(max).append(" please reinititate booking with eligable hikers").toString());
		}

		return age;
	}

	private BookingStatus getBookingStatus(String status) {
		return tourBookingStatusRepository.findByNameContainingIgnoreCase(status);
	}

}
