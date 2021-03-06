package kr.ac.cbnu.computerengineering.medicine.manager.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.ac.cbnu.computerengineering.common.datatype.MedicineDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;
import kr.ac.cbnu.computerengineering.common.managers.dao.IMedicineDao;
import kr.ac.cbnu.computerengineering.common.mybatis.Mybatis;

public class MedicineDaoImpl implements IMedicineDao {
	private SqlSessionFactory session;
	
	public MedicineDaoImpl() {
		this.session = Mybatis.getSqlSessionFactory();
	}
	
	@Override
	public SqlSession getSqlSession(boolean isAutoCommit) {
		return this.session.openSession(isAutoCommit);
	}

	@Override
	public int countRow(SearchParam param) throws Exception {
		SqlSession session = this.session.openSession(true);
		int result = 0;
		try{
			result = session.selectOne("medicine.countRow", param);
		} catch(Exception e) {
			throw new Exception("medicine.countRow" + e.getMessage());
		} finally {
			session.close();
		}
		
		return result;
	}

	@Override
	public List<MedicineDataType> selectMedicineList(SearchParam param) throws Exception {
		SqlSession session = this.session.openSession();
		List<MedicineDataType> result = null;
		try {
			result = session.selectList("medicine.selectMedicineList",param);
		} catch(Exception e) {
			throw new Exception("medicine.selectMedicineList" + e.getMessage());
		} finally {
			session.close();
		}
		
		return result;
	}
	
	public List<MedicineDataType> selectATCByMedicineName(SearchParam param) throws Exception{
		SqlSession session = this.session.openSession();
		List<MedicineDataType> result = null;
		try{
			result = session.selectList("medicine.selectAtcByMedicineName", param);
		} catch(Exception e) {
			throw new Exception("medicine.selectAtcByMedicineName" + e.getMessage());
		} finally {
			session.close();
		}
		
		return result;
	}

	@Override
	public MedicineDataType selectMedicineByMedicineCode(SearchParam param) throws Exception {
		SqlSession session = this.session.openSession();
		MedicineDataType result = null;
		try{
			result = session.selectOne("medicine.selectMedicineByMedicineCode", param);
		} catch(Exception e) {
			throw new Exception("medicine.selectMedicineByMedicineCode" + e.getMessage());
		} finally {
			session.close();
		}
		
		return result;
	}

	@Override
	public List<MedicineDataType> searchMedicineList(SearchParam param) throws Exception {
		SqlSession session = this.session.openSession();
		List<MedicineDataType> result = null;
		try {
			result = session.selectList("medicine.selectMedicineList",param);
		}catch(Exception e){
			throw new Exception("medicine.selectMedicineList" + e.getMessage());
		}finally {
			session.close();
		}
		
		return result;
	}

	@Override
	public List<MedicineDataType> getMedicineNoLimitList(SearchParam searchParam) throws Exception {
		SqlSession session = this.session.openSession();
		List<MedicineDataType> result = null;
		try{
			result = session.selectList("medicine.selectMedicineNoLimitList", searchParam);
		} catch(Exception e) {
			throw new Exception("medicine.selectMedicineNoLimitList" + e.getMessage());
		} finally{
			session.close();
		}
		return result;
	}
	
}
