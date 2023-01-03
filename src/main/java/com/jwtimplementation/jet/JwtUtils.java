package com.jwtimplementation.jet;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils {
	long tokenValidity=10;
	String secret="somerandomsecret";
	public String generateToken(UserDetails userDetails) {
		
		Map<String , Object> claims=new HashMap<>();
		return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
		.setIssuedAt(new Date(System.currentTimeMillis()))
		.setExpiration(new Date(System.currentTimeMillis()+tokenValidity*60*60*1000))
		.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public boolean validateToken(String token,UserDetails userDetails) {
		boolean result=false;
			Claims claims=Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
			//username match and expiration match
			boolean expirationBoolean=claims.getExpiration().after(new Date(System.currentTimeMillis()));
			String usernameFromClaims=claims.getSubject();
			
			if(expirationBoolean&&usernameFromClaims.equals(userDetails.getUsername())) {
				result=true;
			}
			return result;
			
	}

	public String getUsernameFromToken(String token) {
		
			Claims claims=Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
			return claims.getSubject();
			
	}
}
