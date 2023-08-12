package zupzup.manager.ui.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import zupzup.manager.domain.DataResult
import zupzup.manager.domain.models.store.ModifyStoreModel
import zupzup.manager.domain.models.store.StoreModel
import zupzup.manager.domain.usecase.store.ModifyStoreDetailUseCase
import zupzup.manager.ui.common.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
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

    fun modifyStoreDetail(modifyStoreModel: ModifyStoreModel, image: MultipartBody.Part?) {
        viewModelScope.launch {
            modifyStoreDetailUseCase(User.getStoreId(), modifyStoreModel, image).collect {
                if (it is DataResult.Success) {
                    _storeInfo.emit(it.data)
                }
            }
        }
    }
}