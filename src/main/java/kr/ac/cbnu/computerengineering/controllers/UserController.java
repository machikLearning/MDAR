package kr.ac.cbnu.computerengineering.controllers;

import kr.ac.cbnu.computerengineering.admin.service.EtcServiceImpl;
import kr.ac.cbnu.computerengineering.common.AbsADRMServlet;
import kr.ac.cbnu.computerengineering.common.datatype.Constraints;
import kr.ac.cbnu.computerengineering.common.datatype.HospitalDatatype;
import kr.ac.cbnu.computerengineering.common.datatype.UserDataType;
import kr.ac.cbnu.computerengineering.common.datatype.UserRoleType;
import kr.ac.cbnu.computerengineering.common.service.IEctService;
import kr.ac.cbnu.computerengineering.common.service.IUserService;
import kr.ac.cbnu.computerengineering.common.util.Utils;
import kr.ac.cbnu.computerengineering.user.service.UserServiceImpl;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Servlet implementation class UserController
 * 회원가입, 로그인, 아이디찾기, 비밀번호 찾기, 로그아웃, 유저정보변경
 *
 */




@WebServlet("/user/*")
public class UserController extends AbsADRMServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(UserController.class);
	
	private IUserService userService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserController() {
        super();
        this.userService = new UserServiceImpl();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			checkURL(request, response);
		} catch (Exception e) {
			e.printStackTrace();
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
		this.doGet(request, response);
	}
	private void checkURL(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		String url = request.getRequestURI();
		url = url.replace("/ADRM/user/", "");
		if (url.equals("join")){
			this.joinRequest(request, response);
		} else if(url.equals("createUser")){
			this.createUserRequest(request, response);
		} else if(url.equals("login")){
			this.loginRequest(request, response);
		} else if(url.equals("loginCheck")){
			this.loginCheckRequest(request, response);
		} else if(url.contains("idCheck")){
			this.idCheckRequest(request, response);
		} else if(url.contains("emailCheck")){
			this.emailCheckRequest(request, response);
		} else if(url.contains("idFind")){
			this.idFindRequest(request, response);
		} else if (url.contains("idSend")){
			this.idSendRequest(request, response);
		} else if (url.contains("pwFind")){
			this.pwFindRequest(request, response);
		} else if (url.contains("pwSend")){
			this.pwSendRequest(request, response);
		} else if(url.contains("logout")){
			this.logoutRequest(request, response);
		} else if(url.contains("changeUser")){
			this.changeUserRequest(request, response);
		} else if(url.contains("changeFinish")){
			this.changeFinishRequest(request, response);
		} else if(url.equals("downloadApp")){
			this.downloadAppRequest(request, response);
		} else if(url.equals("showLeavePage")){
			this.showLeavePageRequest(request,response);
		} else if(url.equals("leaveUser")){
			this.leaveUserRequest(request, response);
		}
		else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/common/404.jsp");
			dispatcher.forward(request, response);
		}
		
	}

	private void leaveUserRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession httpSession = request.getSession();
		UserDataType userDataType= (UserDataType)httpSession.getAttribute("user");
		int leaveFlag = Integer.valueOf(request.getParameter("result"));
		RequestDispatcher dispatcher = null;
		if(leaveFlag == 1){
			if(this.userService.setLeaveUser(userDataType.getUserId())){
				httpSession.invalidate();
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user/leavePageComplete.jsp");
			}else{
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/common/404.jsp");
			}
			
		}else{
			dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main/selectFunc.jsp");
		}
		dispatcher.forward(request, response);
	}

	private void showLeavePageRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user/leavePage.jsp");
		dispatcher.forward(request, response);
	}

	private void downloadAppRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		super.download(request, response, "DUR.apk", Constraints.DEVLOPMENT_UPLOAD_PATH);
	}

	/**
	 * 로그아웃 리퀘스트
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void logoutRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		session.invalidate();
		response.sendRedirect("/ADRM/user/login");
	}
	
	/**
	 * 로그인 체크
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	private void loginCheckRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
		UserDataType userDataType = Utils.makeUserDataType(request.getParameter("id"), 
				Utils.encryptSHA256(request.getParameter("pw")), null, null, null, null, null, null, 0, null);
		userDataType = this.userService.checkLogin(userDataType);
		RequestDispatcher dispatcher;
		if(userDataType ==  null){
			dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/common/different_password.jsp");
			dispatcher.forward(request, response);
		}
		if(userDataType.getIsLeaved() == 1){
			dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/common/different_password.jsp");
			dispatcher.forward(request, response);
		}else{
			HttpSession session = request.getSession();
			session.setAttribute("user", userDataType);
			dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main/selectFunc.jsp");
			dispatcher.forward(request, response);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 회원가입 페이지
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	private void joinRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String accept = request.getParameter("agree");
		List<HospitalDatatype> hospitals = new ArrayList<>();
		
		try {
			IEctService adminService = new EtcServiceImpl();
			hospitals = adminService.getHospitals();
		} catch(Exception e) {
			throw new Exception(e);
		}
		
		request.setAttribute("hospitals", hospitals);
		
		if(accept == null || !accept.equals("1")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user/agree.jsp");
			dispatcher.forward(request, response);
			return;
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user/join.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * 로그인 페이지
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void loginRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String browser = request.getHeader("User-Agent");
		if ((browser.indexOf("Android") > 0) || (browser.indexOf("iPhone") > 0)) {
			response.sendRedirect("/ADRM/user/downloadApp");
			return ;
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user/login.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * 회원가입 요청
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	
	private void createUserRequest(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		String approvalResult = this.userService.createUser(request.getParameter("id"), 
				Utils.encryptSHA256(request.getParameter("pw")), request.getParameter("name"), "N", request.getParameter("email"), 
				request.getParameter("code"), request.getParameterValues("roles"),new Date(), Integer.parseInt(request.getParameter("hospital")));
		String address = "/user";
		if(approvalResult.equals("JOIN_SUCCESS")){
			address += "/login";
		} else {
			address += "/join";
			request.setAttribute("resultType", "REJECT");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}
	
	/**
	 * 회원 정보 수정 페이지
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	private void changeUserRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		UserDataType userDataType = (UserDataType) session.getAttribute("user");
		List<UserRoleType> userRoleList = this.userService.selectRoles(userDataType);
		request.setAttribute("userRoles", userRoleList);
		

		List<HospitalDatatype> hospitals = new ArrayList<>();
		
		try {
			IEctService adminService = new EtcServiceImpl();
			hospitals = adminService.getHospitals();
		} catch(Exception e) {
			throw new Exception(e);
		}
		
		request.setAttribute("hospitals", hospitals);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user/changeUser.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * 회원 정보 수정 완료 페이지
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	private void changeFinishRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		UserDataType user = (UserDataType) session.getAttribute("user");
		String code = null;
		if(request.getParameter("code") != ""){
			code = request.getParameter("code");
		}
		UserDataType newUserDataType = Utils.makeUserDataType(user.getUserId(), Utils.encryptSHA256(request.getParameter("pw")), request.getParameter("name"),
				"N", request.getParameter("email"), code, request.getParameterValues("roles"), null, Integer.parseInt(request.getParameter("hospital")), null);
		String resultType = "";
		if(this.userService.updateUser(newUserDataType)){
			newUserDataType = this.userService.checkLogin(newUserDataType);
			session.setAttribute("user", newUserDataType);
			resultType = "SUCCESS";
		}else{
			resultType = "REJECT";
		}
		request.setAttribute("resultType", resultType);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main/selectFunc.jsp");
		dispatcher.forward(request, response);
	}
	

	/**
	 * 이메일 체크 ajax 요청용. Json 포맷 출력
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	private void emailCheckRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserDataType userDataType = Utils.makeUserDataType(null, null, null, null, request.getParameter("email"), 
				null, null,null, 0, null);
		JSONObject json = this.userService.makeJson(userDataType,"email");
		PrintWriter out = response.getWriter();
		out.print(json);
	}

	/**
	 * 아이디체크  ajax 요청용. Json 포맷 출력
	 * @param request
	 * @param response
	 * @throws Exception 
	 */

	
	private void idCheckRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserDataType userDataType = Utils.makeUserDataType(request.getParameter("id"),null, null, null,null, null, null, null, 0, null);
		JSONObject json = this.userService.makeJson(userDataType, "id");
		PrintWriter out = response.getWriter();
		out.print(json);
	}
	
	/**
	 * 아이디 찾기 페이지
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void idFindRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user/idFind.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * 충북대 코드로 아이디 찾기 요청, json 포맷 출력
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	
	
	private void idSendRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		UserDataType userDataType = Utils.makeUserDataType(null, null, null, null, request.getParameter("email"), 
				null, null, null, 0, null);
		userDataType = this.userService.selectIdByEmail(userDataType);
		String resultParameter = "";	
		if(userDataType == null) {
			resultParameter="?error=1";
			response.sendRedirect("/ADRM/user/idFind"+resultParameter);
			return;
		}
		String contents = this.userService.makeMailContents(userDataType.getUserId(),"id");
		if(contents != null){
			super.mailSendRequest(userDataType,contents);
			resultParameter="?resultType=IDSEND";
			response.sendRedirect("/ADRM/user/login"+resultParameter);
		}
		else{
			resultParameter="?error=1";
			response.sendRedirect("/ADRM/user/idFind"+resultParameter);
		}
	}
	/**
	 * 비밀번호 찾기 페이지
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	
	private void pwFindRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user/pwFind.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * 비밀번호 찾기요청, 메일로 비밀번호 전송
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	
	private void pwSendRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserDataType userDataType = Utils.makeUserDataType(request.getParameter("id"), null, null, null, null, null, null, null, 0, null);
		userDataType = this.userService.selectID(userDataType);
		String resultParameter ="";
		if(userDataType.getEmail() != null){
			String pw = this.userService.makeTempPassWord(userDataType);
			String contents = this.userService.makeMailContents(pw, "임시 비밀번호");
			super.mailSendRequest(userDataType, contents); 
			resultParameter="?resultType=PWSEND";
			response.sendRedirect("/ADRM/user/login"+resultParameter);
		}
		else{
			resultParameter="?error=1";
			response.sendRedirect("/ADRM/user/pwFind"+resultParameter);
		}
	}

}
