package com.example.zupzup_manager.ui.management

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zupzup_manager.domain.models.MerchandiseModel
import com.example.zupzup_manager.domain.usecase.GetStoreDetailUseCase
import com.example.zupzup_manager.domain.usecase.ModifyMerchandiseUseCase
import com.example.zupzup_manager.ui.common.ManagementState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManagementViewModel @Inject constructor(
    private val getStoreDetailUseCase: GetStoreDetailUseCase,
    private val modifyMerchandiseUseCase: ModifyMerchandiseUseCase
): ViewModel() {

    init {
        getStoreDetail(2)
    }

    private var _managementDetailBody = MutableStateFlow<List<MerchandiseModel>>(listOf())
    val managementDetailBody = _managementDetailBody.asStateFlow()

    private var _managementUiState = MutableStateFlow<ManagementState>(ManagementState.DefaultMode)
    val managementUiState = _managementUiState.asStateFlow()


    // [CHANGE MODE]
    // DefaultMode / AmountMode / InfoMode 세 개의 state
    fun changeState(state: String) {
        viewModelScope.launch {
            when(state){
                "AmountMode" -> _managementUiState.emit(ManagementState.AmountMode)
                "InfoMode" -> _managementUiState.emit(ManagementState.InfoMode)
                else -> _managementUiState.emit(ManagementState.DefaultMode)
            }
        }
    }

    // [DEFAULT MODE]
    // 제품 목록 확인
    private fun getStoreDetail(storeId: Long) {
        viewModelScope.launch {
//            getStoreDetailUseCase(storeId).collect {
//                if (it is DataResult.Success) {
//                    _managementDetailBody.emit(it.data.merchandiseList)
//                } else {
//                    _managementDetailBody.emit(listOf())
//                }
//            }
        }
    }

    // [AMOUNT MODE]
    // 제품 수량 수정하기 -> 수량 증가, 감소, 수정 완료 메서드 사용
    fun plusModifiedAmount(itemId: Long) {
        viewModelScope.launch {
            _managementDetailBody.value.find{it.itemId == itemId}?.plusModifiedAmount()
        }
    }

    fun minusModifiedAmount(itemId: Long) {
        viewModelScope.launch {
            _managementDetailBody.value.find{it.itemId == itemId}?.minusModifiedAmount()
        }
    }

    fun modifyMerchandiseList(merchandiseList: List<MerchandiseModel>) {
        var storeId = 4L //merchandiseList[0].storeId
        viewModelScope.launch {
//            modifyMerchandiseUseCase(storeId, merchandiseList).collect {
//                it.onSuccess {
//                }.onFailure {
//                }
//            }
        }
    }
}