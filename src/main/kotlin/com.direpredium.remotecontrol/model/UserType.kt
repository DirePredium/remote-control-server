package com.direpredium.remotecontrol.model;

enum class UserType(private val type: String) {
	ADMIN("ADMIN"),
	USER("USER");

	override fun toString(): String {
		return type
	}
}