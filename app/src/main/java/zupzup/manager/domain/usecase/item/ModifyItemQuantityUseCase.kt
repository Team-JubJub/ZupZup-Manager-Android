package zupzup.manager.domain.usecase.item

import zupzup.manager.domain.models.item.ItemQuantityModel
import zupzup.manager.domain.repository.ItemRepository
import java.lang.Exception
import javax.inject.Inject

class ModifyItemQuantityUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    suspend operator fun invoke(storeId: Long, itemList: List<ItemQuantityModel>): Result<String> {
        return try {
            val response = itemRepository.modifyItemQuantity(storeId, itemList)
            if (response.isSuccess) {
                Result.success("1")
            } else {
                Result.success(response.toString())
            }
        } catch(e: Exception) {
            Result.failure(e)
        }
    }
}