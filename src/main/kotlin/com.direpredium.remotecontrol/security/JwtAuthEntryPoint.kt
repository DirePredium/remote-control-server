package com.direpredium.remotecontrol.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
class JwtAuthEntryPoint : AuthenticationEntryPoint {

	@Throws(IOException::class, ServletException::class)
	override fun commence(
		request: HttpServletRequest,
		response: HttpServletResponse,
		authException: AuthenticationException
	) {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.message)
	}
}