package zupzup.manager.ui.store

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import zupzup.manager.domain.DataResult
import zupzup.manager.domain.models.store.ModifyStoreModel
import zupzup.manager.domain.models.store.StoreModel
import zupzup.manager.domain.usecase.store.ModifyStoreDetailUseCase
import zupzup.manager.ui.common.User
import java.io.File
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val modifyStoreDetailUseCase: ModifyStoreDetailUseCase
) : ViewModel() {

    private val initStoreModel = StoreModel()
    private var _storeInfo = MutableStateFlow<StoreModel>(initStoreModel)

    fun setPreviousStore(store: StoreModel) {
        _storeInfo.value = store
    }

    fun isStoreChanged(store: StoreModel): Boolean {
        return _storeInfo.value != null && _storeInfo.value != store
    }

    suspend fun modifyStoreDetail(modifyStoreModel: ModifyStoreModel, image: File?) {
        viewModelScope.launch {
            val photo: MultipartBody.Part? = if (image != null) {
                val fileBody = image.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("image", image.name, fileBody)
            } else null

            modifyStoreDetailUseCase(User.getStoreId(), modifyStoreModel, photo).collect {
                if (it is DataResult.Success) {
                    _storeInfo.emit(it.data)
                }
            }
        }.join()
    }
}