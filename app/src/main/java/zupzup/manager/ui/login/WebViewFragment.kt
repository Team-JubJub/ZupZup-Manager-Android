package zupzup.manager.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import zupzup.manager.databinding.FragmentWebviewBinding

@AndroidEntryPoint
class WebViewFragment : Fragment() {

    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWebviewBinding.inflate(inflater, container, false)
        webView = binding.webView
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()

        arguments?.getString(URL_KEY)?.let { url ->
            webView.loadUrl(url)
        }

        return binding.root
    }

    companion object {
        private const val URL_KEY = "url"

        fun newInstance(url: String): WebViewFragment {
            val fragment = WebViewFragment()
            val args = Bundle()
            args.putString(URL_KEY, url)
            fragment.arguments = args
            return fragment
        }
    }
}