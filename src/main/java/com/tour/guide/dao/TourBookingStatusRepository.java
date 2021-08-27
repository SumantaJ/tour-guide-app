package com.tour.guide.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tour.guide.domain.BookingStatus;

@RepositoryRestResource
public interface TourBookingStatusRepository extends JpaRepository<BookingStatus, Long> {
	
	BookingStatus findByNameContainingIgnoreCase(String statusBooked);
}
