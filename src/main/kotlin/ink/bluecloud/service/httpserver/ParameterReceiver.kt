package ink.bluecloud.service.httpserver

import com.alibaba.fastjson2.JSONObject
import ink.bluecloud.service.httpserver.core.AbsHttpHandler
import ink.bluecloud.service.httpserver.core.Request
import ink.bluecloud.service.httpserver.core.Response
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl

class ParameterReceiver(private val timestamp: String,private val result:(url:HttpUrl)->Unit) : AbsHttpHandler("/test") {
    override fun doGet(request: Request, response: Response) {
        response.send(timestamp)
    }

    override fun doPost(request: Request, response: Response) {
        val json = JSONObject.parseObject(String(request.body))
        val time = json.getLong("time")
        val url = json.getString("url")
        if (System.currentTimeMillis() - time > 4200) {
            logger.error("获取的url超时")
            return
        }
        response.send("ok")
        result(url.toHttpUrl())
    }
}