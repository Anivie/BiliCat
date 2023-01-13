package ink.bluecloud.service.video.stream.param

enum class Qn(val index: Int, val value: Int) {
    /**
     * 仅mp4方式支持
     */
    P206_MP4(1,6),
    P306_ALL(2,16),
    P480_ALL(3,32),
    P720_ALL(4,64),
    P720PLUS_All_COOKIE(5,74),
    P1080_ALL_COOKIE(6,80),
    P1080PLUS_ALL_COOKIE_VIP(7,112),
    P1080P60_ALL_COOKIE_VIP(8,116),
    P4K(9,120),

    /**
     * HDR 真彩色
     */
    PHDR(10,125),

    /**
     * 杜比视界
     */
    PDOLBY(11,126),
    P8K(12,127)
}
@Suppress("NOTHING_TO_INLINE")
inline fun Int.toQn(): Qn {
    val qn = this
    val values = Qn.values()
    for (value in values) {
        if (value.value == qn) return value
    }
    throw IllegalArgumentException("Qn value does not exist; Qn:$qn")
}
