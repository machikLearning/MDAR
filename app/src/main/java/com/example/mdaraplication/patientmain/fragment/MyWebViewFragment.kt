package com.example.mdaraplication.patientmain.fragment

import android.os.Build
import android.view.KeyEvent
import android.view.View
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.example.mdaraplication.util.Util
import com.example.mdaraplication.webviewclient.MyWebViewClient
import okhttp3.Cookie
import okhttp3.HttpUrl

open class MyWebViewFragment : Fragment() {
    protected lateinit var webView : WebView
    protected fun setWebView(webView: WebView, url : String) {
        this.webView = webView
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            CookieSyncManager.getInstance().sync()
        }else{
            var cookieManager = CookieManager.getInstance()
            cookieManager.setAcceptCookie(true)
            cookieManager.setAcceptThirdPartyCookies(this.webView,true);
            cookieManager.removeAllCookies(null)
            cookieManager.flush()
            var cookieList:List<Cookie> = Util.getCookieJar().loadForRequest(HttpUrl.parse(Util.getBaseURL()))
            if(cookieList != null) {
                for (cookie: Cookie in cookieList) {
                    var token = cookie.name() +"="+cookie.value()+";"
                    CookieManager.getInstance().setCookie(cookie.domain(),token)
                }
            }
        }
        this.setWebViewOnKeyListener()
        this.webView.webViewClient = MyWebViewClient()
        this.webView.loadUrl(url)
    }

    private fun setWebViewOnKeyListener() {
        var keyListener = object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if(event?.action != KeyEvent.ACTION_DOWN) return true
                else if(event?.action == KeyEvent.KEYCODE_BACK){
                    if(webView.canGoBack())webView.goBack()
                    else activity?.onBackPressed()
                    return true
                }
                return false
            }
        }
        webView.setOnKeyListener(keyListener)
    }
}