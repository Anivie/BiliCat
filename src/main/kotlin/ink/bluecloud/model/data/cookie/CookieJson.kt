package ink.bluecloud.model.data.cookie

import com.alibaba.fastjson2.annotation.JSONField
import kotlinx.serialization.Serializable

/**
 * Cookie 持久化，以下字段为Cookie 核心字段。
 * 请使用此字段去访问Bilibili 门户网站，以更新cookie以此使 okhttp cookie 管理可用
 */
@Serializable
data class CookieJson(
    @JSONField(name = "bili_jct")
    val csrf:String,
    @JSONField(name = "DedeUserID")
    val id:String,
    @JSONField(name = "gourl")
    val url:String="http%3A%2F%2Fwww.bilibili.com",
//    @JSONField(name = "Expires")
//    val expires:String,
    @JSONField(name ="DedeUserID__ckMd5")
    val hashCode:String,
    @JSONField(name = "SESSDATA")
    val session:String
){
    override fun toString(): String {
        return """
             bili_jct=${csrf};
             DedeUserID=${id};
             gourl=${url};
             DedeUserID__ckMd5=${hashCode};
             SESSDATA=${session};
        """.trimIndent()
    }
}
