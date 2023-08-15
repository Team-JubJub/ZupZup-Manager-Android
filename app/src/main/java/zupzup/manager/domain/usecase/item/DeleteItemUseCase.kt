package zupzup.manager.domain.usecase.item

import zupzup.manager.domain.repository.ItemRepository
import javax.inject.Inject

class DeleteItemUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    suspend operator fun invoke(
        storeId: Long, itemId: Long
    ): Result<String> {
        return try {
            val response = itemRepository.deleteItem(storeId, itemId)
            if (response.isSuccess) {
                Result.success("1")
            } else {
                Result.success(response.toString())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}