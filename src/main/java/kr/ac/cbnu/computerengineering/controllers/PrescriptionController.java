package kr.ac.cbnu.computerengineering.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import kr.ac.cbnu.computerengineering.common.datatype.PagingDataType;
import kr.ac.cbnu.computerengineering.common.datatype.PrescriptionDataType;
import kr.ac.cbnu.computerengineering.common.datatype.PrescriptionTemplateDataType;
import kr.ac.cbnu.computerengineering.common.datatype.RegistrationDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchDataReturnType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;
import kr.ac.cbnu.computerengineering.common.datatype.UserDataType;
import kr.ac.cbnu.computerengineering.common.service.IRegistrationService;
import kr.ac.cbnu.computerengineering.common.util.Utils;
import kr.ac.cbnu.computerengineering.prescription.service.RegistrationServiceImpl;


/**
 * Servlet implementation class DoctorController
 */



@WebServlet("/prescription/*")
public class PrescriptionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(UserController.class);
	private IRegistrationService registrationSerivece;
    
    
	public PrescriptionController() {
        super();
        this.registrationSerivece = new RegistrationServiceImpl();
    }
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		try {
			this.checkURL(request, response);
		} catch (Exception e) {
			String errorMessage = "";
			int cnt = 0;
			for(StackTraceElement element : e.getStackTrace()) {
				errorMessage += element.getClassName() +": " + element.getMethodName() +":" + element.getLineNumber()+"\n";
				if(++cnt > 5) {
					errorMessage += "...\n";
					break;
				}
			}
			this.logger.error(e.getMessage()+errorMessage);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/common/error.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void checkURL(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url = request.getRequestURI();
		url = url.replace("/ADRM/prescription/", "");
		if(url.equals("main")) {
			this.main(request, response);
		}  else if(url.equals("searchPatientResult")) {
			this.searchPatientResultRequest(request, response);
		}  else if(url.equals("createRegistration")) {
			this.createRegistrationRequest(request, response);
		}  else if(url.equals("deletePatient")) {
			this.deletePatientRequest(request, response);
		}  else if(url.equals("selectPatientInfo")) {
			this.patientInfoRequest(request, response);
		} else if(url.equals("createPrescription")){
			this.createPrescriptionRequest(request, response);
		} else if(url.equals("searchProhibitionResult")) {
			this.searchProhibitionResultRequest(request, response);
		} else if(url.equals("searchUpperResult")) {
			this.searchUpperResultRequest(request, response);
		} else if(url.equals("searchTolerableResult")) {
			this.searchTolerableResultRequest(request, response);
		} else if(url.equals("prescriptionFinish")) {
			this.prescriptionFinishRequest(request, response);
		} else if(url.equals("viewPrescription")) {
			this.viewPrescriptionRequest(request, response);
		} else if(url.equals("changePrescription")) {
			this.changePrescriptionRequest(request, response);
		} else if(url.equals("deleteChangeProhibitionTolerableUpper")){
			this.deletedeleteChangeProhibitionTolerableUpperRequest(request, response);
		} else if(url.equals("deletePrescription")) {
			this.deletePrescriptionRequest(request, response);
		} else if(url.equals("templateMain")){
			this.templateMain(request, response);
		} else if(url.equals("writeTemplate")){
			this.writeTemplate(request, response);
		} else if(url.equals("insertPrescriptionTemplate")){
			this.insertPrescriptionTemplate(request,response);
		} else if(url.equals("useTemplate")){
			this.useTemplate(request, response);
		} else if(url.equals("searchRegistration")){
			this.searchRegistration(request, response);
		} else if(url.equals("saveRegistrationInSession")){
			this.saveRegistrationInSession(request,response);
		} else if(url.equals("deleteTemplate")){
			this.deletePrescriptionTemplate(request, response);
		} else if(url.equals("updatePrescriptionTemplate")){
			this.updatePrescriptionTemplate(request, response);
		} else if(url.equals("changePrescriptionTemplate")){
			this.changePrescriptionTemplate(request,response);
		} else if(url.equals("searchPrescriptionTemplateMe")){
			this.searchPrescriptionTemplateMe(request, response);
		} else if(url.equals("searchPrescriptionTemplateAdmin")){
			this.searchPrescriptionTemplateAdmin(request, response);
		}
		else{
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/common/404.jsp");
			dispatcher.forward(request, response);
		}
	}

	private void searchPrescriptionTemplateAdmin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SearchParam searchParam = Utils.makeSearchParm(request.getParameter("searchValue"), "admin", null, -1,null, null, -1, -1, request.getParameter("searchOption"), null, -1);
		PagingDataType pagingDataType = Utils.computePagingData(this.registrationSerivece.getTemplateCount(searchParam), Utils.convertPageStringToInt(request.getParameter("page")));
		SearchDataType searchDataType = new SearchDataType().extendParent(pagingDataType);
		searchParam.setStartRow(searchDataType.getStartRow());
		searchParam.setEndRow(searchDataType.getEndRow());
		List<PrescriptionTemplateDataType> prescriptionTemplateDataTypeList = this.registrationSerivece.selectPrescriptionTemplateList(searchParam);
		request.setAttribute("searchDataType", searchDataType);
		request.setAttribute("prescriptionTemplateList", prescriptionTemplateDataTypeList);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/prescription/prescription_searchPrescriptionTemplateAdmin.jsp");
		requestDispatcher.forward(request, response);
	}


	private void searchPrescriptionTemplateMe(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
		SearchParam searchParam;
		HttpSession httpSession = request.getSession();
		UserDataType userDataType = (UserDataType)httpSession.getAttribute("user");
		searchParam = Utils.makeSearchParm(request.getParameter("searchValue"), userDataType.getUserId(), null, -1,null, null, -1, -1, request.getParameter("searchOption"), null, -1);
		PagingDataType pagingDataType = Utils.computePagingData(this.registrationSerivece.getTemplateCount(searchParam), Utils.convertPageStringToInt(request.getParameter("page")));
		SearchDataType searchDataType = new SearchDataType().extendParent(pagingDataType);
		searchParam.setStartRow(searchDataType.getStartRow());
		searchParam.setEndRow(searchDataType.getEndRow());
		List<PrescriptionTemplateDataType> prescriptionTemplateDataTypeList = this.registrationSerivece.selectPrescriptionTemplateList(searchParam);
		
		request.setAttribute("searchDataType", searchDataType);
		request.setAttribute("prescriptionTemplateList", prescriptionTemplateDataTypeList);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/prescription/prescription_searchPrescriptionTemplateMe.jsp");
		requestDispatcher.forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	private void changePrescriptionTemplate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
		request.setAttribute("prescriptionID",request.getParameter("prescriptionID"));
		request.setAttribute("ATCHashMapList", this.registrationSerivece.selectPrescription(Integer.valueOf(request.getParameter("prescriptionID"))));
		request.setAttribute("templateDataType",this.registrationSerivece.selectPrescriptionTemplateList(Integer.valueOf(request.getParameter("templateID"))));
		request.setAttribute("ChangeOrUseBasic", 1);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/prescriptiontemplate/prescription.jsp");
		requestDispatcher.forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	private void updatePrescriptionTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
		String[] prohibitionCodeArray = request.getParameterValues("prohibitionCode");
		String[] tolerableCodeArray= request.getParameterValues("tolerableCode");
		String[] upperCodeArray = request.getParameterValues("upperCode");
		String content = request.getParameter("content");
		String actionPlan = request.getParameter("actionPlan");
		int	prescriptionID = Integer.valueOf(request.getParameter("prescriptionID"));
		this.registrationSerivece.insertProhibitionTolerableUpper(prohibitionCodeArray,tolerableCodeArray,upperCodeArray,prescriptionID,content,actionPlan);
		this.registrationSerivece.updatePrescriptionTemplate(Integer.valueOf(request.getParameter("templateID")),request.getParameter("templateTitle"));
		RequestDispatcher dispatcher = request.getRequestDispatcher("/prescription/templateMain");
		dispatcher.forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}


	private void deletePrescriptionTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject jsonObject = this.registrationSerivece.deletePrescriptionTemplate(Integer.valueOf(request.getParameter("prescriptionID")), Integer.valueOf(request.getParameter("templateID")));
		response.setCharacterEncoding("utf-8");
	    response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(jsonObject);
	}


	@SuppressWarnings("unchecked")
	private void saveRegistrationInSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession httpSession = request.getSession();
		UserDataType user = (UserDataType)httpSession.getAttribute("user");
		RegistrationDataType registrationDataType = this.registrationSerivece.makeRegistrationDataType(request.getParameter("userID"),
				request.getParameter("userName"),request.getParameter("CBNUCode"),Integer.valueOf(request.getParameter("registrationID")),user);
		httpSession.setAttribute("registration", registrationDataType);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", "success");
		response.setCharacterEncoding("utf-8");
	    response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(jsonObject);
	}




	private void searchRegistration(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession httpSession = request.getSession();
		UserDataType userDataType = (UserDataType)httpSession.getAttribute("user");
		SearchParam searchParam = Utils.makeSearchParm(request.getParameter("searchValue"), userDataType.getUserId(), null, -1, null, null, -1,-1, request.getParameter("searchoption"), null, -1);
		PagingDataType paging = Utils.computePagingData(this.registrationSerivece.selectPatientCounts(searchParam),  Utils.convertPageStringToInt(request.getParameter("page")));
		searchParam.setStartRow(paging.getStartRow());
		searchParam.setEndRow(paging.getEndRow());
		SearchDataType searchDataType = new SearchDataType().extendParent(paging);
		List<RegistrationDataType> patients = this.registrationSerivece.selectPatientList(searchParam);
		request.setAttribute("registrationDataTypeList",patients);
		request.setAttribute("searchpaging", searchDataType);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/prescriptiontemplate/searchRegistration.jsp");
		requestDispatcher.forward(request, response);
		
	}


	private void useTemplate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
		request.setAttribute("ATCHashMapList", this.registrationSerivece.selectPrescription(Integer.valueOf(request.getParameter("templatePrescriptionID")))); 
		request.setAttribute("templateDataType", this.registrationSerivece.selectPrescriptionTemplateList(Integer.valueOf(request.getParameter("templateID"))));
		if(!request.getParameter("prescriptionID").equals("") ){
			request.setAttribute("prescriptionID", Integer.valueOf(request.getParameter("prescriptionID")));
		}
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/prescription/prescription.jsp");
		requestDispatcher.forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	private void insertPrescriptionTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
		String[] prohibitionCodeArray = request.getParameterValues("prohibitionCode");
		String[] tolerableCodeArray= request.getParameterValues("tolerableCode");
		String[] upperCodeArray = request.getParameterValues("upperCode");
		String content = request.getParameter("content");
		String actionPlan = request.getParameter("actionPlan");
		int prescriptionID = 0;
		if(request.getParameter("prescriptionID").equals("null")){
			RegistrationDataType registration = this.mySelfRegistration(request);
			prescriptionID = this.registrationSerivece.insertPrescription(registration);
		}else{
			prescriptionID = Integer.valueOf(request.getParameter("prescriptionID"));	
		}
		HttpSession httpSession = request.getSession();
		UserDataType userDataType = (UserDataType)httpSession.getAttribute("user");
		this.registrationSerivece.insertProhibitionTolerableUpper(prohibitionCodeArray,tolerableCodeArray,upperCodeArray,prescriptionID,content,actionPlan);
		this.registrationSerivece.insertPrescriptionTemplate(prescriptionID,(String)request.getParameter("templateTitle"), userDataType.getUserId());
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/prescription/templateMain");
		requestDispatcher.forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	private RegistrationDataType mySelfRegistration(HttpServletRequest request) throws Exception {
		HttpSession httpSession = request.getSession();
		UserDataType userDataType = (UserDataType)httpSession.getAttribute("user");
		return  this.registrationSerivece.selectMyself(userDataType);
	}


	private void writeTemplate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/prescriptiontemplate/prescription.jsp");
		dispatcher.forward(request, response);
	}

	private void templateMain(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SearchParam searchParam;
		if(request.getParameter("template") == null||!request.getParameter("template").equals("basic")){

			HttpSession httpSession = request.getSession();
			UserDataType user = (UserDataType)httpSession.getAttribute("user");
			searchParam = Utils.makeSearchParm(request.getParameter("searchValue"), user.getUserId(), null, -1, null, null, -1, -1, request.getParameter("searchOption"), null, -1);
		}else{

			searchParam = Utils.makeSearchParm(request.getParameter("searchValue"), "admin", null, -1, null, null, -1, -1, request.getParameter("searchOption"), null, -1);
		}
		int page = Utils.convertPageStringToInt(request.getParameter("page"));
		PagingDataType paging = Utils.computePagingData(this.registrationSerivece.getTemplateCount(searchParam), page);
		searchParam.setStartRow(paging.getStartRow());
		searchParam.setEndRow(paging.getEndRow());
		List<PrescriptionTemplateDataType> templateList = this.registrationSerivece.selectPrescriptionTemplateList(searchParam);
		request.setAttribute("prescriptionTemplateDataTypeList", templateList);
		request.setAttribute("paging",paging);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/prescriptiontemplate/templateMain.jsp");
		dispatcher.forward(request, response);

	}


	/**
	 * 의사 메인 페이지
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void main(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
		HttpSession session = request.getSession();
		UserDataType userDataType = (UserDataType) session.getAttribute("user");
		session.removeAttribute("registration");
		SearchParam searchParam = Utils.makeSearchParm(request.getParameter("searchValue"), userDataType.getUserId(), null, -1, null, null, -1,-1, request.getParameter("searchoption"), null, -1);
		int page = Utils.convertPageStringToInt(request.getParameter("page"));
		PagingDataType paging = Utils.computePagingData(this.registrationSerivece.selectPatientCounts(searchParam), page);
		searchParam.setStartRow(paging.getStartRow());
		searchParam.setEndRow(paging.getEndRow());
		List<RegistrationDataType> patients = this.registrationSerivece.selectPatientList(searchParam);
		request.setAttribute("paging", paging);
		request.setAttribute("patientList", patients);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/prescription/main.jsp");
		dispatcher.forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 환자 검색 페이지
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	private void searchPatientResultRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		UserDataType doctor = (UserDataType) session.getAttribute("user");
		SearchParam param = Utils.makeSearchParm(request.getParameter("searchValue"),doctor.getUserId(), null, -1, null, null,
				 -1, -1, request.getParameter("searchOption"), null, -1);
		param.setHospitalIdx(doctor.getHospital().getId());
		SearchDataType searchpaging = Utils.makeSearchDataType(Utils.convertPageStringToInt(request.getParameter("page")),param,this.registrationSerivece.checkPatient(param));
		List<UserDataType> userList = this.registrationSerivece.selectSearchUser(param,searchpaging.getStartRow(),searchpaging.getEndRow());
		request.setAttribute("searchpaging", searchpaging);
		request.setAttribute("userList", userList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/prescription/searchPatientResult.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * 환자 검색페이지에서 환자 선택 처리 및 메인 페이지로 이동
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws ParseException 
	 */
	private void createRegistrationRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
		HttpSession session = request.getSession();
		UserDataType doctor = (UserDataType) session.getAttribute("user");
		JSONObject json = this.registrationSerivece.insertRegistration(doctor, request.getParameter("patientID"));
		response.setCharacterEncoding("utf-8");
	    response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(json);
		
	}
	
	/**
	 * 메인 페이지에서 환자 삭제 요청 처리
	 * @param request
	 * @param response
	 * @throws Exception  
	 */
	private void deletePatientRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject json = this.registrationSerivece.deletePatient(Integer.valueOf(request.getParameter("registrationID")));
		response.setCharacterEncoding("utf-8");
	    response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(json);
	}
	
	/**
	 * 선택한 환자 정보 확인 페이지u
	 * @param response
	 * @throws Exception 
	 */
	private void patientInfoRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		UserDataType user = (UserDataType) session.getAttribute("user");
		RegistrationDataType registrationDataType = (RegistrationDataType)session.getAttribute("registration");
		if(registrationDataType == null){
			registrationDataType = this.registrationSerivece.makeRegistrationDataType(request.getParameter("patientID"),
				request.getParameter("patientName"),request.getParameter("CBNUCode"),Integer.valueOf(request.getParameter("registrationID")),user);
		}
		List<RegistrationDataType> registrationList = this.registrationSerivece.selectPatientInfoRequest(registrationDataType.getPatient().getUserId());
		List<PrescriptionDataType> myPrescriptionList = this.registrationSerivece.selectMyPrescriptionList(registrationDataType.getIdx(),registrationList);
		request.setAttribute("myPrescriptionList", myPrescriptionList);
		request.setAttribute("registrationList", registrationList);
		session.setAttribute("registration", registrationDataType);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/prescription/patientInfo.jsp");
		dispatcher.forward(request, response);
		
		
	}
	
	private void createPrescriptionRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		RegistrationDataType registration = (RegistrationDataType)session.getAttribute("registration");
		List<RegistrationDataType> registrationList = this.registrationSerivece.selectPatientInfoRequest(registration.getPatient().getUserId());
		request.setAttribute("registrationList",registrationList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/prescription/prescription.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * 금지 약물 검색 처리 페이지
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	private void searchProhibitionResultRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SearchDataReturnType searchReturnDataType = this.registrationSerivece.searchATCList(request.getParameter("searchValue"), request.getParameter("searchOption"), request.getParameter("page"));
		request.setAttribute("searchpaging", searchReturnDataType.getSearchDataType());
		request.setAttribute("atcList", searchReturnDataType.getAtcList());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/prescription/prescription_searchProhibitionResult.jsp");
		dispatcher.forward(request, response);
	}
	
	private void prescriptionFinishRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
		String[] prohibitionCodeArray = request.getParameterValues("prohibitionCode");
		String[] tolerableCodeArray= request.getParameterValues("tolerableCode");
		String[] upperCodeArray = request.getParameterValues("upperCode");
		String content = request.getParameter("content");
		content = Utils.newlineToBRTag(content);
		String actionPlan = request.getParameter("actionPlan");
		actionPlan = Utils.newlineToBRTag(actionPlan);
		int prescriptionID;
		HttpSession session = request.getSession();
		RegistrationDataType registration = ((RegistrationDataType)session.getAttribute("registration"));
		if(request.getParameter("prescriptionID").equals("null")){
			prescriptionID = this.registrationSerivece.insertPrescription(registration);
			this.registrationSerivece.insertProhibitionTolerableUpper(prohibitionCodeArray,tolerableCodeArray,upperCodeArray,prescriptionID,content,actionPlan);
		}else{
			prescriptionID = Integer.valueOf(request.getParameter("prescriptionID"));
			this.registrationSerivece.updatePrescription(prohibitionCodeArray,tolerableCodeArray,upperCodeArray,prescriptionID,content,actionPlan);
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/prescription/selectPatientInfo");
		dispatcher.forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 상위 ATC 검색 결과 요청
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	private void searchUpperResultRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SearchDataReturnType searchReturnDataType = this.registrationSerivece.searchATCList(request.getParameter("searchValue"), request.getParameter("searchOption"), request.getParameter("page"));
		request.setAttribute("searchpaging", searchReturnDataType.getSearchDataType());
		request.setAttribute("atcList", searchReturnDataType.getAtcList());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/prescription/prescription_searchUpperResult.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * 복용 atc 검색 결과 요청
	 * 캡슐화 다시?
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	private void searchTolerableResultRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SearchDataReturnType searchReturnDataType = this.registrationSerivece.searchATCList(request.getParameter("searchValue"), request.getParameter("searchOption"), request.getParameter("page"));
		request.setAttribute("searchpaging", searchReturnDataType.getSearchDataType());
		request.setAttribute("atcList", searchReturnDataType.getAtcList());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/prescription/prescription_searchTolerableResult.jsp");
		dispatcher.forward(request, response);
		
	}


	/**
	 * 처방 페이지 금기 약제 삭제 요청 처리
	 * @param request
	 * @param response
	 * @throws Exception  
	 */
	private void deletedeleteChangeProhibitionTolerableUpperRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject json =  this.registrationSerivece.deleteChangeProhibitionUpperTolerable(Integer.valueOf(request.getParameter("id")),request.getParameter("medicineKinds"));
		response.setCharacterEncoding("utf-8");
	    response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(json);

	}
	
	/**
	 * 날짜별 처방 내역 팝업창으로 보기
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	private void viewPrescriptionRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("ATCHashMapList", this.registrationSerivece.selectPrescription(Integer.valueOf(request.getParameter("prescriptionID")))); 
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/prescription/prescriptionInfoByPrescriptionID.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * 처방 내역 지우기
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	private void deletePrescriptionRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject json = this.registrationSerivece.deletePrescription(Integer.valueOf(request.getParameter("id")));
		response.setCharacterEncoding("utf-8");
	    response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(json);

	}
	
	private void changePrescriptionRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		RegistrationDataType registration = (RegistrationDataType)session.getAttribute("registration");
		request.setAttribute("prescriptionID",request.getParameter("prescriptionID"));
		if(registration != null){
			request.setAttribute("registrationList",this.registrationSerivece.selectPatientInfoRequest(registration.getPatient().getUserId()));
		}
		request.setAttribute("ATCHashMapList", this.registrationSerivece.selectPrescription(Integer.valueOf(request.getParameter("prescriptionID"))));
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/prescription/prescription.jsp");
		dispatcher.forward(request, response);
	}
}
