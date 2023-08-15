package zupzup.manager.domain.usecase.item

import okhttp3.MultipartBody
import zupzup.manager.domain.models.item.ItemModifyModel
import zupzup.manager.domain.repository.ItemRepository
import javax.inject.Inject

class ModifyItemUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    suspend operator fun invoke(
        storeId: Long, item: ItemModifyModel, image: MultipartBody.Part?
    ): Result<String> {
        return try {
            val response = itemRepository.modifyItem(storeId, item, image)
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