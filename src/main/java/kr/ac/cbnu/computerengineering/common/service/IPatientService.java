package kr.ac.cbnu.computerengineering.common.service;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import kr.ac.cbnu.computerengineering.common.datatype.HospitalDatatype;
import kr.ac.cbnu.computerengineering.common.datatype.PagingDataType;
import kr.ac.cbnu.computerengineering.common.datatype.PrescriptionDataType;

public interface IPatientService {

	JSONObject getLoginUser(String ID, String password) throws Exception;
	List<PrescriptionDataType> getRegistrationAllData(String ID) throws Exception;
	JSONObject getQRCodeResult(List<PrescriptionDataType> prescriptionList, String[] codes) throws Exception;
	PagingDataType makePrescriptionPage(int size, int nowPage);
	JSONObject checkMedicine(String searchOption, String searchValue, List<PrescriptionDataType> prescriptionList) throws Exception;
	JSONObject createUser(String ID, String password, String name, String disable,
			String email, String CBNUCode, String[] roles, int hospitalID, Date date) throws NoSuchAlgorithmException, Exception;
	void logByLoginCheck(String userId) throws Exception;
	void savePatientRequestLog(String id, int value, String[] codes) throws Exception;
	HospitalDatatype detailHospitalByUserID(String iD) throws Exception;
	JSONObject getHospitalList();

}
