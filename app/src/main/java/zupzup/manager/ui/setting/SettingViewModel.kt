package zupzup.manager.ui.setting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import zupzup.manager.data.datasource.admin.SharedPreferenceDataSource
import zupzup.manager.domain.DataResult
import zupzup.manager.domain.models.store.StoreModel
import zupzup.manager.domain.usecase.admin.LeaveUseCase
import zupzup.manager.domain.usecase.admin.SignOutUseCase
import zupzup.manager.domain.usecase.store.ChangeOpenStatusUseCase
import zupzup.manager.domain.usecase.store.GetStoreDetailUseCase
import zupzup.manager.domain.usecase.store.ModifyStoreMatterUseCase
import zupzup.manager.ui.common.User
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getStoreDetailUseCase: GetStoreDetailUseCase,
    private val changeOpenStatusUseCase: ChangeOpenStatusUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val leaveUseCase: LeaveUseCase,
    private val modifyStoreMatterUseCase: ModifyStoreMatterUseCase,
    private val sharedPreferenceDataSource: SharedPreferenceDataSource
) : ViewModel() {

    init {
        getStoreInfo(User.getStoreId())
    }

    private val initStoreModel = StoreModel()

    // 가게 상태 DB에서 읽어서 상태 초기화 필요, 임시로 기본값 false 지정
    private var _storeInfo = MutableStateFlow<StoreModel>(initStoreModel)
    val storeInfo = _storeInfo

    private var _openStatus = MutableStateFlow<Boolean>(false)
    val openStatus get() = _openStatus.asStateFlow()

    private val _closeDialogEvent = MutableSharedFlow<Unit>()
    val closeDialogEvent: Flow<Unit> get() = _closeDialogEvent

    fun changeStoreStatus(isOpened: Boolean) {
        viewModelScope.launch {
            changeOpenStatusUseCase(User.getStoreId(), isOpened).collect {
                if (it is DataResult.Success) {
                    _openStatus.emit(isOpened)
                }
            }
        }
    }

    suspend fun signOut() {
        viewModelScope.launch {
            signOutUseCase(User.getAccessToken(), User.getRefreshToken(), User.getDeviceToken())
        }.join()

        viewModelScope.launch {
            sharedPreferenceDataSource.deleteData()
        }.join()
    }

    suspend fun leaveZupZup() {
        viewModelScope.launch {
            leaveUseCase(User.getStoreId())
        }.join()

        viewModelScope.launch {
            sharedPreferenceDataSource.deleteData()
        }.join()
    }

    fun getStoreInfo(storeId: Long) {
        viewModelScope.launch {
            getStoreDetailUseCase(storeId).collect {
                if (it is DataResult.Success) {
                    _storeInfo.emit(it.data)
                    _openStatus.emit(it.data.isOpen)
                } else {
                    _storeInfo.emit(initStoreModel)
                }
            }
        }
    }

    fun modifyStoreMatter(storeMatter: String) {
        viewModelScope.launch {
            modifyStoreMatterUseCase(User.getStoreId(), storeMatter).collect {
                if (it is DataResult.Success) {
                    _closeDialogEvent.emit(Unit)
                }
            }
        }
    }
}