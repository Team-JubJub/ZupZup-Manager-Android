package com.example.zupzup_manager.ui.reservationdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zupzup_manager.domain.models.CartModel
import com.example.zupzup_manager.domain.models.CustomerModel
import com.example.zupzup_manager.domain.models.ReservationModel
import com.example.zupzup_manager.ui.reservationdetail.models.ReservationDetail
import com.example.zupzup_manager.ui.reservationdetail.models.ReservationDetailHeaderModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationDetailViewModel @Inject constructor() : ViewModel() {

    private var _reservationDetailHeader = MutableStateFlow(
        ReservationDetailHeaderModel("", "", 0)
    )

    val testReserve = ReservationModel(
        0, 0, "NEW", 1234, CustomerModel("test", "010-test-test"),
        listOf(
            CartModel(0, 0, "test1", 2000, 3),
            CartModel(0, 0, "test2", 3000, 5),
            CartModel(0, 0, "test3", 4000, 6)
        )
    )

    val reservationDetailHeader = _reservationDetailHeader.asStateFlow()

    private var _reservationDetailBody = MutableStateFlow<List<ReservationDetail>>(listOf())
    val reservationDetailBody = _reservationDetailBody.asStateFlow()

    fun setArgsToViewModel(reservation: ReservationModel) {
        setReservationDetailHeader(reservation)
        setReservationDetailBody(reservation)
    }

    private fun setReservationDetailHeader(reservation: ReservationModel) {
        viewModelScope.launch {
            with(reservation) {
                var title = "${cartList[0].name}"
                if (cartList.size > 1) {
                    title += "외 ${cartList.size - 1}개"
                }
                _reservationDetailHeader.emit(ReservationDetailHeaderModel(title, state, reserveId))
            }
        }
    }

    private fun setReservationDetailBody(reservation: ReservationModel) {
        viewModelScope.launch {
            _reservationDetailBody.emit(getReservationDetailList(reservation))
        }
    }

    private fun getReservationDetailList(reservation: ReservationModel): List<ReservationDetail> {
        val dataList = mutableListOf<ReservationDetail>()
        dataList.add(ReservationDetail.HeaderDescription(header = "주문 정보"))
        dataList.add(
            ReservationDetail.ReservationCustomerInfo(
                customer = reservation.customer,
                visitTime = reservation.visitTime
            )
        )
        dataList.add(ReservationDetail.HeaderDescription(header = "주문 내역"))
        reservation.cartList.forEach {
            dataList.add(
                ReservationDetail.ReservationCartItem(
                    cartItem = it,
                    reservationState = reservation.state
                )
            )
        }
        return dataList.toList()
    }

}
