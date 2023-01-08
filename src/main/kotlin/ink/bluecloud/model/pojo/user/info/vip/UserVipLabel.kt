package ink.bluecloud.model.pojo.user.info.vip

data class UserVipLabel(
//        val bg_color: String,
//        val bg_style: Int,
//        val border_color: String,
//        val img_label_uri_hans: String,
//        val img_label_uri_hans_static: String,
//        val img_label_uri_hant: String,
//        val img_label_uri_hant_static: String,
        /**
         * 会员类型
         * vip：大会员
         * annual_vip：年度大会员
         * ten_annual_vip：十年大会员
         * hundred_annual_vip：百年大会员
         */
        val label_theme: String,
//        val path: String,
        /**
         * 会员类型文案
         */
        val text: String,
//        val text_color: String,
//        val use_img_label: Boolean,
    )