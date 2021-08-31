package kr.ac.cbnu.computerengineering.controllers;

import kr.ac.cbnu.computerengineering.common.AbsADRMServlet;
import kr.ac.cbnu.computerengineering.common.datatype.*;
import kr.ac.cbnu.computerengineering.common.managers.IMedicineManager;
import kr.ac.cbnu.computerengineering.common.managers.IUserManager;
import kr.ac.cbnu.computerengineering.common.service.IPatientService;
import kr.ac.cbnu.computerengineering.common.util.Utils;
import kr.ac.cbnu.computerengineering.medicine.manager.MedicineManagerImpl;
import kr.ac.cbnu.computerengineering.patient.service.PatientServiceImpl;
import kr.ac.cbnu.computerengineering.user.manager.UserManagerImpl;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.List;


@WebServlet("/patient/*")
@MultipartConfig(maxFileSize=1024*1024*10, location="D:\\UPLOADFILE")
public class PatientController extends AbsADRMServlet {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(UserController.class);
	
	private IUserManager userManager;
	private IMedicineManager medicineManager;
    private IPatientService patientService;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 앱에서 온 요청을 처리하는 컨트롤러
	 */
    public PatientController() {
        super();
        this.userManager = new UserManagerImpl();
        this.medicineManager = new MedicineManagerImpl();
        this.patientService = new PatientServiceImpl();
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
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/mobile/common/patient/error.jsp");
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
		System.out.println(url);
		url = url.replace("/ADRM/patient/", "");
		if(url.equals("login")) {
			this.loginRequest(request, response);
		} else if(url.equals("loginCheck")){
			this.loginCheckRequest(request, response);
		} else if(url.equals("main")){
			this.mainPageRequest(request, response);
		} else if(url.equals("logout")){
			this.logoutRequest(request, response);
		} else if(url.equals("searchMedicine")){
			this.searchMedicineRequest(request, response);
		} else if(url.equals("searchMedicineResult")){
			this.searchMedicineResultRequest(request, response);
		} else if(url.equals("checkMedicine")){
			this.checkMedicineRequest(request, response);
		} else if(url.equals("idCheck")){
			this.idCheckRequest(request, response);
		} else if(url.equals("pwCheck")){
			this.passwordCheckRequest(request, response);
		} else if(url.equals("checkQRcode")){
			this.checkQRcodeRequest(request, response);
		} else if(url.equals("joinCheck")){
			this.joinCheckRequest(request, response);
		} else if(url.equals("hospitals")){
			this.selectHospital(request, response);
		} else if(url.equals("covid19")){
			this.covid19(request, response);
		} else if(url.equals("uploadFile")){
			this.uploadFile(request, response);
		}
		else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/mobile/common/patient/404.jsp");
			dispatcher.forward(request, response);
		} 
		
	}

	private void uploadFile(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
		String uploadFolder = "D://UPLOADFILE";
		super.uploadBoardFile(request,response,uploadFolder);

	}


	private void selectHospital(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = this.patientService.getHospitalList();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println(json);
	}

	/**
	 * 회원가입 요청 처리
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	private void joinCheckRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String[] roles;
		if((request.getParameter("role1").equals("true"))&&(request.getParameter("role2").equals("true"))){
			roles = new String[2];
			roles[0] = "2";
			roles[1] = "3";
		}else {
			roles = new String[1];
			if(request.getParameter("role1").equals("true")){
				roles[0] = "3";
			}else{
				roles[1] = "2";
			}
		}
		JSONObject json = this.patientService.createUser(request.getParameter("id"), 
				Utils.encryptSHA256(request.getParameter("pw")), request.getParameter("name"), "N", request.getParameter("email"), 
				request.getParameter("code"), roles, Integer.parseInt(request.getParameter("hospital_id")), new Date());
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println(json);
	}

	/**
	 * QR코드 요청 처리
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	private void checkQRcodeRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String[] codes = request.getParameterValues("codes");
		HttpSession httpSession = (HttpSession)request.getSession();
		UserDataType user = (UserDataType) httpSession.getAttribute("user");
		String id = user.getUserId();
		this.patientService.savePatientRequestLog(id, PatientRequestLogDataType.QRSearchMedicine.getValue(), codes);
		List<PrescriptionDataType> prescriptionList = this.patientService.getRegistrationAllData(id);
		JSONObject json = this.patientService.getQRCodeResult(prescriptionList, codes);
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println(json);
	}

	/**
	 * 환자용 비밀번호 확인
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private void passwordCheckRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserDataType userDataType = new UserDataType();
		userDataType.setUserId(request.getParameter("id"));
		UserDataType temp = null;
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		
		if((temp = this.userManager.selectEmailById(userDataType)) != null){
			String random = Utils.base64(temp.getUserId() + temp.getCbnuCode());
			String pw = random.substring(0, 5);
			temp.setUserId(userDataType.getUserId());
			temp.setPassword(pw);
			String content="충북대학교 병원 임시 비밀번호는 "+temp.getPassword()+" 입니다.\n";
			super.mailSendRequest(temp,content);
			temp.setPassword(Utils.encryptSHA256(pw));
			this.userManager.changePassword(temp);
			json.put("resultType", "PWSEND");
			out.print(json);
			return;
		}
		else{
			json.put("resultType", "ERROR");
			out.print(json);
			return;
		}
	}

	/**
	 * 환자용 아이디 확인
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private void idCheckRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserDataType userDataType = new UserDataType();
		userDataType.setEmail(request.getParameter("mail"));
		UserDataType temp = null;
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		
		if((temp = this.userManager.selectIdByEmail(userDataType)) != null){
			temp.setEmail(userDataType.getEmail());
			String content="충북대학교 병원 아이디는 "+temp.getUserId()+" 입니다.\n";
			super.mailSendRequest(temp,content);
			json.put("resultType", "IDSEND");
			out.print(json);
			return;
		}
		else{
			json.put("resultType", "ERROR");
			out.print(json);
			return;
		}
	}

	/**
	 * 로그인 페이지 생성
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void loginRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/mobile/patient/login.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * 로그인 요청 처리
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private void loginCheckRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject json = this.patientService.getLoginUser(request.getParameter("id"), Utils.encryptSHA256(request.getParameter("pw")));
		if (String.valueOf(json.get("resultType")).equals("LOGIN_SUCCESS")) {
			UserDataType user = (UserDataType) json.get("user");
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("user", user);
			this.patientService.logByLoginCheck(user.getUserId());
			json.put("userName", user.getName());
		}
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(json);
	}
	
	/**
	 * 환자 메인 페이지 - 의약품 안전 카드 페이지
	 * @param request
	 * @param response
	 * @throws Exception 
	 * @throws IOException
	 */
	private void mainPageRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession httpSession =(HttpSession)request.getSession();
		UserDataType userDataType = (UserDataType)httpSession.getAttribute("user");
		List<PrescriptionDataType> prescriptionDataTypeList = this.patientService.getRegistrationAllData(userDataType.getUserId());
		if (prescriptionDataTypeList != null && !prescriptionDataTypeList.isEmpty()) {
			PagingDataType paging = this.patientService.makePrescriptionPage(prescriptionDataTypeList.size(), Utils.convertPageStringToInt(request.getParameter("page")));
			request.setAttribute("prescription", prescriptionDataTypeList.get(paging.getNowPage() - 1));
			request.setAttribute("paging", paging);
		}
		HospitalDatatype hospital = this.patientService.detailHospitalByUserID(userDataType.getUserId());
		request.setAttribute("hospital", hospital);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/mobile/patient/main.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * 로그아웃 요청 처리
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void logoutRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject jsonObject = new JSONObject();
		try {
			HttpSession session = request.getSession();
			session.invalidate();
			jsonObject.put("resultType","SUCCESS");
		}catch (Exception e){
			jsonObject.put("resultType","FAIL");
		}
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.println(jsonObject);
	}
	
	/**
	 * 환자 - 약물 검색 페이지
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void searchMedicineRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/mobile/patient/searchMedicineResult.jsp");
		dispatcher.forward(request, response);
	}

	/**
	환자- 약물선택 결과 요청
	 */
	private void searchMedicineResultRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SearchParam param = new SearchParam();
		SearchDataType searchpaging = new SearchDataType();
		HttpSession httpSession = request.getSession();
		UserDataType userDataType = (UserDataType)httpSession.getAttribute("user");
		String id = userDataType.getUserId();
		String searchValue= request.getParameter("searchValue");
		String searchOption= request.getParameter("searchOption");
		String[] codeArray = new String[1];
		codeArray[0] = searchValue;
		this.patientService.savePatientRequestLog(id, PatientRequestLogDataType.SearchMedicineResult.getValue(), codeArray);
		if(request.getParameter("resultType") != null){
			String resultType = request.getParameter("resultType");
			request.setAttribute("resultType", resultType);
		}
		param.setParam(searchValue);
		param.setSearchOption(searchOption);
		List<MedicineDataType> medicineList = this.medicineManager.searchMedicineList(param);
		searchpaging.setSearchOption(searchOption);
		searchpaging.setSearchValue(searchValue);
		request.setAttribute("searchpaging", searchpaging);
		request.setAttribute("medicineList", medicineList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/mobile/patient/searchMedicineResult.jsp");
		dispatcher.forward(request, response);
	
	}
	
	private void checkMedicineRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession httpSession = request.getSession();
		UserDataType userDataType = (UserDataType)httpSession.getAttribute("user");
		String id = userDataType.getUserId();
		String[] codeArray = new String[1];
		codeArray[0] = request.getParameter("code");
		this.patientService.savePatientRequestLog(id, PatientRequestLogDataType.CheckMedicine.getValue(), codeArray);
		List<PrescriptionDataType> prescriptionList = this.patientService.getRegistrationAllData(id);
		JSONObject json = this.patientService.checkMedicine(request.getParameter("searchOption"), request.getParameter("code"), prescriptionList);
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.println(json);
	}

	private void covid19(HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession httpSession = request.getSession();
		UserDataType userDataType = (UserDataType)httpSession.getAttribute("user");
		JSONObject allergy = this.patientService.getAllergyList(userDataType.getUserId());
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.println(allergy);
	}
}