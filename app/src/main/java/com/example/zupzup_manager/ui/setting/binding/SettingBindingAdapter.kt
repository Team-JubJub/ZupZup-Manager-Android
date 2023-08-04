package com.example.zupzup_manager.ui.setting.binding

import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.databinding.BindingAdapter
import com.example.zupzup_manager.R

@BindingAdapter("closedDay")
fun bindClosedDayToTextView(
    textView: TextView,
    closedDay: String?
) {
    if (closedDay == null) textView.text = "휴무일 없음"
    else textView.text = closedDay + " 휴무"
}

@BindingAdapter("isOpenImageView", "isOpenTextView")
fun bindClosedDayToTextView(
    toggleButton: ToggleButton,
    imageView: ImageView,
    textView: TextView
) {
    val isOpen = toggleButton.isChecked
    if (isOpen) {
        imageView.setImageResource(R.drawable.ic_day)
        textView.text = "지금 영업 중이에요!"
    } else {
        imageView.setImageResource(R.drawable.ic_night)
        textView.text = "지금은 문을 닫았어요"
    }
}