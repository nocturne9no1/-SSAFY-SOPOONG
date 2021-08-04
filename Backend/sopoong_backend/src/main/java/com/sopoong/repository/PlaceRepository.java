package com.sopoong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sopoong.model.entity.Place;

public interface PlaceRepository extends JpaRepository<Place, Long>{

}
