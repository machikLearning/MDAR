package kr.ac.cbnu.computerengineering.controllers;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class MainController
 */
@WebServlet("/main/*")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(MainController.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			this.checkURL(request, response);
		} catch(Exception e) {
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
	
	private void checkURL(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURI();
		url = url.replace("/ADRM/main/", "");
		if(url.equals("selectFunc")){
			this.selectFunc(request, response);
		}  else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/common/404.jsp");
			dispatcher.forward(request, response);
		}
	}
	/**
	 * main function 벙의해주는 함수?
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	
	private void selectFunc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main/selectFunc.jsp");
		dispatcher.forward(request, response);
	}
}
