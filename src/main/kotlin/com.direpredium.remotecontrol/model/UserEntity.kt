package com.direpredium.remotecontrol.model;

import javax.persistence.*;

import javax.validation.constraints.Email;

@Entity
@Table(name = "user")
data class UserEntity(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Int = 0,

	var name: String = "",

	@Email
	var email: String = "",

	var password: String = "",

	var status: Boolean = false
)

