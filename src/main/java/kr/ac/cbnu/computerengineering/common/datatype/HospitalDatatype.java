package kr.ac.cbnu.computerengineering.common.datatype;

public class HospitalDatatype {
	private int id;
	private String name;
	private String prefixCode;
	private String contactName1;
	private String contactTel1;
	private String contactName2;
	private String contactTel2;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrefixCode() {
		return prefixCode;
	}
	public void setPrefixCode(String prefixCode) {
		this.prefixCode = prefixCode.toUpperCase();
	}
	public String getContactName1() {
		return contactName1;
	}
	public void setContactName1(String contactName1) {
		this.contactName1 = contactName1;
	}
	public String getContactTel1() {
		return contactTel1;
	}
	public void setContactTel1(String contactTel1) {
		this.contactTel1 = contactTel1;
	}
	public String getContactName2() {
		return contactName2;
	}
	public void setContactName2(String contactName2) {
		this.contactName2 = contactName2;
	}
	public String getContactTel2() {
		return contactTel2;
	}
	public void setContactTel2(String contactTel2) {
		this.contactTel2 = contactTel2;
	}
	
	
}
