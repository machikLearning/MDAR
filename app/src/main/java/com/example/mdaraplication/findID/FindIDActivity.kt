package com.example.mdaraplication.findID

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mdaraplication.R
import com.example.mdaraplication.util.Util
import kotlinx.android.synthetic.main.find_id_activity.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FindIDActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.find_id_activity)
        btn_find_id.setOnClickListener {
            var email = find_id_email.text.toString()
            var retrofit = Util.getRetrofit()
            var findIDInterface = retrofit.create(FindIDInteface::class.java)
            var isFind = false
            var isJson = true
            GlobalScope.launch {
                var sync = async { findIDInterface.findID(email).execute().body() }
                sync.await()
                var message = sync.toString()
                isJson = Util.isJson(message) as Boolean
                if(isJson) {
                    var jsonObject = Util.getJsonObject(message)
                    if (jsonObject.get("resultType").toString().contains("IDSEND")) isFind = true
                }
            }
            Thread.sleep(1000)
            var toast : Toast
            if(!isJson) {
                if (isFind) {
                    toast = Toast.makeText(this, "이메일로 아이디를 전송하였습니다", Toast.LENGTH_SHORT)
                } else {
                    toast = Toast.makeText(this, "해당 이메일로 가입한 내역이 없습니다. 가입해주세요", Toast.LENGTH_SHORT)
                }

            }else{
                toast = Toast.makeText(this,"서버 오류입니다. 관리자에게 문의하세요", Toast.LENGTH_SHORT)
            }
            toast.show()
            this.onBackPressed()
        }
        find_id_cancel.setOnClickListener {
            this.onBackPressed()
        }
    }
}
