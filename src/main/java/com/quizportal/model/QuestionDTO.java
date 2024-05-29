package com.quizportal.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QuestionDTO {

	
	
	private Long questionId;
	private String question;
	private List<String> options;
	private String correctAnswer;
}
