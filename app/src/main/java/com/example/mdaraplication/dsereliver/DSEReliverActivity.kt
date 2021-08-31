package com.example.mdaraplication.dsereliver

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.mdaraplication.R
import kotlinx.android.synthetic.main.dsereliver_activity.*

/* 실시간 검색*/
class DSEReliverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dsereliver_activity)
        dsereliver_webview.webViewClient = WebViewClient()
        dsereliver_webview.webChromeClient = WebChromeClient()
        dsereliver_webview.settings.javaScriptEnabled = true
        val dsereliverWebview = dsereliver_webview.loadUrl("http://10.0.2.2:8080/patient/statistic")
    }
}