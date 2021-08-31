package kr.ac.cbnu.computerengineering.controllers;

import kr.ac.cbnu.computerengineering.admin.service.EtcServiceImpl;
import kr.ac.cbnu.computerengineering.common.AbsADRMServlet;
import kr.ac.cbnu.computerengineering.common.datatype.*;
import kr.ac.cbnu.computerengineering.common.exception.ExcelFormatException;
import kr.ac.cbnu.computerengineering.common.service.IEctService;
import kr.ac.cbnu.computerengineering.common.service.IMedicineService;
import kr.ac.cbnu.computerengineering.common.service.IUserService;
import kr.ac.cbnu.computerengineering.medicine.service.MedicineService;
import kr.ac.cbnu.computerengineering.user.service.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class AdminController
 * 관리자 페이지 컨트롤러
 * 약물 서비스와 유저 서비스에 관한 내용 사용
 */
@WebServlet("/admin/*")
public class AdminController extends AbsADRMServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(UserController.class);
	
	private IMedicineService medicineService;
	private IUserService userService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminController() {
        super();
        this.medicineService = new MedicineService();
        this.userService = new UserServiceImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * GET 요청 프리프로세스 관련 함수
	 * URL 체크만 함
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		try {
			this.checkURL(request, response);
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
	 * POST 요청 프리프로세스
	 * GET요청과 같음
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/**
	 * URL 검사
	 * @param request
	 * @param response
	 * @throws Exception 
	 * @throws ExcelFormatException 
	 */
	private void checkURL(HttpServletRequest request, HttpServletResponse response) throws ExcelFormatException, Exception {
		String url = request.getRequestURI();
		url = url.replace("/ADRM/admin/", "");
		
		if(url.equals("main")) {
			this.main(request, response);
		} else if(url.equals("accountManagement")) {
			this.accountManagement(request, response);
		} else if(url.equals("disableAccount")) {
			this.disableAccount(request, response);
		} else if(url.equals("ATCManagement")) {
			this.ATCManagement(request, response);
		} else if(url.equals("doctorApprovalManagement")){
			this.doctorApprovalManagement(request, response);
		} else if(url.equals("approvalUpdate")){
			this.doctorApprovalUpdateRequest(request, response);
		} else if(url.equals("medicineManagement")){
			this.medicineManagement(request, response);
		} else if(url.equals("ATCExcelDownload")){
			this.ATCExcelDownloadRequestDownloadRequest(request, response);
		} else if(url.equals("medicineExcelDownload")){
			this.medicineExcelDownloadRequest(request, response);
		} else if(url.equals("medicineErrorList")){
			this.medicineErrorListRequest(request, response);
		} else if(url.equals("hospitalManagement")) {
			this.hospitalManagementRequest(request, response);
		} else if(url.equals("deleteHospital")) {
			this.deleteHospitalRequest(request, response);
		} else if(url.equals("modifyHospital")) {
			this.modifyHospitalRequest(request, response);
		} else if(url.equals("prescripitionTemplateMain")){
			this.adminPrescriptionTemplateMain(request, response); 
		} else if(url.equals("writeHospital")) {
			this.writeHospitalRequest(request, response);
		} else if(url.equals("addHospital")) {
			this.addHospitalRequest(request, response);
		} else if(url.equals("updateHospital")) {
			this.updateHospitalRequest(request, response);
		}else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/common/404.jsp");
			dispatcher.forward(request, response);
		}
	}

	/*
	병원 추가 update 함수
	 */
	private void updateHospitalRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HospitalDatatype hospital = new HospitalDatatype();
		hospital.setId(Integer.parseInt(request.getParameter("hospital_id")));
		hospital.setName(request.getParameter("hospital_name"));
		hospital.setContactName1(request.getParameter("contact_name1"));
		hospital.setContactTel1(request.getParameter("contact_tel1"));
		hospital.setContactName2(request.getParameter("contact_name2"));
		hospital.setContactTel2(request.getParameter("contact_tel2"));
		try {
			IEctService adminService = new EtcServiceImpl();
			adminService.modifyHospital(hospital);
		} catch(Exception e) {
			throw new Exception(e);
		}
		
		response.sendRedirect("/ADRM/admin/hospitalManagement");
	}

	/*
	병원 추가 페이지 요청 함수
	 */
	private void writeHospitalRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin/hospital_write_frm.jsp");
		dispatcher.forward(request, response);
	}

	private void adminPrescriptionTemplateMain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/prescription/templateMain");
		requestDispatcher.forward(request, response);
	}

	/**
	 * 병원정보수정
	 * 
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	private void modifyHospitalRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		HospitalDatatype hospital = new HospitalDatatype();
		try {
			IEctService adminService = new EtcServiceImpl();
			hospital = adminService.detailHospital(id);
		} catch(Exception e) {
			throw new Exception(e);
		}
		request.setAttribute("hospital", hospital);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin/hospital_write_frm.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * 병원삭제
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	private void deleteHospitalRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			IEctService adminService = new EtcServiceImpl();
			adminService.deleteHospital(id);
		} catch(Exception e) {
			throw new Exception(e);
		}
		
		response.sendRedirect("/ADRM/admin/hospitalManagement");
	}

	/**
	 * 참여병원추가
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void addHospitalRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HospitalDatatype hospital = new HospitalDatatype();
		hospital.setName(request.getParameter("hospital_name"));
		hospital.setContactName1(request.getParameter("contact_name1"));
		hospital.setContactTel1(request.getParameter("contact_tel1"));
		hospital.setContactName2(request.getParameter("contact_name2"));
		hospital.setContactTel2(request.getParameter("contact_tel2"));
		try {
			IEctService service = new EtcServiceImpl();
			service.addHospital(hospital);
		} catch(Exception e) {
			throw new Exception(e);
		}
		response.sendRedirect("/ADRM/admin/hospitalManagement");
	}

	/**
	 * 참여 병원 관리 페이지
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	private void hospitalManagementRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<HospitalDatatype> hospitals = new ArrayList<>();
		
		try {
			IEctService adminService = new EtcServiceImpl();
			hospitals = adminService.getHospitals();
		} catch(Exception e) {
			throw new Exception(e);
		}
		
		request.setAttribute("hospitals", hospitals);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin/hospital_list.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * 관리자 메인페이지
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void main(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/ADRM/admin/accountManagement");	
	}	
	
	/**
	 * 약물 데이터베이스 양식 다운로드 요청 처리
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void medicineExcelDownloadRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		super.download(request, response, "template_DB.xlsx",Constraints.DEVLOPMENT_UPLOAD_PATH);
	}
	/**
	 * atc 양식 다운로드 요청 처리
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void ATCExcelDownloadRequestDownloadRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		super.download(request, response, "template_ATC.xlsx", Constraints.DEVLOPMENT_UPLOAD_PATH);
	}	
	
	/**
	 * ATC 관리 페이지 보기
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	private void ATCManagement(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		int nowPage=1;
		if(request.getParameter("page")!= null && !request.getParameter("page").equals(""))
			nowPage=Integer.parseInt(request.getParameter("page"));
		
		SearchParam param = new SearchParam();
		String searchValue= request.getParameter("searchValue");
		String searchOption= request.getParameter("searchOption");
		if(searchValue == null || searchValue.equals("")) searchOption = null;
		param.setParam(searchValue);
		param.setSearchOption(searchOption);
		PagingDataType paging = this.medicineService.getATCPagingInfo(param, nowPage);
		param.setStartRow(paging.getStartRow());
		param.setEndRow(paging.getEndRow());
		List<AtcDataType> ATCList = this.medicineService.getATCList(param);
		
		request.setAttribute("paging", paging);
		request.setAttribute("searchInfo", param);
		request.setAttribute("ATCList", ATCList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin/ATC_list.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * 약물데이터페이스 관리 시스템 페이지
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	private void medicineManagement(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int nowPage=1;
		if(request.getParameter("page")!= null && !request.getParameter("page").equals(""))
			nowPage=Integer.parseInt(request.getParameter("page"));
		
		SearchParam param = new SearchParam();
		String searchValue= request.getParameter("searchValue");
		String searchOption= request.getParameter("searchOption");
		if(searchValue == null || searchValue.equals("")) searchOption = null;
		param.setParam(searchValue);
		param.setSearchOption(searchOption);
		PagingDataType paging = this.medicineService.getMedicinePagingInfo(param, nowPage);
		param.setStartRow(paging.getStartRow());
		param.setEndRow(paging.getEndRow());
		List<MedicineDataType> medicineList = this.medicineService.getMedicineList(param);
				
		request.setAttribute("paging", paging);
		request.setAttribute("searchInfo", param);
		request.setAttribute("medicineList", medicineList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin/medicine_list.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * 오류 약물 출력 페이지
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	private void medicineErrorListRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
	}
	
	
	
	
	
	
	
	

	


	/**
	 * 사용자 관리 페이지, 페이징 정보(PageingDataType)를 포함하여 페이지 로드
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	private void accountManagement(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int nowPage=1;
		if(request.getParameter("page")!= null && !request.getParameter("page").equals(""))
			nowPage=Integer.parseInt(request.getParameter("page"));
		PagingDataType paging = this.userService.getUserListPagingInfo(nowPage);	
		SearchParam param = new SearchParam();
		param.setStartRow(paging.getStartRow());
		param.setEndRow(paging.getEndRow());
		
		List<UserDataType> userList = this.userService.selectAccounts(param);
		
		request.setAttribute("paging", paging);
		request.setAttribute("userList", userList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin/user_list.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * 사용자 활성/비활성 요청 처리
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	private void disableAccount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userID = request.getParameter("userID");
		String isDisable = request.getParameter("isDisable");
		String page = request.getParameter("page");
		
		if(page == null) {
			page = "";
		} else {
			page = "?page="+page;
		}
		
		UserDataType userDataType = new UserDataType();
		userDataType.setUserId(userID);
		userDataType.setDisable(isDisable);
		this.userService.disableAccount(userDataType);
		
		response.sendRedirect("/ADRM/admin/accountManagement"+page);
	}
	
	/**
	 * 의사 승인 요청 페이지, 페이징 정보 포함하여 로드
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	private void doctorApprovalManagement(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int nowPage=1;
		if(request.getParameter("page")!= null && !request.getParameter("page").equals(""))
			nowPage=Integer.parseInt(request.getParameter("page"));
		
		
		PagingDataType paging = this.userService.getApprovalUserPagingInfo(nowPage);
		
		SearchParam param = new SearchParam();
		param.setStartRow(paging.getStartRow());
		param.setEndRow(paging.getEndRow());
		
		List<UserDataType> doctorList = this.userService.selectDoctors(param);
		
		request.setAttribute("paging", paging);
		request.setAttribute("doctorList", doctorList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin/doctor_list.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * 의사 승인/거부 요청 처리
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	private void doctorApprovalUpdateRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = request.getParameter("userID");
		String page = request.getParameter("page");
		String approval =  request.getParameter("approval");
		if(page == null) {
			page = "";
		} else {
			page = "?page="+page;
		}
		
		ApprovalDataType approvaldatatype = new ApprovalDataType();
		ApprovalType approvaltype = ApprovalType.valueOf(approval);
		approvaldatatype.setApproval(approvaltype);
		approvaldatatype.setUserId(userId);
		this.userService.updateApprovalById(approvaldatatype);
		response.sendRedirect("/ADRM/admin/doctorApprovalManagement"+page);
		
	}
	
	

	
	
	
	
}
