package com.example.zupzup_manager.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zupzup_manager.domain.DataResult
import com.example.zupzup_manager.domain.usecase.GetReservationListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationListViewModel @Inject constructor (
    private val getReservationListUseCase: GetReservationListUseCase
) : ViewModel() {


    init {
        getReservationList(2)
    }

    fun a() {
        Log.d("JIWOO", "a: ")
    }

    private fun getReservationList(storeId: Long) {
        viewModelScope.launch {
            getReservationListUseCase.invoke(storeId).collect {

                if (it is DataResult.Success) {
                    Log.d("data", it.data.toString())
                }
            }
        }
    }
}