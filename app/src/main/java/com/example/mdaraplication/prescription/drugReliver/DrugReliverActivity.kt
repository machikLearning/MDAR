package com.example.mdaraplication.prescription.drugReliver

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.mdaraplication.R
import com.example.mdaraplication.prescription.data.Drug
import com.example.mdaraplication.prescription.drugList.DrugListAdaptor
import com.example.mdaraplication.util.Util
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.drug_reliver.*
import org.json.JSONObject
import retrofit2.Call
import java.util.ArrayList
import retrofit2.Callback
import retrofit2.Response

class DrugReliverActivity : AppCompatActivity() {
    var drugList : ArrayList<Drug> = ArrayList()
    var listAdapter = DrugListAdaptor(this,drugList)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drug_reliver)
        var spinnerList = arrayOf("약제이름", "ATCCode")
        reliver_option.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, spinnerList)
        reliver_option.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("검색옵션을 설정하세요")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

        }

        reliving_list.adapter = listAdapter
        reliving_list.onItemClickListener = object:AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var drug : Drug = listAdapter.getItem(position) as Drug
                var intent = Intent()
                intent.putExtra("drug",drug)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

        var connecting = Util.getRetrofit()
        var relivingService : ReliverInterface = connecting.create(ReliverInterface::class.java)
        reliving_button.setOnClickListener {
            var reliverOption = reliver_option.selectedItem
            var requestObject : JSONObject = JSONObject()
            requestObject.put("searchOption", reliverOption.toString());
            requestObject.put("searchValue",keyword.text.toString())
            requestObject.put("max",20)
            requestObject.put("page",1)
            relivingService.relivingDrug(searchParam = requestObject.toString()).enqueue(object : Callback<String>{
                override fun onFailure(call: Call<String>, t: Throwable) {
                    TODO("Not yet implemented")
                }
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    var jsonArray : JsonArray = Util.getJsonArray(response.body().toString())
                    for(responseObject in jsonArray){
                        var jsonResponse : JsonObject = responseObject.asJsonObject
                        var jsonItemSeq = Util.getJsonObject(jsonResponse.get("itemSeq").toString())
                        var drug : Drug = Drug(jsonItemSeq.get("id").toString().toInt(), " ", jsonResponse.get("itemName").toString())
                        drugList.add(drug)
                    }
                    listAdapter.notifyDataSetChanged()
                }
            })
        }


    }
}