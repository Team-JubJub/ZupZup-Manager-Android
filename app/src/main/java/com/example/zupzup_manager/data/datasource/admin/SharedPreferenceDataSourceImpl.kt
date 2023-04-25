package com.example.zupzup_manager.data.datasource.admin

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferenceDataSourceImpl @Inject constructor(
    private val sharedPref: SharedPreferences
) : SharedPreferenceDataSource {
    override fun insertStoreId(storeId: Long) {
        with(sharedPref.edit()) {
            putLong("storeId", storeId)
            apply()
        }
    }
}