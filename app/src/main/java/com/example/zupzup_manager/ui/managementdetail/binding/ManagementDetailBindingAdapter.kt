package com.example.zupzup_manager.ui.managementdetail.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zupzup_manager.R
import com.example.zupzup_manager.domain.models.MerchandiseModel
import com.example.zupzup_manager.ui.managementdetail.recyclerview.ManagementDetailRcvAdapter
import com.example.zupzup_manager.ui.reservationdetail.binding.ReservationDetailBindingHelper

@BindingAdapter("merchandiseDetailBody")
fun bindMerchandiseDetailToRecyclerView(
    recyclerView: RecyclerView,
    dataList: List<MerchandiseModel>
) {
    (recyclerView.adapter as ManagementDetailRcvAdapter).submitList(dataList.toList())
}

@BindingAdapter("bindingHelper", "itemId", "tvConfirmedAmount", "orderAmount")
fun bindBindingHelperToAmountButton(
    ivBtnModifyAmount: ImageView,
    bindingHelper: ManagementDetailBindingHelper,
    itemId: Long,
    tvConfirmedAmount: TextView,
    orderAmount: Int
) {
    ivBtnModifyAmount.setOnClickListener {
        when (ivBtnModifyAmount.id) {
            R.id.btn_plus -> {
                if (tvConfirmedAmount.text.toString().toInt() < orderAmount) {
                    bindingHelper.onPlusMerchandiseModifiedAmountBtnClick(itemId)
                    tvConfirmedAmount.text =
                        tvConfirmedAmount.text.toString().toInt().plus(1).toString()
                }
            }
            R.id.btn_minus -> {
                if (tvConfirmedAmount.text.toString().toInt() > 0) {
                    bindingHelper.onMinusMerchandiseModifiedAmountBtnClick(itemId)
                    tvConfirmedAmount.text =
                        tvConfirmedAmount.text.toString().toInt().minus(1).toString()

                }
            }
        }
    }
}