package zupzup.manager.ui.utils

fun Int.toTimeString(): String {
    val text = this.toString()
    val timeString = if (this == 0) {
        "00:00"
    } else if (this < 10) {
        "00:0$this"
    } else if (this < 60) {
        "00:$this"
    } else if (this < 1000) { // 1.00 ~ 9.59
        "0${text.substring(0, 1)}:${text.substring(1, 3)}"
    } else {
        "${text.substring(0, 2)}:${text.substring(2, 4)}"
    }
    return timeString
}

fun Int.toPriceString(): String {
    val numberStr = this.toString()
    val length = numberStr.length
    val result = StringBuilder()
    for (i in 0 until length) {
        val digit = numberStr[i]
        result.append(digit)

        if ((length - i - 1) % 3 == 0 && i != length - 1) {
            result.append(',')
        }
    }
    return result.toString()
}