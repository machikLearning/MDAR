package kr.ac.cbnu.computerengineering.common.datatype;

public class AtcDataType {
	private int idx;								// DB PK
	private String code;							// ATC Code
	private String levelName;						// ATC level name
	private String shortLevelName;					// ATC 다른 이름
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLevelName() {
		if(this.shortLevelName == null || this.shortLevelName.equals("")) return this.levelName;
		return this.shortLevelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getShortLevelName() {
		return shortLevelName;
	}
	public void setShortLevelName(String shortLevelName) {
		this.shortLevelName = shortLevelName;
	}
	
	
}
