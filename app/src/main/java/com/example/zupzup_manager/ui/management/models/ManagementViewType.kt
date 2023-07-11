package com.example.zupzup_manager.ui.management.models

import com.example.zupzup_manager.domain.models.StoreModel
import com.example.zupzup_manager.ui.common.ViewType

sealed class ManagementViewType {
    abstract val viewType: Int
    data class StoreInfoViewType(
        override val viewType: Int = ViewType.STORE_INFO.ordinal,
        val name: String,
        val openTime: String,
        val eventList: String,
        val saleOpenTime: String,
        val saleCloseTime: String
    ) : ManagementViewType()

    data class MerchandiseInfoViewType(
        override val viewType: Int = ViewType.MERCHANDISE_INFO.ordinal,
        val itemId: Long,
        val itemName: String,
        val price: Int,
        val discounted: Int,
        val imgUrl: String,
        val stock: Int,
    ) : ManagementViewType()
}
