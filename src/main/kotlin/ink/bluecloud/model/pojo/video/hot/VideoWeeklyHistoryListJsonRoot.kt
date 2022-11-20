package ink.bluecloud.model.pojo.video.hot

class VideoWeeklyHistoryListJsonRoot {
    data class Root(
        val code: Int,
        val `data`: Data?,
        val message: String,
        val ttl: Int
    )

    data class Data(
        val list: List<Item>
    )

    data class Item(
        val name: String,
        val number: Int,
        val status: Int,
        val subject: String
    )
}