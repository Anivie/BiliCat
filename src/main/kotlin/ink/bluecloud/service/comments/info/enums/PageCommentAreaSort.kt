package ink.bluecloud.service.comments.info.enums

enum class PageCommentAreaSort(val value: Int) {
    TIME(0),

    /**
     * 按点赞数
     */
    LIKE(1),

    /**
     * 按回复数
     */
    REPLY(2);
}