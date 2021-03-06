package kr.ac.cbnu.computerengineering.prescription.manager.dao;

import kr.ac.cbnu.computerengineering.common.datatype.RegistrationDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;
import kr.ac.cbnu.computerengineering.common.datatype.UserDataType;
import kr.ac.cbnu.computerengineering.common.managers.dao.IRegistrationDao;
import kr.ac.cbnu.computerengineering.common.mybatis.Mybatis;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.ArrayList;
import java.util.List;

public class RegistrationDaoImpl implements IRegistrationDao {

private SqlSessionFactory sqlSessionFactory;
	
	
	public RegistrationDaoImpl(){
		this.sqlSessionFactory = Mybatis.getSqlSessionFactory();
	}
	
	@Override
	public List<RegistrationDataType> detailPatientList(UserDataType userDataType) throws Exception{
		SqlSession session = this.sqlSessionFactory.openSession();
		List<RegistrationDataType> result = null;
		try {
			result = session.selectList("registration.detailPatientListJoin",userDataType);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
		return result;
	}
	
	@Override
	public UserDataType searchPatient(SearchParam param) throws Exception{
		SqlSession session = this.sqlSessionFactory.openSession();
		UserDataType result = null;
		try {
			result = session.selectOne("registration.searchPatient",param);
				
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
		
		return result;
	}
	
	@Override
	public List<RegistrationDataType> selectRegistrationIdxByPatientId(UserDataType userDataType) throws Exception{
		SqlSession session = this.sqlSessionFactory.openSession();
		List<RegistrationDataType> result = new ArrayList<RegistrationDataType>();
		try {
			result = session.selectList("registration.searchIdxById",userDataType);
				
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
		
		return result;
	}
	
	@Override
	public void insertRegistration(SearchParam param) throws Exception{
		SqlSession session = this.sqlSessionFactory.openSession(true);
		try {
			session.insert("registration.insertRegistration",param);
				
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
		
	}
	
	@Override
	public int selectPatientCounts(SearchParam searchParam) throws Exception{
		SqlSession session = this.sqlSessionFactory.openSession();
		int result = 0;
		try{
			result = session.selectOne("registration.selectPatientCounts",searchParam);
		} catch(Exception e){
			throw new Exception(e.getMessage());
		} finally{
			session.close();
		}
		return result;
	}

	@Override
	public List<RegistrationDataType> selectPatientList(SearchParam param) throws Exception {
		SqlSession session = this.sqlSessionFactory.openSession();
		List<RegistrationDataType> result = null;
		try {
			result = session.selectList("registration.selectPatientList",param);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
		return result;
	}

	@Override
	public int checkPatient(SearchParam param) throws Exception {
		SqlSession session = this.sqlSessionFactory.openSession();
		int result = 0;
		try{
			result = session.selectOne("registration.checkPatient", param);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally{
			session.close();
		}
		return result;
	}

	@Override
	public List<UserDataType> selectSearchUser(SearchParam param) throws Exception {
		SqlSession session = this.sqlSessionFactory.openSession();
		List<UserDataType> result = null;
		try {
			result = session.selectList("registration.selectSearchUser",param);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
		return result;
	}

	@Override
	public int insertRegistration(RegistrationDataType registration) throws Exception {
		SqlSession session = this.sqlSessionFactory.openSession(true);
		int registrationID;
		try {
			session.insert("registration.insertRegistration",registration);
			registrationID = registration.getIdx();
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
		return registrationID;
	}

	@Override
	public void deletePatient(RegistrationDataType registration) throws Exception {
		SqlSession session = this.sqlSessionFactory.openSession(true);
		try {
			session.delete("registration.deletePatient",registration);
				
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public List<RegistrationDataType> selectAllRegistration(String patientID) throws Exception {
		SqlSession session = this.sqlSessionFactory.openSession(true);
		List<RegistrationDataType> result = new ArrayList<>();
		try {
			result =session.selectList("registration.selectAllRegistration", patientID); 
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
		return result;
	}

	@Override
	public SqlSession getSqlSession() {
		return this.sqlSessionFactory.openSession(false);
	}

	@Override
	public void deletePatient(int registrationID, SqlSession sqlSession) throws Exception {
		try {
			sqlSession.delete("registration.deletePatient",registrationID);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public RegistrationDataType selectDoctorIdAndPatientID(SearchParam searchParam) throws Exception {
		SqlSession session = this.sqlSessionFactory.openSession(true);
		RegistrationDataType registrationDataType = new RegistrationDataType();
		try {
			registrationDataType = session.selectOne("registration.selectDoctorIDByPatientID",searchParam);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
		return registrationDataType;
	}
}
