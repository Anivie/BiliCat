@file:UseSerializers(OKHttpURLSerializer::class)
package ink.bluecloud.model.networkapi.api.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl

@Serializable
data class HttpApi(
    val getLoginQRCode: HttpUrl = "https://passport.bilibili.com/qrcode/getLoginUrl".toHttpUrl(),
    val getLoginStatus: HttpUrl = "https://passport.bilibili.com/qrcode/getLoginInfo".toHttpUrl(),
//    Video
    val getPortalVideos: HttpUrl = "https://api.bilibili.com/x/web-interface/index/top/feed/rcmd".toHttpUrl(),
    val getVideoINFO: HttpUrl = "http://api.bilibili.com/x/web-interface/view".toHttpUrl(),
    val getVideoStreamURL: HttpUrl = "http://api.bilibili.com/x/player/playurl".toHttpUrl(),
    val getVideoWeeklyList: HttpUrl = "https://api.bilibili.com/x/web-interface/popular/series/one".toHttpUrl(),
    val getVideoWeeklyHistoryList: HttpUrl = "https://api.bilibili.com/x/web-interface/popular/series/list".toHttpUrl(),
    val getHotVideoList: HttpUrl = "https://api.bilibili.com/x/web-interface/popular".toHttpUrl(),
    val getFullRank: HttpUrl = "https://api.bilibili.com/x/web-interface/ranking/v2".toHttpUrl(),
//    User
    val getAccountInfo: HttpUrl = "https://api.bilibili.com/x/web-interface/nav".toHttpUrl(),


    val getTest: HttpUrl = "http://127.0.0.1:9090/test".toHttpUrl()

)

object OKHttpURLSerializer: KSerializer<HttpUrl> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("HttpUrlSerializer", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: HttpUrl) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): HttpUrl {
        return decoder.decodeString().toHttpUrl()
    }
}
