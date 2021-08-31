package com.example.mdaraplication.prescription.data

import com.example.mdaraplication.doctorReliever.data.Doctor
import java.io.Serializable

class Prescription(var drugList : ArrayList<Drug>, var doctor : Doctor, var date :String, var validation : Int) : Serializable {
}