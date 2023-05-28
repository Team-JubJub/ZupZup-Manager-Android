package com.example.zupzup_manager.ui.managementdetail.binding

import com.example.zupzup_manager.domain.models.MerchandiseModel
import com.example.zupzup_manager.domain.models.ReservationModel
import javax.inject.Inject

class ManagementDetailBindingHelper @Inject constructor(
    private val onCreateMerchandiseModifyDialogButtononClick: (merchandise: MerchandiseModel, isPartial: Boolean) -> Unit,
    private val plusMerchandiseModifiedAmount: (itemId: Long) -> Unit,
    private val minusMerchandiseModifiedAmount: (itemId: Long) -> Unit,
) {
    fun createMerchandiseModifyDialog(
    ) {

    }

    fun onPlusMerchandiseModifiedAmountBtnClick(itemId: Long) {
        plusMerchandiseModifiedAmount(itemId)
    }

    fun onMinusMerchandiseModifiedAmountBtnClick(itemId: Long) {
        minusMerchandiseModifiedAmount(itemId)
    }
}