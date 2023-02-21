package com.example.zupzup_manager.ui.reservationdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.zupzup_manager.databinding.ItemDescriptionHeaderBinding
import com.example.zupzup_manager.databinding.ItemReservationCartBinding
import com.example.zupzup_manager.databinding.ItemReservationCustomerInfoBinding
import com.example.zupzup_manager.ui.reservationdetail.models.ReservationDetail

class ReservationDetailRcvAdapter :
    ListAdapter<ReservationDetail, ReservationDetailViewHolder>(
        ReservationDetailDiffCallBack()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReservationDetailViewHolder {
        return when (viewType) {
            ViewType.HEADER.ordinal -> {
                ReservationDetailViewHolder.ReservationDetailHeaderDescriptionViewHolder(
                    ItemDescriptionHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ViewType.CUSTOMER_INFO.ordinal -> {
                ReservationDetailViewHolder.ReservationDetailCustomerInfoViewHolder(
                    ItemReservationCustomerInfoBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                ReservationDetailViewHolder.ReservationDetailCartItemViewHolder(
                    ItemReservationCartBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return ViewType.valueOf(currentList[position].viewType).ordinal
    }

    override fun onBindViewHolder(holder: ReservationDetailViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


    class ReservationDetailDiffCallBack : DiffUtil.ItemCallback<ReservationDetail>() {
        override fun areItemsTheSame(
            oldItem: ReservationDetail,
            newItem: ReservationDetail
        ): Boolean {
            return oldItem.viewType == newItem.viewType && oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(
            oldItem: ReservationDetail,
            newItem: ReservationDetail
        ): Boolean {
            return oldItem.viewType == newItem.viewType && oldItem.hashCode() == newItem.hashCode()
        }
    }
}