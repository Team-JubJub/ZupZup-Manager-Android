package com.example.zupzup_manager.ui.merchandisedetail

interface MerchandiseDetailClickListener {
    fun onPlusMerchandiseModifiedAmountBtnClick(itemId: Long)
    fun onMinusMerchandiseModifiedAmountBtnClick(itemId: Long)
    fun navigateToBackStack()
    fun modifyMerchandise()
}