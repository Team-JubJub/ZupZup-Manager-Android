package com.example.zupzup_manager.ui.management.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zupzup_manager.databinding.ItemManagementMerchandiseInfoBinding
import com.example.zupzup_manager.domain.models.MerchandiseModel
import com.example.zupzup_manager.ui.management.clicklistener.ManagementBtnClickListener
import com.example.zupzup_manager.ui.management.ManagementViewModel

class ManagementRcvAdapter(
    private val managementBtnClickListener: ManagementBtnClickListener,
    private val managementViewModel: ManagementViewModel
) : ListAdapter<MerchandiseModel, ManagementRcvAdapter.ManagementViewHolder>(
    MerchandiseModelDiffCallBack()
) {
    class ManagementViewHolder(
        private val binding: ItemManagementMerchandiseInfoBinding,
        private val managementBtnClickListener: ManagementBtnClickListener,
        private val managementViewModel: ManagementViewModel
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MerchandiseModel) {
            with(binding) {
                merchandise = item
                viewModel = managementViewModel
                clickListener = managementBtnClickListener
                executePendingBindings()
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ManagementViewHolder {
        val binding =
            ItemManagementMerchandiseInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ManagementViewHolder(binding, managementBtnClickListener, managementViewModel)
    }

    override fun onBindViewHolder(holder: ManagementViewHolder, position: Int) {
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