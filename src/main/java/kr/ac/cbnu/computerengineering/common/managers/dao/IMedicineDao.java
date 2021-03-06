package kr.ac.cbnu.computerengineering.common.managers.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.ac.cbnu.computerengineering.common.datatype.MedicineDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;

public interface IMedicineDao {
	SqlSession getSqlSession(boolean isAutoCommit);
	public int countRow(SearchParam param) throws Exception;
	public List<MedicineDataType> selectMedicineList(SearchParam param) throws Exception;
	
	
	
	public List<MedicineDataType> selectATCByMedicineName(SearchParam param) throws Exception;
	public MedicineDataType selectMedicineByMedicineCode(SearchParam param) throws Exception;
	public List<MedicineDataType> searchMedicineList(SearchParam param) throws Exception;
	List<MedicineDataType> getMedicineNoLimitList(SearchParam searchParam) throws Exception;
}
