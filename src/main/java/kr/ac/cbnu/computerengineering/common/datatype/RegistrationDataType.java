package kr.ac.cbnu.computerengineering.common.datatype;

import java.util.Date;
import java.util.List;

public class RegistrationDataType {
	private int idx;
	private UserDataType doctor;
	private UserDataType patient;
	private Date registrationDate;
	private List<PrescriptionDataType> prescriptionList;
	
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public UserDataType getDoctor() {
		return doctor;
	}
	public void setDoctor(UserDataType doctor) {
		this.doctor = doctor;
	}
	public UserDataType getPatient() {
		return patient;
	}
	public void setPatient(UserDataType patient) {
		this.patient = patient;
	}
	public List<PrescriptionDataType> getPrescriptionList() {
		return prescriptionList;
	}
	public void setPrescriptionDataType(List<PrescriptionDataType> prescriptionList) {
		this.prescriptionList = prescriptionList;
	}
	
}
