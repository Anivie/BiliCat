package ink.bluecloud.model.data.account

import org.ktorm.ksp.api.Table

@Table("user_info")
data class UserInfo(
    val name:String,
    val mid:String,
    val cookie: String,
    val fans: Int,
    val followers: Int,
    val signature: Int,
    val head: ByteArray
)