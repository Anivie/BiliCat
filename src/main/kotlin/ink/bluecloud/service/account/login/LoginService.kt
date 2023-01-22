package ink.bluecloud.service.account.login

import com.alibaba.fastjson2.parseObject
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.client.j2se.MatrixToImageConfig
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import ink.bluecloud.model.pojo.login.LoginV2Pojo
import ink.bluecloud.service.APIResources
import ink.bluecloud.utils.onIO
import ink.bluecloud.service.seeting.saveSetting
import ink.bluecloud.utils.getForString
import ink.bluecloud.utils.toObjJson
import kotlinx.coroutines.delay
import org.koin.core.annotation.Factory
import java.io.ByteArrayOutputStream
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Factory
class LoginService : APIResources() {
    private lateinit var authKey: String

    suspend fun getCode() = suspendCoroutine { coroutine ->
        httpClient.getFor(
            netWorkResourcesProvider.api.getLoginQRCodeV2
        ) {
            body.string().parseObject().getJSONObject("data").run {
                authKey = getString("qrcode_key")
                coroutine.resume(getString("url").genericQRCode(300))
            }
        }
    }

    suspend fun whenSuccess() = onIO {
        var root: LoginV2Pojo.Root? = null
        while (true) {
            val url = API.getLoginStatusV2.param { it["qrcode_key"] = authKey }
            root = httpClient.getForString(url).toObjJson(LoginV2Pojo.Root::class.java)
            if (root.data!!.code == 0) {
                break
            } else {
                logger.debug( "login message: ${root.data!!.message}")
                delay(1300)
            }
        }
        val cookie = httpClient.getCookieStore().toCookies()
        cookie.refreshToken = root!!.data!!.refresh_token
        cookie.timestamp = root.data!!.timestamp
        settingCenter.saveSetting(cookie)

        logger.info("序列化 Cookie : refreshToken=${cookie.refreshToken.substring(0,cookie.refreshToken.length/2)}******************")
    }

    private fun String.genericQRCode(size: Int) = ByteArrayOutputStream(500).apply {
        val hints = buildMap {
            put(EncodeHintType.MARGIN, 0)
        }

        MatrixToImageWriter.writeToStream(
            QRCodeWriter().encode(this@genericQRCode, BarcodeFormat.QR_CODE, size, size, hints),
            "png",
            this,
            MatrixToImageConfig(0xFF064E38.toInt(), 0x88FFFFFF.toInt())
        )
    }.toByteArray().inputStream()
}