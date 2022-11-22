package ink.bluecloud.model.networkapi.api

import ink.bluecloud.model.networkapi.api.data.HttpApi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import org.koin.core.annotation.Single
import java.nio.file.Files

@Single
@OptIn(ExperimentalSerializationApi::class)
class NetWorkResourcesProviderImpl: NetWorkResourcesProvider() {
    override val api: HttpApi = ProtoBuf.decodeFromByteArray(Files.readAllBytes(apiFilePath))
}