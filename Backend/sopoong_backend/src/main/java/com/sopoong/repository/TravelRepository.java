package com.sopoong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sopoong.model.entity.Travel;

public interface TravelRepository extends JpaRepository<Travel, Long> {
	
}
