package com.example.mdaraplication.webviewclient

import android.os.Build
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import android.webkit.WebView
import android.webkit.WebViewClient

class MyWebViewClient :WebViewClient(){
    override fun onPageFinished(view: WebView?, url: String?) {
        if(Build.VERSION.SDK_INT <  Build.VERSION_CODES.LOLLIPOP){
            CookieSyncManager.getInstance().sync()
        }else{
            CookieManager.getInstance().flush()
        }
    }
}