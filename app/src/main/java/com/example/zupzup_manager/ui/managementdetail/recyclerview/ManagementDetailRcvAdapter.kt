package com.example.zupzup_manager.ui.managementdetail.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zupzup_manager.databinding.ItemManagementMerchandiseModifyBinding
import com.example.zupzup_manager.domain.models.MerchandiseModel
import com.example.zupzup_manager.ui.managementdetail.ManagementDetailBtnClickListener

class ManagementDetailRcvAdapter(
    private val managementDetailBtnClickListener: ManagementDetailBtnClickListener
) :
    ListAdapter<MerchandiseModel, ManagementDetailRcvAdapter.ManagementDetailViewHolder>(
        MerchandiseModelDiffCallBack()
    ) {
    class ManagementDetailViewHolder(
        private val binding: ItemManagementMerchandiseModifyBinding,
        private val managementDetailBtnClickListener: ManagementDetailBtnClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MerchandiseModel) {
            with(binding) {
                merchandise = item
                clickListener = managementDetailBtnClickListener
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManagementDetailViewHolder {
        val binding =
            ItemManagementMerchandiseModifyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ManagementDetailViewHolder(binding, managementDetailBtnClickListener)
    }

    override fun onBindViewHolder(holder: ManagementDetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MerchandiseModelDiffCallBack : DiffUtil.ItemCallback<MerchandiseModel>() {
        override fun areItemsTheSame(
            oldItem: MerchandiseModel,
            newItem: MerchandiseModel
        ): Boolean {
            return oldItem.itemId == newItem.itemId
        }

        override fun areContentsTheSame(
            oldItem: MerchandiseModel,
            newItem: MerchandiseModel
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
}