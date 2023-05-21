package com.example.zupzup_manager.ui.management.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.zupzup_manager.databinding.ItemManagementMerchandiseInfoBinding
import com.example.zupzup_manager.databinding.ItemManagementStoreInfoBinding
import com.example.zupzup_manager.databinding.ItemMerchandiseModifyBinding
import com.example.zupzup_manager.domain.models.ReservationModel
import com.example.zupzup_manager.domain.models.StoreModel
import com.example.zupzup_manager.ui.common.ViewType
import com.example.zupzup_manager.ui.management.models.ManagementViewType

class ManagementRcvAdapter(
    private val navigateToManagementDetail: (StoreModel) -> Unit
) : ListAdapter<ManagementViewType, ManagementViewHolder>(
    ManagementDiffCallBack()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ManagementViewHolder {
        return when (viewType) {
            ViewType.STORE_INFO.ordinal -> {
                val binding = ItemManagementStoreInfoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ManagementViewHolder.ManagementStoreInfoViewHolder(binding)
            }
            ViewType.MERCHANDISE_INFO.ordinal -> {
                val binding = ItemManagementMerchandiseInfoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ManagementViewHolder.ManagementMerchandiseInfoViewHolder(binding)
            }
            else -> {
                val binding = ItemMerchandiseModifyBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ManagementViewHolder.ManagementMerchandiseModifyViewHolder(binding, navigateToManagementDetail)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType
    }

    override fun onBindViewHolder(holder: ManagementViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class ManagementDiffCallBack : DiffUtil.ItemCallback<ManagementViewType>() {
        override fun areItemsTheSame(
            oldItem: ManagementViewType,
            newItem: ManagementViewType
        ): Boolean {
            return if (oldItem.viewType == ViewType.MERCHANDISE_INFO.ordinal && newItem.viewType == ViewType.MERCHANDISE_INFO.ordinal) {
                (oldItem as ManagementViewType.MerchandiseInfoViewType).itemId == (newItem as ManagementViewType.MerchandiseInfoViewType).itemId
            } else {
                oldItem.viewType == newItem.viewType
            }
        }

        override fun areContentsTheSame(
            oldItem: ManagementViewType,
            newItem: ManagementViewType
        ): Boolean {
            return oldItem.viewType == newItem.viewType && oldItem.hashCode() == newItem.hashCode()
        }
    }
}