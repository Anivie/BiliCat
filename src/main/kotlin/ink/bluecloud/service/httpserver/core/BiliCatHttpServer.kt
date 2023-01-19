package ink.bluecloud.service.httpserver.core

import com.sun.net.httpserver.HttpServer
import ink.bluecloud.service.ClientService
import java.io.IOException
import java.net.InetSocketAddress
import java.util.concurrent.Executor
import java.util.logging.Logger

/**
 * rootPath: 网站应用根路径
 */
class BiliCatHttpServer(private val rootPath:String = "/") : ClientService() {
    private val routes = HashSet<AbsHttpHandler>()
    private var isRunning = false
    private var server: HttpServer? = null


    @Throws(IOException::class)
    fun start(port:String = "8080",requestQueue:Int = 50,executor: Executor? = null): BiliCatHttpServer {
        logger.info("Starting http server...")
        check(!isRunning) { "HttpServer is already running" }
        isRunning = true
        HttpServer.create()
        // 绑定地址，端口，请求队列
        server = HttpServer.create(InetSocketAddress(port.toInt()), requestQueue)
        logger.info("Loading route...")
        //注册路由
        for (route in routes) server!!.createContext(route.route, route)
        server!!.createContext(rootPath, StaticResourcesRoute(routes,rootPath)) //注册静态资源路由与根路由
        logger.info("routes(size: ${routes.size}): $routes")
        if (routes.size ==0) logger.warn("http server 路由表是空的，请检查是否有路由没有添加到服务器里面，请使用 BiliCatHttpServer().addRoute() 添加")
        // 配置HttpServer请求处理的线程池，没有配置则使用默认的线程池；
        if (executor != null) server!!.executor = executor
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
}