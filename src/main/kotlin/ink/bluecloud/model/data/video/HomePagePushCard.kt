package ink.bluecloud.model.data.video

import kotlinx.coroutines.Deferred
import java.io.InputStream
import java.time.Duration
import java.util.*

data class HomePagePushCard(
    val title: String,
    val author: String,
    /**
     * 作者MID
     */
    val mid:Long,
    /**
     * 视频BV ID
     */
    val id:String,
    val cid:Long,
    /**
     * 发布时间
     */
    val time: Date,
    val duration: Duration,
    val playVolume: Int,
    val barrageVolume: Int,
    val cover: InputStream
//    val cover: Deferred<InputStream>
)