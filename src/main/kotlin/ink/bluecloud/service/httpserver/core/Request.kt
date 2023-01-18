package ink.bluecloud.service.httpserver.core

import com.sun.net.httpserver.Headers
import com.sun.net.httpserver.HttpExchange
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.URI

class Request(private val exchange: HttpExchange?) {
    val method: String
        get() = exchange!!.requestMethod
    val headers: Headers
        get() = exchange!!.requestHeaders
    val URI: URI
        get() = exchange!!.requestURI
    /**
     * 获取 body 内容
     */
    val bodyByInputStream: InputStream
        get() = exchange!!.requestBody

    @get:Throws(IOException::class)
    val body: ByteArray
        get() {
            val byteArrayOutputStream = ByteArrayOutputStream()
            val stream = bodyByInputStream
            var len: Int
            val bytes = ByteArray(1024)
            while (stream.read(bytes).also { len = it } != -1) {
                byteArrayOutputStream.write(bytes, 0, len)
            }
            return byteArrayOutputStream.toByteArray()
        }
}