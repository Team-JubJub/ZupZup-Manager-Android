package com.example.zupzup_manager.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.zupzup_manager.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

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
    }

    private fun initBinding() {
        with(binding) {
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