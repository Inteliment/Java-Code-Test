package com.tiple.spring.service;


public interface TokenService {

	public void parseJWT(String jwt);
	public String createJWT(String id, String issuer, String subject, long ttlMillis);
		
}
