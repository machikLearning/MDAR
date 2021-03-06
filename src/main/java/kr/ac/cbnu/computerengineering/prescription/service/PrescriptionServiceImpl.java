package kr.ac.cbnu.computerengineering.prescription.service;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;

import kr.ac.cbnu.computerengineering.common.datatype.ContentActionPlanDataType;
import kr.ac.cbnu.computerengineering.common.datatype.PrescriptionATCDataType;
import kr.ac.cbnu.computerengineering.common.datatype.PrescriptionDataType;
import kr.ac.cbnu.computerengineering.common.datatype.PrescriptionTemplateDataType;
import kr.ac.cbnu.computerengineering.common.datatype.RegistrationDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;
import kr.ac.cbnu.computerengineering.common.managers.IPrescriptionManager;
import kr.ac.cbnu.computerengineering.common.service.IMedicineService;
import kr.ac.cbnu.computerengineering.common.service.IPrescriptionService;
import kr.ac.cbnu.computerengineering.common.util.Utils;
import kr.ac.cbnu.computerengineering.medicine.service.MedicineService;
import kr.ac.cbnu.computerengineering.prescription.manager.PrescriptionManagerImpl;

public class PrescriptionServiceImpl implements IPrescriptionService{
	private IPrescriptionManager prescriptionManagerImpl;
	private IMedicineService medicineServiceImpl;
	
	public PrescriptionServiceImpl(){
		this.prescriptionManagerImpl = new PrescriptionManagerImpl();
		this.medicineServiceImpl = new MedicineService();
	}
	
	@Override
	public IMedicineService getMedicineServiceImpl() {
		return medicineServiceImpl;
	}

	@Override
	public int insertPrescription(RegistrationDataType registration) throws Exception {
		return this.prescriptionManagerImpl.insertPrescription(new PrescriptionDataType(registration.getIdx(),Utils.getDate()));
	}

	@Override
	public void insertProhibitionTolerableUpper( String[] prohibitionCodeArray,
		 String[] tolerableCodeArray, String[] upperCodeArray,int prescriptionID, String content, String actionPlan) throws Exception {
		List<PrescriptionATCDataType> prohibitionCodeList = this.makePrescriptionATCDataTypeList(prohibitionCodeArray,prescriptionID);
		List<PrescriptionATCDataType> tolerableCodeList = this.makePrescriptionATCDataTypeList(tolerableCodeArray, prescriptionID);
		List<PrescriptionATCDataType> upperCodeList = this.makePrescriptionATCDataTypeList(upperCodeArray, prescriptionID);
		ContentActionPlanDataType contentActionPlanDataType = this.makeContentActionPlanDataType(content, actionPlan, prescriptionID);
		this.prescriptionManagerImpl.insertProhibitionTolerableUpper(prohibitionCodeList,tolerableCodeList,upperCodeList,contentActionPlanDataType);
	}

	private ContentActionPlanDataType makeContentActionPlanDataType(String content, String actionPlan,
			int prescriptionID) {
		if((content == null && actionPlan == null)){
			return null;
		}
		return new ContentActionPlanDataType(content, actionPlan, prescriptionID);
	}

	private List<PrescriptionATCDataType> makePrescriptionATCDataTypeList(String[] codeArray, int prescriptionID) {
		List<PrescriptionATCDataType> DBATCList =  null;
		if(codeArray != null){
			DBATCList = new ArrayList<PrescriptionATCDataType>();
			for(String code : codeArray){
				DBATCList.add(new PrescriptionATCDataType(code,prescriptionID));
			}
		}
		return DBATCList;
	}

	@Override
	public PrescriptionDataType selectPrescription(int prescriptionID) throws Exception {
		return this.prescriptionManagerImpl.selectPrescription(prescriptionID);
	}

	@Override
	public JSONObject deleteChangeProhibitionUpperTolerable(Integer ID, String medicineKinds) throws Exception {
		return this.prescriptionManagerImpl.deleteChangeProhibitionUpperToelrable(ID,medicineKinds);
	}

	@Override
	public JSONObject deletePrescription(int prescriptionID) throws Exception {
		return this.prescriptionManagerImpl.deletePrescription(prescriptionID);
	}

	@Override
	public PrescriptionDataType selectPatientInfoRequest(String patientID) {
		return this.prescriptionManagerImpl.selectPatientInfoRequest(patientID);
	}

	@Override
	public List<PrescriptionDataType> selectMyPrescriptionList(int registrationID) throws Exception {
		return this.prescriptionManagerImpl.selectMyPrescription(registrationID);
	}

	@Override
	public IPrescriptionManager getPrescriptionManager() {
		return this.prescriptionManagerImpl;
	}

	@Override
	public int countTemplateRow(SearchParam searchParam) throws Exception {
		return this.prescriptionManagerImpl.countTemplateRow(searchParam);
	}

	@Override
	public List<PrescriptionTemplateDataType> getPrescriptionTemplateDataTypeList(SearchParam searchParam) throws Exception {
		return this.prescriptionManagerImpl.selectPrescriptionTemplateDataTypeList(searchParam);
	}

	@Override
	public void insertPrescriptionTemplatae(PrescriptionTemplateDataType prescriptionTemplateDataType) throws Exception {
		this.prescriptionManagerImpl.insertPrescriptionTemplate(prescriptionTemplateDataType);
	}

	
	@Override
	public JSONObject deleteTemplate(int templateID, int prescriptionID) {
		return this.prescriptionManagerImpl.deleteTemplate(templateID, prescriptionID);
	}

	@Override
	public PrescriptionTemplateDataType selectPrescriptionTemplate(int templateID) throws Exception {
		return this.prescriptionManagerImpl.selectPrescriptionTemplate(templateID);
	}

	@Override
	public void updatePrescriptionTemplate(int templateID, String templateTitle) throws Exception {
		PrescriptionTemplateDataType prescriptionTemplateDataType = new PrescriptionTemplateDataType();
		prescriptionTemplateDataType.setTemplateID(templateID);
		prescriptionTemplateDataType.setTemplateTitle(templateTitle);
		this.prescriptionManagerImpl.updatePrescriptionTemplate(prescriptionTemplateDataType);
	}

	@Override
	public void updatePrescription(String[] prohibitionCodeArray, String[] tolerableCodeArray, String[] upperCodeArray,
			int prescriptionID, String content, String actionPlan) {
		List<PrescriptionATCDataType> prohibitionCodeList = this.makePrescriptionATCDataTypeList(prohibitionCodeArray,prescriptionID);
		List<PrescriptionATCDataType> tolerableCodeList = this.makePrescriptionATCDataTypeList(tolerableCodeArray, prescriptionID);
		List<PrescriptionATCDataType> upperCodeList = this.makePrescriptionATCDataTypeList(upperCodeArray, prescriptionID);
		ContentActionPlanDataType contentActionPlanDataType = this.makeContentActionPlanDataType(content, actionPlan, prescriptionID);
		this.prescriptionManagerImpl.updatePrescription(prohibitionCodeList,tolerableCodeList,upperCodeList,contentActionPlanDataType,prescriptionID);

	}

	
}
