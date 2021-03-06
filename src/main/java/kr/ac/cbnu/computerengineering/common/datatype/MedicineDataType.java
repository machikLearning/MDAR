package kr.ac.cbnu.computerengineering.common.datatype;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MedicineDataType {
	private int idx;								
	private String name;							
	private String itemCode;						
	private String barcode;							
	private String standardCode;					
	private String code;							
	private String ingredientCode;					
	private AtcDataType ATCCode;
	private IngredientDatatype ingredient;
	public IngredientDatatype getIngredient() {
		return ingredient;
	}
	public void setIngredient(IngredientDatatype ingredient) {
		this.ingredient = ingredient;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getName() {
		String result = name.replace("?", "");
		if(result.indexOf("(") == -1)
			return result;
		else
			return result.substring(0, result.indexOf("("));
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getStandardCode() {
		return standardCode;
	}
	public void setStandardCode(String standardCode) {
		this.standardCode = standardCode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getIngredientCode() {
		return ingredientCode;
	}
	public void setIngredientCode(String ingredientCode) {
		this.ingredientCode = ingredientCode;
	}
	public AtcDataType getATCCode() {
		return ATCCode;
	}
	public void setATCCode(AtcDataType aTCCode) {
		ATCCode = aTCCode;
	}
	public String getIngredientSubCode() {
		return this.ingredientCode.substring(0, 6);
	}
	public String getSubName() {
		String tmp = this.name.replace("?", "");
		Pattern pattern = Pattern.compile("\\d");
		Matcher matcher = pattern.matcher(tmp);
		int numIdx = 0;
		if(matcher.find())
			numIdx = matcher.start();
		int bracketIdx = tmp.indexOf("(");
		if(bracketIdx == -1) {
			if(numIdx == 0) {
				return tmp;
			} else {
				return tmp.substring(0, numIdx);
			}
		} else {
			if(numIdx == 0) {
				return tmp.substring(0, bracketIdx);
			} else {
				if(bracketIdx > numIdx)
					return tmp.substring(0, numIdx);
				else
					return tmp.substring(0, bracketIdx);
			}
		}
	}
}
