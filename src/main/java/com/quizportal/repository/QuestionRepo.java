package com.quizportal.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quizportal.entity.QuestionEntity;


public interface QuestionRepo  extends JpaRepository <QuestionEntity,Long> 
{
Optional<QuestionEntity> findByQuestionId(Long questionId);
}
