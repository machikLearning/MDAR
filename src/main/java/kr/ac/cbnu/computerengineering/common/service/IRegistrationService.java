package kr.ac.cbnu.computerengineering.common.service;

import java.text.ParseException;
import java.util.List;
import org.json.simple.JSONObject;

import kr.ac.cbnu.computerengineering.common.datatype.PrescriptionDataType;
import kr.ac.cbnu.computerengineering.common.datatype.PrescriptionTemplateDataType;
import kr.ac.cbnu.computerengineering.common.datatype.RegistrationDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchDataReturnType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;
import kr.ac.cbnu.computerengineering.common.datatype.UserDataType;

public interface IRegistrationService {

	int selectPatientCounts(SearchParam searchParam) throws Exception;
	List<RegistrationDataType> selectPatientList(SearchParam param) throws Exception;
	int checkPatient(SearchParam param) throws Exception;
	List<UserDataType> selectSearchUser(SearchParam param, int startRow, int endRow) throws Exception;
	JSONObject insertRegistration(UserDataType doctor, String patientID) throws ParseException;
	int insertPrescription(RegistrationDataType registration) throws Exception;
	SearchDataReturnType searchATCList(String searchValue, String searchOption, String page) throws Exception;
	void insertProhibitionTolerableUpper(String[] prohibitionNameArray, String[] prohibitionCodeArray,
			String[] tolerableNameArray, int prescriptionID, String content, String actionPlan) throws Exception;
	PrescriptionDataType selectPrescription(int prescriptionID) throws Exception;
	JSONObject deleteChangeProhibitionUpperTolerable(Integer ID, String medicineKinds) throws Exception;
	JSONObject deletePrescription(int prescriptionID) throws Exception;
	List<RegistrationDataType> selectPatientInfoRequest(String patientID) throws Exception;
	List<PrescriptionDataType> selectMyPrescriptionList(int registrationID, List<RegistrationDataType> registrationList);
	RegistrationDataType makeRegistrationDataType(String patientID, String patientName, String CBNUCode,
			Integer registrationID, UserDataType user);
	JSONObject deletePatient(int registrationID) throws Exception;
	int getTemplateCount(SearchParam searchParam) throws Exception;
	List<PrescriptionTemplateDataType> selectPrescriptionTemplateList(SearchParam searchParam) throws Exception;
	RegistrationDataType selectMyself(UserDataType userDataType) throws ParseException, Exception;
	void insertPrescriptionTemplate(int prescriptionID, String templateTitle, String userID) throws Exception;
	PrescriptionTemplateDataType selectPrescriptionTemplateList(int templateID) throws Exception;
	JSONObject deletePrescriptionTemplate(int prescriptionID, int templateID);
	void updatePrescriptionTemplate(int templateID, String templateTitle) throws Exception;
	void updatePrescription(String[] prohibitionCodeArray, String[] tolerableCodeArray, String[] upperCodeArray,
			int prescriptionID, String content, String actionPlan);

}
