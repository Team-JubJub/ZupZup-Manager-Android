package com.example.zupzup_manager.ui.managementdetail.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zupzup_manager.domain.models.MerchandiseModel
import com.example.zupzup_manager.ui.managementdetail.recyclerview.ManagementDetailRcvAdapter

@BindingAdapter("merchandiseDetailBody")
fun bindMerchandiseDetailToRecyclerView(
    recyclerView: RecyclerView,
    dataList: List<MerchandiseModel>
) {
    (recyclerView.adapter as ManagementDetailRcvAdapter).submitList(dataList.toList())
}