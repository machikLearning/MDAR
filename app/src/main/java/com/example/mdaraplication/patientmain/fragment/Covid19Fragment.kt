package com.example.mdaraplication.patientmain.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mdaraplication.R
import com.example.mdaraplication.util.Util
import kotlinx.android.synthetic.main.covid19_fragment.*
import kotlinx.android.synthetic.main.covid19_fragment.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.create

class Covid19Fragment: MyWebViewFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.covid19_fragment,container,false)
        view.covid19_yes.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setMessage("약물 알레르기 알리미 앱에는 PEG 또는 폴리소네이트 첨가물 부작용 정보가 등록되어 있지 않습니다. 앱을 설치해준 알레르기 내과 전문의에게 첨가물 과민반응 여부를 확인하세요")
            alertDialog.show()
        }
        var covid19Interface = Util.getRetrofit().create(Covid19Interface::class.java)
        var result : String? = ""
        GlobalScope.launch {
            try {
                var message = async { covid19Interface.covid19().execute().body() }
                result = message.await()
                var isJson = Util.isJson(result.toString()) as Boolean
                if (isJson) {
                    var jsonObject = Util.getJsonObject(result.toString())
                    result = jsonObject.get("result").toString()
                }
            }catch (e : java.lang.Exception){
                result = null
            }
        }
        Thread.sleep(1000)
        var message = ""
        if(result == null){
            message = "통신 오류입니다"
        }else if(result.toString().isEmpty()) {
            message = "약물 알레르기 알리미 앱에 등록된 부작용이 없습니다. 아스트라제네카, 화이자, 모더나, 얀센 백신 모두 접종 가능합니다."
        }else{
            message = "약물 알레르기 알리미 앱에 등록된 약물부작용은 \n \n"+ result + "\n" +
                    " \n" +
                    " 으로 \n" +
                    " \n" +
                    " 아스트라제네카, 화이자, 모더나, 얀센 백신 모두 접종 가능합니다."
            if(message.contains("<br>")){
                message = message.replace("<br>","")
            }
        }
        Thread.sleep(1000)
        view.covid19_no.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setMessage(message)
            alertDialog.show()
        }
        return view
    }
}