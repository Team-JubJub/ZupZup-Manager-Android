package com.example.zupzup_manager.ui.managementdetail.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zupzup_manager.R
import com.example.zupzup_manager.domain.models.MerchandiseModel
import com.example.zupzup_manager.ui.managementdetail.ManagementDetailBtnClickListener
import com.example.zupzup_manager.ui.managementdetail.recyclerview.ManagementDetailRcvAdapter

@BindingAdapter("merchandiseDetailBody")
fun bindMerchandiseDetailToRecyclerView(
    recyclerView: RecyclerView,
    dataList: List<MerchandiseModel>
) {
    (recyclerView.adapter as ManagementDetailRcvAdapter).submitList(dataList.toList())
}

@BindingAdapter("clickListener", "itemId", "tvConfirmedAmount")
fun bindBindingHelperToAmountButton(
    ivBtnModifyAmount: ImageView,
    clickListener: ManagementDetailBtnClickListener,
    itemId: Long,
    tvConfirmedAmount: TextView
) {
    ivBtnModifyAmount.setOnClickListener {
        when (ivBtnModifyAmount.id) {
            R.id.btn_plus -> {
                if (tvConfirmedAmount.text.toString().toInt() < 100) {
                    clickListener.onPlusMerchandiseModifiedAmountBtnClick(itemId)
                    tvConfirmedAmount.text =
                        tvConfirmedAmount.text.toString().toInt().plus(1).toString()
                }
            }
            R.id.btn_minus -> {
                if (tvConfirmedAmount.text.toString().toInt() > 0) {
                    clickListener.onMinusMerchandiseModifiedAmountBtnClick(itemId)
                    tvConfirmedAmount.text =
                        tvConfirmedAmount.text.toString().toInt().minus(1).toString()

                }
            }
        }
    }
}