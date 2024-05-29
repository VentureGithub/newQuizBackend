package com.quizportal.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quizportal.entity.SubmitModel;

public interface Submitrepository extends JpaRepository<SubmitModel, Long>{

	SubmitModel findByEmail(String email);

	List<SubmitModel> findByDate(LocalDate now);

	List<SubmitModel> findByScore(int score);

	List<SubmitModel> findByScoreAndDate(int score, LocalDate date);

}
