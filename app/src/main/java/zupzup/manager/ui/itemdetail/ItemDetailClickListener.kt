package zupzup.manager.ui.itemdetail

import zupzup.manager.domain.models.item.ItemAddModel
import java.io.File

interface ItemDetailClickListener {
    fun onPlusItemModifiedAmountBtnClick(itemId: Long)
    fun onMinusItemModifiedAmountBtnClick(itemId: Long)
    fun navigateToBackStack()
    fun addItem(item: ItemAddModel, image: File?)
    fun modifyItem()
    fun deleteItem(itemId: Long)
}