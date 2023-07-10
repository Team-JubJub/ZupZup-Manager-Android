package com.example.zupzup_manager.ui.management.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zupzup_manager.databinding.ItemManagementMerchandiseInfoBinding
import com.example.zupzup_manager.databinding.ItemManagementStoreInfoBinding
import com.example.zupzup_manager.databinding.ItemMerchandiseModifyBinding
import com.example.zupzup_manager.domain.models.MerchandiseModel
import com.example.zupzup_manager.domain.models.ReservationModel
import com.example.zupzup_manager.domain.models.StoreModel
import com.example.zupzup_manager.ui.common.ViewType
import com.example.zupzup_manager.ui.management.models.ManagementViewType

class ManagementRcvAdapter(
    private val navigateToManagementDetail: (StoreModel) -> Unit
) : ListAdapter<MerchandiseModel, ManagementRcvAdapter.ManagementDetailViewHolder>(
    MerchandiseModelDiffCallBack()
) {
    class ManagementDetailViewHolder(
        private val binding: ItemManagementMerchandiseInfoBinding
        //private val managementDetailBtnClickListener: ManagementDetailBtnClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MerchandiseModel) {
            with(binding) {
                itemName = item.itemName
                discounted = item.discounted
                imgUrl = item.imgUrl
                stock = item.stock
//                clickListener = managementDetailBtnClickListener
//                executePendingBindings()
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ManagementDetailViewHolder {
        val binding =
            ItemManagementMerchandiseInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ManagementDetailViewHolder(binding)
//                ManagementViewHolder.ManagementMerchandiseModifyViewHolder(binding, navigateToManagementDetail)
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