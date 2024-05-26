package com.direpredium.remotecontrol.controller

import com.direpredium.remotecontrol.repository.AdminRepo
import com.direpredium.remotecontrol.security.JwtGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class TestController {

    @GetMapping("/test")
    fun test(): String {
        return "test"
    }

}