package com.example.zupzup_manager.ui.orderdetail.recyclerview

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.zupzup_manager.databinding.ItemDescriptionHeaderBinding
import com.example.zupzup_manager.databinding.ItemOrderCartBinding
import com.example.zupzup_manager.databinding.ItemOrderCustomerInfoBinding
import com.example.zupzup_manager.ui.orderdetail.binding.OrderDetailBindingHelper
import com.example.zupzup_manager.ui.orderdetail.models.OrderDetailViewType

sealed class OrderDetailViewHolder(
    binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: OrderDetailViewType)

    class OrderDetailHeaderDescriptionViewHolder(
        private val binding: ItemDescriptionHeaderBinding
    ) : OrderDetailViewHolder(binding) {
        override fun bind(item: OrderDetailViewType) {
            binding.description = (item as OrderDetailViewType.HeaderDescription).header
        }
    }

    class OrderDetailCustomerInfoViewHolder(
        private val binding: ItemOrderCustomerInfoBinding
    ) : OrderDetailViewHolder(binding) {
        override fun bind(item: OrderDetailViewType) {
            binding.orderInfo =
                item as OrderDetailViewType.OrderCustomerInfoViewType
        }
    }

    class OrderDetailOrderItemViewHolder(
        private val binding: ItemOrderCartBinding,
        private val orderDetailBindingHelper: OrderDetailBindingHelper
    ) : OrderDetailViewHolder(binding) {

        override fun bind(item: OrderDetailViewType) {
            with(binding) {
                order = item as OrderDetailViewType.OrderItemViewType
                orderStatus = item.orderStatus
                bindingHelper = orderDetailBindingHelper
            }
        }
    }
}