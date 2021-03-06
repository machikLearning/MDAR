package kr.ac.cbnu.computerengineering.board.common.datatype;

public class FileDataType {
	private String uploadFolder;
	private String saveName;
	private String nickName;
	private int boardID;
	private long length;

	public FileDataType(String uploadFolder, String saveName, long length, String nickName) {
		this.uploadFolder = uploadFolder;
		this.saveName = saveName;
		this.length = length;
		this.nickName =nickName;
	}
	
	public FileDataType(){
		
	}

	public String getUploadFolder() {
		return uploadFolder;
	}

	public void setUploadFolder(String uploadFolder) {
		this.uploadFolder = uploadFolder;
	}

	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getBoardID() {
		return boardID;
	}

	public void setBoardID(int boardID) {
		this.boardID = boardID;
	}
	
	

}
