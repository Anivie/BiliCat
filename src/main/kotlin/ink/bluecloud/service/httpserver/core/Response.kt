package ink.bluecloud.service.httpserver.core

import com.sun.net.httpserver.Headers
import com.sun.net.httpserver.HttpExchange
import ink.bluecloud.utils.logger
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.OutputStream
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

class Response(private val exchange: HttpExchange?) {
    private val ByteArray = ByteArrayOutputStream()
    var code = 200
    var isClosed = false
        private set

    init {
        headers["Content-Type"] = "text/html; charset=utf-8"
    }

    @get:Throws(IOException::class)
    private val responseBody: OutputStream
        private get() = exchange!!.responseBody

    @Throws(IOException::class)
    fun write(body: Int) {
        if (isClosed) throw IOException("Response stream is closed!")
        ByteArray.write(body)
    }

    @Throws(IOException::class)
    fun write(body: ByteArray?) {
        if (isClosed) throw IOException("Response stream is closed!")
        ByteArray.write(body)
    }

    @Throws(IOException::class)
    fun write(body: ByteArray?, off: Int, len: Int) {
        if (isClosed) throw IOException("Response stream is closed!")
        ByteArray.write(body, off, len)
    }

    @Throws(IOException::class)
    fun write(body: String) {
        if (isClosed) throw IOException("Response stream is closed!")
        ByteArray.write(body.toByteArray(StandardCharsets.UTF_8))
    }

    @Throws(IOException::class)
    fun write(body: String, charset: Charset?) {
        if (isClosed) throw IOException("Response stream is closed!")
        ByteArray.write(body.toByteArray(charset!!))
    }

    @Throws(IOException::class)
    fun flush() {
        if (isClosed) throw IOException("Response stream is closed!")
        ByteArray.flush()
    }

    @Throws(IOException::class)
    fun send(body: ByteArray?) {
        write(body)
        end()
    }

    @Throws(IOException::class)
    fun send(body: ByteArray?, off: Int, len: Int) {
        write(body, off, len)
        end()
    }

    @Throws(IOException::class)
    fun send(body: String, charset: Charset?) {
        write(body, charset)
        end()
    }

    @Throws(IOException::class)
    fun send(body: String) {
        write(body)
        end()
    }

    @Throws(IOException::class)
    fun end() {
        if (isClosed) throw IOException("Response stream is closed!")
        isClosed = true
        exchange!!.sendResponseHeaders(code, ByteArray.size().toLong())
        responseBody.write(ByteArray.toByteArray())
        logger().debug("Http Server${exchange.localAddress} => code:$code")
        ByteArray.close()
    }

    val headers: Headers
        get() = exchange!!.responseHeaders

    fun addHeader(key: String?, value: String?): Headers {
        headers.add(key, value)
        return headers
    }
}