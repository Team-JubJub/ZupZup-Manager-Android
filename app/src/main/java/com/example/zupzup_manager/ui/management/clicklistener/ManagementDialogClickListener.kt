package com.example.zupzup_manager.ui.management.clicklistener

import com.example.zupzup_manager.domain.models.MerchandiseModel

interface ManagementDialogClickListener {
    // Dialog 내부에서 클릭할 때 모드 전환하는 클릭 리스너
    fun changeState(state: String)
    fun navigateToMerchandiseAdd()

}