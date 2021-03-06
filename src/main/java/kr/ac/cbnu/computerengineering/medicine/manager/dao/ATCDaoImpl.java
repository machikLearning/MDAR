package kr.ac.cbnu.computerengineering.medicine.manager.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.ac.cbnu.computerengineering.common.datatype.AtcDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;
import kr.ac.cbnu.computerengineering.common.managers.dao.IATCDao;
import kr.ac.cbnu.computerengineering.common.mybatis.Mybatis;

public class ATCDaoImpl implements IATCDao {
private SqlSessionFactory session;
	
	public ATCDaoImpl() {
		this.session = Mybatis.getSqlSessionFactory();
	}
	
	@Override
	public SqlSession getSqlSession(boolean isAutoCommit) {
		return this.session.openSession(isAutoCommit);
	}

	@Override
	public int countRow(SearchParam param) throws Exception{
		SqlSession session = this.session.openSession(true);
		int result = 0;
		try{
			result = session.selectOne("atc.countRow", param);
		} catch(Exception e) {
			throw new Exception("atc.countRow" + e.getMessage());
		} finally {
			session.close();
		}
		
		return result;
	}

	@Override
	public List<AtcDataType> selectATCList(SearchParam searchParam) throws Exception {
		SqlSession session = this.session.openSession();
		List<AtcDataType> result = null;
		try{
			result = session.selectList("atc.selectATCList", searchParam);
		} catch(Exception e) {
			throw new Exception("atc.selectATCList" + e.getMessage());
			
		} finally {
			session.close();
		}
		
		return result;
	}

}
