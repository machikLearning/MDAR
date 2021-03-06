package kr.ac.cbnu.computerengineering.board.manager.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.json.simple.JSONObject;

import kr.ac.cbnu.computerengineering.board.common.datatype.BoardDataType;
import kr.ac.cbnu.computerengineering.board.common.datatype.FileDataType;
import kr.ac.cbnu.computerengineering.board.common.datatype.RepDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;
import kr.ac.cbnu.computerengineering.common.mybatis.Mybatis;

public class BoardDaoImpl implements IBoardDao {

private SqlSessionFactory sqlSessionFactory;
	
	public SqlSession getSqlSession(){
		SqlSession sqlSession = this.sqlSessionFactory.openSession(false);
		return sqlSession;
	}
	
	public BoardDaoImpl(){
		sqlSessionFactory = Mybatis.getSqlSessionFactory();
	}
	
	@Override
	public void removeBoardFile(int boardID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAllReply(int boardID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeBoardBody(int boardID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BoardDataType selectBoardBody(int boardID) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = this.sqlSessionFactory.openSession(true);
		BoardDataType boardDataType = null;
		try{
			boardDataType = sqlSession.selectOne("board.selectBoardBody", boardID);
		}catch(Exception e){
			e.printStackTrace();
		}
		return boardDataType;
	}

	@Override
	public int countRow(SearchParam searchParam) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = this.sqlSessionFactory.openSession(true);
		int count;
		try{
			count = sqlSession.selectOne("board.countRow", searchParam);
		}catch(Exception e){
			count = -1;
		}finally {
			sqlSession.close();
		}
		return count;
	}

	@Override
	public List<BoardDataType> getBoardList(SearchParam searchParam) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = this.sqlSessionFactory.openSession(true);
		List<BoardDataType> boardList = null;
		try{
			boardList = sqlSession.selectList("board.selectBoardRowList", searchParam);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		return boardList;
	}

	@Override
	public int insertBody(BoardDataType boardDataType) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = this.sqlSessionFactory.openSession(true);
		int boardID = -1;
		try{
			sqlSession.insert("board.insertBoard", boardDataType);
			boardID = boardDataType.getBoardID();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return boardID;
	}

	@Override
	public void insertFileList(List<FileDataType> fileList) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = this.sqlSessionFactory.openSession(true);
		try{
			sqlSession.insert("board.insertFileList",fileList);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
	}

	@Override
	public void insertReply(RepDataType replyDataType) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = this.sqlSessionFactory.openSession(true);
		try{
			sqlSession.insert("board.insertReply",replyDataType);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject modifyReply(RepDataType repDataType) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = new JSONObject();
		SqlSession sqlSession = this.sqlSessionFactory.openSession(true);
		try{
			sqlSession.update("board.modifyReply",repDataType);
			jsonObject.put("result", 1);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		
		return jsonObject;
	}

	@Override
	public void deleteBoardAndFile(int boardID, SqlSession sqlSession) {
		// TODO Auto-generated method stub
		sqlSession.delete("board.deleteFileList",boardID);
	}

	@Override
	public void deleteReplyOrderByReplyID(int boardID, SqlSession sqlSession) {
		// TODO Auto-generated method stub
		sqlSession.delete("board.deleteReplyListOrderByBoardID",boardID);
	}

	@Override
	public void deleteBoard(int boardID, SqlSession sqlSession) {
		// TODO Auto-generated method stub
		sqlSession.delete("board.deleteBoard",boardID);
	}

}
