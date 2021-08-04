package com.sopoong.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sopoong.model.entity.User;


public interface UserRepository extends JpaRepository<User, String>{
	Optional<User> findByUserId(String userId);
	Optional<User> findByUserNickname(String userNickname);
	Optional<User> findByUserEmail(String userEmail);
}
