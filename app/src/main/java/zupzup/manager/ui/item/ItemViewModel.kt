package zupzup.manager.ui.item

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import zupzup.manager.domain.DataResult
import zupzup.manager.domain.models.item.ItemModel
import zupzup.manager.domain.models.item.ItemQuantityModel
import zupzup.manager.domain.usecase.item.GetItemListUseCase
import zupzup.manager.domain.usecase.item.ModifyItemQuantityUseCase
import zupzup.manager.domain.usecase.item.ModifyItemUseCase
import zupzup.manager.ui.common.ManagementState
import zupzup.manager.ui.common.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import zupzup.manager.domain.models.item.ItemModifyModel
import zupzup.manager.domain.usecase.item.AddItemUseCase
import zupzup.manager.ui.item.recyclerview.ItemRcvAdapter
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val getItemListUseCase: GetItemListUseCase,
    private val modifyItemQuantityUseCase: ModifyItemQuantityUseCase,
    private val modifyItemUseCase: ModifyItemUseCase
): ViewModel() {

    init {
        getItemList()
    }

    private var _itemDetailBody = MutableStateFlow<List<ItemModel>>(listOf())
    val itemDetailBody = _itemDetailBody.asStateFlow()

    private var _managementUiState = MutableStateFlow<ManagementState>(ManagementState.DefaultMode)
    val managementUiState = _managementUiState.asStateFlow()

    // TODO
    // UI 상에서 리사이클러뷰 업데이트 [추가/수정/삭제]
    // -> 사진 업데이트 시에는 업데이트된 imageURL을 어떻게 받아야 할지 모르겠음..
    // -> 업데이트(추가, 수정) 시 url이 변경된다면 백엔드에서 url 던져줄 수 있는지?
    suspend fun modifyItemById(updatedItem: ItemModifyModel, itemId: Long) {
        viewModelScope.launch {
            val updatedItemList = _itemDetailBody.value.toMutableList()
            val index = updatedItemList.indexOfFirst { it.itemId == itemId }
            if (index != -1) {
                updatedItemList[index] = ItemModel(itemId, updatedItem.itemName, updatedItem.imageURL, updatedItem.itemPrice, updatedItem.salePrice, updatedItem.itemCount)
                _itemDetailBody.emit(updatedItemList.toList())
            }
        }.join()
    }
    suspend fun deleteItemById(itemId: Long) {
        viewModelScope.launch {
            val updatedItemList = _itemDetailBody.value.toMutableList()
            val index = updatedItemList.indexOfFirst { it.itemId == itemId }
            if (index != -1) {
                updatedItemList.removeAt(index)
                _itemDetailBody.emit(updatedItemList.toList())
            }
        }.join()
    }

    // [CHANGE MODE]
    // DefaultMode / AmountMode / InfoMode 세 개의 state
    fun changeState(state: String) {
        viewModelScope.launch {
            when(state){
                "AmountMode" -> _managementUiState.emit(ManagementState.AmountMode)
                "InfoMode" -> _managementUiState.emit(ManagementState.InfoMode)
                else -> _managementUiState.emit(ManagementState.DefaultMode)
            }
        }
    }

    // [DEFAULT MODE]
    // 제품 목록 확인
    fun getItemList() {
        viewModelScope.launch {
            getItemListUseCase(User.getStoreId()).collect {
                if (it is DataResult.Success) {
                    _itemDetailBody.emit(it.data)
                } else {
                    _itemDetailBody.emit(listOf())
                }
            }
        }
    }

    // [AMOUNT MODE]
    // 제품 수량 수정하기 -> 수량 증가, 감소, 수정 완료 메서드 사용
    fun plusModifiedAmount(itemId: Long) {
        viewModelScope.launch {
            _itemDetailBody.value.find{it.itemId == itemId}?.plusModifiedAmount()
        }
    }

    fun minusModifiedAmount(itemId: Long) {
        viewModelScope.launch {
            _itemDetailBody.value.find{it.itemId == itemId}?.minusModifiedAmount()
        }
    }

    fun modifyItemQuantity(itemList: List<ItemQuantityModel>) {
        viewModelScope.launch {
            val result = modifyItemQuantityUseCase(User.getStoreId(), itemList)
            // 리스트 UI에도 업데이트
            if (result.isSuccess) {
                val updatedItemList = _itemDetailBody.value.toMutableList()

                itemList.forEach { modifiedItem ->
                    val index = updatedItemList.indexOfFirst { it.itemId == modifiedItem.itemId }
                    if (index != -1) {
                        updatedItemList[index] = updatedItemList[index].copy(itemCount = modifiedItem.itemCount)
                    }
                }

                _itemDetailBody.emit(updatedItemList.toList())
            }
        }
    }
}