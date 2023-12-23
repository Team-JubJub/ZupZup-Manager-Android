package zupzup.manager.ui.store.binding

import android.os.Build
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import android.widget.ToggleButton
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.databinding.BindingAdapter
import zupzup.manager.R
import zupzup.manager.domain.models.store.ModifyStoreModel
import zupzup.manager.ui.common.fromDpToPx
import zupzup.manager.ui.store.StoreFragment

@BindingAdapter("modifyList", "tvWeek", "clickListener")
fun bindModifyButton(
    textView: TextView,
    modifyList: List<*>,
    tvWeek: String?,
    clickListener: StoreFragment.StoreClickListener
) {
    val (openTime, closeTime, saleStartTime, saleEndTime) = modifyList.mapNotNull { it as? TextView }
    val closedDay = if (tvWeek == "휴무일 없음") {
        null
    } else {
        tvWeek.toString()
    }


    // 초기 상태(null 값) 수정 완료 버튼 누를 경우
    // 한 번 설정한 이후로 다시 null 값으로 바꾸는 방법은 없음
    textView.setOnClickListener {
        val timeFormats = listOf(openTime, closeTime, saleStartTime, saleEndTime)
        val timeIntervals = timeFormats.map {
            textView.text.takeIf { it.isNotBlank() }
                ?.split(":")
                ?.map{it.toIntOrNull()}
        }
        // 영업 시간 or 할인 시간 유효성 검사하는데, null 값 있으면 그 부분은 검사하지 않고 넘어감
        // ex) 영업 종료 시간은 비었는데, 시작 시간만 설정한 경우 => ok
        // ex) 영업 시간 모두 설정되어있는데, 시작 시간이 더 늦는 경우 => 검사 걸림
        // 할인 시간도 마찬가지로 검사함
        val isInvalid = if (timeIntervals.all { it?.size == 2 }) {
            val checkInvalidity = { index1: Int, index2: Int ->
                (timeIntervals[index1]?.get(0) ?: 0) > (timeIntervals[index2]?.get(0) ?: 0) ||
                        ((timeIntervals[index1]?.get(0) ?: 0) == (timeIntervals[index2]?.get(0) ?: 0) && (timeIntervals[index1]?.get(1) ?: 0) >= (timeIntervals[index2]?.get(1) ?: 0))
            }

            val invalidMessage = when {
                checkInvalidity(0, 1) -> "영업 시간을 다시 설정해주세요."
                checkInvalidity(2, 3) -> "할인 시간을 다시 설정해주세요."
                else -> null
            }

            invalidMessage?.let {
                Toast.makeText(textView.context, it, Toast.LENGTH_SHORT).show()
                true
            } ?: false
        } else {
            // 만약 모든 시간을 설정해야 하면, Toast 주석 풀고, true로 값 변경할 것
            // => 아래 클릭 리스너 실행되지 않음, 조건 만족해야 수정됨
//            Toast.makeText(textView.context, "시간을 설정해주세요.", Toast.LENGTH_SHORT).show()
            false
        }

        if (!isInvalid) {
            val modifyStoreModel = ModifyStoreModel(
                openTime = openTime.text.toString(),
                closeTime = closeTime.text.toString(),
                saleTimeStart = saleStartTime.text.toString(),
                saleTimeEnd = saleEndTime.text.toString(),
                closedDay = closedDay
            )
            Log.d("수정된 값", "$modifyStoreModel")

            clickListener.modifyStoreDetail(modifyStoreModel, null)
        }
    }
//    Glide
//        .with(imageView.context)
//        .transform(
//            CenterCrop()
//        )
//        // disk 캐싱 추가
//        .diskCacheStrategy(DiskCacheStrategy.ALL)
//        .into(imageView)
}

@RequiresApi(Build.VERSION_CODES.M)
@BindingAdapter("linearLayout", "anotherTime", "timePicker", "anotherTimePicker", "timeLayout", "line", "modifyButton")
fun bindTimePickerWithTextView(
    textView: TextView,
    linearLayout: LinearLayout,
    anotherTime: String,
    timePicker: TimePicker,
    anotherTimePicker: TimePicker,
    timeLayout: LinearLayout,
    line: View,
    modifyButton: TextView
) {
    val initialTime = textView.text.toString()

    // 시간 텍스트뷰를 선택할 때
    linearLayout.setOnClickListener {
        // 타임피커 및 구분선 visibility 처리, 레이아웃 배경 변경
        if (timePicker.visibility == View.VISIBLE) {
            timePicker.visibility = View.GONE
            line.visibility = View.GONE

            val drawableResourceId = R.drawable.frame_stroke_rect_corner_8_ivorygray_200_100
            val drawable = ContextCompat.getDrawable(timeLayout.context, drawableResourceId)
            timeLayout.background = drawable
            timeLayout.setPadding(16f.fromDpToPx())
        } else {
            anotherTimePicker.visibility = View.GONE
            timePicker.visibility = View.VISIBLE
            line.visibility = View.VISIBLE

            val drawableResourceId = R.drawable.frame_price_or_time_top
            val drawable = ContextCompat.getDrawable(timeLayout.context, drawableResourceId)
            timeLayout.background = drawable
            timeLayout.setPadding(16f.fromDpToPx())

            // 타임피커 및 구분선 visibility 처리, 레이아웃 배경 변경
            timePicker.setIs24HourView(true)
            if (!initialTime.isNullOrEmpty()) {
                val initialHour = initialTime.substring(0, 2).toInt()
                val initialMinute = initialTime.substring(3, 5).toInt()
                timePicker.hour = initialHour
                timePicker.minute = initialMinute
            }
        }
    }

    // 타임피커 선택할 때
    timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
        Log.d("테스트2", anotherTime)
        var anotherHour = 0
        var anotherMinute = 0

        if (!anotherTime.isNullOrEmpty()) {
            anotherHour = anotherTime.substring(0, 2).toInt()
            anotherMinute = anotherTime.substring(3, 5).toInt()
        }

        val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
        textView.text = selectedTime

        // 마감시간 타임피커와 종료시간 타임피커인 경우, 이전 시간을 선택할 수 없도록 제한함
        if (timePicker.id == R.id.close_time_picker || timePicker.id == R.id.sale_end_time_picker) {
            if (anotherTime.isNullOrEmpty()) {
                anotherHour = 0
                anotherMinute = 0
            }
            if (hourOfDay < anotherHour || (hourOfDay == anotherHour && minute <= anotherMinute)) {
                val adjustedHour = if (anotherHour == 0 && anotherMinute == 0) 23 else anotherHour
                val adjustedMinute = if (anotherHour == 0 && anotherMinute == 0) 59 else anotherMinute
                timePicker.hour = adjustedHour
                timePicker.minute = adjustedMinute

                modifyButton.isClickable = false
                modifyButton.background = ContextCompat.getDrawable(timeLayout.context, R.drawable.frame_rectangle_corner_14_neutralgray_150)
                Toast.makeText(timePicker.context, "이전 시간을 선택할 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
            else {
                modifyButton.isClickable = true
                val drawable = ContextCompat.getDrawable(timeLayout.context, R.drawable.frame_rectangle_corner_14_tangerine_300)
                modifyButton.background = drawable
            }
            // 영업시간 타임피커와 시작시간 타임피커인 경우, 이후 시간을 선택할 수 없도록 제한함
        } else if (timePicker.id == R.id.open_time_picker || timePicker.id == R.id.sale_start_time_picker) {
            if (anotherTime.isNullOrEmpty()) {
                anotherHour = 23
                anotherMinute = 59
            }
            if (hourOfDay > anotherHour || (hourOfDay == anotherHour && minute >= anotherMinute)) {
                val adjustedHour = if (anotherHour == 23 && anotherMinute == 59) 0 else anotherHour
                val adjustedMinute = if (anotherHour == 23 && anotherMinute == 59) 0 else anotherMinute
                timePicker.hour = adjustedHour
                timePicker.minute = adjustedMinute

                modifyButton.isClickable = false
                modifyButton.background = ContextCompat.getDrawable(timeLayout.context, R.drawable.frame_rectangle_corner_14_neutralgray_150)
                Toast.makeText(timePicker.context, "이후 시간을 선택할 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
            else {
                modifyButton.isClickable = true
                val drawable = ContextCompat.getDrawable(timeLayout.context, R.drawable.frame_rectangle_corner_14_tangerine_300)
                modifyButton.background = drawable
            }
        }
}
}

@BindingAdapter("closedDay", "sun", "mon", "tue", "wed", "thu", "fri", "sat")
fun bindWeekWithTextView(
    weekTextView: TextView,
    closedDay: String?,
    sun: ToggleButton,
    mon: ToggleButton,
    tue: ToggleButton,
    wed: ToggleButton,
    thu: ToggleButton,
    fri: ToggleButton,
    sat: ToggleButton
) {
    if (closedDay == null) weekTextView.text = "휴무일 없음"
    else weekTextView.text = closedDay

    val toggleButtons = listOf(sun, mon, tue, wed, thu, fri, sat)
    val weekdays = listOf("일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일")
    val days = weekTextView.text.toString().split(", ").toMutableList()

    for (i in toggleButtons.indices) {
        val dayOfWeek = weekdays[i]

        toggleButtons[i].isChecked = days.contains(dayOfWeek)

        toggleButtons[i].setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && !days.contains(dayOfWeek)) {
                days.add(dayOfWeek)
            } else if (!isChecked && days.contains(dayOfWeek)) {
                days.remove(dayOfWeek)
            }
            val orderedDays = weekdays.filter { days.contains(it) }
            val selectedDaysString = orderedDays.joinToString(", ")
            weekTextView.text = if (selectedDaysString.isBlank()) {
                "휴무일 없음"
            } else {
                selectedDaysString
            }
        }
    }
}