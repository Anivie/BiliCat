package ink.bluecloud.model.data.video

data class VideoStreamData(
    val video: StreamMap,
    val audio: StreamMap,
)

class StreamMap {
    private val map: HashMap<Int, StreamList> = HashMap()

    fun set(id: Int, list: StreamList) {
        map[id] = list
    }

    fun put(id: Int, codecs: String, url: String) {
        val list = map[id] ?: StreamList()
        map[id] = list.put(codecs, url)
    }

    fun put(id: Int, codecs: String, urls: ArrayList<String>) {
        val list = map[id] ?: StreamList()
        map[id] = list.put(codecs, urls)
    }

    fun get(id: Int, codecs: String): ArrayList<String> {
        return map.get(id)?.get(codecs) ?: ArrayList()
    }

    /**
     * 获取该清晰度的媒体文件的URL
     * @param id 媒体文件质量
     * @param codecs 媒体文件编码
     */
    fun get(id: Int, codecs: VideoCodec): ArrayList<String> {
        return map.get(id)?.get(codecs.value) ?: ArrayList()
    }

    /**
     * 获取该清晰度的媒体文件的URL
     * @param id 媒体文件质量
     * @throws IllegalArgumentException 如果没有找到该清晰度的媒体文件就抛出此异常。所以在获取某一个清晰度视频时请确定是否存在该清晰度
     */
    fun get(id: Int): StreamList {
        return map.get(id) ?: throw NullPointerException("not found for id: $id")
    }

    fun keys(): MutableSet<Int> {
        return map.keys
    }

    fun values(): MutableCollection<StreamList> {
        return map.values
    }

    override fun toString(): String {
        return map.toString()
    }

    /////////////////////////////////////////////////////////////////////////////////
    class StreamList {
        private val map: HashMap<String, ArrayList<String>> = HashMap()

        fun set(codecs: String, urls: ArrayList<String>): StreamList {
            map[codecs] = urls
            return this
        }

        fun put(codecs: String, url: String): StreamList {
            val list = map[codecs] ?: ArrayList()
            list.add(url)
            map[codecs] = list
            return this
        }

        fun put(codecs: String, urls: ArrayList<String>): StreamList {
            val list = map[codecs] ?: ArrayList()
            list.addAll(urls)
            map[codecs] = list
            return this
        }

        fun get(codecs: String): ArrayList<String> {
            return map.get(codecs) ?: ArrayList()
        }

        fun get(codecs: VideoCodec): ArrayList<String> {
            return map.get(codecs.value) ?: ArrayList()
        }

        fun values(): MutableCollection<java.util.ArrayList<String>> {
            return map.values
        }

        fun key(): MutableSet<String> {
            return map.keys
        }


        override fun toString(): String {
            return map.toString()
        }
    }
}
