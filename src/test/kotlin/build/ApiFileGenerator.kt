@file:UseSerializers(OKHttpURLSerializer::class)

package build

import ink.bluecloud.model.networkapi.api.data.HttpApi
import ink.bluecloud.model.networkapi.api.data.OKHttpURLSerializer
import ink.bluecloud.utils.logger
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import okhttp3.HttpUrl.Companion.toHttpUrl
import java.nio.file.Files
import java.nio.file.Paths

@OptIn(ExperimentalSerializationApi::class)
fun main() {
    buildAPI()
}

@OptIn(ExperimentalSerializationApi::class)
fun buildAPI(){
    ProtoBuf.encodeToByteArray(
        HttpApi(
            //  Login
            getLoginQRCode = "https://passport.bilibili.com/qrcode/getLoginUrl".toHttpUrl(),
            getLoginQRCodeV2 = "https://passport.bilibili.com/x/passport-login/web/qrcode/generate".toHttpUrl(),//20230119
            getLoginStatus = "https://passport.bilibili.com/qrcode/getLoginInfo".toHttpUrl(),
            getLoginStatusV2 = "https://passport.bilibili.com/x/passport-login/web/qrcode/poll".toHttpUrl(),//20230119
            postOutLogin = "https://passport.bilibili.com/login/exit/v2".toHttpUrl(),//20230119
            //  Video
            getPortalVideos = "https://api.bilibili.com/x/web-interface/index/top/feed/rcmd".toHttpUrl(),
            getVideoINFO = "https://api.bilibili.com/x/web-interface/view".toHttpUrl(),
            getVideoStreamURL = "https://api.bilibili.com/x/player/playurl".toHttpUrl(),
            getVideoWeeklyList = "https://api.bilibili.com/x/web-interface/popular/series/one".toHttpUrl(),
            getVideoWeeklyHistoryList = "https://api.bilibili.com/x/web-interface/popular/series/list".toHttpUrl(),
            getHotVideoList = "https://api.bilibili.com/x/web-interface/popular".toHttpUrl(),
            getFullRank = "https://api.bilibili.com/x/web-interface/ranking/v2".toHttpUrl(),
            getCidInfo = "https://hd.biliplus.com/api/cidinfo".toHttpUrl(),//20230110
            getBvIdInfo = "https://api.bilibili.com/x/player/pagelist".toHttpUrl(),//20230110
            //  User
            getAccountInfo = "https://api.bilibili.com/x/web-interface/nav".toHttpUrl(),
            getUserAttention = "https://api.bilibili.com/x/relation/followings".toHttpUrl(),//20230113
            getUserAttentionV2 = "https://app.biliapi.net/x/v2/relation/followings".toHttpUrl(),//20230113
            getUserFans = "https://api.bilibili.com/x/relation/followers".toHttpUrl(),//20230123
            //  Other
            getBili = "https://www.bilibili.com".toHttpUrl(),
            // Barrage
            getRealTimeBarrage = "https://api.bilibili.com/x/v2/dm/web/seg.so".toHttpUrl(),
            getHistoricalBarret = "https://api.bilibili.com/x/v2/dm/web/history/seg.so".toHttpUrl(),//20230109
            getHistoricalBarretDate = "https://api.bilibili.com/x/v2/dm/history/index".toHttpUrl(),//20230109
            postSendBarret = "https://api.bilibili.com/x/v2/dm/post".toHttpUrl(),//20230110
            postCancelBarret = "https://api.bilibili.com/x/dm/recall".toHttpUrl(),//20230111
            // Comment
            getCommentAreaPageLoad = "https://api.bilibili.com/x/v2/reply".toHttpUrl(),//20221215
            getCommentLazyPageLoad = "https://api.bilibili.com/x/v2/reply/main".toHttpUrl(),//20230108
            getCommentReply = "https://api.bilibili.com/x/v2/reply/reply".toHttpUrl(),//20230108
            getCommentReplyTree = "https://api.bilibili.com/x/v2/reply/dialog/cursor".toHttpUrl(),//20230108
            getCommentAreaHot = "https://api.bilibili.com/x/v2/reply/hot".toHttpUrl(),//20230108
            getCommentAreaCount = "https://api.bilibili.com/x/v2/reply/count".toHttpUrl(),//20230108
            //Cookie
            getIsUpdateCookie="https://passport.bilibili.com/x/passport-login/web/cookie/info".toHttpUrl(),//20230116
            postUpdateCookie="https://passport.bilibili.com/x/passport-login/web/cookie/refresh".toHttpUrl(),//20230122
            postConfirmUpdateCookie="https://passport.bilibili.com/x/passport-login/web/confirm/refresh".toHttpUrl(),//20230122
            postSSOCookie="https://passport.bilibili.com/web/sso/list".toHttpUrl(),//20230122
        )
    ).run {
        logger().info("build api")
        Files.write(Paths.get("config\\HttpAPI.proto"), this)
    }
}