package com.example.mdaraplication.prescription.drugList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.mdaraplication.R
import com.example.mdaraplication.prescription.data.Drug
import java.util.ArrayList

class DrugListAdaptor(val context : Context, val drugList: ArrayList<Drug>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.drug_item_list, null)
        val atcCode = view.findViewById<TextView>(R.id.atc_code)
        val drugName = view.findViewById<TextView>(R.id.drug_name)
        val drug = drugList[position]
        atcCode.text = drug.atcCode
        drugName.text = drug.drugName
        return view
    }

    override fun getItem(position: Int): Any {
        return drugList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0;
    }

    override fun getCount(): Int {
        return drugList.size
    }
}