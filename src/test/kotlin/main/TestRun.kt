package main

import ink.bluecloud.service.clientservice.video.stream.VideoStream

class TestRun {
    suspend fun main(){
        val stream = VideoStream().getVideoStream("BV1Mg411i74w", 898882561)
        println(stream.acceptQuality)
        println(stream.acceptQualityDescribe)
    }
}