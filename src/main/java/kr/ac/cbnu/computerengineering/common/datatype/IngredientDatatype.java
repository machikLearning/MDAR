package kr.ac.cbnu.computerengineering.common.datatype;

import java.util.List;

public class IngredientDatatype {
	private int idx;
	private String code;
	private List<MaterialDatatype> materials;
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
	public List<MaterialDatatype> getMaterials() {
		return materials;
	}
	public void setMaterials(List<MaterialDatatype> materials) {
		this.materials = materials;
	}
	
	
}
