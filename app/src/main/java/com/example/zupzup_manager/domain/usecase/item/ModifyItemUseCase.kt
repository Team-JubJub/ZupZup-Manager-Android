package com.example.zupzup_manager.domain.usecase.item

import com.example.zupzup_manager.domain.models.item.ModifyItemModel
import com.example.zupzup_manager.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import javax.inject.Inject

class ModifyItemUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    suspend operator fun invoke(storeId: Long, item: ModifyItemModel, image: MultipartBody.Part?): Flow<Result<String>> {
        return flow {
            emit(itemRepository.modifyItem(storeId, item, image))
        }.flowOn(Dispatchers.IO)
    }
}