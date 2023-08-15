package zupzup.manager.ui.setting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import zupzup.manager.domain.DataResult
import zupzup.manager.domain.models.store.StoreModel
import zupzup.manager.domain.usecase.store.ChangeOpenStatusUseCase
import zupzup.manager.domain.usecase.store.GetStoreDetailUseCase
import zupzup.manager.domain.usecase.store.ModifyStoreMatterUseCase
import zupzup.manager.domain.usecase.admin.SignOutUseCase
import zupzup.manager.ui.common.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getStoreDetailUseCase: GetStoreDetailUseCase,
    private val changeOpenStatusUseCase: ChangeOpenStatusUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val modifyStoreMatterUseCase: ModifyStoreMatterUseCase
) : ViewModel() {

    init {
        getStoreInfo(User.getStoreId())
    }

    private val initStoreModel = StoreModel()
    // 가게 상태 DB에서 읽어서 상태 초기화 필요, 임시로 기본값 false 지정
    private var _storeInfo = MutableStateFlow<StoreModel>(initStoreModel)
    val storeInfo = _storeInfo

    private var _openStatus = MutableStateFlow<Boolean>(storeInfo.value.isOpen)

    private val _closeDialogEvent = MutableSharedFlow<Unit>()
    val closeDialogEvent: Flow<Unit> get() = _closeDialogEvent

    fun changeStoreStatus(isOpened: Boolean) {
        viewModelScope.launch {
            changeOpenStatusUseCase(User.getStoreId(), isOpened).collect {
                if (it is DataResult.Success) {
                    _openStatus.emit(it.data.toBoolean())
                }
            }
        }
    }

    suspend fun signOut() {
        viewModelScope.launch {
            signOutUseCase(User.getAccessToken(), User.getRefreshToken())
            Log.d("TAG", "로그아웃 완료 vm")
        }.join()
    }

    fun getStoreInfo(storeId: Long) {
        viewModelScope.launch {
            getStoreDetailUseCase(storeId).collect {
                if (it is DataResult.Success) {
                    _storeInfo.emit(it.data)
                } else {
                    _storeInfo.emit(initStoreModel)
                }
            }
        }
    }

    fun modifyStoreMatter(storeMatter: String){
        viewModelScope.launch {
            modifyStoreMatterUseCase(User.getStoreId(), storeMatter).collect {
                if (it is DataResult.Success) {
                    _closeDialogEvent.emit(Unit)
                }
            }
        }
    }
}