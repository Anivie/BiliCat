package ink.bluecloud.model.data.video


enum class AudioQn(val value: Int) {
    K64(30216),
    K132(30232),
    K192(30280),
    DOLBY(30250),
    Hi_Res(30251),
}
fun Int.toAudioQn():AudioQn{
    val qn = this
    val values = AudioQn.values();
    for (value in values) {
        if (value.value == qn) return value
    }
    throw IllegalArgumentException("AudioQn value does not exist; AudioQn:$qn")
}