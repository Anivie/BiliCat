package ink.bluecloud.model.pojo.user.info

/**
     * 用户认证信息
     */
    data class OfficialVerify(
        /**
         * 认证信息	无为空
         */
        val desc: String?,
        /**
         * 是否认证
         * -1：无
         * 0：个人认证
         * 1：机构认证
         */
        val type: Int,

        val role: Int,
        val title: String?,
    )