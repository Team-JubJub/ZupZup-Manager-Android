package zupzup.manager.ui.item.clicklistener

import zupzup.manager.domain.models.item.ItemModel

interface ItemBtnClickListener {
    fun createBottomSheet()
    fun onPlusItemModifiedAmountBtnClick(itemId: Long)
    fun onMinusItemModifiedAmountBtnClick(itemId: Long)
    fun modifyItemQuantity(state: String)
    fun navigateToItemModify(merchandise: ItemModel)
}