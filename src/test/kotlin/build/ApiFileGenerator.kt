@file:UseSerializers(OKHttpURLSerializer::class)

package build

import ink.bluecloud.model.networkapi.api.data.HttpApi
import ink.bluecloud.model.networkapi.api.data.OKHttpURLSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import okhttp3.HttpUrl.Companion.toHttpUrl
import java.nio.file.Files
import java.nio.file.Paths

@OptIn(ExperimentalSerializationApi::class)
fun main() {
    ProtoBuf.encodeToByteArray(
        HttpApi(
            //  Login
            getLoginQRCode = "https://passport.bilibili.com/qrcode/getLoginUrl".toHttpUrl(),
            getLoginStatus = "https://passport.bilibili.com/qrcode/getLoginInfo".toHttpUrl(),
            //  Video
            getPortalVideos = "https://api.bilibili.com/x/web-interface/index/top/feed/rcmd".toHttpUrl(),
            getVideoINFO = "http://api.bilibili.com/x/web-interface/view".toHttpUrl(),
            getVideoStreamURL = "http://api.bilibili.com/x/player/playurl".toHttpUrl(),
            getVideoWeeklyList = "https://api.bilibili.com/x/web-interface/popular/series/one".toHttpUrl(),
            getVideoWeeklyHistoryList = "https://api.bilibili.com/x/web-interface/popular/series/list".toHttpUrl(),
            getHotVideoList = "https://api.bilibili.com/x/web-interface/popular".toHttpUrl(),
            getFullRank = "https://api.bilibili.com/x/web-interface/ranking/v2".toHttpUrl(),
            //  User
            getAccountInfo = "https://api.bilibili.com/x/web-interface/nav".toHttpUrl(),
            //  Other
            getBili = "https://www.bilibili.com".toHttpUrl(),
        )
    ).run {
        println(contentToString())
        Files.write(Paths.get("config\\HttpAPI.proto"), this)
    }
}