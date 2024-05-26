package com.direpredium.remotecontrol.security;

import com.direpredium.remotecontrol.model.UserType;
import com.direpredium.remotecontrol.repository.AdminRepo;
import com.direpredium.remotecontrol.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
class CustomUserDetailsService: UserDetailsService {

	@Autowired
	private lateinit var adminRepo: AdminRepo

	@Autowired
	private lateinit var userRepo: UserRepo

	private var userType: UserType? = null

	fun getUserType(): UserType? {
		return userType
	}

	fun setUserType(userType: UserType) {
		this.userType = userType
	}

	@Override
	public override fun loadUserByUsername(username: String): UserDetails {
		userType?.let {
			when (it) {
				UserType.ADMIN -> {
					val adminEntity = adminRepo.findByUsername(username)
						.orElseThrow { UsernameNotFoundException("Admin Username $username not found") }
					val adminAuthority = SimpleGrantedAuthority(UserType.ADMIN.toString())
					val authorities: MutableCollection<GrantedAuthority> = ArrayList()
					authorities.add(adminAuthority)
					return User(adminEntity.username, adminEntity.password, authorities)
				}
				UserType.USER -> {
					val userEntity = userRepo.findByEmail(username)
						.orElseThrow { UsernameNotFoundException("User Email $username not found") }
					val USERAuthority = SimpleGrantedAuthority(UserType.USER.toString())
					val authorities: MutableCollection<GrantedAuthority> = ArrayList()
					authorities.add(USERAuthority)
					return User(userEntity.email, userEntity.password, authorities)
				}
			}
		}
		throw UsernameNotFoundException("User type not set or invalid")
	}
	
}
