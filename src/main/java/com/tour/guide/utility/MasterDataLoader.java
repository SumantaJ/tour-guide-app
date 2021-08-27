package com.tour.guide.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tour.guide.dao.TourBookingStatusRepository;
import com.tour.guide.dao.TourHikeTrailsRepository;
import com.tour.guide.domain.BookingStatus;
import com.tour.guide.domain.HikeTrail;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MasterDataLoader {

	@Autowired
	TourHikeTrailsRepository tourHikeTrailsRepository;

	@Autowired
	TourBookingStatusRepository tourBookingStatusRepository;

	@EventListener(ApplicationReadyEvent.class)
	public void loadMasterData() throws Exception {
		
		// read json and write to db
		if (tourHikeTrailsRepository.count() == 0) {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<HikeTrail>> typeReference = new TypeReference<List<HikeTrail>>() {
			};

			InputStream inputStream = TypeReference.class.getResourceAsStream("/hike_trails.json");
			try {
				List<HikeTrail> users = mapper.readValue(inputStream, typeReference);
				tourHikeTrailsRepository.saveAll(users);
				log.info("Hike Trail Loaded!");
			} catch (IOException e) {
				log.error("Unable to load hike trail: " + e.getMessage());
				throw new Exception("Unable to load hike trails: " + e.getMessage());
			}
		}

		if (tourBookingStatusRepository.count() == 0) {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<BookingStatus>> typeReference = new TypeReference<List<BookingStatus>>() {
			};

			InputStream inputStream = TypeReference.class.getResourceAsStream("/booking_status.json");
			try {
				List<BookingStatus> users = mapper.readValue(inputStream, typeReference);
				tourBookingStatusRepository.saveAll(users);
				log.info("Booking Status Master Data Loaded!");
			} catch (IOException e) {
				log.error("Unable to load booking status: " + e.getMessage());
				throw new Exception("Unable to load booking status: " + e.getMessage());
			}
		}

	}

}
