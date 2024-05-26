package com.direpredium.remotecontrol.repository;

import com.direpredium.remotecontrol.model.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface AdminRepo : JpaRepository<AdminEntity, Int> {
	fun findByUsername(username: String): Optional<AdminEntity>
	fun existsByUsername(username: String): Boolean
}