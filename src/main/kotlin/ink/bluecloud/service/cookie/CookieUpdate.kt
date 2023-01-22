package ink.bluecloud.service.cookie

import com.alibaba.fastjson2.JSONObject
import com.alibaba.fastjson2.parseObject
import ink.bluecloud.exceptions.CodeException
import ink.bluecloud.exceptions.InvalidCookieException
import ink.bluecloud.model.data.cookie.CookieJson
import ink.bluecloud.model.pojo.CodePojo
import ink.bluecloud.network.http.CookieManager
import ink.bluecloud.service.APIResources
import ink.bluecloud.service.httpserver.ParameterReceiver
import ink.bluecloud.service.httpserver.core.BiliCatHttpServer
import ink.bluecloud.service.seeting.SettingCenterImpl
import ink.bluecloud.service.seeting.saveSetting
import ink.bluecloud.utils.getForCodePojo
import ink.bluecloud.utils.getForString
import ink.bluecloud.utils.postForString
import ink.bluecloud.utils.toObjJson
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
     * 注：客户端的 Cookie 不需要主动频繁更新，请保持周期在 90 天左右
     * 是否需要更新Cookie
     * https://github.com/SocialSisterYi/bilibili-API-collect/issues/524
     * 更新流程：
     * 1. 获取是否需要更新
     * 2. 计算更新路径，将打开一个网页由浏览器的js计算
     * 3. 获取新Cookie 与 新token
     * 4. 确认使用此次更新
     * 5. sso 刷新: 访问刷新cookie的url列表
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
        logger.info("${if (refresh) "需" else "不需"}要更新 Cookie; timestamp: $timestamp")
        if (!json.getBoolean("refresh")) return;


        //开始更新: 计算路径
        var server: BiliCatHttpServer? = null
        server = BiliCatHttpServer().addRoute(ParameterReceiver(timestamp) { urlHashPath ->
            //关闭服务器
            server!!.stop(0)
            //访问计算出的 URL 路径
            httpClient.getFor(api(urlHashPath).url) {
                //获取最新 Cookie(将以header set-cookie 形式返回,okhttp 会自动维护) 与 token
                val updateAPI = api(API.postUpdateCookie, APILevel.Post) {
                    it["csrf"] = getCsrf()
                    it["refresh_csrf"] = getValue(body.string()) ?: return@api
                    it["refresh_token"] = getRefreshToken() ?: throw InvalidCookieException()
                    it["source"] = "main_web"
                }
                httpClient.postFor(updateAPI.url, updateAPI.params) {
                    val newToken =
                        body.string().toObjJson(CodePojo::class.java).data!!.parseObject().getString("refresh_token")
                    //确认更新(老cookie将失效)
                    val confirmUpdateAPI = api(API.postConfirmUpdateCookie, APILevel.Post) {
                        it["csrf"] = getCsrf()
                        it["refresh_token"] = getRefreshToken() ?: throw InvalidCookieException()
                    }
                    httpClient.postFor(confirmUpdateAPI.url, confirmUpdateAPI.params) {
                        //SSO 刷新
                        val ssoAPI = api(API.postSSOCookie) {
                            it["biliCSRF"] = getCsrf()
                        }
                        httpClient.getFor(ssoAPI.url) {
                            val urlCookieList =
                                body.string().toObjJson(CodePojo::class.java).data!!.parseObject().getJSONArray("sso")
                            urlCookieList.forEach {
                                val urlCookie = it.toString().toHttpUrl()
                                httpClient.getFor(it.toString().toHttpUrl()) {
                                    logger.info("success updating cookie :${urlCookie.host}")
                                }
                            }
                            //构建最终的 新Cookie
                            val newCookie = httpClient.getCookieStore().toCookies()
                            newCookie.refreshToken = newToken
                            newCookie.timestamp = System.currentTimeMillis()
                            settingCenter.saveSetting(newCookie)
                            logger.info("cookie 已本地序列化")
                        }
                    }
                }
            }
        }).start()
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler http://127.0.0.1:8080");//使用默认浏览器打开url

    }

    /**
     * 获取路径中的值
     */
    private fun getValue(page: String): String? {
        val start = page.indexOf("<div id=\"1-name\">")
        val end = page.indexOf("<div id=\"1-value\">")
        try {
            return page.substring(start + "<div id=\"1-name\">".length, end).replace("</div>", "").trim()
        } catch (e: Exception) {
            logger.error(
                "解析html的标签路径中存在的值失败\n\n${page}\n---------------------------------------------------\n", e
            )
        }
        return null
    }
}