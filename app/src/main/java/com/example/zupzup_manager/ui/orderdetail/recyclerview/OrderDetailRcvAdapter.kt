package com.example.zupzup_manager.ui.orderdetail.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.zupzup_manager.databinding.ItemDescriptionHeaderBinding
import com.example.zupzup_manager.databinding.ItemOrderCartBinding
import com.example.zupzup_manager.databinding.ItemOrderCustomerInfoBinding
import com.example.zupzup_manager.ui.common.ViewType
import com.example.zupzup_manager.ui.orderdetail.binding.OrderDetailBindingHelper
import com.example.zupzup_manager.ui.orderdetail.models.OrderDetailViewType

class OrderDetailRcvAdapter(
    private val orderDetailBindingHelper: OrderDetailBindingHelper
) :
    ListAdapter<OrderDetailViewType, OrderDetailViewHolder>(
        OrderDetailDiffCallBack()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderDetailViewHolder {
        return when (viewType) {
            ViewType.HEADER.ordinal -> {
                val binding = ItemDescriptionHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                OrderDetailViewHolder.OrderDetailHeaderDescriptionViewHolder(binding)
            }
            ViewType.CUSTOMER_INFO.ordinal -> {
                val binding = ItemOrderCustomerInfoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                OrderDetailViewHolder.OrderDetailCustomerInfoViewHolder(binding)
            }
            else -> {
                val binding = ItemOrderCartBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                OrderDetailViewHolder.OrderDetailOrderItemViewHolder(
                    binding,
                    orderDetailBindingHelper
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType
    }

    override fun onBindViewHolder(holder: OrderDetailViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


    class OrderDetailDiffCallBack : DiffUtil.ItemCallback<OrderDetailViewType>() {
        override fun areItemsTheSame(
            oldItem: OrderDetailViewType,
            newItem: OrderDetailViewType
        ): Boolean {
            return if (oldItem.viewType == ViewType.ORDER_ITEM.ordinal && newItem.viewType == ViewType.ORDER_ITEM.ordinal) {
                (oldItem as OrderDetailViewType.OrderItemViewType).orderItem.itemId == (newItem as OrderDetailViewType.OrderItemViewType).orderItem.itemId
            } else {
                oldItem.viewType == newItem.viewType
            }
        }

        override fun areContentsTheSame(
            oldItem: OrderDetailViewType,
            newItem: OrderDetailViewType
        ): Boolean {
            return oldItem.viewType == newItem.viewType && oldItem.hashCode() == newItem.hashCode()
        }
    }
}