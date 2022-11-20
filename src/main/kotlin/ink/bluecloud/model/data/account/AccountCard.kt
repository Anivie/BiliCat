package ink.bluecloud.model.data.account

import java.io.InputStream

data class AccountCard(
    val name:String,
    val mid:Long,
    val coin: Int,
    val vip:Boolean,
    val head: InputStream,
    //等级
    val level:Int,
    val levelCurrentExp: Int,
    val levelCurrentMin: Int,
    val levelNextExp: Int,
    //头像框
    val expire: Int,
    val image: InputStream?,
    val imageEnhance: InputStream?,
    val imageEnhanceFrame: InputStream?  ,
    val pendantName: String?,
    val pid: Int,
    //认证信息
    val officialDesc: String,
    val officialRole: Int,
    val officialTitle: String,
    val officialType: Int,
)