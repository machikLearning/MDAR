package kr.ac.cbnu.computerengineering.common.managers;

import java.util.List;

import kr.ac.cbnu.computerengineering.common.datatype.AtcDataType;
import kr.ac.cbnu.computerengineering.common.datatype.MedicineDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;

public interface IMedicineManager {
	
	/**
	 * 검색어에 따른 ATC 데이터의 수를 리턴한다. 검색 옵션별로 구현됨
	 * @param param
	 * @return 
	 * @throws Exception
	 */
	public int countATCRow(SearchParam param) throws Exception;
	
	/**
	 * 검색어에 따른 ATC 데이터 리스트를 리턴한다. 검색 옵션별로 구현됨
	 * @param searchDataType
	 * @return
	 * @throws Exception 
	 */
	public int countMedicineRow(SearchParam param) throws Exception;
	
	public List<MedicineDataType> getMedicineList(SearchParam param) throws Exception;
	
	
	
	
	
	public List<MedicineDataType> selectATCByMedicineName(SearchParam param) throws Exception;
	public MedicineDataType selectMedicineByMedicineCode(SearchParam param) throws Exception;
	public List<MedicineDataType> searchMedicineList(SearchParam param) throws Exception;
	public List<AtcDataType> getATCList(SearchParam searchParam) throws Exception;
	public List<MedicineDataType> getMedicineNoLimitList(SearchParam searchParam) throws Exception;

}
