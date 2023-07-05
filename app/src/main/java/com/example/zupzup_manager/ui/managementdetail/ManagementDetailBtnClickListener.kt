package com.example.zupzup_manager.ui.managementdetail

import com.example.zupzup_manager.domain.models.MerchandiseModel

interface ManagementDetailBtnClickListener {

    fun createMerchandiseModifyDialog(merchandiseDetailBody: List<MerchandiseModel>)

    fun onPlusMerchandiseModifiedAmountBtnClick(itemId: Long)

    fun onMinusMerchandiseModifiedAmountBtnClick(itemId: Long)

    fun navigateToBackStack()

    fun navigateToMerchandiseAdd()

    fun navigateToMerchandiseModify(merchandise: MerchandiseModel)
}