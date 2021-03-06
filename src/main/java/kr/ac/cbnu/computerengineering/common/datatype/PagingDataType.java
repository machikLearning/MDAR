package kr.ac.cbnu.computerengineering.common.datatype;
/**
 * 뭐하는거야 이건 게시판 페이지인가
 * @author user
 *
 */
public class PagingDataType {
	private int nowPage;
	private int pageGroupSize;
	private int pageGroupCount;
	private int nowPageGroup;
	private int pageCount;
	private int startPage;
	private int endPage;
	private int startRow;
	private int endRow;
	private int count;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
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
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getNowPage() {
		return nowPage;
	}
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}
	public int getPageGroupSize() {
		return pageGroupSize;
	}
	public void setPageGroupSize(int pageGroupSize) {
		this.pageGroupSize = pageGroupSize;
	}
	public int getPageGroupCount() {
		return pageGroupCount;
	}
	public void setPageGroupCount(int pageGroupCount) {
		this.pageGroupCount = pageGroupCount;
	}
	public int getNowPageGroup() {
		return nowPageGroup;
	}
	public void setNowPageGroup(int nowPageGroup) {
		this.nowPageGroup = nowPageGroup;
	}

	
	
}
