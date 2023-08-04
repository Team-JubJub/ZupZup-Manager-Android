package com.example.zupzup_manager.data.datasource.admin

interface SharedPreferenceDataSource {

    fun insertAccessToken(accessToken : String)

    fun getAccessToken() : String

    fun insertRefreshToken(refreshToken : String)

    fun getRefreshToken() : String

    fun insertStoreId(storeId : Long)

    fun getStoreId() : Long

    fun deleteData()
}