package com.example.zupzup_manager.ui.reservationdetail

import android.graphics.drawable.GradientDrawable
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zupzup_manager.domain.models.CartModel
import com.example.zupzup_manager.ui.common.ReservationStateMapper
import com.example.zupzup_manager.ui.common.toDecimalFormat
import com.example.zupzup_manager.ui.common.toDetailDateFormat
import com.example.zupzup_manager.ui.common.toTimeFormat
import com.example.zupzup_manager.ui.reservationdetail.models.ReservationDetail


@BindingAdapter("reservationDetailBody")
fun bindReservationDetailListToRecyclerView(
    recyclerView: RecyclerView,
    dataList: List<ReservationDetail>
) {
    (recyclerView.adapter as ReservationDetailRcvAdapter).submitList(dataList)
}

@BindingAdapter("state")
fun bindReservationStateToTextView(
    textView: TextView,
    state: String
) {
    (textView.background as GradientDrawable).setColor(
        ContextCompat.getColor(
            textView.context,
            ReservationStateMapper.getStateBackgroundColor(state)
        )
    )

    textView.text = ReservationStateMapper.getStateDescription(state)
}

@BindingAdapter("detailReserveId")
fun bindReservationDateToTextView(
    textView: TextView,
    reserveId: Long
) {
    textView.text = reserveId.toDetailDateFormat()
}

@BindingAdapter("visitTime")
fun bindReservationVisitTimeToTextView(
    textView: TextView,
    visitTime: Int
) {
    textView.text = visitTime.toTimeFormat()
}

@BindingAdapter("totalPrice")
fun bindReservationTotalPriceToTextView(
    textView: TextView,
    cartList: List<CartModel>?
) {
    if (cartList != null) {
        textView.text = cartList.sumOf { it.salesPrice * it.amount }.toDecimalFormat()
    }
}