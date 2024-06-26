package zupzup.manager.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import zupzup.manager.domain.DataResult
import zupzup.manager.domain.usecase.admin.GetLocalStoreIdUseCase
import zupzup.manager.domain.usecase.admin.SignInUseCase
import zupzup.manager.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val getLocalStoreIdUseCase: GetLocalStoreIdUseCase
) : ViewModel() {

    private var _loginState = MutableStateFlow<UiState<Triple<String, String, Long>>>(UiState.Loading)
    val loginState = _loginState.asStateFlow()

    init {
        autoLogin()
    }

    fun signIn(id: String, pw: String, deviceToken: String) {
        viewModelScope.launch {
            _loginState.emit(UiState.Loading)
            signInUseCase(id, pw, deviceToken).collect {
                if (it is DataResult.Success) {
                    _loginState.emit(UiState.Success(Triple(it.data.accessToken, it.data.refreshToken, it.data.storeId)))
                } else if (it is DataResult.Failure) {
                    _loginState.emit(UiState.Error(it.errorMessage))
                }
            }
        }
    }

    private fun autoLogin() {
        viewModelScope.launch {
            getLocalStoreIdUseCase().collect {
                if (it is DataResult.Success) {
                    _loginState.emit(UiState.Success(it.data))
                } else if (it is DataResult.Failure) {
                    _loginState.emit(UiState.Error(it.errorMessage))
                }
            }
        }
    }
}