package com.quizportal.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegistrationModel {

	
	private String name;
	private String mobileNumber;
	private String email;
	private String password;
}