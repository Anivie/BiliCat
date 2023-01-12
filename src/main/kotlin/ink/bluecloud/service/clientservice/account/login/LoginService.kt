package ink.bluecloud.service.clientservice.account.login

import com.alibaba.fastjson2.JSONObject
import com.alibaba.fastjson2.parseObject
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.client.j2se.MatrixToImageConfig
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import ink.bluecloud.service.ClientService
import ink.bluecloud.utils.onIO
import ink.bluecloud.utils.settingloader.saveSetting
import kotlinx.coroutines.delay
import org.koin.core.annotation.Factory
import java.io.ByteArrayOutputStream
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Factory
class LoginService: ClientService() {
    private lateinit var authKey:String

    suspend fun getCode() = suspendCoroutine { coroutine ->
        httpClient.getFor(
            netWorkResourcesProvider.api.getLoginQRCode
        ) {
            body.string().parseObject().getJSONObject("data").run {
                authKey = getString("oauthKey")
                coroutine.resume(getString("url").genericQRCode(300))
            }
        }
    }

    suspend fun whenSuccess() = onIO {
        var json: JSONObject?
        while (true) {
            json = suspendCoroutine { coroutine ->
                httpClient.postFor(
                    netWorkResourcesProvider.api.getLoginStatus,
                    mapOf("oauthKey" to authKey, "gourl" to "https://www.bilibili.com/"),
                ) {
                    coroutine.resume(body.string().parseObject())
                }
            }

            if (json!!.getBoolean("status")) {
                break
            } else {
                delay(500)
            }
        }

        settingCenter.saveSetting(httpClient.getCookieStore().toCookies())
        return@onIO json!!
    }

    private suspend fun getJson() = suspendCoroutine { coroutine ->
        httpClient.postFor(
            netWorkResourcesProvider.api.getLoginStatus,
            mapOf("oauthKey" to authKey, "gourl" to "https://www.bilibili.com/"),
        ) {
            coroutine.resume(body.string().parseObject())
        }
    }

    private fun String.genericQRCode(size: Int) = ByteArrayOutputStream(500).apply {
        val hints = buildMap {
            put(EncodeHintType.MARGIN, 0)
        }

        MatrixToImageWriter.writeToStream(
            QRCodeWriter().encode(this@genericQRCode, BarcodeFormat.QR_CODE, size, size,hints),
            "png",
            this,
            MatrixToImageConfig(0xFF064E38.toInt(), 0x88FFFFFF.toInt())
        )
    }.toByteArray().inputStream()
}