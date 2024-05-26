package com.direpredium.remotecontrol

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.File
import java.io.FileInputStream
import java.net.URL
import java.security.KeyStore
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

@SpringBootApplication
class RemoteControlApplication

fun main(args: Array<String>) {
    runApplication<RemoteControlApplication>()
}