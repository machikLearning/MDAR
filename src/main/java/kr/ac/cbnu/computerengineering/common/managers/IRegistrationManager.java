package kr.ac.cbnu.computerengineering.common.managers;

import java.util.List;

import org.json.simple.JSONObject;

import kr.ac.cbnu.computerengineering.common.datatype.PrescriptionDataType;
import kr.ac.cbnu.computerengineering.common.datatype.RegistrationDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;
import kr.ac.cbnu.computerengineering.common.datatype.UserDataType;

public interface IRegistrationManager{
	
	public List<RegistrationDataType> detailPatientList(UserDataType userDataType) throws Exception;
	public UserDataType searchPatient(SearchParam param) throws Exception;
	public List<RegistrationDataType> selectRegistrationIdxByPatientId(UserDataType userDataType) throws Exception;
	public void insertRegistration(SearchParam param) throws Exception;
	public int checkPatient(SearchParam param) throws Exception;
	public List<UserDataType> selectSearchUser(SearchParam param) throws Exception;
	public JSONObject insertRegistration(RegistrationDataType registration);
	public void deletePatient(RegistrationDataType registration) throws Exception;
	public List<RegistrationDataType> selectPatientList(SearchParam param) throws Exception;
	public int selectPatientCounts(SearchParam searchParam) throws Exception;
	public List<RegistrationDataType> selectAllRegistration(String patientID) throws Exception;
	public JSONObject deletePatient(int registrationID, IPrescriptionManager prescriptionManager, List<PrescriptionDataType> prescriptionList) throws Exception;
	public RegistrationDataType selectMyself(SearchParam searchParam) throws Exception;
}
