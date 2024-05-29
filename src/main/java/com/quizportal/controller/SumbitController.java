package com.quizportal.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quizportal.entity.SignEntity;
import com.quizportal.entity.SubmitModel;
import com.quizportal.model.submitModelRequest;
import com.quizportal.repository.SignRepo;
import com.quizportal.repository.Submitrepository;

@RestController
@RequestMapping("/bhai")
@CrossOrigin(origins = "*")
public class SumbitController {

	@Autowired
	private Submitrepository repo;

	@Autowired
	private SignRepo signRepo;

	@PostMapping("/bhai-dekho")
	public String nn(@RequestBody submitModelRequest data) {
		System.out.print(data);

		SubmitModel resultData = new SubmitModel();

		Optional<SignEntity> userData = this.signRepo.findByEmail(data.getEmail());
		SubmitModel responseData = this.repo.findByEmail(data.getEmail());
		if (responseData == null) {
			resultData.setDate(LocalDate.now());
			resultData.setName(userData.get().getName());
			resultData.setScore(data.getScore());
			resultData.setEmail(data.getEmail());
			resultData.setTotalQuestions(data.getTotalQuestions());
			this.repo.save(resultData);
		} else {
			responseData.setDate(LocalDate.now());
			this.repo.save(responseData);
		}
		return "Masseges | data store successfully";
	}

	@GetMapping("/allData/by-date")
	public List<SubmitModel> getAlldata(@RequestParam("date") String date) throws IllegalAccessException {

		LocalDate dateData = LocalDate.parse(date);
		try {
			List<SubmitModel> responseData = this.repo.findByDate(dateData);
			return responseData;
		} catch (Exception e) {
			throw new IllegalAccessException("Internal server error");
		}
	}

	@GetMapping("/pass-student/by-score-and-Date")
	public List<SubmitModel> getAllPassStudent(@RequestParam("score") int score, @RequestParam("date") String date)
			throws IllegalAccessException {

		LocalDate dateData = LocalDate.parse(date);
		try {
			List<SubmitModel> responseData = this.repo.findByScoreAndDate(score, dateData);
			return responseData;
		} catch (Exception e) {
			throw new IllegalAccessException("Internal server error");
		}
	}

	@GetMapping("/pass-student/by-score")
	public List<SubmitModel> getAllPassStudent(@RequestParam("score") int score) throws IllegalAccessException {

		try {
			List<SubmitModel> responseData = this.repo.findByScore(score);
			return responseData;
		} catch (Exception e) {
			throw new IllegalAccessException("Internal server error");
		}
	}

	@GetMapping("/all-result")
	public List<SubmitModel> getAllResult() throws IllegalAccessException {

		try {
			List<SubmitModel> responseData = this.repo.findAll();
			return responseData;
		} catch (Exception e) {
			throw new IllegalAccessException("Internal server error");
		}
	}

	@GetMapping("/enable-user")
	public String enableUser(@RequestParam("email") String email) throws IllegalAccessException {

		try {
			Optional<SignEntity> data = this.signRepo.findByEmail(email);
			SignEntity entityData = data.get();
			entityData.setFlag(false);
			SignEntity responseData = this.signRepo.save(entityData);
			if (responseData == null) {
				throw new IllegalArgumentException("error");
			}
			return "Enable Successfully user for login !";

		} catch (Exception e) {
			throw new IllegalAccessException("Internal server error");
		}
	}

}
