package com.example.zupzup_manager.ui.managementdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zupzup_manager.data.common.toTimeString
import com.example.zupzup_manager.domain.DataResult
import com.example.zupzup_manager.domain.models.MerchandiseModel
import com.example.zupzup_manager.domain.models.StoreModel
import com.example.zupzup_manager.domain.usecase.GetStoreDetailUseCase
import com.example.zupzup_manager.domain.usecase.ModifyMerchandiseUseCase
import com.example.zupzup_manager.ui.management.models.ManagementViewType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManagementDetailViewModel @Inject constructor(
    private val modifyMerchandiseUseCase: ModifyMerchandiseUseCase
): ViewModel() {

    private var _merchandiseDetailBody = MutableStateFlow<List<MerchandiseModel>>(listOf())
    val merchandiseDetailBody = _merchandiseDetailBody.asStateFlow()

    fun setMerchandiseList(merchandiseList: List<MerchandiseModel>) {
        viewModelScope.launch {
            _merchandiseDetailBody.emit(merchandiseList)
        }
    }

    fun plusModifiedAmount(itemId: Long) {
        viewModelScope.launch {
            _merchandiseDetailBody.value.find{it.itemId == itemId}?.plusModifiedAmount()
        }
    }

    fun minusModifiedAmount(itemId: Long) {
        viewModelScope.launch {
            _merchandiseDetailBody.value.find{it.itemId == itemId}?.minusModifiedAmount()
        }
    }

    fun modifyMerchandise(merchandiseList: List<MerchandiseModel>) {
        var storeId = merchandiseList[0].storeId
        viewModelScope.launch {
            modifyMerchandiseUseCase(storeId, merchandiseList).collect {
                it.onSuccess {
                    Log.d("test", "viewmodel success: ")
                }.onFailure {
                    Log.d("test", "viewmodel failure: ")
                }
            }
        }
    }
}