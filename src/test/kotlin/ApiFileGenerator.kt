@file:UseSerializers(OKHttpURLSerializer::class)

import ink.bluecloud.model.networkapi.api.data.OKHttpURLSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import java.nio.file.Files
import java.nio.file.Paths

@OptIn(ExperimentalSerializationApi::class)
fun main() {
    ProtoBuf.encodeToByteArray(
        HttpApi(
        "https://passport.bilibili.com/qrcode/getLoginUrl".toHttpUrl(),
        "https://passport.bilibili.com/qrcode/getLoginInfo".toHttpUrl(),

        "https://api.bilibili.com/x/web-interface/index/top/feed/rcmd".toHttpUrl(),
        "http://api.bilibili.com/x/web-interface/view".toHttpUrl(),
        "http://api.bilibili.com/x/player/playurl".toHttpUrl(),
        "https://api.bilibili.com/x/web-interface/popular/series/one".toHttpUrl(),
        "https://api.bilibili.com/x/web-interface/popular/series/list".toHttpUrl(),
        "https://api.bilibili.com/x/web-interface/popular".toHttpUrl(),
        "https://api.bilibili.com/x/web-interface/ranking/v2".toHttpUrl(),

        "https://api.bilibili.com/x/web-interface/nav".toHttpUrl(),
    )).run {
        println(contentToString())
        Files.write(Paths.get("config\\HttpAPI.proto"),this)
    }
}

@Serializable
data class HttpApi(
    val getLoginQRCode: HttpUrl ,
    val getLoginStatus: HttpUrl ,
    val getPortalVideos: HttpUrl,
    val getVideoINFO: HttpUrl,
    val getVideoStreamURL: HttpUrl ,
    val getVideoWeeklyList: HttpUrl,
    val getVideoWeeklyHistoryList: HttpUrl,
    val getHotVideoList: HttpUrl ,
    val getFullRank: HttpUrl,
    val getAccountInfo: HttpUrl,
)