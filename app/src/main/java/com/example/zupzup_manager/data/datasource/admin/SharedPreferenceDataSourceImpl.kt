package com.example.zupzup_manager.data.datasource.admin

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferenceDataSourceImpl @Inject constructor(
    private val sharedPref: SharedPreferences
) : SharedPreferenceDataSource {

    private val storeIdKey = "storeId"

    override fun insertStoreId(storeId: Long) {
        with(sharedPref.edit()) {
            putLong(storeIdKey, storeId)
            apply()
        }
    }

    override fun getStoreId(): Long {
        val storeId = sharedPref.getLong(storeIdKey, -1L)
        if (storeId == -1L) {
            throw NullPointerException()
        }
        return storeId
    }
}