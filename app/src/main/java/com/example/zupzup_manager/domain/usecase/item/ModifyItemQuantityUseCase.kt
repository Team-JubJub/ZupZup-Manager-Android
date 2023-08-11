package com.example.zupzup_manager.domain.usecase.item

import android.util.Log
import com.example.zupzup_manager.domain.models.item.ItemQuantityModel
import com.example.zupzup_manager.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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