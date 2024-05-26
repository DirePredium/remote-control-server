package com.direpredium.remotecontrol.security;

import com.direpredium.remotecontrol.model.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfig {

	@Autowired
	private lateinit var customUserDetailsService: CustomUserDetailsService

	@Autowired
	private lateinit var jwtAuthEntryPoint: JwtAuthEntryPoint

	@Bean
	fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
		http
			.csrf().disable()
			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthEntryPoint)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			.requestMatchers(AntPathRequestMatcher("/test")).permitAll()
			.requestMatchers(AntPathRequestMatcher("/api/v1/adminRegister")).permitAll()
			.requestMatchers(AntPathRequestMatcher("/api/v1/adminLogin")).permitAll()
			.requestMatchers(AntPathRequestMatcher("/api/v1/userRegister")).permitAll()
			.requestMatchers(AntPathRequestMatcher("/api/v1/userLogin")).permitAll()
			.requestMatchers(AntPathRequestMatcher("/api/v1/admin/**")).hasAuthority(UserType.ADMIN.toString())
			.requestMatchers(AntPathRequestMatcher("/api/v1/user/**")).hasAnyAuthority(UserType.USER.toString(), UserType.ADMIN.toString())
			.anyRequest().authenticated()
			.and()
			.httpBasic()
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
		return http.build()
	}

	@Bean
	fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
		return authenticationConfiguration.authenticationManager
	}

	@Bean
	fun passwordEncoder(): PasswordEncoder {
		return BCryptPasswordEncoder()
	}

	@Bean
	fun jwtAuthenticationFilter(): JwtAuthenticationFilter {
		return JwtAuthenticationFilter()
	}
}