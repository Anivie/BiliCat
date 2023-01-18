package ink.bluecloud.service.cookie

import com.alibaba.fastjson2.JSONObject
import ink.bluecloud.exceptions.CodeException
import ink.bluecloud.model.data.cookie.CookieJson
import ink.bluecloud.network.http.CookieManager
import ink.bluecloud.service.APIResources
import ink.bluecloud.service.httpserver.ParameterReceiver
import ink.bluecloud.service.httpserver.core.BiliCatHttpServer
import ink.bluecloud.utils.getForCodePojo
import ink.bluecloud.utils.getForString
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.jsoup.HttpStatusException
import org.koin.core.annotation.Factory
import java.util.UUID

@Factory
class CookieUpdate : APIResources() {

    fun loadCookie(cookie: CookieJson) {
        httpClient.getCookieStore().parseTo(cookie)
        updateCookie()
    }

    fun loadCookie(cookie: String) {
        httpClient.getCookieStore().parseAllTo(cookie)
        updateCookie()
    }

    fun getCookieStore(): CookieManager {
        return httpClient.getCookieStore()
    }

    /**
     * 更新 Cookie (不等待更新，但在未来更新完毕)
     */
    private fun updateCookie() = httpClient.getFor(API.getBili) {
//        httpClient.getCookieStore().parseTo("buvid3=${UUID.randomUUID()}")
        //首页更新Cookie，以此来添加字段
        use {
            logger.info("System-services Get Cookie-Update -> ${API.getBili}")
            if (it.code != 200) throw HttpStatusException(
                "http status code is not 200", code, netWorkResourcesProvider.api.getBili.toString()
            )
        }
    }

    /**
     * 是否需要更新Cookie
     * https://github.com/SocialSisterYi/bilibili-API-collect/issues/524
     */
    suspend fun doUpdateCookie() {
        //是否需要更新
        val api = api(API.getIsUpdateCookie)
        val json = JSONObject.parseObject(
            httpClient.getForCodePojo(api.url).data ?: throw CodeException(
                -101, "响应体未携带 data 字段"
            )
        )
        val timestamp = json.getString("timestamp")
        val refresh = json.getBoolean("refresh")
        logger.info("${if (refresh) "需" else "不需"}要更新 Cookie")
        if (!json.getBoolean("refresh")) return;


        //开始更新: 计算路径
        var server: BiliCatHttpServer? = null
        server = BiliCatHttpServer().addRoute(ParameterReceiver(timestamp) {
            //关闭服务器
            server!!.stop(0)
            //更新 Cookie
            httpClient.getFor(api(it).url) {
                val updateAPI = api(
                    //等待将URL写入api文件中
                    "https://passport.bilibili.com/x/passport-login/web/cookie/refresh".toHttpUrl(),
                    APILevel.Post
                ) {
                    it["csrf"] = getCsrf()
                    it["refresh_csrf"] = getValue(body.string())
                    it["refresh_token"] = getRefreshToken() ?: getCsrf()
                    it["source"] = "main_web"
                }

                httpClient.postFor(updateAPI.url, updateAPI.params) {
                    println(body.string())
                }
            }
            //确认更新
            //SSO 刷新
        }).start()
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler http://127.0.0.1:8080");//使用默认浏览器打开url

    }

    /**
     * 获取路径中的值
     */
    private fun getValue(page: String): String {
        val start = page.indexOf("<div id=\"1-name\">")
        val end = page.indexOf("<div id=\"1-value\">")
        return page.substring(start + "<div id=\"1-name\">".length, end).replace("</div>", "").trim()
    }
}