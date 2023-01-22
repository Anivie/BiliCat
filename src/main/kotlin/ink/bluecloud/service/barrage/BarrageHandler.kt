package ink.bluecloud.service.barrage

import ink.bluecloud.model.pojo.barrage.real.Barrage
import ink.bluecloud.model.pojo.barrage.real.Barrages
import ink.bluecloud.model.pojo.barrage.real.toBarrage
import ink.bluecloud.service.ClientService
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import org.koin.core.annotation.Single

@Single
class BarrageHandler(private val cid: Long):ClientService() {
    @OptIn(ExperimentalSerializationApi::class)
   fun handle(bytes: ByteArray): List<Barrage> {
        logger.info("视频弹幕包长度(byte): ${bytes.size}")
        kotlin.runCatching { ProtoBuf.decodeFromByteArray<Barrages>(bytes).barrages.map { it.toBarrage(cid) } }
            .onFailure {
                logger.error(
                    "An error occurred and the bullet screen package could not be parsed: \n" +
                            "------------------------------------------------------------------------\n" +
                            " ${String(bytes)}\n" +
                            "------------------------------------------------------------------------\n\n", it
                )
            }
            .onSuccess { return it }
       return ArrayList()
    }
}