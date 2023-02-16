package com.example.zupzup_manager.ui.reservationlist

import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zupzup_manager.domain.models.CartModel
import com.example.zupzup_manager.domain.models.ReservationModel
import com.example.zupzup_manager.ui.common.UiState

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
    textView : TextView,
    cartList : List<CartModel>
) {
    textView.text = "${cartList[0].name}"
    if(cartList.size > 1) {
        textView.text = textView.text.toString() + "외 ${cartList.size}개"
    }
}