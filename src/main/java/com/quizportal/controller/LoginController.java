package com.quizportal.controller;


import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.quizportal.configuration.JwtTokenUtil;
import com.quizportal.entity.SignEntity;
import com.quizportal.model.JwtRequest;
import com.quizportal.model.Response2;
import com.quizportal.model.ResponseData;
import com.quizportal.model.ResponseForToken;
import com.quizportal.model.ResponseWithObject;
import com.quizportal.repository.SignRepo;

import io.jsonwebtoken.impl.DefaultClaims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
@Tag(name = "Login-API")
public class LoginController {


	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LoginController.class);

	private UserDetails userDetails;
	
	@Autowired
	private SignRepo signRepo;

	
	// for json
	@PostMapping(value = "/authenticatebyjson")
	@Operation(summary="to authenticate by json",description="this api is used to login by email")
	public ResponseEntity<?> createAuthenticationTokenWithPath(@RequestBody JwtRequest authenticationRequest)
			throws Exception {
		
		
      SignEntity signData=this.signRepo.findByEmailAndFlag(authenticationRequest.getEmail(),true);
		
		if(signData!=null) {
			return ResponseForToken.generateResponse("you have login at onece", HttpStatus.INTERNAL_SERVER_ERROR, "500");
		}
		

		log.info("from by json variable login usrname:{} {}", authenticationRequest.getEmail(),
				authenticationRequest.getPassword());
		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);
		Optional<SignEntity> signOptional=this.signRepo.findByEmail(authenticationRequest.getEmail());
		
		SignEntity userData = signOptional.get();
		userData.setFlag(true);
		SignEntity responseUserData = this.signRepo.save(userData);
		
		
		SignEntity signEntity=signOptional.get();
		ResponseData responseData=new ResponseData();
		responseData.setEmail(signEntity.getEmail());
		responseData.setName(signEntity.getName());
		if (token != null) {
			return new ResponseWithObject().generateResponse(token, HttpStatus.OK, "200",responseData);
		} else {
			return ResponseForToken.generateResponse(" ", HttpStatus.INTERNAL_SERVER_ERROR, "500");
		}
	}

	@GetMapping(value = "/refreshtoken")
	@Operation(summary="to refresh the token",description="this api is used to refresh the token")
	public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {
		// From the HttpRequest get the claims
		DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");
		if (claims == null) {
			return Response2.generateResponse("Token is already valid ", HttpStatus.UNAUTHORIZED, "000");
		} else {
			Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
			String token = jwtTokenUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
			return ResponseForToken.generateResponse(token, HttpStatus.OK, "200");
		}
	}
	 
	
	   
	  
      
	private void authenticate(String email, String password) {
		Objects.requireNonNull(email);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (DisabledException e) {
			throw new DisabledException("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new UsernameNotFoundException("INVALID_CREDENTIALS", e);
		}
	}

	public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
		Map<String, Object> expectedMap = new HashMap<>();
		for (Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}
}
