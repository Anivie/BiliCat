package ink.bluecloud.model.pojo.barrage.real

/**
 * 弹幕颜色
 */
data class BarrageColor(
    val color: Int,
    val r:Int,
    val g:Int,
    val b:Int
)
inline fun Int.toBarrageColor():BarrageColor {
    val b = this and 0xff
    val g = (this shr 8) and 0xff
    val r = (this shr 16) and 0xff
    return BarrageColor(this, r, g, b)
}
