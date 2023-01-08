package ink.bluecloud.model.pojo.user.info

/**
     * 用户等级
     */
    data class LevelInfo(
        val current_exp: Int,
        /**
         * 用户等级
         */
        val current_level: Int,
        val current_min: Int,
        val next_exp: Int,
    )