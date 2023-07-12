package com.example.zupzup_manager.ui.common

sealed class ManagementState{
    object Default : ManagementState()
    object Amount : ManagementState()
    object Info : ManagementState()
}
