package ink.bluecloud.model.networkapi.api

import ink.bluecloud.model.networkapi.api.data.HttpApi
import ink.bluecloud.model.networkapi.api.data.HttpHeaders
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import okhttp3.Cookie
import org.koin.core.annotation.Single
import java.nio.file.Files

@Single
@OptIn(ExperimentalSerializationApi::class)
class NetWorkResourcesProviderImpl: NetWorkResourcesProvider() {
    override val headers: HttpHeaders = ProtoBuf.decodeFromByteArray(Files.readAllBytes(headersFilePath))
    override val api: HttpApi = ProtoBuf.decodeFromByteArray(Files.readAllBytes(apiFilePath))
    override val cookieManager: Cookie
        get() = TODO("Not yet implemented")
}