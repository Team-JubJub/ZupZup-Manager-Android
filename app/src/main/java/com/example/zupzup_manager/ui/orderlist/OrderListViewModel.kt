package com.example.zupzup_manager.ui.orderlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zupzup_manager.domain.DataResult
import com.example.zupzup_manager.domain.models.OrderModel
import com.example.zupzup_manager.domain.usecase.GetOrderListUseCase
import com.example.zupzup_manager.ui.common.UiState
import com.example.zupzup_manager.ui.common.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderListViewModel @Inject constructor(
    private val getOrderListUseCase: GetOrderListUseCase
) : ViewModel() {

    init {
        Log.d("TAG", ": init viewmodel")
        getOrderList(User.getAccessToken(), User.getStoreId())
    }

    private var _orderListUiState =
        MutableStateFlow<UiState<List<OrderModel>>>(UiState.Loading)
    val orderListUiState = _orderListUiState.asStateFlow()


    private fun getOrderList(accessToken: String, storeId: Long) {
        viewModelScope.launch {
            getOrderListUseCase(accessToken, storeId).collect {
                if (it is DataResult.Success) {
                    Log.d("vm TAG", "orderlist test - success")
                    _orderListUiState.emit(UiState.Success(it.data))
                } else {
                    _orderListUiState.emit(UiState.Error("에러입니다."))
                }
            }
        }
    }
}