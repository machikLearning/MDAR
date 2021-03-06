package kr.ac.cbnu.computerengineering.board.common.datatype;

import java.util.Date;
import java.util.List;

public class BoardDataType {

	private String boardBody;
	private int boardID;
	private List<FileDataType> fileList;
	private List<RepDataType> replyList;
	private List<BoardDataType> childBoardDataTypeList;
	private Date date;
	private String userID;
	private String boardTitle;
	
	public List<BoardDataType> getChildBoardDataTypeList() {
		return childBoardDataTypeList;
	}

	public void setChildBoardDataTypeList(List<BoardDataType> childBoardDataTypeList) {
		this.childBoardDataTypeList = childBoardDataTypeList;
	}

	public int getParentID() {
		return parentID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	private int parentID;
	private int depth;
	public BoardDataType() {
		// TODO Auto-generated constructor stub
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



	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public void setFileList(List<FileDataType> loadFileList) {
		// TODO Auto-generated method stub
		this.fileList = loadFileList;
	}



	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	public String getBoardBody() {
		return boardBody;
	}

	public void setBoardBody(String boardBody) {
		this.boardBody = boardBody;
	}




	public int getBoardID() {
		return boardID;
	}

	public void setBoardID(int boardID) {
		this.boardID = boardID;
	}

	public List<FileDataType> getFileList() {
		return fileList;
	}

}
