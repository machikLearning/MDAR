package com.example.mdaraplication.signup

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mdaraplication.R
import com.example.mdaraplication.findID.FindIDActivity
import com.example.mdaraplication.util.Util
import kotlinx.android.synthetic.main.signup.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

class SignUpActivity: AppCompatActivity() {
    private lateinit var userID:String
    private lateinit var pw : String
    private lateinit var pwChecker : String
    private lateinit var email : String
    private lateinit var userName : String
    private var doctorRole : Boolean = false
    private var patientRole : Boolean = false
    private var hospital : Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        var url = "patient/joinCheck"
        setContentView(R.layout.signup)
        hospital_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("병원을 선택하세요")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                hospital = position+1
            }

        }
        initField()
        roles_doctor.setOnCheckedChangeListener { buttonView, isChecked ->
            this.doctorRole = isChecked
        }
        roles_patient.setOnCheckedChangeListener { buttonView, isChecked ->
            this.patientRole = isChecked
        }
        signup_cancel.setOnClickListener {
            this.onBackPressed()
        }
        btn_signup.setOnClickListener {
            if(preProcess()){
                var retrofit = Util.getRetrofit()
                var signupInterface = retrofit.create(SignupInterface::class.java)
                var isSignup = false
                GlobalScope.launch {
                    var message = async { signupInterface.signup(userID,pw,email,"",doctorRole,patientRole,hospital,userName).execute().body() }
                    var await = message.await()
                    var strJson = await.toString()
                    var jsonObject = Util.getJsonObject(strJson)
                    if(jsonObject.get("resultType").toString().contains("JOIN_SUCCESS")) isSignup = true

                }
                Thread.sleep(1000)
                var toast : Toast
                if(isSignup){
                    var toast = Toast.makeText(this,"회원가입이 완료되었습니다",Toast.LENGTH_SHORT)
                    toast.show()
                    this.onBackPressed()
                }else{
                    toast = Toast.makeText(this,"통신 에러입니다",Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
        }
    }

    private fun preProcess(): Boolean {
        initField()
        if(!checkNull()) return false
        if(!passwordCheck()) return false
        return true
    }

    private fun initField() {
        this.userID = signup_userID.text.toString()
        this.pw = signup_password.text.toString()
        this.pwChecker = signup_passwordCheck.text.toString()
        this.userName = signup_name.text.toString()
        this.email = signup_email.text.toString()
        this.userName = signup_name.text.toString()
    }

    private fun passwordCheck(): Boolean {
        if(this.pw == this.pwChecker){
            return true
        }
        var toast = Toast.makeText(this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT)
        toast.show()
        return false
    }

    private fun checkNull(): Boolean {
        var message = ""
        if(this.userID == "") {
            message = "아이디를 입력하세요"
            var toast = Toast.makeText(this,message,Toast.LENGTH_SHORT)
            toast.show()
            return false
        }else if(pw == ""){
            message = "비밀번호를 입력하세요"
            var toast = Toast.makeText(this,message,Toast.LENGTH_SHORT)
            toast.show()

            return false
        }else if(email == ""){
            message = "이메일을 입력하세요"
            var toast = Toast.makeText(this, message,Toast.LENGTH_SHORT)
            toast.show()

            return false
        }else if(userName == ""){
            message = "이름을 입력하세요"
            var toast = Toast.makeText(this,message,Toast.LENGTH_SHORT)
            toast.show()

            return false
        }else if(!this.doctorRole && !this.patientRole){
            message = "유형을 선택하세요"
            var toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
            toast.show()
            return false
        }
        return true
    }
}