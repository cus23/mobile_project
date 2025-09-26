package com.hfad.catchat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment

class HelpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_help, container, false)

        val yzuWebView = view.findViewById<WebView>(R.id.yzuWebView)
        val webSettings: WebSettings = yzuWebView.settings
        webSettings.javaScriptEnabled = true
        yzuWebView.webViewClient = WebViewClient()
        yzuWebView.loadUrl("https://www.yzu.edu.tw/")

        return view
    }
}
