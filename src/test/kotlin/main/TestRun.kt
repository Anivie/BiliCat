package main

import ink.bluecloud.model.data.cookie.CookieJson
import ink.bluecloud.service.clientservice.video.stream.VideoStream

class TestRun {
    suspend fun run(){
        val stream = VideoStream().getVideoStream("BV1Mg411i74w", 898882561)
        println(stream.video.getVideoQnALL())
        println(stream.acceptQuality)
        println(stream.acceptQualityDescribe)
    }
}