package kr.ac.cbnu.computerengineering.common.datatype;

import java.util.Date;
import java.util.List;

public class UserDataType {
	private String userId;
	private String password;
	private String name;
	private String disable;
	private List<UserRoleDataType> roles;
	private String cbnuCode;
	private Date date;
	private ApprovalType approval;
	private String email;
	private HospitalDatatype hospital;
	private int isLeaved;
	
	
	public boolean isNew() {
		Date today = new Date();
		long diff = today.getTime() - this.date.getTime();
	    long diffDays = diff / (24 * 60 * 60 * 1000);
	    if(diffDays < 1) return true;
		return false;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDisable() {
		return disable;
	}

	public void setDisable(String disable) {
		this.disable = disable;
	}

	public List<UserRoleDataType> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRoleDataType> roles) {
		this.roles = roles;
	}

	public String getCbnuCode() {
		return cbnuCode;
	}

	public void setCbnuCode(String cbnuCode) {
		this.cbnuCode = cbnuCode;
	}

	public int getIsLeaved() {
		return isLeaved;
	}

	public void setIsLeaved(int isLeaved) {
		this.isLeaved = isLeaved;
	}

	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ApprovalType getApproval() {
		return approval;
	}

	public void setApproval(ApprovalType approval) {
		this.approval = approval;
	}

	public HospitalDatatype getHospital() {
		return hospital;
	}

	public void setHospital(HospitalDatatype hospital) {
		this.hospital = hospital;
	}
	
	
}
