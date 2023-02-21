package com.example.zupzup_manager.ui.reservationlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zupzup_manager.databinding.ItemReservationCardBinding
import com.example.zupzup_manager.domain.models.ReservationModel

class ReservationListRcvAdapter(
    private val navigateToReservationDetail: (reservation: ReservationModel) -> Unit
) :
    ListAdapter<ReservationModel, ReservationListRcvAdapter.ReservationListViewHolder>(
        ReservationModelDiffCallBack()
    ) {

    class ReservationListViewHolder(
        private val binding: ItemReservationCardBinding,
        private val navigateToReservationDetail: (reservation: ReservationModel) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReservationModel) {
            with(binding) {
                reservation = item
                navigate = navigateToReservationDetail
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationListViewHolder {
        val binding =
            ItemReservationCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationListViewHolder(binding, navigateToReservationDetail)
    }

    override fun onBindViewHolder(holder: ReservationListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ReservationModelDiffCallBack : DiffUtil.ItemCallback<ReservationModel>() {
        override fun areItemsTheSame(
            oldItem: ReservationModel,
            newItem: ReservationModel
        ): Boolean {
            return oldItem.reserveId == newItem.reserveId
        }

        override fun areContentsTheSame(
            oldItem: ReservationModel,
            newItem: ReservationModel
        ): Boolean {
            return oldItem.reserveId == newItem.reserveId
        }
    }

}