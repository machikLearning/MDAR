package kr.ac.cbnu.computerengineering.common.datatype;

import java.util.List;

public class SearchDataReturnType<T>{
	private List<AtcDataType> atcList;
	private SearchDataType searchDataType;
	private List<T> templateList;
	
	public SearchDataReturnType(){
		
	}
	
	public List<AtcDataType> getAtcList(){
		return this.atcList;
	}
	
	public SearchDataType getSearchDataType(){
		return this.searchDataType;
	}
	
	public void setAtcList(List<AtcDataType> atcList){
		this.atcList = atcList;
	}
	
	public void setSearchDataType(SearchDataType searchDataType){
		this.searchDataType = searchDataType;
	}

	public List<T> getTemplateList() {
		return templateList;
	}

	public void setTemplateList(List<T> templateList) {
		this.templateList = templateList;
	}


	
}

