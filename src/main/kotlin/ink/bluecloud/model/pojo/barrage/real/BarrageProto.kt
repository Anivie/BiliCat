package ink.bluecloud.model.pojo.barrage.real

import kotlinx.serialization.Serializable
import java.time.Duration

@Serializable
data class Barrages(
    val barrages: ArrayList<BarrageSource>,
)

@Serializable
data class BarrageSource(
    /**
     * 弹幕dmid: 唯一 可用于操作参数
     */
    val dmid: Long,
    /**
     * 视频内弹幕出现时间(ms)
     */
    val progress: Int?,
    /**
     * 弹幕类型
    1 2 3：普通弹幕
    4：底部弹幕
    5：顶部弹幕
    6：逆向弹幕
    7：高级弹幕
    8：代码弹幕
    9：BAS弹幕（仅限于特殊弹幕专包）
     */
    val type: Int,
    /**
     * 	弹幕字号	18：小
    25：标准
    36：大
     */
    val fontSize: Int,
    /**
     * 弹幕颜色	十进制RGB888值
     */
    val color: Int,
    /**
     * 发送者mid的HASH	用于屏蔽用户和查看用户发送的所有弹幕 也可反查用户id
     */
    val midHash: String,
    /**
     * 弹幕内容	utf-8编码
     */
    val content: String,
    /**
     * 弹幕发送时间	时间戳(s)
     */
    val sendTime: Long,
    /**
     * 权重	用于智能屏蔽，根据弹幕语义及长度通过AI识别得出
    范围：[0-10]
    值越大权重越高
     */
    val weight: Int?,
    /**
     * 未知
     */
    val action: String?,
    /**
     * 弹幕池	0：普通池
    1：字幕池
    2：特殊池（代码/BAS弹幕）
     */
    val pool: Int?,
    /**
     * 弹幕dmid	字串形式
    唯一 可用于操作参数
     */
    val idStr: String,
)


inline fun BarrageSource.toBarrage(): Barrage = Barrage(
    dmid,
    progress?.toLong()?.let { Duration.ofMillis(it) },
    type.toBarrageType(),
    fontSize.toBarrageFontSize(),
    color.toBarrageColor(),
    midHash,
    content,
    sendTime*1000,
    weight,
    pool?.toBarragePool()
)