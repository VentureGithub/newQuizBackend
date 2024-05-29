package com.quizportal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name="result_details")
public class ResultEntity {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="result_id")
	private Long resultId;
	@Column(name="name")
	private String name;
	@Column(name="email")
	private String email;
	@Column(name="score")
	private  int score;
	//@Column(name="total_questions_attempted")
	//private int totalQuestionsAttempted;
}
