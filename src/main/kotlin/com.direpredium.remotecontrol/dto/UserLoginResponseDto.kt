package com.direpredium.remotecontrol.dto;

data class UserDetails(
	var username: String? = null,
	var email: String? = null,
	var id: Int? = null
)

class UserLoginResponseDto {
	var success: Boolean = false
	var message: String? = null
	var token: String? = null
	var user: UserDetails? = null

	fun setUser(username: String?, email: String?, id: Int?) {
		this.user = UserDetails(username, email, id)
	}
}

