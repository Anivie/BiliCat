package ink.bluecloud.service.cache

abstract class CacheCenter {
    abstract suspend fun loadCache(vararg name: String,cacheMaker: suspend () -> ByteArray):ByteArray
}