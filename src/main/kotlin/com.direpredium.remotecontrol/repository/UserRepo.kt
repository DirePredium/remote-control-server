package com.direpredium.remotecontrol.repository;

import com.direpredium.remotecontrol.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface UserRepo : JpaRepository<UserEntity, Int> {
	fun findByEmail(email: String): Optional<UserEntity>
	fun existsByEmail(email: String): Boolean
}