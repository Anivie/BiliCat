package ink.bluecloud.model.data.video

enum class Codec(val value: String) {
    /**
     * AVC（H.264）
     */
    VIDEO_AVC("AVC"),

    /**
     * HEVC（H.265） 推荐
     */
    VIDEO_HEVC("HEV"),
    VIDEO_AV1("AV0"),

    AUDIO_MP4("MP4")
}

@Suppress("NOTHING_TO_INLINE")
inline fun String.toCodec(): Codec {
    val str = this.uppercase()
    for (codec in Codec.values()) {
        if (str.contains(codec.value)) return codec
    }
    throw IllegalStateException("Codec String are out of the scope of the encoding dictionary; codec string: $str")
}