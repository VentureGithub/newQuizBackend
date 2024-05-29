package com.quizportal.entity;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name="question_details")
public class QuestionEntity {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="question_id")
	private Long questionId;
	@Column(name="question")
	private String question;
	//@ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
	//@CollectionTable(name = "options", joinColumns = @JoinColumn(name = "question_id"))
	@Column(name="option")
	private List<String> options;
	@Column(name="correct_answer")
	private String correctAnswer;
}
