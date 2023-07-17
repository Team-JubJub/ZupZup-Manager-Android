package com.example.zupzup_manager.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zupzup_manager.domain.DataResult
import com.example.zupzup_manager.domain.models.StoreModel
import com.example.zupzup_manager.domain.usecase.GetStoreDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getStoreDetailUseCase: GetStoreDetailUseCase,
) : ViewModel() {

    init {
        getStoreInfo(2)
    }

    private val initStoreModel = StoreModel(0,"","","",
        Pair(0.0, 0.0),"",listOf(),listOf(), Pair(0, 0))
    // 가게 상태 DB에서 읽어서 상태 초기화 필요, 임시로 기본값 false 지정
    private var _storeInfo = MutableStateFlow<StoreModel>(initStoreModel)
    val storeInfo = _storeInfo


    fun changeStoreStatus() {
//        _storeStatus.value = !_storeStatus.value!!
    }

    private fun getStoreInfo(storeId: Long) {
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
}