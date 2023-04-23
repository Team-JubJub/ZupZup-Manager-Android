package com.example.zupzup_manager.ui.management.recyclerview

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.zupzup_manager.databinding.ItemManagementMerchandiseInfoBinding
import com.example.zupzup_manager.databinding.ItemManagementStoreInfoBinding
import com.example.zupzup_manager.databinding.ItemMerchandiseModifyBinding
import com.example.zupzup_manager.ui.management.models.ManagementViewType

sealed class ManagementViewHolder(
    binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: ManagementViewType)

    class ManagementMerchandiseModifyViewHolder(
        private val binding: ItemMerchandiseModifyBinding
    ) : ManagementViewHolder(binding) {
        override fun bind(item: ManagementViewType) {
        }
    }

    class ManagementStoreInfoViewHolder(
        private val binding: ItemManagementStoreInfoBinding
    ) : ManagementViewHolder(binding) {
        override fun bind(item: ManagementViewType) {
            binding.name = (item as ManagementViewType.StoreInfoViewType).name
            binding.openTime = (item as ManagementViewType.StoreInfoViewType).openTime
            binding.event = (item as ManagementViewType.StoreInfoViewType).eventList
            binding.saleOpenTime = (item as ManagementViewType.StoreInfoViewType).saleOpenTime
            binding.saleCloseTime = (item as ManagementViewType.StoreInfoViewType).saleCloseTime
        }
    }

    class ManagementMerchandiseInfoViewHolder(
        private val binding: ItemManagementMerchandiseInfoBinding
    ) : ManagementViewHolder(binding) {
        override fun bind(item: ManagementViewType) {
            binding.itemName = (item as ManagementViewType.MerchandiseInfoViewType).itemName
            binding.price = (item as ManagementViewType.MerchandiseInfoViewType).price
            binding.discounted = (item as ManagementViewType.MerchandiseInfoViewType).discounted
            binding.imgUrl = (item as ManagementViewType.MerchandiseInfoViewType).imgUrl
            binding.stock = (item as ManagementViewType.MerchandiseInfoViewType).stock
        }
    }
}