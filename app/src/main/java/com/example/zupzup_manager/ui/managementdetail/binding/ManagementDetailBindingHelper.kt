package com.example.zupzup_manager.ui.managementdetail.binding

import javax.inject.Inject

class ManagementDetailBindingHelper @Inject constructor(
    private val plusCartItemConfirmedAmount: (itemId: Long) -> Unit,
    private val minusCartItemConfirmedAmount: (itemId: Long) -> Unit,
) {
    fun onPlusCartItemConfirmedAmountBtnClick(itemId: Long) {
        plusCartItemConfirmedAmount(itemId)
    }

    fun onMinusCartItemConfirmedAmountBtnClick(itemId: Long) {
        minusCartItemConfirmedAmount(itemId)
    }
}