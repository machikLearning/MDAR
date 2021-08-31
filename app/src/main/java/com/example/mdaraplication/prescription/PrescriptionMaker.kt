package com.example.mdaraplication.prescription

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mdaraplication.R
import com.example.mdaraplication.doctorReliever.DoctorReliever
import com.example.mdaraplication.doctorReliever.data.Doctor
import com.example.mdaraplication.prescription.data.Drug
import com.example.mdaraplication.prescription.data.Prescription
import com.example.mdaraplication.prescription.drugList.DrugListAdaptor
import com.example.mdaraplication.prescription.drugReliver.DrugReliverActivity
import com.example.mdaraplication.util.Util
import com.google.gson.Gson
import kotlinx.android.synthetic.main.prescription_maker.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList
/*MDAR 처방전 작성 Activity*/
class PrescriptionMaker : AppCompatActivity() {
    var drugList : ArrayList<Drug> = ArrayList()
    val drugListAdaptor = DrugListAdaptor(this, drugList)
    private lateinit var doctor : Doctor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prescription_maker)
        drug_list.adapter = drugListAdaptor
        drug_adder.setOnClickListener {
            startActivityForResult(Intent(this, DrugReliverActivity::class.java), 1);
        }

        doctor_reliever_button.setOnClickListener {
            startActivityForResult(Intent(this,DoctorReliever::class.java),1)
        }

        insert_prescription_button.setOnClickListener {
            var prescription = Prescription(drugList,doctor,date.text.toString(),validity.text.toString().toInt())
            var strJson = Gson().toJson(prescription)
            var prescriptionMakerService = Util.getRetrofit().create(PrescriptionMakerInterface :: class.java)
            prescriptionMakerService.insertPrescription(prescription = strJson).enqueue(object : Callback<String>{
                override fun onFailure(call: Call<String>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                if(data?.extras?.containsKey("drug")!!) {
                    drugList.add(data?.extras?.get("drug") as Drug)
                    drugListAdaptor.notifyDataSetChanged()
                }else{
                    doctor = data?.extras?.get("doctor") as Doctor
                    selected_doctor.text = doctor.doctorName
                }
            }
        }
    }
}