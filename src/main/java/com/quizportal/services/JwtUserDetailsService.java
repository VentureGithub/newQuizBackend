package com.quizportal.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.quizportal.entity.SignEntity;
//import com.quizportal.repository.SignRepo;
import com.quizportal.repository.SignRepo;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	//@Autowired
	//UserEntranceRepo entranceRepo;

	@Autowired
	private SignRepo signRepo;

	Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		try {
			Optional<SignEntity> user = signRepo.findByEmail(email);
			if (user == null || !user.isPresent()) {
				throw new UsernameNotFoundException("User not found with username: " + email);

			} else {
				return new User(user.get().getEmail(), user.get().getPassword(), new ArrayList<>());
			}

		} catch (Exception e) {
			throw new UsernameNotFoundException("User not found with username: " + email);
		}

	}
}

