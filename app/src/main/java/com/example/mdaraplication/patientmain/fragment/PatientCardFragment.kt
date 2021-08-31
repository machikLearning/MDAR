package com.example.mdaraplication.patientmain.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.example.mdaraplication.R
import com.example.mdaraplication.util.Util
import com.example.mdaraplication.webviewclient.MyWebViewClient

class PatientCardFragment : MyWebViewFragment() {
    private var baseURL = Util.getBaseURL()
    private var url : String = "patient/main";

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view  = inflater.inflate(R.layout.patient_card_fragment, container, false)
        var webView = view.findViewById<WebView>(R.id.patient_main_webview)
        webView.webViewClient = MyWebViewClient()
        webView.webChromeClient = WebChromeClient()
        webView.settings.javaScriptEnabled = true
        super.setWebView(webView,baseURL+url)
        return view
    }
}