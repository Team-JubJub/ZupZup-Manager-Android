package com.example.zupzup_manager.ui.item.clicklistener

import com.example.zupzup_manager.domain.models.item.ItemModel

interface ItemBtnClickListener {
    fun createBottomSheet()
    fun onPlusItemModifiedAmountBtnClick(itemId: Long)
    fun onMinusItemModifiedAmountBtnClick(itemId: Long)
    fun modifyItemQuantity(state: String)
    fun navigateToItemModify(merchandise: ItemModel)
}