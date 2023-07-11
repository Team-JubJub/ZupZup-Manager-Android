package com.example.zupzup_manager.ui.management

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zupzup_manager.data.common.toTimeString
import com.example.zupzup_manager.domain.DataResult
import com.example.zupzup_manager.domain.models.MerchandiseModel
import com.example.zupzup_manager.domain.models.StoreModel
import com.example.zupzup_manager.domain.usecase.GetStoreDetailUseCase
import com.example.zupzup_manager.ui.common.UiState
import com.example.zupzup_manager.ui.management.models.ManagementViewType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManagementViewModel @Inject constructor(
    private val getStoreDetailUseCase: GetStoreDetailUseCase
): ViewModel() {

    init {
        getStoreDetail(2)
    }

    private var _managementDetailBody = MutableStateFlow<List<MerchandiseModel>>(listOf())
    val managementDetailBody = _managementDetailBody.asStateFlow()

    private fun getStoreDetail(storeId: Long) {
        viewModelScope.launch {
            getStoreDetailUseCase(storeId).collect {
                if (it is DataResult.Success) {
                    _managementDetailBody.emit(it.data.merchandiseList)
                } else {
                    _managementDetailBody.emit(listOf())
                }
            }
        }
    }
}