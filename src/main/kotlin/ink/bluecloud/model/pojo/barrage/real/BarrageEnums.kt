package ink.bluecloud.model.pojo.barrage.real

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
enum class BarrageType(val index: Int,val value: Int) {
    CommonBarrageS1(1,1),
    CommonBarrageS2(2,1),
    CommonBarrageS3(3,1),
    BottomBarrage(4,4),
    TopBarrage(5,5),
    ReverseBarrage(6,6),
    SeniorBarrage(7,7),
    CodeBarrage(8,8),
    BAS_Barrage(9,9)
}
/**
 * 	弹幕字号
18：小
25：标准
36：大
 */
enum class BarrageFontSize(val value: Int) {
    Small(18),
    Default(25),
    Large(36)
}



/**
 * 弹幕池
 * 0：普通池
1：字幕池
2：特殊池（代码/BAS弹幕）
 */
enum class BarragePool(val value: Int) {
    Default(0),
    Subtitle(1),
    Special(2)
}

inline fun Int.toBarrageType():BarrageType = BarrageType.values()[this-1]
inline fun Int.toBarrageFontSize():BarrageFontSize = BarrageFontSize.values().first { this == it.value }
inline fun Int.toBarragePool():BarragePool = BarragePool.values()[this-1]
