package com.direpredium.remotecontrol.security;

import com.direpredium.remotecontrol.model.UserType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class JwtAuthenticationFilter : OncePerRequestFilter() {

	@Autowired
	private lateinit var jwtGenerator: JwtGenerator

	@Autowired
	private lateinit var customUserDetailsService: CustomUserDetailsService


	@Throws(ServletException::class, IOException::class)
	override fun doFilterInternal(
		request: HttpServletRequest,
		response: HttpServletResponse,
		filterChain: FilterChain
	) {
		val token = getJWTfromRequest(request)
		if (token != null && jwtGenerator.validateToken(token)) {
			val username = jwtGenerator.getUsernameFromJWT(token)
			val userType = jwtGenerator.getUserTypeFromJWT(token)
			customUserDetailsService.setUserType(UserType.valueOf(userType))
			val userDetails: UserDetails = customUserDetailsService.loadUserByUsername(username)
			val authenticationToken = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)

			authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
			SecurityContextHolder.getContext().authentication = authenticationToken
		}
		filterChain.doFilter(request, response)
	}

	private fun getJWTfromRequest(request: HttpServletRequest): String? {
		val bearerToken = request.getHeader("Authorization")
		return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			bearerToken.substring(7)
		} else {
			null
		}
	}
}