package zupzup.manager.domain.usecase.item

import zupzup.manager.domain.DataResult
import zupzup.manager.domain.models.item.ItemModel
import zupzup.manager.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetItemListUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    suspend operator fun invoke(storeId: Long): Flow<DataResult<List<ItemModel>>> {
        return flow {
            itemRepository.getItemList(storeId).onSuccess { itemList ->
                emit(DataResult.Success(itemList))
            }.onFailure {
                emit(DataResult.Failure("1"))
            }
        }.flowOn(Dispatchers.IO)
    }
}