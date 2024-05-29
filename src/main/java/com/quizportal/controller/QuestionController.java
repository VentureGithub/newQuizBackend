package com.quizportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quizportal.model.QuestionDTO;
import com.quizportal.model.ResponseWithList;
import com.quizportal.model.ResponseWithObject;
import com.quizportal.services.QuestionService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@Tag(name = "Quiz-API")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@PostMapping(value = "/addQuestion")
	public ResponseEntity<?> addQuestions(@RequestBody List<QuestionDTO> questionDTO) {
		List<QuestionDTO> responseDTO = questionService.saveQuestion(questionDTO);
		if (responseDTO != null) {
			return new ResponseWithObject().generateResponse("Questions are already saved", HttpStatus.OK, "200",
					responseDTO);
		} else {
			return new ResponseWithObject().generateResponse("Questions are not already saved",
					HttpStatus.INTERNAL_SERVER_ERROR, "500", "");
		}

	}

	@GetMapping(value = "/getAllQuestions")
	public ResponseEntity<?> findQuestions() {
		List<QuestionDTO> qDTO = questionService.getAllQuestions();
		return new ResponseWithList().generateResponse("Questions list are get disaplayed", HttpStatus.OK, "200", qDTO);
	}

}
