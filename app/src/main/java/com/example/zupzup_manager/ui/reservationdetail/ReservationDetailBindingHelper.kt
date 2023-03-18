package com.example.zupzup_manager.ui.reservationdetail

import com.example.zupzup_manager.domain.models.CartModel
import com.example.zupzup_manager.domain.models.ReservationModel
import com.example.zupzup_manager.ui.common.ViewType
import com.example.zupzup_manager.ui.reservationdetail.models.ReservationDetailHeaderModel
import com.example.zupzup_manager.ui.reservationdetail.models.ReservationDetailViewType

class ReservationDetailBindingHelper(
    private val onCreateReservationConfirmBottomSheetButtonClick: (reservation: ReservationModel) -> Unit,
    private val plusCartItemConfirmedAmount: (itemId: Long) -> Unit,
    private val minusCartItemConfirmedAmount: (itemId: Long) -> Unit
) {
    fun createReservationConfirmBottomSheet(
        reservationDetailHeader: ReservationDetailHeaderModel,
        reservationDetailBody: List<ReservationDetailViewType>
    ) {
        val customerInfo =
            reservationDetailBody.find { it.viewType == ViewType.CUSTOMER_INFO.ordinal } as ReservationDetailViewType.ReservationCustomerInfoViewType
        val cartList = reservationDetailBody.filter { it.viewType == ViewType.CART_ITEM.ordinal }
            .map { it as ReservationDetailViewType.ReservationCartItemViewType }
        val confirmedReservationModel = ReservationModel(
            storeId = reservationDetailHeader.storeId,
            reserveId = reservationDetailHeader.reserveId,
            state = reservationDetailHeader.state,
            visitTime = customerInfo.visitTime,
            customer = customerInfo.customer,
            cartList = cartList.map {
                CartModel(
                    itemId = it.cartItem.itemId,
                    storeId = reservationDetailHeader.storeId,
                    name = it.cartItem.name,
                    salesPrice = it.cartItem.salesPrice,
                    amount = it.getConfirmedAmount()
                )
            }
        )
        onCreateReservationConfirmBottomSheetButtonClick(
            confirmedReservationModel
        )
    }

    fun onPlusCartItemConfirmedAmountBtnClick(itemId: Long) {
        plusCartItemConfirmedAmount(itemId)
    }

    fun onMinusCartItemConfirmedAmountBtnClick(itemId: Long) {
        minusCartItemConfirmedAmount(itemId)
    }
}