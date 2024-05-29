package com.quizportal.services;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quizportal.entity.SignEntity;
import com.quizportal.model.RegistrationModel;
import com.quizportal.repository.SignRepo;

@Service
public class RegistrationService {

	
	
	private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RegistrationService.class);
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	private SignRepo signRepo;
	
	@Autowired
	ModelMapper mapper;
	
	public String registerUser(RegistrationModel reg)
	{
		try
		{
		SignEntity usEntity=mapper.map(reg,SignEntity.class);
		usEntity.setName(reg.getName());
		usEntity.setEmail(reg.getEmail());
		usEntity.setFlag(false);
		usEntity.setPassword(bcryptEncoder.encode(reg.getPassword()));
		signRepo.save(usEntity);
		return "Success";
		} catch(Exception e)
		{
			e.printStackTrace();
			log.error("There is an error in registration : {}",e.getMessage());
			return "Error";
		}
		
	}
	public String findByMobile(RegistrationModel reg)
	{
		try
		{
			Optional<SignEntity> mobileCheck=signRepo.findByMobileNumber(reg.getMobileNumber());
		   if(mobileCheck.isPresent())
		   {
			   return "A";
		   }
		   else
		   {
			   return "NA";
		   }
		} catch(Exception e)
		{
			e.printStackTrace();
			log.error("There is an exception registering the user in mobile number: {}",e.getMessage());
			return "Error";
		}
	}
}
