package com.direpredium.remotecontrol.controller;

import com.direpredium.remotecontrol.dto.*;
import com.direpredium.remotecontrol.model.AdminEntity;
import com.direpredium.remotecontrol.model.UserEntity;
import com.direpredium.remotecontrol.model.UserType;
import com.direpredium.remotecontrol.repository.AdminRepo;
import com.direpredium.remotecontrol.repository.UserRepo;
import com.direpredium.remotecontrol.security.CustomUserDetailsService;
import com.direpredium.remotecontrol.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
class AuthController {

	@Autowired
	private lateinit var adminRepo: AdminRepo

	@Autowired
	private lateinit var userRepo: UserRepo

	@Autowired
	private lateinit var authenticationManager: AuthenticationManager

	@Autowired
	private lateinit var customUserDetailsService: CustomUserDetailsService

	@Autowired
	private lateinit var passwordEncoder: PasswordEncoder

	@Autowired
	private lateinit var jwtGenerator: JwtGenerator

	@PostMapping("api/v1/adminRegister")
	fun adminRegister(@RequestBody adminAuthDto: AdminAuthDto): ResponseEntity<String> {
		println("adminRegister")
		return if (adminRepo.existsByUsername(adminAuthDto.username)) {
			ResponseEntity("Username is taken !!", HttpStatus.BAD_REQUEST)
		} else {
			val adminEntity = AdminEntity().apply {
				username = adminAuthDto.username
				password = passwordEncoder.encode(adminAuthDto.password)
			}
			adminRepo.save(adminEntity)
			ResponseEntity("User Register successful !!", HttpStatus.CREATED)
		}
	}

	@PostMapping("api/v1/adminLogin")
	fun login(@RequestBody adminAuthDto: AdminAuthDto): ResponseEntity<AdminLoginResponseDto> {
		println("adminLogin")
		customUserDetailsService.setUserType(UserType.ADMIN)
		val authentication = authenticationManager.authenticate(
			UsernamePasswordAuthenticationToken(adminAuthDto.username, adminAuthDto.password)
		)
		SecurityContextHolder.getContext().authentication = authentication

		val token = jwtGenerator.generateToken(authentication, UserType.ADMIN.toString())
		val responseDto = AdminLoginResponseDto()
		responseDto.success = true
		responseDto.message = "login successful !!"
		responseDto.token = token

		val adminOptional = adminRepo.findByUsername(adminAuthDto.username)
		if (adminOptional.isPresent) {
			val admin = adminOptional.get()
			responseDto.setAdmin(admin.username, admin.id)
		} else {
			throw NoSuchElementException("Admin not found")
		}

		return ResponseEntity(responseDto, HttpStatus.OK)
	}

	@PostMapping("api/v1/userRegister")
	fun userRegister(@RequestBody userRegisterDto: UserRegisterDto): ResponseEntity<SuccessandMessageDto> {
		println("userRegister")
		val response = SuccessandMessageDto()
		if (userRepo.existsByEmail(userRegisterDto.email)) {
			response.message = "Email is already registered !!"
			response.success = false
			return ResponseEntity(response, HttpStatus.BAD_REQUEST)
		}
		val userEntity = UserEntity().apply {
			name = userRegisterDto.username
			password = passwordEncoder.encode(userRegisterDto.password)
			email = userRegisterDto.email
			status = true
		}
		userRepo.save(userEntity)
		response.message = "Profile Created Successfully !!"
		response.success = true
		return ResponseEntity(response, HttpStatus.OK)
	}

	@PostMapping("api/v1/userLogin")
	fun userLogin(@RequestBody userLoginDto: UserLoginDto): ResponseEntity<UserLoginResponseDto> {
		println("userLogin")
		customUserDetailsService.setUserType(UserType.USER)
		val authentication = authenticationManager.authenticate(
			UsernamePasswordAuthenticationToken(userLoginDto.email, userLoginDto.password)
		)
		SecurityContextHolder.getContext().authentication = authentication
		val token = jwtGenerator.generateToken(authentication, UserType.USER.toString())
		val responseDto = UserLoginResponseDto()
		responseDto.success = true
		responseDto.message = "login successful !!"
		responseDto.token = token
		val userOptional = userRepo.findByEmail(userLoginDto.email)
		if (userOptional.isPresent) {
			val user = userOptional.get()
			responseDto.setUser(user.name, user.email, user.id)
		} else {
			throw NoSuchElementException("User not found")
		}

		return ResponseEntity(responseDto, HttpStatus.OK)
	}

}