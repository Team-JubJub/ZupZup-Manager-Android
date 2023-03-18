package com.example.zupzup_manager.ui.reservationdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.zupzup_manager.databinding.ItemDescriptionHeaderBinding
import com.example.zupzup_manager.databinding.ItemReservationCartBinding
import com.example.zupzup_manager.databinding.ItemReservationCustomerInfoBinding
import com.example.zupzup_manager.ui.common.ViewType
import com.example.zupzup_manager.ui.reservationdetail.models.ReservationDetailViewType

class ReservationDetailRcvAdapter(
    private val reservationDetailBindingHelper: ReservationDetailBindingHelper
) :
    ListAdapter<ReservationDetailViewType, ReservationDetailViewHolder>(
        ReservationDetailDiffCallBack()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReservationDetailViewHolder {
        return when (viewType) {
            ViewType.HEADER.ordinal -> {
                val binding = ItemDescriptionHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ReservationDetailViewHolder.ReservationDetailHeaderDescriptionViewHolder(binding)
            }
            ViewType.CUSTOMER_INFO.ordinal -> {
                val binding = ItemReservationCustomerInfoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ReservationDetailViewHolder.ReservationDetailCustomerInfoViewHolder(binding)
            }
            else -> {
                val binding = ItemReservationCartBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ReservationDetailViewHolder.ReservationDetailCartItemViewHolder(
                    binding,
                    reservationDetailBindingHelper
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType
    }

    override fun onBindViewHolder(holder: ReservationDetailViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


    class ReservationDetailDiffCallBack : DiffUtil.ItemCallback<ReservationDetailViewType>() {
        override fun areItemsTheSame(
            oldItem: ReservationDetailViewType,
            newItem: ReservationDetailViewType
        ): Boolean {
            return if (oldItem.viewType == ViewType.CART_ITEM.ordinal && newItem.viewType == ViewType.CART_ITEM.ordinal) {
                (oldItem as ReservationDetailViewType.ReservationCartItemViewType).cartItem.itemId == (newItem as ReservationDetailViewType.ReservationCartItemViewType).cartItem.itemId
            } else {
                oldItem.viewType == newItem.viewType
            }
        }

        override fun areContentsTheSame(
            oldItem: ReservationDetailViewType,
            newItem: ReservationDetailViewType
        ): Boolean {
            return oldItem.viewType == newItem.viewType && oldItem.hashCode() == newItem.hashCode()
        }
    }
}