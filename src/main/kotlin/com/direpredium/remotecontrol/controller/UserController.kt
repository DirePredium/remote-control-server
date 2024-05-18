package com.direpredium.remotecontrol.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class User(val id: Long, val name: String)

@RestController
@RequestMapping("/users")
class UserController {

    @GetMapping
    fun getUsers(): List<User> {
        return listOf(
            User(1, "Alice"),
            User(2, "Bob"),
            User(3, "Charlie")
        )
    }

}