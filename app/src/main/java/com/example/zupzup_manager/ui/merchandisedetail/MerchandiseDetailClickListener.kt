package com.example.zupzup_manager.ui.merchandisedetail

import com.example.zupzup_manager.domain.models.MerchandiseModel

interface MerchandiseDetailClickListener {
    fun onPlusMerchandiseModifiedAmountBtnClick(itemId: Long)
    fun onMinusMerchandiseModifiedAmountBtnClick(itemId: Long)
    fun navigateToBackStack()
    fun modifyMerchandise()
}