package com.example.zupzup_manager.ui.reservationdetail.recyclerview

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.zupzup_manager.databinding.ItemDescriptionHeaderBinding
import com.example.zupzup_manager.databinding.ItemReservationCartBinding
import com.example.zupzup_manager.databinding.ItemReservationCustomerInfoBinding
import com.example.zupzup_manager.ui.reservationdetail.binding.ReservationDetailBindingHelper
import com.example.zupzup_manager.ui.reservationdetail.models.ReservationDetailViewType

sealed class ReservationDetailViewHolder(
    binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: ReservationDetailViewType)

    class ReservationDetailHeaderDescriptionViewHolder(
        private val binding: ItemDescriptionHeaderBinding
    ) : ReservationDetailViewHolder(binding) {
        override fun bind(item: ReservationDetailViewType) {
            binding.description = (item as ReservationDetailViewType.HeaderDescription).header
        }
    }

    class ReservationDetailCustomerInfoViewHolder(
        private val binding: ItemReservationCustomerInfoBinding
    ) : ReservationDetailViewHolder(binding) {
        override fun bind(item: ReservationDetailViewType) {
            binding.reservationInfo =
                item as ReservationDetailViewType.ReservationCustomerInfoViewType
        }
    }

    class ReservationDetailCartItemViewHolder(
        private val binding: ItemReservationCartBinding,
        private val reservationDetailBindingHelper: ReservationDetailBindingHelper
    ) : ReservationDetailViewHolder(binding) {

        override fun bind(item: ReservationDetailViewType) {
            with(binding) {
                cart = item as ReservationDetailViewType.ReservationCartItemViewType
                reservationState = item.reservationState
                bindingHelper = reservationDetailBindingHelper
            }
        }
    }
}