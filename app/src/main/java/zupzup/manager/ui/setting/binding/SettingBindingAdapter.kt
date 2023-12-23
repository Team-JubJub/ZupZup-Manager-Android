package zupzup.manager.ui.setting.binding

import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.databinding.BindingAdapter
import zupzup.manager.R

@BindingAdapter("closedDay")
fun bindClosedDayToTextView(
    textView: TextView,
    closedDay: String?
) {
    if (closedDay == null) textView.text = "휴무일 없음"
    else textView.text = closedDay + " 휴무"
}