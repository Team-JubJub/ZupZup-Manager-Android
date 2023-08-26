package zupzup.manager.data.common

fun Int.toTimeString(): String {
    val str = this.toString()
    return if (this == 0) {
        "00:00"
    } else if (this < 10) {
        "00:0$this"
    } else if (this < 100) {
        "00:$str"
    } else if (this < 1000) { // 958
        "0${str.substring(0, 1)}:${str.substring(1)}"
    } else "${str.substring(0, 2)}:${str.substring(2)}"
}