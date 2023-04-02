package com.example.zupzup_manager.ui.reservationdetail.binding

import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zupzup_manager.R
import com.example.zupzup_manager.domain.models.CartModel
import com.example.zupzup_manager.ui.common.ReservationStateMapper
import com.example.zupzup_manager.ui.common.toDecimalFormat
import com.example.zupzup_manager.ui.common.toDetailDateFormat
import com.example.zupzup_manager.ui.common.toTimeFormat
import com.example.zupzup_manager.ui.reservationdetail.recyclerview.ReservationDetailRcvAdapter
import com.example.zupzup_manager.ui.reservationdetail.models.ReservationDetailViewType


@BindingAdapter("reservationDetailBody")
fun bindReservationDetailListToRecyclerView(
    recyclerView: RecyclerView,
    dataList: List<ReservationDetailViewType>
) {
    (recyclerView.adapter as ReservationDetailRcvAdapter).submitList(dataList.toList())
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

@BindingAdapter("bindingHelper", "itemId", "tvConfirmedAmount", "orderAmount")
fun bindBindingHelperToAmountButton(
    ivBtnModifyAmount: ImageView,
    bindingHelper: ReservationDetailBindingHelper,
    itemId: Long,
    tvConfirmedAmount: TextView,
    orderAmount: Int
) {
    ivBtnModifyAmount.setOnClickListener {
        when (ivBtnModifyAmount.id) {
            R.id.btn_plus -> {
                if (tvConfirmedAmount.text.toString().toInt() < orderAmount) {
                    bindingHelper.onPlusCartItemConfirmedAmountBtnClick(itemId)
                    tvConfirmedAmount.text =
                        tvConfirmedAmount.text.toString().toInt().plus(1).toString()
                }
            }
            R.id.btn_minus -> {
                if (tvConfirmedAmount.text.toString().toInt() > 0) {
                    bindingHelper.onMinusCartItemConfirmedAmountBtnClick(itemId)
                    tvConfirmedAmount.text =
                        tvConfirmedAmount.text.toString().toInt().minus(1).toString()

                }
            }
        }
    }
}
