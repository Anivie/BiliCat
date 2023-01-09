package ink.bluecloud.model.pojo.barrage.real

import java.time.Duration

data class Barrage(
    /**
     * 弹幕dmid: 唯一 可用于操作参数
     */
    val dmid: Long,
    /**
     * 视频内弹幕出现时间(ms)
     */
    val progress: Duration?,
    /**
     * 弹幕类型
     */
    val type: BarrageType,
    /**
     * 	弹幕字号
     */
    val fontSize: BarrageFontSize,
    /**
     * 弹幕颜色	十进制RGB888值
     */
    val color: BarrageColor,
    /**
     * 发送者mid的HASH	用于屏蔽用户和查看用户发送的所有弹幕 也可反查用户id
     */
    val midHash: String,
    /**
     * 弹幕内容	utf-8编码
     */
    val content: String,
    /**
     * 弹幕发送时间	时间戳(ms)
     */
    val sendTime: Long,
    /**
     * 权重	用于智能屏蔽，根据弹幕语义及长度通过AI识别得出
    范围：[0-10]
    值越大权重越高
     */
    val weight: Int?,
    /**
     * 弹幕池
     */
    val pool: BarragePool?,
)