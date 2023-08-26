package zupzup.manager.ui.common

import zupzup.manager.R

object OrderStateMapper {
    fun getStateBackgroundColor(state: String): Int {
        return when (state) {
            "NEW" -> R.color.orange_400
            "CONFIRM" -> R.color.tangerine_300
            "COMPLETE" -> R.color.green_300
            else -> R.color.ivory_gray_400
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