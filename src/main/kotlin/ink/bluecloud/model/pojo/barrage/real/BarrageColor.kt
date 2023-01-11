package ink.bluecloud.model.pojo.barrage.real

/**
 * 弹幕颜色
 */
data class BarrageColor(
    val color: Int,//默认 16777215 白色
    val r: Int,
    val g: Int,
    val b: Int,
)

/**
 * 十进制RGB888值 构建一个 BarrageColor 对象
 */
inline fun Int.toBarrageColor(): BarrageColor {
    val b = this and 0xff
    val g = (this shr 8) and 0xff
    val r = (this shr 16) and 0xff
    return BarrageColor(this, r, g, b)
}

inline fun buildBarrageColor(value: Int = 16777215): BarrageColor = value.toBarrageColor()
