package com.example.zupzup_manager.ui.reservationdetail

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.zupzup_manager.databinding.ItemDescriptionHeaderBinding
import com.example.zupzup_manager.databinding.ItemReservationCartBinding
import com.example.zupzup_manager.databinding.ItemReservationCustomerInfoBinding
import com.example.zupzup_manager.ui.reservationdetail.models.ReservationDetail

sealed class ReservationDetailViewHolder(
    binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: ReservationDetail)

    class ReservationDetailHeaderDescriptionViewHolder(
        private val binding: ItemDescriptionHeaderBinding
    ) : ReservationDetailViewHolder(binding) {
        override fun bind(item: ReservationDetail) {
            binding.description = (item as ReservationDetail.HeaderDescription).header
        }
    }

    class ReservationDetailCustomerInfoViewHolder(
        private val binding: ItemReservationCustomerInfoBinding
    ) : ReservationDetailViewHolder(binding) {
        override fun bind(item: ReservationDetail) {
            binding.reservationInfo = item as ReservationDetail.ReservationCustomerInfo
        }
    }

    class ReservationDetailCartItemViewHolder(
        private val binding: ItemReservationCartBinding
    ) : ReservationDetailViewHolder(binding) {
        override fun bind(item: ReservationDetail) {
            binding.cartItem = (item as ReservationDetail.ReservationCartItem).cartItem
            binding.reservationState = item.reservationState
        }
    }
}