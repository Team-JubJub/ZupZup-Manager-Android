package zupzup.manager.domain.repository

import zupzup.manager.domain.models.item.ItemModel
import zupzup.manager.domain.models.item.ItemQuantityModel
import zupzup.manager.domain.models.item.ItemModifyModel
import okhttp3.MultipartBody
import zupzup.manager.domain.models.item.ItemAddModel

interface ItemRepository {
    suspend fun getItemList(storeId: Long): Result<List<ItemModel>>
    suspend fun addItem(storeId: Long, item: ItemAddModel, image: MultipartBody.Part?): Result<String>
    suspend fun modifyItemQuantity(storeId: Long, quantity: List<ItemQuantityModel>): Result<String>
    suspend fun modifyItem(itemId: Long, storeId: Long, item: ItemModifyModel, image: MultipartBody.Part?): Result<String>
    suspend fun deleteItem(storeId: Long, itemId: Long): Result<String>
}