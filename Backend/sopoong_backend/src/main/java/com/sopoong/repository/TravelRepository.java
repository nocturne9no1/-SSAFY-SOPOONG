package com.sopoong.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sopoong.model.entity.Travel;

public interface TravelRepository extends JpaRepository<Travel, Long> {

	Page<Travel> findByUser_UserIdIn(List<String> userIds, Pageable pageable);
	
}
