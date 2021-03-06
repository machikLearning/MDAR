package kr.ac.cbnu.computerengineering.common.datatype;

import java.util.Date;

public class LogDataType {
	private String userID;
	private String ATCCode;
	private Date date;
	private int patientRequestLogNumber;
	
	public LogDataType(){
		
	}
	
	public LogDataType(String userID, String ATCCode, Date date){
		this.userID = userID;
		this.ATCCode = ATCCode;
		this.date = date;
	}
	
	public LogDataType(String userID, String ATCCode, Date date, int patientRequestLogNumber){
		this(userID,ATCCode,date);
		this.patientRequestLogNumber = patientRequestLogNumber;
	}
	

	public String getUserID() {
		return userID;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getATCCode() {
		return ATCCode;
	}

	public void setATCCode(String aTCCode) {
		ATCCode = aTCCode;
	}

	public int getPatientRequestLogNumber() {
		return patientRequestLogNumber;
	}

	public void setPatientRequestLogNumber(int patientRequestLogNumber) {
		this.patientRequestLogNumber = patientRequestLogNumber;
	}
	
	
}
