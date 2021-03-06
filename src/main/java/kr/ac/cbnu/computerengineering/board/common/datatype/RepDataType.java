package kr.ac.cbnu.computerengineering.board.common.datatype;

import java.util.Date;
import java.util.List;

public class RepDataType {
	private int replyid;
	private int depth;
	private String userID;
	private List<RepDataType> replyList;
	private Date date;
	private String replyBody;
	private int parentID;
	private int boardID;
	
	public String getReplyBody() {
		return replyBody;
	}

	public int getParentID() {
		return parentID;
	}

	public int getBoardID() {
		return boardID;
	}

	public void setBoardID(int boardID) {
		this.boardID = boardID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
	}

	public void setReplyBody(String replyBody) {
		this.replyBody = replyBody;
	}

	public RepDataType(){
		
	}

	public int getReplyid() {
		return replyid;
	}

	public void setReplyid(int replyid) {
		this.replyid = replyid;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public List<RepDataType> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<RepDataType> replyList) {
		this.replyList = replyList;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
