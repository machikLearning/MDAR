package com.example.mdaraplication.login

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle

import android.webkit.CookieSyncManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mdaraplication.R
import com.example.mdaraplication.findID.FindIDActivity
import com.example.mdaraplication.findPW.FindPWActivity
import com.example.mdaraplication.patientmain.PatientMainActivity
import com.example.mdaraplication.signup.SignUpActivity
import com.example.mdaraplication.util.Util
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import kotlinx.android.synthetic.main.login_activity.*;
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class MDARLoginActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        var cookieJar = PersistentCookieJar(SetCookieCache(),  SharedPrefsCookiePersistor(this));
        Util.setRetrofit(cookieJar)
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            CookieSyncManager.createInstance(this);
        }
        btn_login.setOnClickListener {
            var strUserID = userID.text.toString()
            var strPassword = password.text.toString()
            connectServer(strUserID,strPassword)
        }

        btn_register.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }
        find_id.setOnClickListener{
            startActivity(Intent(this, FindIDActivity::class.java))
        }
        find_pw.setOnClickListener{
            startActivity(Intent(this, FindPWActivity::class.java))
        }
        var sharedPreferences = getSharedPreferences(Util.getSharedPrefName(), Context.MODE_PRIVATE)
        var sharedPreferencesEdit = sharedPreferences.edit()
        if(sharedPreferences.contains(Util.getAutoLogin())){
            if(sharedPreferences.getBoolean(Util.getAutoLogin(),false)){
                var strUserID = sharedPreferences.getString(Util.getIDKey(),"").toString()
                var strPassword = sharedPreferences.getString(Util.getPassword(),"").toString()
                connectServer(strUserID,strPassword)
            }else{
                auto_login.isChecked = false
            }
        }
        auto_login.setOnCheckedChangeListener { buttonView, isChecked ->
            sharedPreferencesEdit.putBoolean(Util.getAutoLogin(),isChecked)
            sharedPreferencesEdit.commit()
        }
    }

    private fun connectServer(strUserID :String, strPassword:String) {
        var networkFail = "네트워크 연결상태가 좋지 않습니다. 다시 로그인을 시도해주세요"
        var loginFail = "아이디 또는 비밀번호가 맞지 않습니다"
        var sharedPreferences = getSharedPreferences(Util.getSharedPrefName(), Context.MODE_PRIVATE)
        var sharedPreferencesEdit = sharedPreferences.edit()
        var retrofit = Util.getRetrofit()
        var loginService: LoginInterface = retrofit.create(LoginInterface::class.java)
        var possibleLogin = false
        var isJson = true
        var isLogin = false
        var fail =""
        GlobalScope.launch {
            var message = async { loginService.requestLogin(strUserID,strPassword).execute().body() }
            var await = message.await()
            var strJson = await.toString()
            isJson = Util.isJson(strJson) as Boolean
            if(isJson) {
                var jsonObject = Util.getJsonObject(strJson)
                if (jsonObject.get("resultType").toString().contains("LOGIN_SUCCESS")) {
                    possibleLogin = true
                    sharedPreferencesEdit.putString(Util.getIDKey(), strUserID)
                    sharedPreferencesEdit.putString(Util.getUserName(), jsonObject.get("userName").toString())
                    if (sharedPreferences.getBoolean(Util.getAutoLogin(), false)) {
                        sharedPreferencesEdit.putString(Util.getIDKey(), strUserID)
                        sharedPreferencesEdit.putString(Util.getPassword(), strPassword)
                    }
                    sharedPreferencesEdit.commit()
                }
            }
        }
        Thread.sleep(1000)
        if(isJson) {
            if (possibleLogin) {
                startActivity(Intent(this, PatientMainActivity::class.java))
            } else {
                var failMessage = Toast.makeText(this, loginFail, Toast.LENGTH_SHORT)
                failMessage.show()
            }
        }else{
            Toast.makeText(this,"서버 오류입니다. 관리자에게 문의하세요", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            CookieSyncManager.getInstance().startSync();
        }
    }

    override fun onPause() {
        super.onPause()
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            CookieSyncManager.getInstance().stopSync();
        }
    }
}
