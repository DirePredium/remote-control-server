package com.direpredium.remotecontrol.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
class JwtGenerator {

	fun generateToken(authentication: Authentication, userType: String): String {
		val username = authentication.name
		val currentDate = Date()
		val expiryDate = Date(currentDate.time + SecurityConstants.JWT_EXPIRATION_ACCESS)

		return Jwts.builder()
			.setSubject(username)
			.setIssuedAt(currentDate)
			.setExpiration(expiryDate)
			.signWith(SignatureAlgorithm.HS256, SecurityConstants.JWT_SECERT)
			.claim("usertype", userType)
			.compact()
	}

	fun getUsernameFromJWT(token: String): String {
		val claims: Claims = Jwts.parser()
			.setSigningKey(SecurityConstants.JWT_SECERT)
			.parseClaimsJws(token)
			.body
		return claims.subject
	}

	fun getUserTypeFromJWT(token: String): String {
		val claims: Claims = Jwts.parser()
			.setSigningKey(SecurityConstants.JWT_SECERT)
			.parseClaimsJws(token)
			.body
		return claims["usertype"].toString()
	}

	fun validateToken(token: String): Boolean {
		return try {
			Jwts.parser().setSigningKey(SecurityConstants.JWT_SECERT).parseClaimsJws(token)
			true
		} catch (ex: Exception) {
			throw AuthenticationCredentialsNotFoundException("JWT token is not valid: $token")
		}
	}
}