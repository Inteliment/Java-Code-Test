package com.tiple.spring.service;

import org.springframework.beans.factory.annotation.Autowired;

public class ServiceObject {
	
	@Autowired
	TokenServiceImpl tokenserviceImpl;
	
	
	
	public TokenServiceImpl getTokenserviceImpl() {
		return tokenserviceImpl;
	}



	public void setTokenserviceImpl(TokenServiceImpl tokenserviceImpl) {
		this.tokenserviceImpl = tokenserviceImpl;
	}



	public static TokenService  getTokenObject(){
		return new TokenServiceImpl();
		//return this.tokenserviceImpl;
	}

}
