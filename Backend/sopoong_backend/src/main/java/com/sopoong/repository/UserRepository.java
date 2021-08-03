package com.sopoong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sopoong.model.entity.User;


public interface UserRepository extends JpaRepository<User, String>{

}
