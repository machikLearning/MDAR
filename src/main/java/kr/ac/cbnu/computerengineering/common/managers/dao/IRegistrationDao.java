package kr.ac.cbnu.computerengineering.common.managers.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.ac.cbnu.computerengineering.common.datatype.RegistrationDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;
import kr.ac.cbnu.computerengineering.common.datatype.UserDataType;

public interface IRegistrationDao{
	
	public List<RegistrationDataType> detailPatientList(UserDataType userDataType) throws Exception;
	public UserDataType searchPatient(SearchParam param) throws Exception;
	public List<RegistrationDataType> selectRegistrationIdxByPatientId(UserDataType userDataType) throws Exception;
	public void insertRegistration(SearchParam param) throws Exception;
	public void deletePatient(int registrationID, SqlSession sqlSession) throws Exception;
	public int checkPatient(SearchParam param) throws Exception;
	public List<UserDataType> selectSearchUser(SearchParam param) throws Exception;
	public int insertRegistration(RegistrationDataType registration) throws Exception;
	public void deletePatient(RegistrationDataType registration) throws Exception;
	public List<RegistrationDataType> selectPatientList(SearchParam param) throws Exception;
	public int selectPatientCounts(SearchParam searchParam) throws Exception;
	public List<RegistrationDataType> selectAllRegistration(String patientID) throws Exception;
	public SqlSession getSqlSession();
	public RegistrationDataType selectDoctorIdAndPatientID(SearchParam searchParam) throws Exception;
}
