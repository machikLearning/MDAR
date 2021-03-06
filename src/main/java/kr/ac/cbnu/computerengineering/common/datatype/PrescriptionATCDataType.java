package kr.ac.cbnu.computerengineering.common.datatype;

public class PrescriptionATCDataType {
	private String ATCCode;
	private String ATCName;
	private int prescriptionID;
	private int ATCID;
	private String ATCShortName;
	
	public PrescriptionATCDataType(int ATCID, String ATCName, String ATCCode, int prescriptionID, String ATCShortName){
		this.ATCID = ATCID;
		this.ATCName = ATCName;
		this.prescriptionID = prescriptionID;
		this.ATCCode = ATCCode;
		this.ATCShortName = ATCShortName;
	}
	
	public PrescriptionATCDataType(String ATCCode, int prescriptionID){
		this.ATCCode = ATCCode;
		this.prescriptionID = prescriptionID;
	}
	
	public PrescriptionATCDataType(){
		
	}

	public String getATCCode() {
		return this.ATCCode;
	}

	public int getPrescriptionID() {
		return this.prescriptionID;
	}
	
	public String getATCName(){
		return this.ATCName;
	}
	
	public int getATCID(){
		return this.ATCID;
	}

	public String getATCShortName() {
		return ATCShortName;
	}
}
