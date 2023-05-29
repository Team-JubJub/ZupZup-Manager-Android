package com.example.zupzup_manager.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.zupzup_manager.databinding.ActivityLoginBinding
import com.example.zupzup_manager.ui.activity.MainActivity
import com.example.zupzup_manager.ui.common.UiState
import com.example.zupzup_manager.ui.common.User
import com.example.zupzup_manager.ui.common.progress.ProgressDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    private var progressDialog = ProgressDialogFragment()

    private val loginButtonOnClickListener = object : LoginButtonClickListener {
        override fun onClickLoginButton(id: String, pw: String) {
            if (id.isEmpty() || pw.isEmpty()) {
                Toast.makeText(this@LoginActivity, "아이디, 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                loginViewModel.signIn(id, pw)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initBinding()
        collectLoginState()
    }

    private fun collectLoginState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.loginState.collect {
                    when (it) {
                        is UiState.Loading -> {
                            showProgressDialog()
                        }
                        is UiState.Success -> {
                            setUserStoreId(it.data)
                            hideProgressDialog()
                            navigateMainActivity()
                        }
                        is UiState.Error -> {
                            hideProgressDialog()
                            Toast.makeText(applicationContext, it.errorMessage, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun setUserStoreId(storeId: Long) {
        User.setStoreId(storeId)
    }

    private fun navigateMainActivity() {
        val mainIntent = Intent(this, MainActivity::class.java)
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK + Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(mainIntent)
    }

    private fun showProgressDialog() {
        progressDialog.showProgressDialog(supportFragmentManager)
    }

    private fun hideProgressDialog() {
        progressDialog.hideProgressDialog()
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