package main

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import kotlin.concurrent.thread


fun main() {
    val serverSocket = ServerSocket(8080)
    while (true) {
        serverSocket.accept().run {
            thread {
                runCatching {
                    BufferedReader(InputStreamReader(getInputStream())).run {
                        while (true) {
                            readLine()?.run {
                                println(this)
                            }?: break
                        }
                    }
                }.onFailure { println(it.message) }
            }
        }
    }
}