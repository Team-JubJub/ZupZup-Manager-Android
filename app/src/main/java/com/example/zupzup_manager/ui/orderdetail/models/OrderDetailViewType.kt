package com.example.zupzup_manager.ui.orderdetail.models

import com.example.zupzup_manager.domain.models.order.CustomerModel
import com.example.zupzup_manager.domain.models.order.OrderSpecificModel
import com.example.zupzup_manager.ui.common.ViewType

sealed class OrderDetailViewType {
    abstract val viewType: Int

    data class HeaderDescription(
        override val viewType: Int = ViewType.HEADER.ordinal,
        val header: String
    ) : OrderDetailViewType()

    data class OrderCustomerInfoViewType(
        override val viewType: Int = ViewType.CUSTOMER_INFO.ordinal,
        val customer: CustomerModel,
        val visitTime: String
    ) : OrderDetailViewType()

    data class OrderItemViewType(
        override val viewType: Int = ViewType.ORDER_ITEM.ordinal,
        val orderStatus: String,
        val orderItem: OrderSpecificModel
    ) : OrderDetailViewType() {
        private var confirmedAmount = orderItem.itemCount
        fun plusConfirmedAmount() {
            confirmedAmount = confirmedAmount.plus(1)
        }

        fun minusConfirmedAmount() {
            confirmedAmount = confirmedAmount.minus(1)
        }

        fun getConfirmedAmount(): Int = confirmedAmount
    }
}
