package main

import ink.bluecloud.service.clientservice.account.cookie.CookieUpdate
import ink.bluecloud.service.clientservice.video.info.VideoInfo
import ink.bluecloud.service.clientservice.video.stream.VideoStream

class TestRun {
    suspend fun run() {
        for (cookie in CookieUpdate().getCookieStore().getBiliCookieStore()) {
            println(cookie)
        }
    }
}