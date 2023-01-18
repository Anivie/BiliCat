package ink.bluecloud.service.httpserver.core

import com.sun.net.httpserver.HttpServer
import ink.bluecloud.service.ClientService
import java.io.IOException
import java.net.InetSocketAddress
import java.util.concurrent.Executor
import java.util.logging.Logger

class BiliCatHttpServer : ClientService() {
    var port = "8080"
    var executor: Executor? = null
    val routes = HashSet<AbsHttpHandler>()
    var isRunning = false
        private set
    var requestQueue = 50
    var server: HttpServer? = null

    @Throws(IOException::class)
    fun start(): BiliCatHttpServer {
        logger.info("Starting http server...")
        check(!isRunning) { "HttpServer is already running" }
        isRunning = true
        HttpServer.create()
        // 绑定地址，端口，请求队列
        server = HttpServer.create(InetSocketAddress(port.toInt()), 50)
        logger.info("Loading route...")
        //注册路由
        for (route in routes) server!!.createContext(route.route, route)
        server!!.createContext(RootPath, StaticResourcesRoute(routes)) //注册静态资源路由与根路由
        logger.info("routes${routes.size}: $routes")
        if (routes.size ==0) logger.warn("routes.size = 0")
        // 配置HtteServer请求处理的线程池，没有配置则使用默认的线程池；
        if (executor != null) server!!.executor = executor
        executor = server!!.executor
        server!!.start()
        logger.info("Server have been started. Listen to port: $port")

        return this
    }

    fun addRoute(route: AbsHttpHandler):BiliCatHttpServer {
        routes.add(route)
        return this
    }

    fun removeRoute(route: AbsHttpHandler) {
        routes.remove(route)
    }

    fun stop(delay: Int) {
        server!!.stop(delay)
        logger.info("Server have been stopped")
    }

    companion object {
        var RootPath = "/"
    }
}