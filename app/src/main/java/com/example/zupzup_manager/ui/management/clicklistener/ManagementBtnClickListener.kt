package com.example.zupzup_manager.ui.management.clicklistener

import com.example.zupzup_manager.domain.models.MerchandiseModel

interface ManagementBtnClickListener {
    fun createBottomSheet()
    fun onPlusMerchandiseModifiedAmountBtnClick(itemId: Long)
    fun onMinusMerchandiseModifiedAmountBtnClick(itemId: Long)
    fun modifyMerchandiseList(state: String)
    fun navigateToMerchandiseModify(merchandise: MerchandiseModel)
}