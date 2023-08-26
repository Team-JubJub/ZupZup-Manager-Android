package zupzup.manager.data.datasource.item

import zupzup.manager.data.dto.item.parameter.ItemPatchDto
import zupzup.manager.data.dto.item.parameter.ItemModifyRequest
import zupzup.manager.data.dto.item.response.ItemDto
import zupzup.manager.data.service.ItemService
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import zupzup.manager.data.dto.item.parameter.ItemAddRequest
import javax.inject.Inject

class ItemDataSourceImpl @Inject constructor(
    private val itemService: ItemService
): ItemDataSource {

    override suspend fun getItemList(storeId: Long): Response<List<ItemDto>> {
        return itemService.getItemList(
            storeId = storeId
        )
    }

    override suspend fun addItem(
        storeId: Long,
        item: ItemAddRequest,
        image: MultipartBody.Part?
    ): Response<String> {
        return itemService.addItem(
            storeId = storeId,
            item = item,
            image = image
        )
    }

    override suspend fun modifyItemQuantity(
        storeId: Long,
        quantity: List<ItemPatchDto>
    ): Response<String> {

        val quantityJson = Gson().toJson(quantity)
        val quantityRequestBody = quantityJson.toRequestBody("application/json; charset=utf-8".toMediaType())

        return itemService.modifyItemQuantity(
            storeId = storeId,
            quantity = quantityRequestBody
        )
    }

    override suspend fun modifyItem(
        itemId: Long,
        storeId: Long,
        item: ItemModifyRequest,
        image: MultipartBody.Part?
    ): Response<String> {
        return itemService.modifyItem(
            itemId = itemId,
            storeId = storeId,
            item = item,
            image = image
        )
    }

    override suspend fun deleteItem(
        storeId: Long,
        itemId: Long
    ): Response<String> {
        return itemService.deleteItem(
            storeId = storeId,
            itemId = itemId
        )
    }
}