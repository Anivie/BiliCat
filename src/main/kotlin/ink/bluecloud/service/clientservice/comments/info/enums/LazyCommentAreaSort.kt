package ink.bluecloud.service.clientservice.comments.info.enums

enum class LazyCommentAreaSort(val value: Int) {
    /**
     * 热度排序
     */
    HOT_ONLY(0),

    /**
     * 热度+时间排序
     */
    HOT_AND_TIME(1),

    /**
     * 时间排序
     */
    TIME_ONLY(2),
    /**
     * 热度排序
     */
    DEFAULT_HOT(3);
}