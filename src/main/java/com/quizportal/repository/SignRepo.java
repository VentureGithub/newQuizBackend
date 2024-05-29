package com.quizportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quizportal.entity.SignEntity;
import java.util.Optional;

public interface SignRepo extends JpaRepository <SignEntity,Long> 
{
	Optional<SignEntity> findByEmail(String email);
	Optional<SignEntity> findByMobileNumber(String mobileNumber);
	SignEntity findByEmailAndFlag(String email, boolean b);
}

