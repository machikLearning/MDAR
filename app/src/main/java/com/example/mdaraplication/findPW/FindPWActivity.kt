package com.example.mdaraplication.findPW

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mdaraplication.R
import com.example.mdaraplication.util.Util
import kotlinx.android.synthetic.main.find_pw_activity.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FindPWActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.find_pw_activity)
        btn_find_pw.setOnClickListener {
            var id = find_pw_id.text.toString()
            var retrofit = Util.getRetrofit()
            var findIDInterface = retrofit.create(FindPWInterface::class.java)
            var isFind = false
            var isJson = true
            GlobalScope.launch {
                var sync = async { findIDInterface.findPW(id).execute().body() }
                sync.await()
                var message = sync.toString()
                isJson = Util.isJson(message) as Boolean
                if(isJson) {
                    var jsonObject = Util.getJsonObject(message)
                    if (jsonObject.get("resultType").toString().contains("PWSEND")) isFind = true
                }
            }
            Thread.sleep(1000)
            var toast : Toast
            if(isJson) {
                if (isFind) {
                    toast = Toast.makeText(this, "이메일로 임시 비밀번호를 전송하였습니다", Toast.LENGTH_SHORT)
                } else {
                    toast = Toast.makeText(this, "해당 아이디로 가입한 내역이 없습니다. 가입해주세요", Toast.LENGTH_SHORT)
                }
            }else{
                toast = Toast.makeText(this,"서버 오류입니다. 관리자에게 문의하세요", Toast.LENGTH_SHORT)
            }
            toast.show()
            this.onBackPressed()
        }
        find_pw_cancel.setOnClickListener {
            this.onBackPressed()
        }
    }
}
