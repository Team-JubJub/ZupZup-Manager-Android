package com.example.zupzup_manager.ui.management.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zupzup_manager.databinding.ItemManagementMerchandiseInfoBinding
import com.example.zupzup_manager.domain.models.MerchandiseModel
import com.example.zupzup_manager.ui.management.clicklistener.ManagementBtnClickListener
import com.example.zupzup_manager.ui.management.ManagementViewModel

class ManagementRcvAdapter(
    private val managementBtnClickListener: ManagementBtnClickListener,
    private val managementViewModel: ManagementViewModel,
    private val fragmentLifecycleOwner: LifecycleOwner
) : ListAdapter<MerchandiseModel, ManagementRcvAdapter.ManagementViewHolder>(
    MerchandiseModelDiffCallBack()
) {
    class ManagementViewHolder(
        private val binding: ItemManagementMerchandiseInfoBinding,
        private val managementBtnClickListener: ManagementBtnClickListener,
        private val managementViewModel: ManagementViewModel,
        private val fragmentLifecycleOwner: LifecycleOwner
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MerchandiseModel) {
            with(binding) {
                merchandise = item
                clickListener = managementBtnClickListener
                lifecycleOwner = fragmentLifecycleOwner
                viewModel = managementViewModel
                executePendingBindings()
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ManagementViewHolder {
        val binding =
            ItemManagementMerchandiseInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ManagementViewHolder(binding, managementBtnClickListener, managementViewModel, fragmentLifecycleOwner)
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