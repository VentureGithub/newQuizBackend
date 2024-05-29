package com.quizportal.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quizportal.entity.SignEntity;
import com.quizportal.model.RegistrationModel;
import com.quizportal.model.Response2;
import com.quizportal.model.ResponseData;
import com.quizportal.model.ResponseWithObject;
import com.quizportal.repository.SignRepo;
import com.quizportal.services.RegistrationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
@Tag(name = "Registration-API")
public class RegistrationController {

	
	@Autowired
	private RegistrationService registrationService;
	
	
	@Autowired
	private SignRepo signRepo;
	private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RegistrationController.class);

	@PostMapping(value = "/registerToApp")
	@Operation(summary="create an account",description="this api is used to create an account")
	public ResponseEntity<?> registration(@RequestBody RegistrationModel reg) {

		String mobileStatus = registrationService.findByMobile(reg);
		if ("A".equals(mobileStatus)) {
			return Response2.generateResponse("Mobile Number already available", HttpStatus.OK, "201");
		} else {
			String regResponse = registrationService.registerUser(reg);
			log.info("Registration status of the user {} ", regResponse);
			if (regResponse.equalsIgnoreCase("existing")) {
				return Response2.generateResponse("User already exist ", HttpStatus.FOUND, "302");
			} else if (regResponse.equalsIgnoreCase("Error")) {
				return Response2.generateResponse("Something wnet wrong", HttpStatus.OK, "200");
			} else {
				Optional<SignEntity> signOptional=this.signRepo.findByEmail(reg.getEmail());
				SignEntity signEntity=signOptional.get();
				ResponseData responseData=new ResponseData();
				responseData.setEmail(signEntity.getEmail());
				responseData.setName(signEntity.getName());
				return new ResponseWithObject().generateResponse("Successfully register", HttpStatus.OK, "200",responseData);
			}
		}
	}

	
}

