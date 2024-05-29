package com.kkrew.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private String accessKey = "access키";
	private SecretKey accessSecretKey = Keys.hmacShaKeyFor(accessKey.getBytes(StandardCharsets.UTF_8));
	
	private int accessTokenValidTime = 1000 * 60 * 60 * 3;
	
	public String createToken(String id, String name) {
		
		Date now = new Date();
		
		String accessToken = Jwts.builder()
				.header().add("typ", "jwt").and()
				.claims().add("name", name).add("id", id)
				.expiration(new Date(now.getTime() + accessTokenValidTime)).and()
				.signWith(accessSecretKey)
				.compact();
		
		return accessToken;
	}
	
	public Jws<Claims> validate(String token) {
		return Jwts.parser().verifyWith(accessSecretKey).build().parseSignedClaims(token);
	}
}
