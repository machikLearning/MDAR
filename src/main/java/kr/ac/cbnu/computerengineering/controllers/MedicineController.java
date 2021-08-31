package kr.ac.cbnu.computerengineering.controllers;

import kr.ac.cbnu.computerengineering.common.datatype.MedicineDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;
import kr.ac.cbnu.computerengineering.common.managers.IMedicineManager;
import kr.ac.cbnu.computerengineering.medicine.manager.MedicineManagerImpl;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class MedicienListServlet
 */

/**
 * 우선 둘 것
 * @author moodeath
 *
 */
public class MedicineController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private IMedicineManager medicineManager;
    private Logger logger = Logger.getLogger(UserController.class);
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MedicineController() {
        super();
        this.medicineManager = new MedicineManagerImpl();
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
		url = url.replace("/ADRM/medicine/", "");
		if(url.equals("searchMedicine")){
			this.medicineSearchRequest(request, response);
		} else if(url.equals("searchAtc")){
			this.atcSearchRequest(request,response);
		} else if(url.equals("searchATCByMedicineName")){
			this.atcSearchByMedicineRequest(request,response);
		} else if (url.contains("search")){
			this.searchPageRequest(request, response);
		} else if(url.contains("list")) {
			this.medicineListRequest(request, response);
		} else if(url.contains("upload")) {
			this.medicineUploadRequest(request,response);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/common/404.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	
	
	private void medicineUploadRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/upload/upload.jsp");
		dispatcher.forward(request, response);
	}
	
	private void medicineListRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SearchParam param = new SearchParam();
		List<MedicineDataType> result = this.medicineManager.getMedicineList(param);
		request.setAttribute("MEDICINE_LIST", result);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/select/medicine_list.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void searchPageRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/select/medicine_searchList.jsp");
		dispatcher.forward(request, response);  //리스트형식으로 보여주는 부분		
	}

	private void medicineSearchRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		SearchParam param = new SearchParam();
		param.setParam("medicineCode");		
		String code = request.getParameter("medicineCode");
		param.setId(code);
		List<MedicineDataType> result = this.medicineManager.getMedicineList(param);
		request.setAttribute("MEDICINE_LIST", result);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/select/medicine_list.jsp");
		dispatcher.forward(request, response);  //리스트형식으로 보여주는 부분
	}

	
	private void atcSearchRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		SearchParam param = new SearchParam();
		param.setParam("atc");		
		String code = request.getParameter("atc");
		param.setId(code);
		List<MedicineDataType> result = this.medicineManager.getMedicineList(param);
		request.setAttribute("MEDICINE_ATC_LIST", result);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/select/medicine_atc_list.jsp");
		dispatcher.forward(request, response);  //리스트형식으로 보여주는 부분		
	}
	
	private void atcSearchByMedicineRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		String name = (String) request.getParameter("medicineName");
		SearchParam param = new SearchParam();
		param.setParam(name);
		List<MedicineDataType> medicineDataType = this.medicineManager.selectATCByMedicineName(param);
		request.setAttribute("MEDICINE_ATC_LIST", medicineDataType);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/select/medicine_atc_list.jsp");
		dispatcher.forward(request, response);
	}
}
