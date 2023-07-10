package com.example.zupzup_manager.ui.reservationlist.binding

import android.graphics.drawable.GradientDrawable
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zupzup_manager.domain.models.CartModel
import com.example.zupzup_manager.domain.models.ReservationModel
import com.example.zupzup_manager.ui.common.ReservationStateMapper
import com.example.zupzup_manager.ui.common.UiState
import com.example.zupzup_manager.ui.common.toSimpleDateFormat
import com.example.zupzup_manager.ui.common.toTimeFormat
import com.example.zupzup_manager.ui.reservationlist.recyclerview.ReservationListRcvAdapter

@BindingAdapter("uiState")
fun bindReservationListToRecyclerView(
    recyclerView: RecyclerView,
    uiState: UiState<List<ReservationModel>>
) {
    when (uiState) {
        is UiState.Success -> {
            (recyclerView.adapter as ReservationListRcvAdapter).submitList(uiState.data)
        }
        is UiState.Error -> {
            Toast.makeText(recyclerView.context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
        }
        else -> {
            Toast.makeText(recyclerView.context, "로딩중", Toast.LENGTH_SHORT).show()
        }
    }
}

@BindingAdapter("cartList")
fun bindReservationListToRecyclerView(
    textView: TextView,
    cartList: List<CartModel>
) {
    if (cartList.isNotEmpty()) {
        textView.text = "${cartList[0].name}"
        if (cartList.size > 1) {
            textView.text = textView.text.toString() + "외 ${cartList.size - 1}개"
        }
    }
}

@BindingAdapter("state")
fun bindReservationStateToTextView(
    constraintLayout: ConstraintLayout,
    state: String
) {
    (constraintLayout.background as GradientDrawable).setColor(
        ContextCompat.getColor(
            constraintLayout.context,
            ReservationStateMapper.getStateBackgroundColor(state)
        )
    )
}

@BindingAdapter("reserveId")
fun bindReservationDateToTextView(
    textView: TextView,
    reserveId: Long
) {
    textView.text = reserveId.toSimpleDateFormat()
}

@BindingAdapter("visitTime")
fun bindReservationVisitTimeToTextView(
    textView: TextView,
    visitTime: Int
) {
    textView.text = visitTime.toTimeFormat()
}