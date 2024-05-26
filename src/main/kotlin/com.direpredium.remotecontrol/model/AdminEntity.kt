package com.direpredium.remotecontrol.model;


import javax.persistence.*;

@Entity
@Table(name = "admin")
data class AdminEntity(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Int = 0,

	var username: String = "",
	var password: String = ""
)
