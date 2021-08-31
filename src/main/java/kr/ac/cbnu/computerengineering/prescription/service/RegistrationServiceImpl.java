package kr.ac.cbnu.computerengineering.prescription.service;

import kr.ac.cbnu.computerengineering.common.datatype.*;
import kr.ac.cbnu.computerengineering.common.managers.IRegistrationManager;
import kr.ac.cbnu.computerengineering.common.service.IPrescriptionService;
import kr.ac.cbnu.computerengineering.common.service.IRegistrationService;
import kr.ac.cbnu.computerengineering.common.service.IUserService;
import kr.ac.cbnu.computerengineering.common.util.Utils;
import kr.ac.cbnu.computerengineering.prescription.manager.RegistrationManagerImpl;
import kr.ac.cbnu.computerengineering.user.service.UserServiceImpl;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.util.List;

/*
 의사의 기능에 관한 서비스 항목
 RegistrationManager : 의사가 처방중인 환자를 생성, 조회, 수정, 삭제하는 것에 사용
 prescriptionService : 선택된 환자에게 처방전을 생성, 조회, 수정, 삭제하는 것에 사용
 */
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
/*
환자 검사 함수
 */
	@Override
	public List<RegistrationDataType> selectPatientList(SearchParam param) throws Exception {
		return this.registrationManager.selectPatientList(param);
	}
	/*
	이미 등록된 환자인지 검사하는 함수
	 */
	@Override
	public int checkPatient(SearchParam param) throws Exception {		
		return this.registrationManager.checkPatient(param);
	}

	/*
	환자 선택 시 환자의 정보를 갖고오는 함수
	 */
	@Override
	public List<UserDataType> selectSearchUser(SearchParam param, int startRow, int endRow) throws Exception {
		param.setStartRow(startRow);
		param.setEndRow(endRow);
		return this.userService.selectSearchUser(param);
	}
	/*
	환자 등록 함수
	 */
	@Override
	public JSONObject insertRegistration(UserDataType doctor, String patientID) throws ParseException {
		UserDataType patient = Utils.makeUserDataType(patientID, null, null, null, null, null, null, null, 0, null);
		RegistrationDataType registration = Utils.makeRegistrationDataType(-1, doctor, patient);
		registration.setRegistrationDate(Utils.getDate());
		return this.registrationManager.insertRegistration(registration);
	}
	/*
	처방전 작성 함수
	 */

	@Override
	public int insertPrescription(RegistrationDataType registration) throws Exception {
		return this.prescriptionService.insertPrescription(registration);
	}

	/*
	ATC코드로 약물 검색하는 함수
	 */
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
	/*
	처방전 작성 함수
	 */
	@Override
	public void insertProhibitionTolerableUpper(String[] prohibitionCodeArray,
			String[] tolerableCodeArray, String[] upperCodeArray,int prescriptionID, String content, String actionPlan) throws Exception {
		this.prescriptionService.insertProhibitionTolerableUpper(prohibitionCodeArray,tolerableCodeArray,upperCodeArray,prescriptionID,content,actionPlan);
		
	}
	/*
	처방전 내용을 갖고오는 함수
	 */
	@Override
	public PrescriptionDataType selectPrescription(int prescriptionID) throws Exception {
		return this.prescriptionService.selectPrescription(prescriptionID);
	}
	/*
	처방전 내용 중 약제 정보만 삭제하는 함수
	 */
	@Override
	public JSONObject deleteChangeProhibitionUpperTolerable(Integer ID, String medicineKinds) throws Exception {
		return this.prescriptionService.deleteChangeProhibitionUpperTolerable(ID,medicineKinds);
	}
	/*
	처방전 삭제
	 */
	@Override
	public JSONObject deletePrescription(int prescriptionID) throws Exception {
		return this.prescriptionService.deletePrescription(prescriptionID);
	}
	/*
	자신이 등록한 모든 환자를 갖고오는 함수
	 */
	@Override
	public List<RegistrationDataType> selectPatientInfoRequest(String patientID) throws Exception {
		return this.registrationManager.selectAllRegistration(patientID);
	}
	/*
	선택한 환자의 모든 처방전을 갖고오는 함수
	 */
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
	/*
	등록되지 않은 환자의 처방전 정보를 갖고 올때 사용하는 함수, 다른 의사가 작성한 환자의 처방전 리스트가 필요할 때 사용된다
	 */
	@Override
	public RegistrationDataType makeRegistrationDataType(String patientID, String patientName, String CBNUCode,
			Integer registrationID, UserDataType user) {
		UserDataType patient = Utils.makeUserDataType(patientID, null, 
				patientName, null, null, CBNUCode, null, null, 0, null);
		return Utils.makeRegistrationDataType(registrationID,user,patient);
	}

	/*
	환자의 정보를 지우는 함수
	 */
	@Override
	public JSONObject deletePatient(int registrationID) throws Exception {
		List<PrescriptionDataType> prescriptionList = this.prescriptionService.selectMyPrescriptionList(registrationID);
		return this.registrationManager.deletePatient(registrationID,this.prescriptionService.getPrescriptionManager(),prescriptionList);
	}

	/*
	의사가 개인화된 처방전 템플릿을 선택하는 함수
	 */
	@Override
	public List<PrescriptionTemplateDataType> selectPrescriptionTemplateList(SearchParam searchParam) throws Exception {
		return this.prescriptionService.getPrescriptionTemplateDataTypeList(searchParam);
	}

	@Override
	public int getTemplateCount(SearchParam searchParam) throws Exception {
		return this.prescriptionService.countTemplateRow(searchParam);
	}

	/*
	의사가 자기자신을 환자로 설정할 때 사용하는 함수
	 */
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

	/*
	의사가 개인화된 처방전을 작성 후 저장하는 함수
	 */
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
	/*
	처방전 내용 수정
	 */
	@Override
	public void updatePrescription(String[] prohibitionCodeArray, String[] tolerableCodeArray, String[] upperCodeArray,
			int prescriptionID, String content, String actionPlan) {
		this.prescriptionService.updatePrescription(prohibitionCodeArray,tolerableCodeArray,upperCodeArray,prescriptionID,content,actionPlan);
	}
	
}
