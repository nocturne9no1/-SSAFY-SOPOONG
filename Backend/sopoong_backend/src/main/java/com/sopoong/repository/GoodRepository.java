package com.sopoong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sopoong.model.entity.Good;

public interface GoodRepository extends JpaRepository<Good, Long>{

}
