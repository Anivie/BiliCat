package ink.bluecloud.model.pojo.login

class LoginV2Pojo {
    data class Root(
        val code: Int,
        val `data`: Data?,
        val message: String,
        val ttl: Int,
    )

    data class Data(
        val code: Int,
        val message: String?,
        val refresh_token: String,
        val timestamp: Long,
        val url: String?,
    )
}