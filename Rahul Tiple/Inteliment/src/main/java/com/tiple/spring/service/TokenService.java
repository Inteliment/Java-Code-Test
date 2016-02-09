package com.tiple.spring.service;

import java.util.List;

import com.tiple.spring.model.Person;

public interface TokenService {

	public void parseJWT(String jwt);
	public String createJWT(String id, String issuer, String subject, long ttlMillis);
		
}
