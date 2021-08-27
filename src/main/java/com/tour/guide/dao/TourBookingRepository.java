package com.tour.guide.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tour.guide.domain.Booking;

@RepositoryRestResource
public interface TourBookingRepository extends JpaRepository<Booking, Long> {

	List<Booking> findBybookingRefNumber(Long bookingRefId);

}
