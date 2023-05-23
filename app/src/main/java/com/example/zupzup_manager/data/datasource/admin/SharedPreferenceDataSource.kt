package com.example.zupzup_manager.data.datasource.admin

interface SharedPreferenceDataSource {

    fun insertStoreId(storeId : Long)

    fun getStoreId() : Long
}