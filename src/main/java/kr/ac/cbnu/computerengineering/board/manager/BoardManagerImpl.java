package kr.ac.cbnu.computerengineering.board.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.json.simple.JSONObject;

import kr.ac.cbnu.computerengineering.board.common.datatype.BoardDataType;
import kr.ac.cbnu.computerengineering.board.common.datatype.BoardRole;
import kr.ac.cbnu.computerengineering.board.common.datatype.FileDataType;
import kr.ac.cbnu.computerengineering.board.common.datatype.RepDataType;
import kr.ac.cbnu.computerengineering.board.manager.dao.BoardDaoImpl;
import kr.ac.cbnu.computerengineering.board.manager.dao.IBoardDao;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;

public class BoardManagerImpl implements IBoardManager {

	private IBoardDao boardDao;
	
	public BoardManagerImpl() {
		// TODO Auto-generated constructor stub
		this.boardDao = new BoardDaoImpl();
	}
	
	
	@Override
	public void removeBoardContent(int boardID) {
		// TODO Auto-generated method stub
		this.boardDao.removeBoardFile(boardID);
		this.boardDao.removeAllReply(boardID);
		this.boardDao.removeBoardBody(boardID);
	}

	@Override
	public void setBoardRole(List<BoardRole> boardRoleList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BoardDataType loadBoardBody(int boardID) {
		// TODO Auto-generated method stub
		BoardDataType boardDataType = this.boardDao.selectBoardBody(boardID);
		return boardDataType;
	}


	@Override
	public int countRow(SearchParam searchParam) {
		// TODO Auto-generated method stub
		return this.boardDao.countRow(searchParam);
	}


	@Override
	public List<BoardDataType> getBoardList(SearchParam searchParam) {
		// TODO Auto-generated method stub
		return this.boardDao.getBoardList(searchParam);
	}


	@Override
	public void insertBody(BoardDataType boardDataType, String[] attachFile) {
		// TODO Auto-generated method stub
		int boardID = this.boardDao.insertBody(boardDataType);
		List<FileDataType> fileList = new ArrayList<FileDataType>();
		if(attachFile != null){
			for(String nickName : attachFile){
				FileDataType fileDataType = new FileDataType();
				fileDataType.setBoardID(boardID);
				fileDataType.setNickName(nickName);
				fileDataType.setSaveName(nickName);
				fileList.add(fileDataType);
			}
			try{
				this.boardDao.insertFileList(fileList);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}


	@Override
	public BoardDataType selectBoardBody(int boardID) {
		// TODO Auto-generated method stub
		return this.boardDao.selectBoardBody(boardID);
	}


	@Override
	public void deleteBoardContent(int boardID) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = this.boardDao.getSqlSession();
		this.boardDao.deleteReplyOrderByReplyID(boardID,sqlSession);
		this.boardDao.deleteBoardAndFile(boardID,sqlSession);
		this.boardDao.deleteBoard(boardID, sqlSession);
	}


	@Override
	public void insertReply(RepDataType replyDataType) {
		// TODO Auto-generated method stub
		this.boardDao.insertReply(replyDataType);
	}


	@Override
	public JSONObject modifyReply(RepDataType repDataType) {
		// TODO Auto-generated method stub
		return this.boardDao.modifyReply(repDataType);
	}




}
