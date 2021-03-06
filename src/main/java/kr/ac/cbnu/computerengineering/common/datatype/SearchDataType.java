package kr.ac.cbnu.computerengineering.common.datatype;

public class SearchDataType extends PagingDataType {
	String searchValue;
	String searchOption;
	
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	public String getSearchOption() {
		return searchOption;
	}
	public void setSearchOption(String searchOption) {
		this.searchOption = searchOption;
	}
	public SearchDataType extendParent(PagingDataType pagingDataType) {
		this.setNowPage(pagingDataType.getNowPage());
		this.setPageGroupCount(pagingDataType.getPageGroupCount());
		this.setNowPageGroup(pagingDataType.getNowPageGroup());
		this.setPageCount(pagingDataType.getPageCount());
		this.setStartPage(pagingDataType.getStartPage());
		this.setEndPage(pagingDataType.getEndPage());
		this.setPageGroupSize(pagingDataType.getPageGroupSize());
		this.setStartRow(pagingDataType.getStartRow());
		this.setEndRow(pagingDataType.getEndRow());
		this.setCount(pagingDataType.getCount());
		return this;
	}
}
