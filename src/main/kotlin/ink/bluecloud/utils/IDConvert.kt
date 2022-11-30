package ink.bluecloud.utils


/**
 * ID转换工具
 */
class IDConvert {
    private val table = "fZodR9XQDSUm21yCkr6zBqiveYah8bt4xsWpHnJE7jL5VG3guMTKNPAwcF"
    private val tr: ArrayList<Map<String, Any>> = ArrayList()
    private val s = intArrayOf(11, 10, 3, 8, 4, 6)
    private val xor = 177451812.0
    private val add = 8728348608.0


    /**
     * BV转AV
     * @param bv
     * @return
     */
    fun BvToAv(bv: String): String? {
        return "AV" + BvToAvNumber(bv)
    }

    /**
     * BV转AV
     * @param bv
     */
    fun BvToAvNumber(bv: String): Int {
        val a = table.toCharArray()
        for (i in 0..57) {
            val map: MutableMap<String, Any> = HashMap()
            map[a[i].toString()] = i
            tr.add(map)
        }
        var r = 0.0
        val xe = bv.toCharArray()
        for (i in 0..5) {
            for (c in tr) {
                for (key in c.keys) {
                    if (key == xe[s[i]].toString()) {
                        val value = c[key]
                        r += Integer.valueOf(value.toString()) * Math.pow(58.0, i.toDouble())
                    }
                }
            }
        }
        return (r - add).toInt() xor xor.toInt()
    }

    /**
     * Av 转 BV
     * @param av
     */
    fun AvToBv(av: String): String? {
        var av = av
        av = av.replace("\\D.".toRegex(), "")
        val temp = av.toInt()
        val a = table.toCharArray()
        val arr = charArrayOf('B', 'V', '1', ' ', ' ', '4', ' ', '1', ' ', '7', ' ', ' ')
        val r = (Integer.valueOf(av) xor xor.toInt()) + add
        for (i in 0..5) {
            arr[s[i]] = a[(Math.floor(r / Math.pow(58.0, i.toDouble())) % 58).toInt()]
            val dd = (Math.floor(r / Math.pow(58.0, i.toDouble())) % 58).toInt()
        }
        return String(arr)
    }
}

inline fun String.toAvNumber():Int = IDConvert().BvToAvNumber(this)