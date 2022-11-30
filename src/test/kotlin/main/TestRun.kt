package main

import ink.bluecloud.service.clientservice.barrage.real.RealTimeBarrage
import java.util.*

class TestRun {
    private val bvid: String = "BV1L24y1C7ai"
    private val cid: Long = 902687446

    suspend fun run() {
        for (barrage in RealTimeBarrage().getBarrages(cid)) {
            println("${Date(barrage.sendTime)} ${barrage.content}")
        }
    }
}