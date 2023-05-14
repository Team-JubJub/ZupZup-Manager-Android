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
    private val getStoreDetailUseCase: GetStoreDetailUseCase,
    private val modifyMerchandiseUseCase: ModifyMerchandiseUseCase
): ViewModel() {

    private var _merchandiseDetailBody = MutableStateFlow<List<MerchandiseModel>>(listOf())
    val merchandiseDetailBody = _merchandiseDetailBody.asStateFlow()

    private fun makeMerchandiseList(storeModel: StoreModel): List<MerchandiseModel> {
        return storeModel.merchandiseList.map {
            MerchandiseModel(
                itemId = it.itemId,
                itemName = it.itemName,
                price = it.price,
                discounted = it.discounted,
                imgUrl = it.imgUrl,
                stock = it.stock,
                storeId = it.storeId
            )
        }
    }

    fun getMerchandiseList(storeId: Long) {
        viewModelScope.launch {
            getStoreDetailUseCase(storeId).collect {
                if (it is DataResult.Success) {
                    _merchandiseDetailBody.emit(makeMerchandiseList(it.data))
                } else {
                    _merchandiseDetailBody.emit(listOf())
                }
            }
        }
    }

    fun modifyMerchandise(storeModel: StoreModel) {
        viewModelScope.launch {
            with(storeModel) {
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
}