package com.quizportal.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizportal.entity.QuestionEntity;
import com.quizportal.entity.ResultEntity;
import com.quizportal.entity.SignEntity;
import com.quizportal.model.QuestionDTO;

import com.quizportal.repository.QuestionRepo;
import com.quizportal.repository.ResultRepo;
import com.quizportal.repository.SignRepo;

@Service
public class QuestionService {

	
	@Autowired
	private QuestionRepo questionRepo;
	
	@Autowired
	private ResultRepo resultRepo;
	
	
	@Autowired
	private SignRepo signRepo;
	
	public List<QuestionDTO> saveQuestion(List<QuestionDTO> questionDTO)
	{
		
		List<QuestionEntity> q=new ArrayList<>();
		for(QuestionDTO qu: questionDTO)
		{
			QuestionEntity questionEntity=new QuestionEntity();
			questionEntity.setQuestion(qu.getQuestion());
			questionEntity.setOptions(qu.getOptions());
			questionEntity.setCorrectAnswer(qu.getCorrectAnswer());
			q.add(questionEntity);
		}
		questionRepo.saveAll(q);
		return questionDTO;
		
		
	}
	
	
	public List<QuestionDTO> getAllQuestions()
	{
		List<QuestionEntity> qE=questionRepo.findAll();
		List<QuestionDTO> qDTO=new ArrayList<>();
		for(QuestionEntity qDetails:qE)
		{
			QuestionDTO p=new QuestionDTO();
			p.setQuestionId(qDetails.getQuestionId());
			p.setQuestion(qDetails.getQuestion());
			p.setOptions(qDetails.getOptions());
			p.setCorrectAnswer(qDetails.getCorrectAnswer());
			qDTO.add(p);
		}
		return qDTO;
	}
}
	
	
	


