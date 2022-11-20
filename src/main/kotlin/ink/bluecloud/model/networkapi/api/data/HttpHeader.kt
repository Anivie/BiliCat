@file:UseSerializers(OKHttpHeaderSerializer::class)
package ink.bluecloud.model.networkapi.api.data

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import okhttp3.Headers
import okhttp3.Headers.Companion.toHeaders

@Serializable
data class HttpHeaders(
    val biliUserInfoHeaders: Headers,
    val favListHeaders: Headers,
    val biliWwwM4sHeaders: Headers,
    val logoutHeaders: Headers,
    val biliAppJsonAPIHeaders: Headers,
    val commonHeaders: Headers,
    val headers: Headers,
    val biliJsonAPIHeaders: Headers,
    val biliAppDownHeaders: Headers,
    val biliLoginAuthVaHeaders: Headers,
    val biliLoginAuthHeaders: Headers,
    val danmuHeaders: Headers,
    val allFavListHeaders: Headers,
    val biliWwwFLVHeaders: Headers,
    val actionHeaders: Headers,
)

object OKHttpHeaderSerializer: KSerializer<Headers> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("HeadersSerializer", PrimitiveKind.BYTE)
    override fun serialize(encoder: Encoder, value: Headers) {
        encoder.encodeSerializableValue(encoder.serializersModule.serializer(),value.toMap())
    }

    override fun deserialize(decoder: Decoder): Headers {
        return decoder.decodeSerializableValue(decoder.serializersModule.serializer<Map<String,String>>()).toHeaders()
    }
}
