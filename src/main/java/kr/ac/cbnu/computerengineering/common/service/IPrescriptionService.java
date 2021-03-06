package kr.ac.cbnu.computerengineering.common.service;

import java.text.ParseException;
import java.util.List;
import org.json.simple.JSONObject;

import kr.ac.cbnu.computerengineering.common.datatype.PrescriptionDataType;
import kr.ac.cbnu.computerengineering.common.datatype.PrescriptionTemplateDataType;
import kr.ac.cbnu.computerengineering.common.datatype.RegistrationDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;
import kr.ac.cbnu.computerengineering.common.managers.IPrescriptionManager;

public interface IPrescriptionService {
	
	public IMedicineService getMedicineServiceImpl();

	int insertPrescription(RegistrationDataType registration) throws ParseException, Exception;

	void insertProhibitionTolerableUpper(String[] prohibitionNameArray, String[] prohibitionCodeArray,
			String[] tolerableNameArray, int prescriptionID, String content, String actionPlan) throws Exception;

	PrescriptionDataType selectPrescription(int prescriptionID) throws Exception;

	JSONObject deleteChangeProhibitionUpperTolerable(Integer iD, String medicineKinds) throws Exception;

	JSONObject deletePrescription(int prescriptionID) throws Exception;

	PrescriptionDataType selectPatientInfoRequest(String patientID);

	List<PrescriptionDataType> selectMyPrescriptionList(int registrationID) throws Exception;

	public IPrescriptionManager getPrescriptionManager();

	int countTemplateRow(SearchParam searchParam) throws Exception;

	List<PrescriptionTemplateDataType> getPrescriptionTemplateDataTypeList(SearchParam searchParam) throws Exception;

	void insertPrescriptionTemplatae(PrescriptionTemplateDataType prescriptionTemplateDataType) throws Exception;

	JSONObject deleteTemplate(int templateID, int prescriptionID);

	public PrescriptionTemplateDataType selectPrescriptionTemplate(int templateID) throws Exception;

	public void updatePrescriptionTemplate(int templateID, String templateTitle) throws Exception;

	public void updatePrescription(String[] prohibitionCodeArray, String[] tolerableCodeArray, String[] upperCodeArray,
			int prescriptionID, String content, String actionPlan);

}
