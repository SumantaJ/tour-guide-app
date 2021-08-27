package com.tour.guide.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tour.guide.domain.HikeTrail;

@RepositoryRestResource
public interface TourHikeTrailsRepository extends JpaRepository<HikeTrail, Long> {
}
