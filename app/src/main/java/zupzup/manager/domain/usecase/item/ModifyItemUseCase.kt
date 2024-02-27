package zupzup.manager.domain.usecase.item

import okhttp3.MultipartBody
import zupzup.manager.domain.models.item.ItemModifyModel
import zupzup.manager.domain.repository.ItemRepository
import javax.inject.Inject

class ModifyItemUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    suspend operator fun invoke(
        itemId: Long, storeId: Long, item: ItemModifyModel, image: MultipartBody.Part?
    ): Result<String> {
        return try {
            val response = itemRepository.modifyItem(itemId, storeId, item, image)

            if (response.isSuccess) {
                val responseBody = response.getOrThrow()
                Result.success(responseBody)
            } else {
                val errorBody = response.exceptionOrNull()?.message ?: response.toString()
                Result.success(errorBody)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}