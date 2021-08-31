package com.example.mdaraplication.search

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import android.webkit.WebChromeClient
import androidx.appcompat.app.AppCompatActivity
import com.example.mdaraplication.R
import com.example.mdaraplication.patientmain.PatientMainActivity
import com.example.mdaraplication.prescription.PrescriptionMaker
import com.example.mdaraplication.survey.SurveyMainActivity
import com.example.mdaraplication.util.Util
import com.example.mdaraplication.webviewclient.MyWebViewClient
import kotlinx.android.synthetic.main.search_drug.*
import okhttp3.Cookie
import okhttp3.HttpUrl

class SearchDrugActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        var url = Util.getBaseURL()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_drug)
        val webview = search_webview
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            CookieSyncManager.getInstance().sync()
        }else{
            var cookieManager = CookieManager.getInstance()
            cookieManager.setAcceptCookie(true)
            cookieManager.setAcceptThirdPartyCookies(webview,true);
            cookieManager.removeAllCookies(null)
            cookieManager.flush()
            var cookieList:List<Cookie> = Util.getCookieJar().loadForRequest(HttpUrl.parse(url))
            if(cookieList != null) {
                for (cookie: Cookie in cookieList) {
                    var token = cookie.name() +"="+cookie.value()+";"
                    CookieManager.getInstance().setCookie(cookie.domain(),token)
                }
            }
        }
        webview.webViewClient = MyWebViewClient()
        webview.webChromeClient = WebChromeClient()
        webview.settings.javaScriptEnabled = true
        webview.loadUrl(url+"patient/searchDrug")
    }

    override fun onBackPressed() {
        if(search_webview.canGoBack()){
            search_webview.goBack()
        }else super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.mainActivity -> startActivity(Intent(this, PatientMainActivity :: class.java))
//            R.id.surveyActivity -> startActivity((Intent(this, SurveyMainActivity :: class.java)))
//            R.id.prescriptionMakerActivity -> startActivity(Intent(this, PrescriptionMaker :: class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}