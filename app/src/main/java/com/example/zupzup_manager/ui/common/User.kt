package com.example.zupzup_manager.ui.common

object User {

    private var storeId: Long? = null

    fun setStoreId(newStoreId: Long) {
        storeId = newStoreId
    }

    fun getStoreId(): Long {
        return if (storeId != null) {
            storeId!!
        } else {
            -1
        }
    }
}