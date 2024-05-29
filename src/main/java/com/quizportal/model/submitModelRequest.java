package com.quizportal.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class submitModelRequest {

	private int score;
	private int totalQuestions;
	private String email;

}
