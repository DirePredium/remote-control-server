package com.direpredium.remotecontrol.dto;

class AdminDetails {
	var username: String? = null
	var id: Int = 0
}

class AdminLoginResponseDto {
	var success: Boolean = false
	var message: String? = null
	var token: String? = null
	var admin: AdminDetails? = null

	fun setAdmin(username: String, id: Int) {
		this.admin = AdminDetails().apply {
			this.username = username
			this.id = id
		}
	}
}
