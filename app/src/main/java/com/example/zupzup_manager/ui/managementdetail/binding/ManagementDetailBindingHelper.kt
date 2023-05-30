package com.example.zupzup_manager.ui.managementdetail.binding

import com.example.zupzup_manager.domain.models.MerchandiseModel
import com.example.zupzup_manager.domain.models.ReservationModel
import com.example.zupzup_manager.domain.models.StoreModel
import javax.inject.Inject

class ManagementDetailBindingHelper @Inject constructor(
    private val onCreateMerchandiseModifyDialogButtononClick: (merchandiseList: List<MerchandiseModel>) -> Unit,
    private val plusMerchandiseModifiedAmount: (itemId: Long) -> Unit,
    private val minusMerchandiseModifiedAmount: (itemId: Long) -> Unit,
    private val navigateBack: () -> Unit,
    private val navigateMerchandiseAdd: () -> Unit,
    private val navigateMerchandiseModify: (merchandise: MerchandiseModel) -> Unit
) {
    fun createMerchandiseModifyDialog(merchandiseDetailBody: List<MerchandiseModel>) {
        onCreateMerchandiseModifyDialogButtononClick(merchandiseDetailBody)
    }

    fun onPlusMerchandiseModifiedAmountBtnClick(itemId: Long) {
        plusMerchandiseModifiedAmount(itemId)
    }

    fun onMinusMerchandiseModifiedAmountBtnClick(itemId: Long) {
        minusMerchandiseModifiedAmount(itemId)
    }

    fun navigateToBackStack() {
        navigateBack()
    }

    fun navigateToMerchandiseAdd() {
        navigateMerchandiseAdd()
    }

    fun navigateToMerchandiseModify(merchandise: MerchandiseModel) {
        navigateMerchandiseModify(merchandise)
    }
}