package com.example.zupzup_manager.ui.common

import com.example.zupzup_manager.R

object ReservationStateMapper {
    fun getStateBackgroundColor(state: String): Int {
        return when (state) {
            "NEW" -> R.color.bg_2
            "CONFIRM" -> R.color.purple
            "COMPLETE" -> R.color.green
            else -> R.color.warm_gray5
        }
    }

    fun getStateDescription(state: String): String {
        return when (state) {
            "NEW" -> "신규"
            "CONFIRM" -> "확정"
            "COMPLETE" -> "완료"
            else -> "취소"
        }
    }

}