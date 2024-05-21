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

//    setEnableGeneratedCert()
//    val tempResp = URL("https://185.253.44.51:8181/users").readText()
//
//    println(tempResp)
}

fun setEnableGeneratedCert() {
    val keystoreFile = File("src/main/resources/keystore.p12")
    val keystorePassword = "123456".toCharArray()
    val keyStore = KeyStore.getInstance("PKCS12")
    keyStore.load(FileInputStream(keystoreFile), keystorePassword)

    val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
    tmf.init(keyStore)

    val trustManagers = tmf.getTrustManagers()

    val sslContext = SSLContext.getInstance("TLS")
    sslContext.init(null, trustManagers, null)

    HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.socketFactory)
}