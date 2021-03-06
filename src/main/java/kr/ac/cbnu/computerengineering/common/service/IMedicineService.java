package kr.ac.cbnu.computerengineering.common.service;

import java.util.List;

import kr.ac.cbnu.computerengineering.common.datatype.AtcDataType;
import kr.ac.cbnu.computerengineering.common.datatype.MedicineDataType;
import kr.ac.cbnu.computerengineering.common.datatype.PagingDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;

public interface IMedicineService {
	public PagingDataType getATCPagingInfo(SearchParam param, int page) throws Exception; 
	
	public PagingDataType getMedicinePagingInfo(SearchParam param, int page) throws Exception;
	public List<MedicineDataType> getMedicineList(SearchParam param) throws Exception;
	
//	여기서 새로 시작
	public int countRow(SearchParam searchParam) throws Exception;
	public List<AtcDataType> getATCList(SearchParam searchParam) throws Exception;
	public int countMedicineRow(SearchParam searchParam) throws Exception;
	public List<MedicineDataType> getMedicineNoLimitList(SearchParam searchParam) throws Exception;
}
