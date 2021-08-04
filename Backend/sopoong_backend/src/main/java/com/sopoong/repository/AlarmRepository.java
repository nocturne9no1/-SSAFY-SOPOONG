package com.sopoong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sopoong.model.entity.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Long>{

}
