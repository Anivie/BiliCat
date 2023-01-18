package ink.bluecloud.service.httpserver.core

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * 全局静态资源路由
 */
class StaticResourcesRoute(private val Routes: HashSet<AbsHttpHandler>) : AbsHttpHandler(BiliCatHttpServer.RootPath) {
    init {
        require(BiliCatHttpServer.RootPath.endsWith("/")) { "The RootPath must end with '/'" }
    }

    @Throws(IOException::class)
    override fun doGet(request: Request, response: Response) {
        //浏览器访问的获取路由
        var path = request.URI.path
        //如果访问根路径，则重定向到 Index 页面
        if (path == "/") path = StaticResources.indexPath
        //判断是不是一个已经注册的路由
        val finalPath = path
        val isRoute = Routes.stream().anyMatch { absHttpHandler: AbsHttpHandler -> absHttpHandler.route == finalPath }
        if (isRoute) logger.info("Error： 静态资源与注册路由路径一致")
        //是否存在静态资源,存在和获取，不存在则 404
        val stream = StaticResources.getStaticResourceAsStream(path)
        if (stream == null) {
            notFound(request, response)
            return
        }
        //返回这个静态资源
        var arrayOutputStream: ByteArrayOutputStream? = null
        try {
            arrayOutputStream = ByteArrayOutputStream()
            val bytes = readStaticResource(stream, arrayOutputStream)
            response.send(bytes)
        } finally {
            arrayOutputStream?.close()
            stream.close()
        }
        if (!response.isClosed) response.send("Error: 未能加载的静态资源")
        if (!response.isClosed) logger.info("Error: 未能加载的静态资源: " + StaticResources.staticResourcePath + path)
    }

    @Throws(IOException::class)
    private fun notFound(request: Request, response: Response) {
        response.code = 404
        response.send("Not Found")
    }

    @Throws(IOException::class)
    private fun readStaticResource(stream: InputStream, arrayOutputStream: ByteArrayOutputStream): ByteArray {
        var len: Int
        val bytes = ByteArray(1024)
        while (stream.read(bytes).also { len = it } != -1) {
            arrayOutputStream.write(bytes, 0, len)
        }
        return arrayOutputStream.toByteArray()
    }

    @Throws(IOException::class)
    override fun doPost(request: Request, response: Response) {
        notFound(request, response)
    }
}