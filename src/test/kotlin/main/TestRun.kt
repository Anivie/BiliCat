package main

import ink.bluecloud.service.clientservice.video.hot.HotVideoList
import ink.bluecloud.service.clientservice.video.info.VideoInfo
import ink.bluecloud.service.clientservice.video.stream.VideoStream
import org.apache.logging.log4j.LogManager

class TestRun {
    suspend fun run() {
        val (video,audio) = VideoStream().getVideoStream("BV1sP411g7PZ", 892292717)
        println(audio.get().url)
        println(video.get().url)


        println(VideoInfo().getVideoInfo("BV1Mg411i74w").toString())
    }
}