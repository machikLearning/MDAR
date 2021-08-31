package com.example.mdaraplication.doctorReliever

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mdaraplication.R
import com.example.mdaraplication.doctorReliever.data.Doctor
import com.example.mdaraplication.util.Util
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.doctor_reliver.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*MDAR Main*/
class DoctorReliever : AppCompatActivity(){
    var doctorList : ArrayList<Doctor> = ArrayList()
    var doctorAdapter = DoctorListAdapter(this,doctorList)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.doctor_reliver)
        doctor_list.adapter = doctorAdapter
        doctor_list.onItemClickListener = object: AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var doctor :Doctor = doctor_list.adapter.getItem(position) as Doctor
                var intent = Intent()
                intent.putExtra("doctor",doctor)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

        var connecting = Util.getRetrofit()
        var doctorRelieverService = connecting.create(DoctorRelieverInterface::class.java)
        doctorRelieverService.relivingDrug().enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                var jsonList : JsonArray = Util.getJsonArray(response.body().toString())
                for(jsonObject in jsonList){
                    var jsonclinic : JsonObject = jsonObject.asJsonObject
                    var jsonDoctor : JsonObject = Util.getJsonObject(jsonclinic.get("doctor").toString())
                    doctorList.add(Doctor(jsonDoctor.get("userID").toString(),jsonclinic.get("clinicDate").toString(),jsonclinic.get("id").toString().toInt()))
                }
                doctorAdapter.notifyDataSetChanged()
            }
        })
    }

    inner class DoctorListAdapter(val context : Context, val doctorList : ArrayList<Doctor>) : BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = LayoutInflater.from(context).inflate(R.layout.doctor_list, null)
            val doctorName = view.findViewById<TextView>(R.id.doctor_name)
            val clinicDate = view.findViewById<TextView>(R.id.clinic_date)
            val doctor = doctorList[position]
            doctorName.text = doctor.doctorName
            clinicDate.text = doctor.clinicDate
            return view
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getItem(position: Int): Any {
            return doctorList[position]
        }

        override fun getCount(): Int {
            return doctorList.size
        }

    }
}