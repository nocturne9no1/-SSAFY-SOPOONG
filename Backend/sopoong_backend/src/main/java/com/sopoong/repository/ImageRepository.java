package com.sopoong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sopoong.model.entity.Image;


public interface ImageRepository extends JpaRepository<Image, Long>{

}
