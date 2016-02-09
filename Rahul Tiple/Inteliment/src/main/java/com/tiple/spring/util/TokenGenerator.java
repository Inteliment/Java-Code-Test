package com.tiple.spring.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class TokenGenerator {
	public static void main(String args[]){
		String wsToken = createJWT("1", "rahul-issue", "subject-tokenIsuuer", 10000);
		System.out.println("wsToken :: "+wsToken);
		parseJWT(wsToken);
		// sysout

	}

	//Sample method to construct a JWT
	
	public static String createJWT(String id, String issuer, String subject, long ttlMillis) {
	
	//The JWT signature algorithm we will be using to sign the token
	SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	
	long nowMillis = System.currentTimeMillis();
	Date now = new Date(nowMillis);
	
	//We will sign our JWT with our ApiKey secret
	byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("testsecret");
	Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
	
	  //Let's set the JWT Claims
	JwtBuilder builder = Jwts.builder().setId(id)
	                                .setIssuedAt(now)
	                                .setSubject(subject)
	                                .setIssuer(issuer)
	                                .setAudience("test")
	                                .signWith(signatureAlgorithm, signingKey);
	
	 //if it has been specified, let's add the expiration
	if (ttlMillis >= 0) {
	    long expMillis = nowMillis + ttlMillis;
	    Date exp = new Date(expMillis);
	    builder.setExpiration(exp);
	}
	
	 //Builds the JWT and serializes it to a compact, URL-safe string
	return builder.compact();
	}
	//Sample method to validate and read the JWT
	public static void  parseJWT(String jwt) {
	//This line will throw an exception if it is not a signed JWS (as expected)
		Claims claims = Jwts.parser()         
		   .setSigningKey(DatatypeConverter.parseBase64Binary("testsecret"))
		   .parseClaimsJws(jwt).getBody();
		System.out.println("ID: " + claims.getId());
		System.out.println("Subject: " + claims.getSubject());
		System.out.println("Issuer: " + claims.getIssuer());
		System.out.println("Expiration: " + claims.getExpiration());
		System.out.println("Aud: " + claims.getAudience());
	
	}
}
