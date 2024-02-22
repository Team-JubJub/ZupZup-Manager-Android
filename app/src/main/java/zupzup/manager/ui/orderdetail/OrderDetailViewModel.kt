package zupzup.manager.ui.orderdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import zupzup.manager.domain.models.order.OrderModel
import zupzup.manager.domain.usecase.order.CancelOrderUseCase
import zupzup.manager.domain.usecase.order.CompleteOrderUseCase
import zupzup.manager.domain.usecase.order.ConfirmOrderUseCase
import zupzup.manager.domain.usecase.order.RejectOrderUseCase
import zupzup.manager.domain.usecase.lunasoft.SendNotificationTalkUseCase
import zupzup.manager.ui.common.UiEventState
import zupzup.manager.ui.common.User
import zupzup.manager.ui.common.ViewType
import zupzup.manager.ui.orderdetail.models.OrderDetailHeaderModel
import zupzup.manager.ui.orderdetail.models.OrderDetailViewType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val confirmOrderUseCase: ConfirmOrderUseCase,
    private val rejectOrderUseCase: RejectOrderUseCase,
    private val cancelOrderUseCase: CancelOrderUseCase,
    private val completeOrderUseCase: CompleteOrderUseCase,
    private val sendNotificationTalkUseCase: SendNotificationTalkUseCase
) : ViewModel() {

    private var _orderDetailHeader = MutableStateFlow(
        OrderDetailHeaderModel(0, 0, "", "", "", "", "", "")
    )
    val orderDetailHeader = _orderDetailHeader.asStateFlow()

    private var _orderDetailBody = MutableStateFlow<List<OrderDetailViewType>>(listOf())
    val orderDetailBody = _orderDetailBody.asStateFlow()

    private var _orderProcessingUiState = MutableSharedFlow<UiEventState>()
    val orderProcessingUiState = _orderProcessingUiState.asSharedFlow()

    fun setArgsToViewModel(order: OrderModel) {
        setOrderDetailHeader(order)
        setOrderDetailBody(order)
    }

    fun plusConfirmedAmount(itemId: Long) {
        (_orderDetailBody.value.find {
            it.viewType == ViewType.ORDER_ITEM.ordinal && (it as OrderDetailViewType.OrderItemViewType).orderItem.itemId == itemId
        } as OrderDetailViewType.OrderItemViewType).plusConfirmedAmount()
    }

    fun minusConfirmedAmount(itemId: Long) {
        (_orderDetailBody.value.find {
            it.viewType == ViewType.ORDER_ITEM.ordinal && (it as OrderDetailViewType.OrderItemViewType).orderItem.itemId == itemId
        } as OrderDetailViewType.OrderItemViewType).minusConfirmedAmount()
    }

    private fun setOrderDetailHeader(order: OrderModel) {
        viewModelScope.launch {
            with(order) {
                var title = "${orderList[0].itemName}"
                if (orderList.size > 1) {
                    title += "외 ${orderList.size - 1}개"
                }
                _orderDetailHeader.emit(
                    OrderDetailHeaderModel(
                        orderId = orderId,
                        storeId = storeId,
                        orderStatus = orderStatus,
                        orderTitle = orderTitle,
                        orderTime = orderTime,
                        storeName = storeName,
                        storeAddress = storeAddress,
                        category = category
                    )
                )
            }
        }
    }

    private fun setOrderDetailBody(order: OrderModel) {
        viewModelScope.launch {
            _orderDetailBody.emit(makeOrderDetailList(order))
        }
    }

    private fun makeOrderDetailList(order: OrderModel): List<OrderDetailViewType> {
        val dataList = mutableListOf<OrderDetailViewType>()
        dataList.add(OrderDetailViewType.HeaderDescription(header = "주문 정보"))
        dataList.add(
            OrderDetailViewType.OrderCustomerInfoViewType(
                customer = order.customer,
                visitTime = order.visitTime
            )
        )
        dataList.add(OrderDetailViewType.HeaderDescription(header = "주문 내역"))
        order.orderList.forEach {
            dataList.add(
                OrderDetailViewType.OrderItemViewType(
                    orderItem = it,
                    orderStatus = order.orderStatus
                )
            )
        }
        return dataList.toList()
    }

    fun confirmOrder(orderModel: OrderModel, isPartial: Boolean) {
        viewModelScope.launch {
            _orderProcessingUiState.emit(UiEventState.Processing)
            with(orderModel) {
                confirmOrderUseCase(storeId, orderId, orderList).collect {
                    it.onSuccess {
//                        val newState = if (isPartial) "PARTIAL" else "CONFIRM"
//                        sendNotificationTalkUseCase(this, newState).onSuccess {
                            _orderProcessingUiState.emit(UiEventState.Complete)
//                        }.onFailure {
//                            _orderProcessingUiState.emit(UiEventState.Fail("알림톡 에러가 발생했습니다."))
//                        }
                    }.onFailure {
                        _orderProcessingUiState.emit(UiEventState.Fail("에러가 발생했습니다."))
                    }
                }
            }
        }
    }

    fun rejectOrder(orderModel: OrderModel) {
        viewModelScope.launch {
            _orderProcessingUiState.emit(UiEventState.Processing)
            with(orderModel) {
                rejectOrderUseCase(User.getStoreId(), orderId).collect {
                    it.onSuccess {
//                        val newState = "CANCEL"
//                        sendNotificationTalkUseCase(this, newState).onSuccess {
                            _orderProcessingUiState.emit(UiEventState.Complete)
//                        }.onFailure {
//                            _orderProcessingUiState.emit(UiEventState.Fail("알림톡 에러가 발생했습니다."))
//                        }
                    }.onFailure {
                        _orderProcessingUiState.emit(UiEventState.Fail("에러가 발생했습니다."))
                    }
                }
            }
        }
    }

    fun cancelOrder(orderModel: OrderModel) {
        viewModelScope.launch {
            _orderProcessingUiState.emit(UiEventState.Processing)
            with(orderModel){
                cancelOrderUseCase(User.getStoreId(), orderId, orderList).collect {
                    it.onSuccess {
                        _orderProcessingUiState.emit(UiEventState.Complete)
                    }.onFailure {
                        _orderProcessingUiState.emit(UiEventState.Fail("에러가 발생했습니다."))
                    }
                }
            }
        }
    }

    fun completeOrder(orderId: Long) {
        viewModelScope.launch {
            _orderProcessingUiState.emit(UiEventState.Processing)
            completeOrderUseCase(User.getStoreId(), orderId).collect {
                it.onSuccess {
                    _orderProcessingUiState.emit(UiEventState.Complete)
                }.onFailure {
                    _orderProcessingUiState.emit(UiEventState.Fail("에러가 발생했습니다."))
                }
            }
        }
    }
}
