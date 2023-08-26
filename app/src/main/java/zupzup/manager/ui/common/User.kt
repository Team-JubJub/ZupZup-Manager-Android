package zupzup.manager.ui.common

object User {

    private var accessToken: String? = null
    private var refreshToken: String? = null
    private var storeId: Long? = null

    fun setAccessToken(newAccessToken: String) {
        accessToken = newAccessToken
    }
    fun setRefreshToken(newRefreshToken: String) {
        refreshToken = newRefreshToken
    }
    fun setStoreId(newStoreId: Long) {
        storeId = newStoreId
    }

    fun getAccessToken(): String {
        return if (accessToken != null) {
            accessToken!!
        } else {
            "-1"
        }
    }

    fun getRefreshToken(): String {
        return if (refreshToken != null) {
            refreshToken!!
        } else {
            "-1"
        }
    }

    fun getStoreId(): Long {
        return if (storeId != null) {
            storeId!!
        } else {
            -1
        }
    }
}