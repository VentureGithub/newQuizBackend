package com.quizportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.quizportal.entity.ResultEntity;

public interface ResultRepo extends JpaRepository <ResultEntity,Long>
{

}
