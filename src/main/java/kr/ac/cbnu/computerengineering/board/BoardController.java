package kr.ac.cbnu.computerengineering.board;

import kr.ac.cbnu.computerengineering.board.common.datatype.BoardDataType;
import kr.ac.cbnu.computerengineering.board.common.datatype.BoardRole;
import kr.ac.cbnu.computerengineering.board.common.datatype.FileDataType;
import kr.ac.cbnu.computerengineering.board.common.datatype.RepDataType;
import kr.ac.cbnu.computerengineering.board.manager.BoardManagerImpl;
import kr.ac.cbnu.computerengineering.board.manager.IBoardManager;
import kr.ac.cbnu.computerengineering.common.AbsADRMServlet;
import kr.ac.cbnu.computerengineering.common.datatype.*;
import kr.ac.cbnu.computerengineering.common.util.Utils;
import kr.ac.cbnu.computerengineering.controllers.UserController;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@WebServlet("/board/*")
public class BoardController extends AbsADRMServlet {
	private static final long serialVersionUID = 1L;
	private IBoardManager boardManager;
	private Logger logger = Logger.getLogger(UserController.class);
	
	public BoardController(){
		this.boardManager = new BoardManagerImpl();
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
	
	private void checkURL(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
		// TODO Auto-generated method stub
		String url  = request.getRequestURI();
		url = url.replace("/ADRM/board/", "");
		if(url.equals("boardMain")){
			this.boardMain(request,response);
		}else if(url.equals("writeBoard")){
			this.write(request,response);
		}else if(url.equals("insertBody")){
			this.insertBody(request,response);
		}else if(url.equals("insertPicture")){
			this.insertPicture(request,response);
		}else if(url.equals("insertFile")){
			this.insertFile(request,response);
		}else if(url.equals("selectBody")){
			this.selectBody(request,response);
		}else if(url.equals("manage")){
			this.manage(request,response);
		}else if(url.equals("setBoardRole")){
			this.setBoardRole(request,response);
		}else if(url.equals("deleteBoard")){
			this.deleteBoardContent(request,response);
		}else if(url.equals("downloadFile")){
			this.downloadFile(request, response);
		}else if(url.equals("boardImagePopup")){
			this.boardImagePopup(request,response);
		}else if(url.equals("boardFilePopup")){
			this.boardFilePopup(request, response);
		}else if(url.equals("loadDaumEditor")){
			this.loadDaumEditor(request,response);
		}else if(url.equals("insertReply")){
			this.insertReply(request,response);
		}else if(url.equals("confirmAuthority")){
			this.confirmAuthority(request, response);
		}else if(url.equals("modifyReply")){
			this.modifyReply(request,response);
		}else if(url.equals("deleteReply")){
			this.deleteReply(request,response);
		}else if(url.equals("modifyBoard")){
			this.modifyBoard(request,response);
		}else if(url.equals("saveFileRowData")){
			this.saveFileRowData(request,response);
		}else if(url.equals("deleteBoard")){
			this.deleteBoardContent(request, response);
		}
	}

	private void saveFileRowData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		JSONObject jsonObject = this.saveFile(request,response,Constraints.FILE_UPLOAD_PATH, "/ATTACH/");
		response.setContentType("application/x-json; charset=UTF-8");
		response.getWriter().print(jsonObject);
		
	}

	private void boardFilePopup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/board/boardFilePopup.jsp");
		dispatcher.forward(request, response);
	}

	private void modifyBoard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int boardID = Integer.valueOf((String)request.getParameter("boardID"));
		BoardDataType boardDataType = this.boardManager.selectBoardBody(boardID);
		request.setAttribute("boardDataType", boardDataType);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/board/daumeditor.jsp");
		requestDispatcher.forward(request, response);
	}

	
	private void deleteReply(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
		// TODO Auto-generated method stub
		int replyID = Integer.valueOf((String)request.getParameter("replyID"));
		RepDataType repDataType = new RepDataType();
		repDataType.setReplyid(replyID);
		repDataType.setDate(Utils.getDate());
		repDataType.setReplyBody("삭제 되었습니다");
		JSONObject jsonObject = this.boardManager.modifyReply(repDataType);
		response.setCharacterEncoding("utf-8");
	    response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(jsonObject);
	}

	private void modifyReply(HttpServletRequest request, HttpServletResponse response) throws ParseException, ServletException, IOException {
		// TODO Auto-generated method stub	
		int replyID = Integer.valueOf((String)request.getParameter("replyID"));
		RepDataType repDataType = new RepDataType();
		repDataType.setReplyid(replyID);
		repDataType.setReplyBody((String)request.getParameter("replyBody"));
		repDataType.setDate(Utils.getDate());
		this.boardManager.modifyReply(repDataType);
		request.setAttribute("boardID", Integer.valueOf((String)request.getParameter("boardID")));
		RequestDispatcher dispatcher = request.getRequestDispatcher("/board/selectBody");
		dispatcher.forward(request, response);
		
	}

	@SuppressWarnings("unchecked")
	private void confirmAuthority(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession httpSession = request.getSession();
		UserDataType user = (UserDataType)httpSession.getAttribute("user");
		JSONObject jsonObject = new JSONObject();
		String userID = (String)request.getParameter("userID");
		if(userID.equals(user.getUserId())||userID.equals(Constraints.ADMIN_ID)){
			jsonObject.put("result", 1);
		}
		response.setCharacterEncoding("utf-8");
	    response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(jsonObject);
		
	}

	private void insertReply(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		// TODO Auto-generated method stub
		try{
		HttpSession httpSession = request.getSession();
		RepDataType replyDataType = new RepDataType();
		replyDataType.setUserID(((UserDataType)httpSession.getAttribute("user")).getUserId());
		replyDataType.setReplyBody((String)request.getParameter("replyBody"));
		replyDataType.setDate(Utils.getDate());
		replyDataType.setBoardID(Integer.valueOf((String)request.getParameter("boardID")));
		if(((String)request.getParameter("depth")) == null){
			replyDataType.setDepth(1);
		}else{
			replyDataType.setDepth(Integer.valueOf(((String)request.getParameter("depth"))));
			replyDataType.setParentID(Integer.valueOf(((String)request.getParameter("parentID"))));
		}
		this.boardManager.insertReply(replyDataType);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void loadDaumEditor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/board/daumeditor.jsp");
		dispatcher.forward(request, response);
	}

	private void boardImagePopup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/board/boardImagePopup.jsp");
		dispatcher.forward(request, response);
	}

	private void selectBody(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int boardID = Integer.valueOf((String)request.getParameter("boardID"));
		BoardDataType boardDataType = this.boardManager.selectBoardBody(boardID);
		request.setAttribute("boardDataType", boardDataType);
		request.setAttribute("replyDataType", boardDataType.getReplyList());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/board/selectBoard.jsp");
		dispatcher.forward(request, response);	
	}

	private void insertFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		JSONObject jsonObject = this.saveFile(request,response,Constraints.FILE_UPLOAD_PATH, "/ATTACH/");
		response.setContentType("application/x-json; charset=UTF-8");
		response.getWriter().print(jsonObject);
	}

	private void downloadFile(HttpServletRequest request, HttpServletResponse response)  {
		// TODO Auto-generated method stub
		try {
			this.download(request, response, (String)request.getAttribute("fileName"),Constraints.FILE_UPLOAD_PATH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void boardMain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String searchValue = (String)request.getParameter("searchValue");
		String searchOption = (String)request.getParameter("searchOption");
		SearchParam searchParam = Utils.makeSearchParm(searchValue, null, null, -1, null, null, -1, -1,searchOption, null, -1);
		int count = this.boardManager.countRow(searchParam);
		PagingDataType pagingDataType = Utils.computePagingData(count, Utils.convertPageStringToInt(request.getParameter("page")));
		SearchDataType searchDataType = new SearchDataType().extendParent(pagingDataType);
		searchParam.setStartRow(searchDataType.getStartRow());
		searchParam.setEndRow(searchDataType.getEndRow());
		searchDataType.setSearchValue(searchValue);
		searchDataType.setSearchOption(searchOption);
		List<BoardDataType> boardList = this.boardManager.getBoardList(searchParam);
		boardList = this.sortTree(boardList);
		request.setAttribute("boardList", boardList);
		request.setAttribute("searchpaging", searchDataType);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/board/boardMain.jsp");
		dispatcher.forward(request, response);
	}

	private List<BoardDataType> sortTree(List<BoardDataType> boardList) {
		// TODO Auto-generated method stub
		List<BoardDataType> returnBoardList = new ArrayList<BoardDataType>();
		for(BoardDataType boardDataType : boardList){
			returnBoardList.add(boardDataType);
			if(boardDataType.getChildBoardDataTypeList() == null||boardDataType.getChildBoardDataTypeList().isEmpty()){
				continue;
			}else{
				returnBoardList.addAll(this.sortTree(boardDataType.getChildBoardDataTypeList()));
			}
		}
		return returnBoardList;
	}

	private void deleteBoardContent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.boardManager.deleteBoardContent(Integer.valueOf((String)request.getAttribute("boardID")));
		RequestDispatcher dispatcher = request.getRequestDispatcher("/board/boardMain");
		dispatcher.forward(request, response);
	}


	private void setBoardRole(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		List<BoardRole> boardRoleList = new ArrayList<BoardRole>();
		Pattern pattern = Pattern.compile("^doctor(.)*");
		Set<String> keys = request.getParameterMap().keySet();
		for(String key : keys){
			Matcher matcher = pattern.matcher(key);
			BoardRole boardRole;
			if(matcher.find()){
				boardRole = new BoardRole(UserRoleType.DOCTOR, key.substring(7), request.getParameter(key));
			}else{
				boardRole = new BoardRole(UserRoleType.PATIENT, key.substring(7), request.getParameter(key));
			}
			boardRoleList.add(boardRole);
		}
		this.boardManager.setBoardRole(boardRoleList);
	}

	private void manage(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		this.boardManager.deleteBoardContent(Integer.valueOf((String)request.getAttribute("contentID")));
	}
	
	private void insertPicture(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		JSONObject jsonObject = this.saveFile(request,response,Constraints.PICTURE_UPLOAD_PATH, "/BOARD/");
		response.setContentType("application/x-json; charset=UTF-8");
		response.getWriter().print(jsonObject);
	}

	

	@SuppressWarnings("unchecked")
	private JSONObject saveFile(HttpServletRequest request, HttpServletResponse response, String entireUploadPath,
			String partitialUploadPath) throws ServletException {
		// TODO Auto-generated method stub
		String folder = entireUploadPath;
		JSONObject jsonObject = new JSONObject();
		try{
			List<FileDataType> fileList = super.uploadBoardFile(request, response, folder);
			
			JSONArray jsonArray = new JSONArray();
			for(FileDataType fileDataType : fileList){
				JSONObject jsonObjectArray = new JSONObject();
				jsonObjectArray.put("saveName", fileDataType.getSaveName());
				jsonObjectArray.put("length", fileDataType.getLength());
				jsonObjectArray.put("uploadFolder", "/uploadfile" + partitialUploadPath+fileDataType.getSaveName());
				jsonObjectArray.put("nickName", fileDataType.getNickName());
				jsonArray.add(jsonObjectArray);
			}
			jsonObject.put("fileList", jsonArray);
			jsonObject.put("result","success");
		}catch (Exception e) {
			// TODO: handle exception
			jsonObject.put("result","fail");
		}
		return jsonObject;
	}

	private void insertBody(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		// TODO Auto-generated method stub
		try{
			HttpSession httpSession = request.getSession();
			BoardDataType boardDataType = new BoardDataType();
			boardDataType.setBoardBody((String)request.getParameter("content"));
			boardDataType.setBoardTitle(((String)request.getParameter("name")));
			boardDataType.setDate(Utils.getDate());
			boardDataType.setUserID(((UserDataType)httpSession.getAttribute("user")).getUserId());
			if(request.getParameter("parentID").isEmpty()){
				boardDataType.setDepth(0);
			}else{
				boardDataType.setParentID(Integer.valueOf(request.getParameter("parentID")));
				boardDataType.setDepth(Integer.valueOf(request.getParameter("depth")));
			}
			String[] attachFile = request.getParameterValues("attach_file");
			this.boardManager.insertBody(boardDataType,attachFile);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/board/boardMain");
			dispatcher.forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private void write(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
			
		if(!request.getParameter("parentID").isEmpty()){
			request.setAttribute("parentID", Integer.valueOf(request.getParameter("parentID")));
			request.setAttribute("depth", Integer.valueOf(request.getParameter("depth"))+1);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/board/daumeditor.jsp");
		dispatcher.forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
