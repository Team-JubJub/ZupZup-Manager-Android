package com.example.zupzup_manager.ui.reservationdetail.models

import com.example.zupzup_manager.domain.models.CartModel
import com.example.zupzup_manager.domain.models.CustomerModel
import com.example.zupzup_manager.ui.common.ViewType

sealed class ReservationDetailViewType {
    abstract val viewType: Int

    data class HeaderDescription(
        override val viewType: Int = ViewType.HEADER.ordinal,
        val header: String
    ) : ReservationDetailViewType()

    data class ReservationCustomerInfoViewType(
        override val viewType: Int = ViewType.CUSTOMER_INFO.ordinal,
        val customer: CustomerModel,
        val visitTime: Int
    ) : ReservationDetailViewType()

    data class ReservationCartItemViewType(
        override val viewType: Int = ViewType.CART_ITEM.ordinal,
        val reservationState: String,
        val cartItem: CartModel
    ) : ReservationDetailViewType() {
        private var confirmedAmount = cartItem.amount
        fun plusConfirmedAmount() {
            confirmedAmount = confirmedAmount.plus(1)
        }

        fun minusConfirmedAmount() {
            confirmedAmount = confirmedAmount.minus(1)
        }

        fun getConfirmedAmount(): Int = confirmedAmount
    }
}
