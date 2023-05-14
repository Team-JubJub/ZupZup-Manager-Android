package com.example.zupzup_manager.ui.management

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zupzup_manager.data.common.toTimeString
import com.example.zupzup_manager.domain.DataResult
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

    private var _managementDetailBody = MutableStateFlow<List<ManagementViewType>>(listOf())
    val managementDetailBody = _managementDetailBody.asStateFlow()


    private fun getManagementViewTypeList(storeModel: StoreModel): List<ManagementViewType> {
        val viewTypeList = mutableListOf<ManagementViewType>()
        viewTypeList.add(
            ManagementViewType.StoreInfoViewType(
                name = storeModel.name,
                openTime = storeModel.openTime,
                eventList = storeModel.eventList.joinToString("\n"),
                saleOpenTime = storeModel.saleTime.first.toTimeString(),
                saleCloseTime = storeModel.saleTime.second.toTimeString())
        )
        viewTypeList.add(
            ManagementViewType.MerchandiseModifyViewType(
                storeId = storeModel.storeId
            )
        )
        storeModel.merchandiseList.forEach {
            viewTypeList.add(
                ManagementViewType.MerchandiseInfoViewType(
                    itemId = it.itemId,
                    itemName = it.itemName,
                    price = it.price,
                    discounted = it.discounted,
                    imgUrl = it.imgUrl,
                    stock = it.stock
                )
            )
        }
        return viewTypeList
    }

    private fun getStoreDetail(storeId: Long) {
        viewModelScope.launch {
            getStoreDetailUseCase(storeId).collect {
                if (it is DataResult.Success) {
                    _managementDetailBody.emit(getManagementViewTypeList(it.data))
                } else {
                    _managementDetailBody.emit(listOf())
                }
            }
        }
    }
}