package com.example.zupzup_manager.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zupzup_manager.domain.DataResult
import com.example.zupzup_manager.domain.usecase.LoginUseCase
import com.example.zupzup_manager.ui.common.UiEventState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private var _loginEventState = MutableSharedFlow<UiEventState>()
    val loginEventState get() = _loginEventState.asSharedFlow()

    fun login(id: String, pw: String) {
         viewModelScope.launch {
             _loginEventState.emit(UiEventState.Processing)
             loginUseCase(id, pw).collect {
                 if(it is DataResult.Success) {
                     _loginEventState.emit(UiEventState.Complete)
                 }
                 else if(it is DataResult.Failure){
                     if(it.errorCode == 0) { // Exception 이 아닌, 단순 id, pw 가 틀린 경우
                         _loginEventState.emit(UiEventState.Fail("아이디 혹은 비밀번호를 잘 못 입력하셨습니다."))
                     }
                     else {
                         _loginEventState.emit(UiEventState.Fail("에러 발생."))
                     }
                 }
             }
         }
    }

}