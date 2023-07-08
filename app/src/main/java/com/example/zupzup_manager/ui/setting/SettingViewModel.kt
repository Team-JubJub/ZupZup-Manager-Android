package com.example.zupzup_manager.ui.setting

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
) : ViewModel() {

    // 가게 상태 DB에서 읽어서 상태 초기화 필요, 임시로 기본값 false 지정
    private var _storeStatus = MutableLiveData(false)
    val storeStatus = _storeStatus

    fun changeStoreStatus() {
        _storeStatus.value = !_storeStatus.value!!
    }
}