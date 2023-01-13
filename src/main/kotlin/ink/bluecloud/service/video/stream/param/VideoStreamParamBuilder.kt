package ink.bluecloud.service.video.stream.param

/**
 * 视频参数构建
 * @Building:等待重构
 */
class VideoStreamParamBuilder(val qn: Qn = Qn.P720_ALL, val fnval: Fnval = Fnval.Dash) {
    private val fnList: ArrayList<Int> = ArrayList()
    fun append(fnval0: Fnval) {
        if (fnval0.type <= 16) throw IllegalArgumentException("Type must be video encoding format")
        fnList.add(fnval0.type)
    }

    fun build():String {
        val string: StringBuilder = StringBuilder()
        string.append("fnver").append("=").append(0).append("&")
        string.append("fourk").append("=").append(1).append("&")
        string.append("qn").append("=").append(qn.value).append("&")
        when (qn.value) {
            Qn.P4K.value -> {
                string.append("128=128").append("&")
                append(Fnval.P4K)
            }

            Qn.PHDR.value -> {
                string.append("64=64").append("&")
                append(Fnval.Dash_HDR)
            }

            Qn.PDOLBY.value -> {
                string.append("512=512").append("&")
            }

            Qn.P8K.value -> {
                string.append("1024=1024").append("&")
                append(Fnval.Dash_8K)
            }
        }
        var count: Int = fnval.type
        fnList.forEach {
            count += it
        }
        string.append("fnval").append("=").append(count)
        return string.toString()
    }
}