package com.direpredium.remotecontrol.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user")
class UserController {

    @GetMapping("/isLoggedIn")
    fun isLoggedIn(): Boolean {
        return true
    }

}