package zupzup.manager.domain.usecase.item

import okhttp3.MultipartBody
import zupzup.manager.domain.models.item.ItemAddModel
import zupzup.manager.domain.repository.ItemRepository
import javax.inject.Inject

class AddItemUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    suspend operator fun invoke(
        storeId: Long, item: ItemAddModel, image: MultipartBody.Part?
    ): Result<String> {
        return try {
            val response = itemRepository.addItem(storeId, item, image)

            if (response.isSuccess) {
                val responseBody = response.getOrThrow()
                Result.success(responseBody)
            } else {
                Result.success(response.toString())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}