package ink.bluecloud.service.httpserver.core

import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream

/**
 * 静态资源加载器
 * 不支持运行时变更根路径
 */
object StaticResources {
    const val staticResourcePath = "resources:static"
    const val indexPath = "/index.html"
    @Throws(IOException::class)
    fun getStaticResourceAsStream(path: String?): InputStream? {
        //如果资源在 Resources 目录下
        if (staticResourcePath.startsWith("resources:")) {
            return getResourceAsStream(staticResourcePath.substring("resources:".length) + path)
        } else {
            if (File(staticResourcePath + path).exists()) return FileInputStream(staticResourcePath + path)
        }
        return null
    }

    @Throws(IOException::class)
    private fun getResourceAsStream(path: String): InputStream? {
        return Thread.currentThread().contextClassLoader.getResourceAsStream(path)
    }

    fun defaultPath(): String {
        return "resources:static"
    }
}