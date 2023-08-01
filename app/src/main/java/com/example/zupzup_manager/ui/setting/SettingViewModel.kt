package com.example.zupzup_manager.ui.setting

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zupzup_manager.domain.DataResult
import com.example.zupzup_manager.domain.models.StoreModel
import com.example.zupzup_manager.domain.usecase.ChangeOpenStatusUseCase
import com.example.zupzup_manager.domain.usecase.GetStoreDetailUseCase
import com.example.zupzup_manager.domain.usecase.SignOutUseCase
import com.example.zupzup_manager.ui.common.UiState
import com.example.zupzup_manager.ui.common.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getStoreDetailUseCase: GetStoreDetailUseCase,
    private val changeOpenStatusUseCase: ChangeOpenStatusUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    init {
        getStoreInfo(User.getAccessToken(), User.getStoreId())
    }

    private val initStoreModel = StoreModel()
    // 가게 상태 DB에서 읽어서 상태 초기화 필요, 임시로 기본값 false 지정
    private var _storeInfo = MutableStateFlow<StoreModel>(initStoreModel)
    val storeInfo = _storeInfo

    private var _openStatus = MutableStateFlow<Boolean>(storeInfo.value.isOpen)
    val openStatus = _openStatus

    fun changeStoreStatus(isOpened: Boolean) {
        viewModelScope.launch {
            changeOpenStatusUseCase(User.getAccessToken(), User.getStoreId(), isOpened).collect {
                if (it is DataResult.Success) {
                    _openStatus.emit(it.data.toBoolean())
                }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            signOutUseCase(User.getAccessToken(), User.getRefreshToken())
        }
    }

    private fun getStoreInfo(accessToken: String, storeId: Long) {
        viewModelScope.launch {
            getStoreDetailUseCase(accessToken, storeId).collect {
                if (it is DataResult.Success) {
                    _storeInfo.emit(it.data)
                } else {
                    _storeInfo.emit(initStoreModel)
                }
            }
        }
    }
}