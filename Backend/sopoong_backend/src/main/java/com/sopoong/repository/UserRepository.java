package com.sopoong.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sopoong.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUserId(String userId);
	Optional<User> findByUserNickname(String userNickname);
	Optional<User> findByUserEmail(String userEmail);
	
	Optional<User> findUserByUserEmailAndUserPassword(String userEmail, String userPassword);
	
}
