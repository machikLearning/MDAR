package kr.ac.cbnu.computerengineering.common.managers.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;

import kr.ac.cbnu.computerengineering.common.datatype.AtcDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;

public interface IATCDao {
	SqlSession getSqlSession(boolean isAutoCommit);
	public int countRow(SearchParam param) throws Exception;
	public List<AtcDataType> selectATCList(SearchParam searchParam) throws Exception;
}
