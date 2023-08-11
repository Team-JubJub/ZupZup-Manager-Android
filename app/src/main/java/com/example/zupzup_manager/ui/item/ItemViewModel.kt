package com.example.zupzup_manager.ui.item

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zupzup_manager.domain.DataResult
import com.example.zupzup_manager.domain.models.item.ItemModel
import com.example.zupzup_manager.domain.models.item.ItemQuantityModel
import com.example.zupzup_manager.domain.usecase.item.GetItemListUseCase
import com.example.zupzup_manager.domain.usecase.item.ModifyItemQuantityUseCase
import com.example.zupzup_manager.domain.usecase.item.ModifyItemUseCase
import com.example.zupzup_manager.ui.common.ManagementState
import com.example.zupzup_manager.ui.common.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val getItemListUseCase: GetItemListUseCase,
    private val modifyItemQuantityUseCase: ModifyItemQuantityUseCase,
    private val modifyItemUseCase: ModifyItemUseCase
): ViewModel() {

    init {
        getItemList(User.getStoreId())
    }

    private var _itemDetailBody = MutableStateFlow<List<ItemModel>>(listOf())
    val itemDetailBody = _itemDetailBody.asStateFlow()

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
    private fun getItemList(storeId: Long) {
        viewModelScope.launch {
            getItemListUseCase(storeId).collect {
                if (it is DataResult.Success) {
                    _itemDetailBody.emit(it.data)
                } else {
                    _itemDetailBody.emit(listOf())
                }
            }
        }
    }

    // [AMOUNT MODE]
    // 제품 수량 수정하기 -> 수량 증가, 감소, 수정 완료 메서드 사용
    fun plusModifiedAmount(itemId: Long) {
        viewModelScope.launch {
            _itemDetailBody.value.find{it.itemId == itemId}?.plusModifiedAmount()
        }
    }

    fun minusModifiedAmount(itemId: Long) {
        viewModelScope.launch {
            _itemDetailBody.value.find{it.itemId == itemId}?.minusModifiedAmount()
        }
    }

    fun modifyItemQuantity(itemList: List<ItemQuantityModel>) {
        viewModelScope.launch {
            modifyItemQuantityUseCase(User.getStoreId(), itemList)
        }
    }
}