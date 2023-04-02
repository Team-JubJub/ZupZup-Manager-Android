package com.example.zupzup_manager.ui.reservationdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zupzup_manager.domain.models.CartModel
import com.example.zupzup_manager.domain.models.ReservationModel
import com.example.zupzup_manager.domain.usecase.CancelReservationUseCase
import com.example.zupzup_manager.domain.usecase.CompleteReservationUseCase
import com.example.zupzup_manager.domain.usecase.ConfirmReservationUseCase
import com.example.zupzup_manager.domain.usecase.RejectReservationUseCase
import com.example.zupzup_manager.ui.common.UiEventState
import com.example.zupzup_manager.ui.common.ViewType
import com.example.zupzup_manager.ui.reservationdetail.models.ReservationDetailHeaderModel
import com.example.zupzup_manager.ui.reservationdetail.models.ReservationDetailViewType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationDetailViewModel @Inject constructor(
    private val confirmReservationUseCase: ConfirmReservationUseCase,
    private val rejectReservationUseCase: RejectReservationUseCase,
    private val cancelReservationUseCase: CancelReservationUseCase,
    private val completeReservationUseCase: CompleteReservationUseCase
) : ViewModel() {

    private var _reservationDetailHeader = MutableStateFlow(
        ReservationDetailHeaderModel("", "", 0, 0)
    )
    val reservationDetailHeader = _reservationDetailHeader.asStateFlow()

    private var _reservationDetailBody = MutableStateFlow<List<ReservationDetailViewType>>(listOf())
    val reservationDetailBody = _reservationDetailBody.asStateFlow()

    private var _reservationProcessingUiState = MutableSharedFlow<UiEventState>()
    val reservationProcessingUiState = _reservationProcessingUiState.asSharedFlow()

    fun setArgsToViewModel(reservation: ReservationModel) {
        setReservationDetailHeader(reservation)
        setReservationDetailBody(reservation)
    }

    fun plusConfirmedAmount(itemId: Long) {
        (_reservationDetailBody.value.find {
            it.viewType == ViewType.CART_ITEM.ordinal && (it as ReservationDetailViewType.ReservationCartItemViewType).cartItem.itemId == itemId
        } as ReservationDetailViewType.ReservationCartItemViewType).plusConfirmedAmount()
    }

    fun minusConfirmedAmount(itemId: Long) {
        (_reservationDetailBody.value.find {
            it.viewType == ViewType.CART_ITEM.ordinal && (it as ReservationDetailViewType.ReservationCartItemViewType).cartItem.itemId == itemId
        } as ReservationDetailViewType.ReservationCartItemViewType).minusConfirmedAmount()
    }

    private fun setReservationDetailHeader(reservation: ReservationModel) {
        viewModelScope.launch {
            with(reservation) {
                var title = "${cartList[0].name}"
                if (cartList.size > 1) {
                    title += "외 ${cartList.size - 1}개"
                }
                _reservationDetailHeader.emit(
                    ReservationDetailHeaderModel(
                        title = title,
                        state = state,
                        reserveId = reserveId,
                        storeId = storeId
                    )
                )
            }
        }
    }

    private fun setReservationDetailBody(reservation: ReservationModel) {
        viewModelScope.launch {
            _reservationDetailBody.emit(makeReservationDetailList(reservation))
        }
    }

    private fun makeReservationDetailList(reservation: ReservationModel): List<ReservationDetailViewType> {
        val dataList = mutableListOf<ReservationDetailViewType>()
        dataList.add(ReservationDetailViewType.HeaderDescription(header = "주문 정보"))
        dataList.add(
            ReservationDetailViewType.ReservationCustomerInfoViewType(
                customer = reservation.customer,
                visitTime = reservation.visitTime
            )
        )
        dataList.add(ReservationDetailViewType.HeaderDescription(header = "주문 내역"))
        reservation.cartList.forEach {
            dataList.add(
                ReservationDetailViewType.ReservationCartItemViewType(
                    cartItem = it,
                    reservationState = reservation.state
                )
            )
        }
        return dataList.toList()
    }

    fun confirmReservation(reserveId: Long, cartList: List<CartModel>) {
        viewModelScope.launch {
            _reservationProcessingUiState.emit(UiEventState.Processing)
            confirmReservationUseCase(reserveId, cartList).collect {
                it.onSuccess {
                    _reservationProcessingUiState.emit(UiEventState.Complete)
                }.onFailure {
                    _reservationProcessingUiState.emit(UiEventState.Fail("에러가 발생했습니다."))
                }
            }
        }
    }

    fun rejectReservation(reserveId: Long) {
        viewModelScope.launch {
            _reservationProcessingUiState.emit(UiEventState.Processing)
            rejectReservationUseCase(reserveId).collect {
                it.onSuccess {
                    _reservationProcessingUiState.emit(UiEventState.Complete)
                }.onFailure {
                    _reservationProcessingUiState.emit(UiEventState.Fail("에러가 발생했습니다."))
                }
            }
        }
    }

    fun cancelReservation(reserveId: Long) {
        viewModelScope.launch {
            _reservationProcessingUiState.emit(UiEventState.Processing)
            cancelReservationUseCase(reserveId).collect {
                it.onSuccess {
                    _reservationProcessingUiState.emit(UiEventState.Complete)
                }.onFailure {
                    _reservationProcessingUiState.emit(UiEventState.Fail("에러가 발생했습니다."))
                }
            }
        }
    }

    fun completeReservation(reserveId: Long) {
        viewModelScope.launch {
            _reservationProcessingUiState.emit(UiEventState.Processing)
            completeReservationUseCase(reserveId).collect {
                it.onSuccess {
                    _reservationProcessingUiState.emit(UiEventState.Complete)
                }.onFailure {
                    _reservationProcessingUiState.emit(UiEventState.Fail("에러가 발생했습니다."))
                }
            }
        }
    }
}
