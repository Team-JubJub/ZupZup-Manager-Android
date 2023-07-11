package com.example.zupzup_manager.ui.reservationlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zupzup_manager.domain.DataResult
import com.example.zupzup_manager.domain.models.ReservationModel
import com.example.zupzup_manager.domain.usecase.GetReservationListUseCase
import com.example.zupzup_manager.ui.common.UiState
import com.example.zupzup_manager.ui.common.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationListViewModel @Inject constructor(
    private val getReservationListUseCase: GetReservationListUseCase
) : ViewModel() {

//    init {
//        getReservationList(User.getStoreId(), 3)
//    }

    private var _reservationListUiState =
        MutableStateFlow<UiState<List<ReservationModel>>>(UiState.Loading)
    val reservationListUiState = _reservationListUiState.asStateFlow()


    fun getReservationList(storeId: Long, state: Int) {
        viewModelScope.launch {
            getReservationListUseCase(storeId, state).collect {
                if (it is DataResult.Success) {
                    _reservationListUiState.emit(UiState.Success(it.data))
                } else {
                    _reservationListUiState.emit(UiState.Error("에러입니다."))
                }
            }
        }
    }
}