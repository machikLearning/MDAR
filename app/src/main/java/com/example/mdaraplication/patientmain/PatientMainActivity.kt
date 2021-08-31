package com.example.mdaraplication.patientmain

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.mdaraplication.R
import com.example.mdaraplication.dsereliver.DSEReliverActivity
import com.example.mdaraplication.login.MDARLoginActivity
import com.example.mdaraplication.ocr.OCRMain
import com.example.mdaraplication.ocr.PrescriptionOCRActivity
import com.example.mdaraplication.patientmain.fragment.Covid19Fragment
import com.example.mdaraplication.patientmain.fragment.DrugReliveFragment
import com.example.mdaraplication.patientmain.fragment.PatientCardFragment
import com.example.mdaraplication.prescription.PrescriptionMaker
import com.example.mdaraplication.qrcode.QRCodeActivity
import com.example.mdaraplication.survey.SurveyMainActivity
import com.example.mdaraplication.util.Util
import com.google.android.material.navigation.NavigationView
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.patient_main.*
import kotlinx.android.synthetic.main.tool_bar.*
import kotlinx.android.synthetic.main.tool_bar.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject

class PatientMainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.patient_main)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar!!
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setDisplayHomeAsUpEnabled(true)
        var pref = getSharedPreferences(Util.getSharedPrefName(), Context.MODE_PRIVATE)
        patient_main_username.text = pref.getString(Util.getUserName(),Util.getDefaultUserName())
        toolbar.menu_bar_button.setOnClickListener(View.OnClickListener { patient_main.openDrawer(Gravity.RIGHT) })
        btn_logout.setOnClickListener {
            var logoutInterface = Util.getRetrofit().create(LogoutInterface :: class.java)
            var result : String? = null
            GlobalScope.launch {
                try{
                    var message = async { logoutInterface.logout().execute().body() }
                    result = message.await()
                }catch (e :java.lang.Exception){
                    result = null
                }
            }
            Thread.sleep(1000)
            if(result == null){
                var toast = Toast.makeText(this,"네트워크 에러입니다",Toast.LENGTH_SHORT)
                toast.show()
            }else{
                var jsonObject :JsonObject? = null
                try{
                    jsonObject = Util.getJsonObject(result!!)
                    if(jsonObject.get("resultType").toString().contains("SUCCESS")){
                        var sharedPreferencesEdit = getSharedPreferences(Util.getSharedPrefName(),Context.MODE_PRIVATE).edit()
                        sharedPreferencesEdit.putBoolean(Util.getAutoLogin(),false)
                        startActivity(Intent(this,MDARLoginActivity::class.java))
                    }else{
                        var toast = Toast.makeText(this,"다시 시도하세요",Toast.LENGTH_SHORT)
                        toast.show()
                    }
                }catch (e:Exception){
                    var toast = Toast.makeText(this,"에러입니다",Toast.LENGTH_SHORT)
                    toast.show()
                }
            }

        }
        var fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.add(R.id.function_fragment, PatientCardFragment())
        fragmentManager.commit()
        navigation.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if(patient_main.isDrawerOpen(GravityCompat.START)){
            patient_main.closeDrawers()
        }else super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragmentTransaction = supportFragmentManager.beginTransaction()
        when(item.itemId){
            R.id.drug_relive_fragment ->{
                fragmentTransaction.replace(R.id.function_fragment, DrugReliveFragment())
                fragmentTransaction.commitAllowingStateLoss()
            }
            R.id.mainActivity->{
                fragmentTransaction.replace(R.id.function_fragment, PatientCardFragment())
                fragmentTransaction.commitAllowingStateLoss()
            }
            R.id.qr_code ->{
                try {
                    startActivity(Intent(this, QRCodeActivity::class.java))
                }catch(e:Exception){
                }
            }
            R.id.covid19_fragment ->{
                fragmentTransaction.replace(R.id.function_fragment, Covid19Fragment())
                fragmentTransaction.commitAllowingStateLoss()
            }
            R.id.patient_ocr->{
                startActivity(Intent(this, OCRMain::class.java))
            }
//            R.id.surveyActivity -> startActivity((Intent(this, SurveyMainActivity :: class.java)))
//            R.id.prescriptionMakerActivity -> startActivity(Intent(this, PrescriptionMaker :: class.java))
//            R.id.dseReliverActivity -> startActivity(Intent(this,DSEReliverActivity::class.java))
        }
        patient_main.closeDrawers()
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("activityResult", "OK")
        when(requestCode){
            1-> {
                if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
                    Log.d("activityResult", "OK")
                }
            }
        }
    }
}