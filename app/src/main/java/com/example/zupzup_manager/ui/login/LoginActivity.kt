package com.example.zupzup_manager.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.zupzup_manager.databinding.ActivityLoginBinding
import com.example.zupzup_manager.ui.common.UiEventState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    private val loginButtonOnClickListener = object : LoginButtonClickListener {
        override fun onClickLoginButton(id: String, pw: String) {
            if (id.isEmpty() || pw.isEmpty()) {
                Toast.makeText(this@LoginActivity, "아이디, 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                loginViewModel.login(id, pw)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initBinding()
        collectLoginEvent()
    }

    private fun collectLoginEvent() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.loginEventState.collect { loginEventState ->
                    when (loginEventState) {
                        is UiEventState.Processing -> {
                            binding.loginEventProgress.visibility = View.VISIBLE
                        }
                        is UiEventState.Complete -> {
                            binding.loginEventProgress.visibility = View.GONE
                            Toast.makeText(
                                this@LoginActivity,
                                "로그인 성공 ${loginViewModel.adminState.value}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is UiEventState.Fail -> {
                            binding.loginEventProgress.visibility = View.GONE
                            Toast.makeText(
                                this@LoginActivity,
                                loginEventState.errorMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun initBinding() {
        with(binding) {
            viewModel = loginViewModel
            loginBtnOnClick = loginButtonOnClickListener
        }
    }

    private fun initView() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    interface LoginButtonClickListener {
        fun onClickLoginButton(id: String, pw: String)
    }
}