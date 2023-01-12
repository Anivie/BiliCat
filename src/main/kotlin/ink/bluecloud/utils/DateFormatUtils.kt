package ink.bluecloud.utils

import java.text.DateFormat
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

/**
 * 格式化如期
 */
inline fun Date.format(pattern: String = "yyyy-MM-dd"): String = SimpleDateFormat(pattern).format(this)


/**
 * 加日期
 */
inline fun Date.add(amount: Int, field: Int = Calendar.MONTH): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.MONTH, -1)
    return calendar.time
}

/**
 * 日期格式校验
 */
inline fun String.isDate(format0: String = "yyyy-MM-dd", strict: Boolean = true): Boolean {
    if (strict && this.length != format0.length) return false
    if (this.isEmpty() || format0.isEmpty()) return false

    var dttm: String = this
    var format: String = format0
    if (format.replace("'.+?'".toRegex(), "").indexOf("y") < 0) {
        format += "/yyyy"
        val formatter: DateFormat = SimpleDateFormat("/yyyy")
        dttm += formatter.format(Date())
    }
    val formatter = SimpleDateFormat(format)
    formatter.isLenient = false
    val pos = ParsePosition(0)
    val date = formatter.parse(dttm, pos)
    if (date == null || pos.errorIndex > 0) return false
    if (pos.index != dttm.length) return false
    return formatter.calendar[Calendar.YEAR] <= 9999
}