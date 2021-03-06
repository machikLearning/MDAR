package kr.ac.cbnu.computerengineering.prescription.service;

import java.text.ParseException;
import java.util.List;
import org.json.simple.JSONObject;

import kr.ac.cbnu.computerengineering.common.datatype.AtcDataType;
import kr.ac.cbnu.computerengineering.common.datatype.PagingDataType;
import kr.ac.cbnu.computerengineering.common.datatype.PrescriptionDataType;
import kr.ac.cbnu.computerengineering.common.datatype.PrescriptionTemplateDataType;
import kr.ac.cbnu.computerengineering.common.datatype.RegistrationDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchDataReturnType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;
import kr.ac.cbnu.computerengineering.common.datatype.UserDataType;
import kr.ac.cbnu.computerengineering.common.managers.IRegistrationManager;
import kr.ac.cbnu.computerengineering.common.service.IPrescriptionService;
import kr.ac.cbnu.computerengineering.common.service.IRegistrationService;
import kr.ac.cbnu.computerengineering.common.service.IUserService;
import kr.ac.cbnu.computerengineering.common.util.Utils;
import kr.ac.cbnu.computerengineering.prescription.manager.RegistrationManagerImpl;
import kr.ac.cbnu.computerengineering.user.service.UserServiceImpl;

public class RegistrationServiceImpl implements IRegistrationService {
	
	private IRegistrationManager registrationManager;
	private IPrescriptionService prescriptionService;
	private IUserService userService;
	
	public RegistrationServiceImpl(){
		this.registrationManager = new RegistrationManagerImpl();
		this.prescriptionService = new PrescriptionServiceImpl();
		this.userService = new UserServiceImpl();
	}
	
	public IPrescriptionService getPrescriptionServiceImpl(){
		return this.prescriptionService;
	}
	
	@Override
	public int selectPatientCounts(SearchParam searchParam) throws Exception {
		return this.registrationManager.selectPatientCounts(searchParam);
	}
	@Override
	public List<RegistrationDataType> selectPatientList(SearchParam param) throws Exception {
		return this.registrationManager.selectPatientList(param);
	}
	@Override
	public int checkPatient(SearchParam param) throws Exception {		
		return this.registrationManager.checkPatient(param);
	}
	@Override
	public List<UserDataType> selectSearchUser(SearchParam param, int startRow, int endRow) throws Exception {
		param.setStartRow(startRow);
		param.setEndRow(endRow);
		return this.userService.selectSearchUser(param);
	}
	@Override
	public JSONObject insertRegistration(UserDataType doctor, String patientID) throws ParseException {
		UserDataType patient = Utils.makeUserDataType(patientID, null, null, null, null, null, null, null, 0, null);
		RegistrationDataType registration = Utils.makeRegistrationDataType(-1, doctor, patient);
		registration.setRegistrationDate(Utils.getDate());
		return this.registrationManager.insertRegistration(registration);
	}

	@Override
	public int insertPrescription(RegistrationDataType registration) throws Exception {
		return this.prescriptionService.insertPrescription(registration);
	}
	
	@Override
	public SearchDataReturnType searchATCList(String searchValue, String searchOption, String page) throws Exception {
		SearchParam searchParam = Utils.makeSearchParm(searchValue, null, null, -1, null, null, -1, -1, searchOption, null, -1);
		int count = this.prescriptionService.getMedicineServiceImpl().countRow(searchParam);
		PagingDataType pagingDataType = Utils.computePagingData(count, Utils.convertPageStringToInt(page));
		SearchDataType searchDataType = new SearchDataType().extendParent(pagingDataType);
		searchParam.setStartRow(searchDataType.getStartRow());
		searchParam.setEndRow(searchDataType.getEndRow());
		searchDataType.setSearchValue(searchValue);
		searchDataType.setSearchOption(searchOption);
		List<AtcDataType> atcList = this.prescriptionService.getMedicineServiceImpl().getATCList(searchParam);
		SearchDataReturnType searchDataReturnType = new SearchDataReturnType();
		searchDataReturnType.setSearchDataType(searchDataType);
		searchDataReturnType.setAtcList(atcList);
		return searchDataReturnType;
	}
	
	@Override
	public void insertProhibitionTolerableUpper(String[] prohibitionCodeArray,
			String[] tolerableCodeArray, String[] upperCodeArray,int prescriptionID, String content, String actionPlan) throws Exception {
		this.prescriptionService.insertProhibitionTolerableUpper(prohibitionCodeArray,tolerableCodeArray,upperCodeArray,prescriptionID,content,actionPlan);
		
	}
	@Override
	public PrescriptionDataType selectPrescription(int prescriptionID) throws Exception {
		return this.prescriptionService.selectPrescription(prescriptionID);
	}
	@Override
	public JSONObject deleteChangeProhibitionUpperTolerable(Integer ID, String medicineKinds) throws Exception {
		return this.prescriptionService.deleteChangeProhibitionUpperTolerable(ID,medicineKinds);
	}
	@Override
	public JSONObject deletePrescription(int prescriptionID) throws Exception {
		return this.prescriptionService.deletePrescription(prescriptionID);
	}
	
	@Override
	public List<RegistrationDataType> selectPatientInfoRequest(String patientID) throws Exception {
		return this.registrationManager.selectAllRegistration(patientID);
	}
	@Override
	public List<PrescriptionDataType> selectMyPrescriptionList(int registrationID,
			List<RegistrationDataType> registrationList) {
		for(RegistrationDataType registrationDataType : registrationList){
			if(registrationDataType.getIdx() == registrationID){
				return registrationDataType.getPrescriptionList();
			}
		}
		return null;
	}
	
	@Override
	public RegistrationDataType makeRegistrationDataType(String patientID, String patientName, String CBNUCode,
			Integer registrationID, UserDataType user) {
		UserDataType patient = Utils.makeUserDataType(patientID, null, 
				patientName, null, null, CBNUCode, null, null, 0, null);
		return Utils.makeRegistrationDataType(registrationID,user,patient);
	}
	
	@Override
	public JSONObject deletePatient(int registrationID) throws Exception {
		List<PrescriptionDataType> prescriptionList = this.prescriptionService.selectMyPrescriptionList(registrationID);
		return this.registrationManager.deletePatient(registrationID,this.prescriptionService.getPrescriptionManager(),prescriptionList);
	}
	@Override
	public List<PrescriptionTemplateDataType> selectPrescriptionTemplateList(SearchParam searchParam) throws Exception {
		return this.prescriptionService.getPrescriptionTemplateDataTypeList(searchParam);
	}
	@Override
	public int getTemplateCount(SearchParam searchParam) throws Exception {
		return this.prescriptionService.countTemplateRow(searchParam);
	}
	@Override
	public RegistrationDataType selectMyself(UserDataType userDataType) throws Exception {
		SearchParam searchParam = Utils.makeSearchParm(userDataType.getUserId(), userDataType.getUserId(), null, -1, null, null, -1, -1, null, null, -1);
		RegistrationDataType registrationDataType = this.registrationManager.selectMyself(searchParam);
		if(registrationDataType == null){
			registrationDataType = new RegistrationDataType();
			registrationDataType.setDoctor(userDataType);
			registrationDataType.setPatient(userDataType);
			registrationDataType.setRegistrationDate(Utils.getDate());
			this.registrationManager.insertRegistration(registrationDataType);
		}
		return registrationDataType;
	}
	
	@Override
	public void insertPrescriptionTemplate(int prescriptionID, String templateTitle, String userID) throws Exception {
		PrescriptionTemplateDataType prescriptionTemplateDataType = new PrescriptionTemplateDataType();
		prescriptionTemplateDataType.setPrescriptionID(prescriptionID);
		prescriptionTemplateDataType.setTemplateTitle(templateTitle);
		prescriptionTemplateDataType.setWriter(userID);
		this.prescriptionService.insertPrescriptionTemplatae(prescriptionTemplateDataType);
	}
	@Override
	public PrescriptionTemplateDataType selectPrescriptionTemplateList(int templateID) throws Exception {
		return this.prescriptionService.selectPrescriptionTemplate(templateID);
	}
	@Override
	public JSONObject deletePrescriptionTemplate(int prescriptionID, int templateID) {
		return this.prescriptionService.deleteTemplate(templateID, prescriptionID);
	}

	@Override
	public void updatePrescriptionTemplate(int templateID, String templateTitle) throws Exception {
		this.prescriptionService.updatePrescriptionTemplate(templateID,templateTitle);
	}

	@Override
	public void updatePrescription(String[] prohibitionCodeArray, String[] tolerableCodeArray, String[] upperCodeArray,
			int prescriptionID, String content, String actionPlan) {
		this.prescriptionService.updatePrescription(prohibitionCodeArray,tolerableCodeArray,upperCodeArray,prescriptionID,content,actionPlan);
	}
	
}
