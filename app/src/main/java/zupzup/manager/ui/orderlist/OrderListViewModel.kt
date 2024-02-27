package zupzup.manager.ui.orderlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import zupzup.manager.domain.DataResult
import zupzup.manager.domain.models.order.OrderModel
import zupzup.manager.domain.usecase.order.GetOrderListUseCase
import zupzup.manager.ui.common.UiState
import zupzup.manager.ui.common.User
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
        getOrderList(User.getStoreId())
    }

    private var _orderListUiState =
        MutableStateFlow<UiState<List<OrderModel>>>(UiState.Loading)
    val orderListUiState = _orderListUiState.asStateFlow()


    fun getOrderList(storeId: Long) {
        viewModelScope.launch {
            getOrderListUseCase(storeId).collect {
                if (it is DataResult.Success) {
                    if (it.data.isEmpty()) {
                        _orderListUiState.emit(UiState.Empty)
                    } else {
                        _orderListUiState.emit(UiState.Success(it.data))
                    }
                } else {
                    _orderListUiState.emit(UiState.Error("에러입니다."))
                }
            }
        }
    }
}