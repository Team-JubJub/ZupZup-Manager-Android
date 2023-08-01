package com.example.zupzup_manager.ui.store.binding

import android.media.Image
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import android.widget.ToggleButton
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.zupzup_manager.R
import com.example.zupzup_manager.domain.models.ModifyStoreModel
import com.example.zupzup_manager.ui.common.fromDpToPx
import com.example.zupzup_manager.ui.custom.CustomRoundedCornersTransformation
import com.example.zupzup_manager.ui.store.StoreFragment

@BindingAdapter("modifyList", "clickListener")
fun bindModifyButton(
    textView: TextView,
    modifyList: List<*>,
    clickListener: StoreFragment.StoreClickListener
) {
    val (openTime, closeTime, saleStartTime, saleEndTime, tvWeek) = modifyList

    val modifyStoreModel = ModifyStoreModel(
        openTime = openTime.toString(),
        closeTime = closeTime.toString(),
        saleTimeStart = saleStartTime.toString(),
        saleTimeEnd = saleEndTime.toString(),
        closedDay = tvWeek.toString()
    )

    textView.setOnClickListener {
        Log.d("TAG", "바꾸라고")
        Log.d("TAG", modifyStoreModel.toString())
        clickListener.modifyStoreDetail(modifyStoreModel, null)
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
@BindingAdapter("anotherTime", "timePicker", "anotherTimePicker", "timeLayout", "line", "modifyButton")
fun bindTimePickerWithTextView(
    textView: TextView,
    anotherTime: String,
    timePicker: TimePicker,
    anotherTimePicker: TimePicker,
    timeLayout: LinearLayout,
    line: View,
    modifyButton: TextView
) {
    val initialTime = textView.text.toString()
    Log.d("initial", textView.text.toString())
    Log.d("anotherTextView", anotherTime)
    Log.d("timePicker", timePicker.toString())
    val initialHour = initialTime.substring(0, 2).toInt()
    val initialMinute = initialTime.substring(3, 5).toInt()

    // 시간 텍스트뷰를 선택할 때
    textView.setOnClickListener {
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
            timePicker.hour = initialHour
            timePicker.minute = initialMinute
        }
    }

    // 타임피커 선택할 때
    timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
        val anotherHour = anotherTime.substring(0, 2).toInt()
        val anotherMinute = anotherTime.substring(3, 5).toInt()

        val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
        textView.text = selectedTime

        // 마감시간 타임피커와 종료시간 타임피커인 경우, 이전 시간을 선택할 수 없도록 제한함
        if (timePicker.id == R.id.close_time_picker || timePicker.id == R.id.sale_end_time_picker) {
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