package zupzup.manager.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import zupzup.manager.databinding.ActivityLoginBinding
import zupzup.manager.ui.activity.MainActivity
import zupzup.manager.ui.common.UiState
import zupzup.manager.ui.common.User
import zupzup.manager.ui.common.progress.ProgressDialogFragment
import zupzup.manager.ui.fcm.MyFirebaseMessagingService

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    private val webViewFragment = WebViewFragment()
    private var progressDialog = ProgressDialogFragment()

    private val loginButtonOnClickListener = object : LoginButtonClickListener {
        override fun onClickLoginButton(id: String, pw: String): Boolean {
            if (id.isEmpty() || pw.isEmpty()) {
                Toast.makeText(this@LoginActivity, "아이디, 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                MyFirebaseMessagingService().getFirebaseToken { deviceToken ->
                    User.setDeviceToken(deviceToken)
                    loginViewModel.signIn(id, pw, deviceToken)
                }
            }

            this@LoginActivity.currentFocus?.let { view ->
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }

            return true
        }

        override fun findIdWebView() {
            openWebView("https://zupzupofficial.com/find_id_main")
        }

        override fun findPwdWebView() {
            openWebView("https://zupzupofficial.com/find_pw_main")
        }

        override fun signUpWebView() {
            openWebView("https://zupzupofficial.com/signin_terms")
        }

        private fun openWebView(url: String) {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val webViewFragment = WebViewFragment.newInstance(url)
            fragmentTransaction.replace(binding.fragmentContainer.id, webViewFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
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
                            setUserInfo(it.data)
                            hideProgressDialog()
                            navigateMainActivity()
                        }

                        is UiState.Error -> {
                            hideProgressDialog()
                            Toast.makeText(applicationContext, it.errorMessage, Toast.LENGTH_SHORT)
                                .show()
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun setUserInfo(userInfo: Triple<String, String, Long>) {
        User.setAccessToken(userInfo.first)
        User.setRefreshToken(userInfo.second)
        User.setStoreId(userInfo.third)
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
        fun onClickLoginButton(id: String, pw: String): Boolean
        fun findIdWebView()
        fun findPwdWebView()
        fun signUpWebView()
    }
}