package com.example.zupzup_manager.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zupzup_manager.domain.DataResult
import com.example.zupzup_manager.domain.usecase.GetLocalStoreIdUseCase
import com.example.zupzup_manager.domain.usecase.SignInUseCase
import com.example.zupzup_manager.ui.common.UiState
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

    private var _loginState = MutableStateFlow<UiState<Pair<String, Long>>>(UiState.Loading)
    val loginState = _loginState.asStateFlow()

    init {
        autoLogin()
    }

    fun signIn(id: String, pw: String) {
        viewModelScope.launch {
            _loginState.emit(UiState.Loading)
            signInUseCase(id, pw).collect {
                if (it is DataResult.Success) {
                    _loginState.emit(UiState.Success(Pair(it.data.accessToken, it.data.storeId)))
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