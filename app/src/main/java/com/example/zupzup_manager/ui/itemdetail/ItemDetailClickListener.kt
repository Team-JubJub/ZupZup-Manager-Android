package com.example.zupzup_manager.ui.itemdetail

interface ItemDetailClickListener {
    fun onPlusItemModifiedAmountBtnClick(itemId: Long)
    fun onMinusItemModifiedAmountBtnClick(itemId: Long)
    fun navigateToBackStack()
    fun modifyItem()
}