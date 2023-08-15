package zupzup.manager.ui.itemdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import zupzup.manager.domain.models.item.ItemAddModel
import zupzup.manager.domain.models.item.ItemModifyModel
import zupzup.manager.domain.usecase.item.AddItemUseCase
import zupzup.manager.domain.usecase.item.DeleteItemUseCase
import zupzup.manager.domain.usecase.item.ModifyItemUseCase
import zupzup.manager.ui.common.User
import java.io.File
import javax.inject.Inject


@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    private val addItemUseCase: AddItemUseCase,
    private val modifyItemUseCase: ModifyItemUseCase,
    private val deleteItemUseCase: DeleteItemUseCase
) : ViewModel() {

    // 제품 등록
    fun addItem(item: ItemAddModel, image: File?) {
        val photo: MultipartBody.Part? = if (image != null) {
            val fileBody = image.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("photo", image.name, fileBody)
        } else null

        viewModelScope.launch {
            addItemUseCase(User.getStoreId(), item, photo)
        }
    }

    // 제품 수정
    fun modifyItem(item: ItemModifyModel, image: File?) {
        val photo: MultipartBody.Part? = if (image != null) {
            val fileBody = image.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("photo", image.name, fileBody)
        } else null

        viewModelScope.launch {
            modifyItemUseCase(User.getStoreId(), item, photo)
        }
    }

    // 제품 삭제
    fun deleteItem(itemId: Long) {
        viewModelScope.launch {
            deleteItemUseCase(User.getStoreId(), itemId)
        }
    }

//    // 제품 삭제
//    fun deleteItem(itemId: Long): Flow<Long?> {
//        return flow {
//            val response = deleteItemUseCase(User.getStoreId(), itemId)
//            if (response.isSuccess) {
//                emit(itemId)
//            } else {
//                emit(null)
//            }
//        }
//    }
}