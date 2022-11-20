package ink.bluecloud.network.http

import com.alibaba.fastjson2.JSONObject
import ink.bluecloud.model.data.cookie.CookieJson
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl

/**
 * okhttp 的 Cookie 管理类
 * 对每一个域名都有一个对应的 Cookie，该 Cookie由 okhttp 进行维护
 */
class CookieManager : CookieJar {
    //    private val cookieStore: HashMap<String, List<Cookie>> = HashMap()
    private val cookieStore: HashMap<String, HashMap<String, Cookie>> = HashMap()

    private val apiHost: HttpUrl = "https://api.bilibili.com".toHttpUrl()

    //是否共享B站下的 Cookie 使其子域也能使用主域下的 Cookie
    private val shareCookie: Boolean = true


    /**
     * 从管理器中加载Cookie
     * 不推荐直接调用，这是为okhttp提供的方法
     */
    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val cookies: ArrayList<Cookie> = ArrayList()
        cookieStore[url.host]?.values?.forEach {
            cookies.add(it)
        }
        return cookies
    }

    /**
     * 添加Cookie进管理器
     * 不推荐直接调用，这是为okhttp提供的方法
     */
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        var host = url.host
        //共享所有b站域名下的 Cookie；以变量 apiHost 定义的域名为主进行管理
        if (shareCookie and host.contains("bilibili.com")) host = apiHost.host

        //封装进Map
        val map: HashMap<String, Cookie> = cookieStore.get(host) ?: HashMap()
        cookies.forEach {
            map["${it.name}$"] = it
        }
        //添加到集合中
        cookieStore[host] = map
    }

    /**
     * 获取 CookieManager 正在维护的 CookieList
     */
    fun getBiliCookieStore(): List<Cookie> {
        return loadForRequest(apiHost)
    }

    /**
     * 将 CookieJson 解析并添加进管理器
     */
    fun parseTo(cookie: CookieJson) {
        parseAllTo(cookie.toString())
    }

    /**
     * 解析多个Cookie 为 List<Cookie> 并存入管理器。
     * 多个Cookie直接用 ‘;’分割
     */
    fun parseAllTo(cookies: String) {
        cookies.split(";").forEach { cookie ->
            parseTo(cookie.trim())
        }
    }

    /**
     * 将Cookie 解析为 Cookie 对象 并存入管理器
     * 注意：此方法仅能解析出一个Cookie
     * 如：key1=val1; key2=val2  ==解析成==>  key1=val1; path=/
     */
    fun parseTo(cookie: String) {
        val loadForRequest: ArrayList<Cookie> = loadForRequest(apiHost) as ArrayList<Cookie>
        Cookie.parse(apiHost, cookie)?.let { loadForRequest.add(it) }
        saveFromResponse(apiHost, loadForRequest)
    }

    override fun toString(): String {
        val cookies = loadForRequest(apiHost)
        return buildString { cookies.forEach { append(it.name).append("=").append(it.value).append(";") } }
    }

    fun toJson(host: HttpUrl = apiHost): JSONObject {
        val cookies = loadForRequest(host)
        val json = JSONObject()
        cookies.forEach { json.put(it.name, it.value) }
        return json
    }

    fun toCookies(host: HttpUrl = apiHost): CookieJson {
        val json = toJson(host)
        return CookieJson(
            csrf = json.getString("bili_jct"),
            id = json.getString("DedeUserID"),
            hashCode = json.getString("DedeUserID__ckMd5"),
            session = json.getString("SESSDATA")
        )
    }
}