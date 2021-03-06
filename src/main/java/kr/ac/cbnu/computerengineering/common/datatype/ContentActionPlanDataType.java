package kr.ac.cbnu.computerengineering.common.datatype;

public class ContentActionPlanDataType {
	private String content;
	private String actionPlan;
	private int prescriptionID;
	
	public ContentActionPlanDataType(String content, String actionPlan, int prescriptionID){
		this.content = content;
		this.actionPlan = actionPlan;
		this.prescriptionID = prescriptionID;
	}
	
	public ContentActionPlanDataType(){
		
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getActionPlan() {
		return actionPlan;
	}

	public void setActionPlan(String actionPlan) {
		this.actionPlan = actionPlan;
	}

	public int getPrescriptionID() {
		return prescriptionID;
	}

	public void setPrescriptionID(int prescriptionID) {
		this.prescriptionID = prescriptionID;
	}
	
	public String getSimpleActionPlan() {
		return this.actionPlan.replace("\r", "").replace("\n", "<br>");
	}
}
