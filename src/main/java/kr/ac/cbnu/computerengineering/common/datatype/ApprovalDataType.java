package kr.ac.cbnu.computerengineering.common.datatype;

import java.util.Date;

public class ApprovalDataType {
	private int idx;
	private String userId;
	private ApprovalType approval;
	private Date date;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public ApprovalType getApproval() {
		return approval;
	}
	
	public void setApproval(ApprovalType approval){
		this.approval = approval;
	}
	
}
