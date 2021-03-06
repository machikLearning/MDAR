package kr.ac.cbnu.computerengineering.common.datatype;

import java.util.List;

public class MaterialDatatype {
	private int idx;
	private String code;
	private String korName;
	private String engName;
	private String engShortName;
	private List<AtcDataType> atcs;
	
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
	public String getKorName() {
		return korName;
	}
	public void setKorName(String korName) {
		this.korName = korName;
	}
	public String getEngName() {
		return engName;
	}
	public void setEngName(String engName) {
		this.engName = engName;
	}
	public String getEngShortName() {
		if(this.engShortName == null || this.engShortName.equals("")) return this.engName;
		return engShortName;
	}
	public void setEngShortName(String engShortName) {
		this.engShortName = engShortName;
	}
	public List<AtcDataType> getAtcs() {
		return atcs;
	}
	public void setAtcs(List<AtcDataType> atcs) {
		this.atcs = atcs;
	}
	
	
}
