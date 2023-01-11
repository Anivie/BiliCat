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
            getCidInfo = "https://hd.biliplus.com/api/cidinfo".toHttpUrl(),//20230110
            getBvIdInfo = "https://api.bilibili.com/x/player/pagelist".toHttpUrl(),//20230110
            //  User
            getAccountInfo = "https://api.bilibili.com/x/web-interface/nav".toHttpUrl(),
            //  Other
            getBili = "https://www.bilibili.com".toHttpUrl(),
            // Barrage
            getRealTimeBarrage = "http://api.bilibili.com/x/v2/dm/web/seg.so".toHttpUrl(),
            getHistoricalBarret = "http://api.bilibili.com/x/v2/dm/web/history/seg.so".toHttpUrl(),//20230109
            getHistoricalBarretDate = "http://api.bilibili.com/x/v2/dm/history/index".toHttpUrl(),//20230109
            postSendBarret = "http://api.bilibili.com/x/v2/dm/post".toHttpUrl(),//20230110
            // Comment
            getCommentAreaPageLoad = "http://api.bilibili.com/x/v2/reply".toHttpUrl(),//20221215
            getCommentLazyPageLoad = "http://api.bilibili.com/x/v2/reply/main".toHttpUrl(),//20230108
            getCommentReply = "http://api.bilibili.com/x/v2/reply/reply".toHttpUrl(),//20230108
            getCommentReplyTree = "http://api.bilibili.com/x/v2/reply/dialog/cursor".toHttpUrl(),//20230108
            getCommentAreaHot = "http://api.bilibili.com/x/v2/reply/hot".toHttpUrl(),//20230108
            getCommentAreaCount = "http://api.bilibili.com/x/v2/reply/count".toHttpUrl(),//20230108
        )
    ).run {
        println(contentToString())
        Files.write(Paths.get("config\\HttpAPI.proto"), this)
    }
}