package kr.ac.cbnu.computerengineering.common.datatype;

import java.util.Date;
import java.util.List;

public class PrescriptionDataType {
	private Date date;
	private int prescriptionID;
	private int registrationID;
	private String doctorID;
	private List<PrescriptionATCDataType> prohibitionList;
	private List<PrescriptionATCDataType> tolerableList;
	private List<PrescriptionATCDataType> upperList;
	private ContentActionPlanDataType contentActionPlanDataType;
	
	public PrescriptionDataType(int registrationID,Date date){
		this.registrationID = registrationID;
		this.date = date;
	}
	
	public PrescriptionDataType(List<PrescriptionATCDataType> prohibitionList, List<PrescriptionATCDataType> tolerableList,
			List<PrescriptionATCDataType> upperList, ContentActionPlanDataType contentActionPlanDataType){
		this(prohibitionList,tolerableList,upperList);
		this.contentActionPlanDataType = contentActionPlanDataType;
	}
	
	public PrescriptionDataType(List<PrescriptionATCDataType> prohibitionList, List<PrescriptionATCDataType> tolerableList, List<PrescriptionATCDataType> upperList){
		this.prohibitionList = prohibitionList;
		this.tolerableList = tolerableList;
		this.upperList = upperList;
	}
	
	public PrescriptionDataType(){
		
	}
	
	public int getPrescriptionID(){
		return this.prescriptionID;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setPrescriptionID(int prescriptionID) {
		this.prescriptionID = prescriptionID;
	}
	
	public int getRegistrationID(){
		return this.registrationID;
	}

	public List<PrescriptionATCDataType> getProhibitionList() {
		return prohibitionList;
	}

	public void setProhibitionList(List<PrescriptionATCDataType> prohibitionList) {
		this.prohibitionList = prohibitionList;
	}

	public List<PrescriptionATCDataType> getTolerableList() {
		return tolerableList;
	}

	public void setTolerableList(List<PrescriptionATCDataType> tolerableList) {
		this.tolerableList = tolerableList;
	}

	public List<PrescriptionATCDataType> getUpperList() {
		return upperList;
	}

	public void setUpperList(List<PrescriptionATCDataType> upperList) {
		this.upperList = upperList;
	}

	public ContentActionPlanDataType getContentActionPlanDataType() {
		return contentActionPlanDataType;
	}

	public void setContentActionPlanDataType(ContentActionPlanDataType contentActionPlanDataType) {
		this.contentActionPlanDataType = contentActionPlanDataType;
	}
	
	public void setDoctorID(String doctorID){
		this.doctorID = doctorID;
	}
	
	public String getDoctorID(){
		return this.doctorID;
	}
	
}
