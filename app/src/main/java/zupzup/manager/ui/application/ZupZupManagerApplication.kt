package zupzup.manager.ui.application

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ZupZupManagerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "{NATIVE_APP_KEY}")
    }
}