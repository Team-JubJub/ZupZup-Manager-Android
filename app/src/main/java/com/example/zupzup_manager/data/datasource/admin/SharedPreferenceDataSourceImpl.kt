package com.example.zupzup_manager.data.datasource.admin

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferenceDataSourceImpl @Inject constructor(
    private val sharedPref: SharedPreferences
) : SharedPreferenceDataSource {

    private val accessTokenKey = "accessToken"
    private val refreshTokenKey = "refreshToken"
    private val storeIdKey = "storeId"

    override fun insertAccessToken(accessToken: String) {
        with(sharedPref.edit()) {
            putString(accessTokenKey, accessToken)
            apply()
        }
    }

    override fun getAccessToken(): String {
        val accessToken = sharedPref.getString(accessTokenKey, "-1")
        if (accessToken == "-1" || accessToken == null) {
            throw NullPointerException()
        }
        return accessToken
    }

    override fun insertRefreshToken(refreshToken: String) {
        with(sharedPref.edit()) {
            putString(refreshTokenKey, refreshToken)
            apply()
        }
    }

    override fun getRefreshToken(): String {
        val refreshToken = sharedPref.getString(refreshTokenKey, "-1")
        if (refreshToken == "-1" || refreshToken == null) {
            throw NullPointerException()
        }
        return refreshToken
    }

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

    override fun deleteData() {
        with(sharedPref.edit()) {
            clear()
            apply()
        }
    }
}