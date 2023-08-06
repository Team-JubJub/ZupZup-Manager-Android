package com.example.zupzup_manager.ui.common

import android.content.res.Resources
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale

fun Int.toTimeFormat(): String {
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

fun Long.toSimpleDateFormat(): String {
    return SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.KOREAN).format(this)
}

fun Long.toDetailDateFormat(): String {
    return SimpleDateFormat("yyyy년 MM월 dd일 HH:mm", Locale.KOREAN).format(this)
}

fun Int.toDecimalFormat() : String {
    return DecimalFormat("#,###").format(this)+"원"
}

fun Float.fromDpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}