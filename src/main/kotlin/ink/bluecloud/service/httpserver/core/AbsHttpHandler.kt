package ink.bluecloud.service.httpserver.core

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import ink.bluecloud.service.ClientService
import java.io.IOException
import java.util.*
import java.util.logging.Logger

abstract class AbsHttpHandler(route: String) : HttpHandler, ClientService() {
    private var exchange: HttpExchange? = null
    val route: String

    init {
        this.route = route.trim { it <= ' ' }.lowercase(Locale.getDefault())
    }

    @Throws(IOException::class)
    override fun handle(exchange: HttpExchange) {
        logger.debug("Method: ${exchange.requestMethod}  Path: ${exchange.requestURI.path}")
        this.exchange = exchange
        //根据根据请求方法来分发请求
        doMethod(exchange.requestMethod)
    }

    @Throws(IOException::class)
    private fun doMethod(methodName: String) {
        val response = Response(exchange)
        val request = Request(exchange)
        when (methodName.uppercase(Locale.getDefault())) {
            "GET" -> {
                try {
                    doGet(request, response)
                } catch (e: Exception) {
                    doError(e)
                }
            }

            "POST" -> {
                try {
                    doPost(request, response)
                } catch (e: Exception) {
                    doError(e)
                }
            }

            else -> {
                logger.info("无法解析的 Method :$methodName")
            }
        }
        close(response)
    }

    @Throws(IOException::class)
    protected fun close(response: Response) {
        if (!response.isClosed) response.end()
        exchange!!.close()
    }

    protected fun doError(e: Exception) {
        //发送响应标头。必须在之前调用 下一步。
//        exchange.sendResponseHeaders(200, "agag".length());
        e.printStackTrace()
    }

    @Throws(IOException::class)
    protected abstract fun doGet(request: Request, response: Response)

    @Throws(IOException::class)
    protected abstract fun doPost(request: Request, response: Response)
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        return if (o !is AbsHttpHandler) false else route == o.route
    }

    override fun hashCode(): Int {
        return route.hashCode()
    }

    override fun toString(): String {
        return route
    }


}