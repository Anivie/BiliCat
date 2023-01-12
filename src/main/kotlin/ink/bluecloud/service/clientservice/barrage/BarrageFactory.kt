package ink.bluecloud.service.clientservice.barrage

import ink.bluecloud.model.pojo.barrage.real.*
import java.time.Duration

object BarrageFactory {
    /**
     * @param cid 视频CID
     * @param content 弹幕内容
     * @param progress 弹幕出现在视频内的时间，请让精度在毫秒值 Duration.ofMillis(1000)
     * @param fontsize
     * @param color
     * @param sendTime
     */
    fun newBarrage(
        cid: Long,
        content: String,
        progress: Duration = Duration.ofMillis(1000),
        color: BarrageColor = buildBarrageColor(),
        fontsize: BarrageFontSize = BarrageFontSize.Default,
        pool: BarragePool = BarragePool.Default,
        type: BarrageType = BarrageType.CommonBarrageS1,
        sendTime: Long = System.currentTimeMillis(),
    ): Barrage = Barrage(
        cid, -1, progress, type, fontsize, color, "-1", content, sendTime, -1, pool
    )

}