package com.example.mdaraplication.survey

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.mdaraplication.R
import kotlinx.android.synthetic.main.survey_main.*

/*MDAR 부작용 Activity*/
class SurveyMainActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.survey_main)
        val webview = survey_main_webview.loadUrl("http://10.0.2.2:8080/patient/statistics")
    }

    override fun onBackPressed() {
        return super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

}