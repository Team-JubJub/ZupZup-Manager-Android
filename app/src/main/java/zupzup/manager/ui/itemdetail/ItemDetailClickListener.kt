package zupzup.manager.ui.itemdetail

import zupzup.manager.domain.models.item.ItemAddModel
import zupzup.manager.domain.models.item.ItemModifyModel
import java.io.File

interface ItemDetailClickListener {
    fun onPlusItemModifiedAmountBtnClick(itemId: Long)
    fun onMinusItemModifiedAmountBtnClick(itemId: Long)
    fun navigateToBackStack()
    fun selectImage()
    fun addItem(item: ItemAddModel)
    fun modifyItem(updatedItem: ItemModifyModel, itemId: Long)
    fun deleteItem(itemId: Long)
}