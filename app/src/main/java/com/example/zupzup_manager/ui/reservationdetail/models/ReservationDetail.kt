package com.example.zupzup_manager.ui.reservationdetail.models

import com.example.zupzup_manager.domain.models.CartModel
import com.example.zupzup_manager.domain.models.CustomerModel

sealed class ReservationDetail {
    abstract val viewType: String

    data class HeaderDescription(
        override val viewType: String = "HEADER",
        val header: String
    ) : ReservationDetail()

    data class ReservationCustomerInfo(
        override val viewType: String = "CUSTOMER_INFO",
        val customer: CustomerModel,
        val visitTime: Int
    ) : ReservationDetail()

    data class ReservationCartItem(
        override val viewType: String = "CART_ITEM",
        val reservationState : String,
        val cartItem: CartModel
    ) : ReservationDetail()
}
