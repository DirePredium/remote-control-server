package com.direpredium.remotecontrol.controller;

import com.direpredium.remotecontrol.repository.AdminRepo
import com.direpredium.remotecontrol.security.JwtGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/admin")
class AdminController @Autowired constructor(
	private val jwtGenerator: JwtGenerator,
	private val passwordEncoder: PasswordEncoder,
	private val adminRepo: AdminRepo
) {

	@GetMapping("/isLoggedIn")
	fun isLoggedIn(): Boolean {
		return true
	}

}

