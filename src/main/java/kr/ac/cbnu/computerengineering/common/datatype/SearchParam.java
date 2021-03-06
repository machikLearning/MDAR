package kr.ac.cbnu.computerengineering.common.datatype;

import java.util.Date;

public class SearchParam {
	private String param;
	private String id;
	private String cbnuCode;
	private int hospitalIdx;
	public int getHospitalIdx() {
		return hospitalIdx;
	}

	public void setHospitalIdx(int hospitalIdx) {
		this.hospitalIdx = hospitalIdx;
	}

	private int idx;
	private String medicineCode;
	private String atcFirst;
	private int startRow;
	private int endRow;
	private String searchOption;
	private Date date;
	private int no;
	
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public String getAtcFirst() {
		return atcFirst;
	}

	public void setAtcFirst(String atcFirst) {
		this.atcFirst = atcFirst;
	}

	public String getMedicineCode() {
		return medicineCode;
	}

	public void setMedicineCode(String medicineCode) {
		this.medicineCode = medicineCode;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getCbnuCode() {
		return cbnuCode;
	}

	public void setCbnuCode(String cbnuCode) {
		this.cbnuCode = cbnuCode;
	}

	public String getParam() {
		return param;
	}
	
	public String getId() {
		return id;
	}

	public void setParam(String param) {
		this.param = param;
	}
	
	public void setId(String id){
		this.id = id;
	}

	public String getSearchOption() {
		return searchOption;
	}

	public void setSearchOption(String searchOption) {
		this.searchOption = searchOption;
	}
	
}
