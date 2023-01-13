package ink.bluecloud.service.cache

import org.koin.core.annotation.Single
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import kotlin.io.path.exists

@Single class CacheCenterImpl: CacheCenter() {
    override suspend fun loadCache(vararg name: String, cacheMaker: suspend () -> ByteArray):ByteArray {
        return Paths.get("cache", *name).run {
            if (exists()) {
                Files.readAllBytes(this)
            } else {
                cacheMaker().apply {
                    Files.write(this@run,this,StandardOpenOption.CREATE)
                }
            }
        }
    }
}