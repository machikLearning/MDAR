package kr.ac.cbnu.computerengineering.board.manager.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import org.json.simple.JSONObject;

import kr.ac.cbnu.computerengineering.board.common.datatype.BoardDataType;
import kr.ac.cbnu.computerengineering.board.common.datatype.FileDataType;
import kr.ac.cbnu.computerengineering.board.common.datatype.RepDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;

public interface IBoardDao {

	void removeBoardFile(int boardID);

	void removeAllReply(int boardID);

	void removeBoardBody(int boardID);

	BoardDataType selectBoardBody(int boardID);

	int countRow(SearchParam searchParam);

	List<BoardDataType> getBoardList(SearchParam searchParam);

	int insertBody(BoardDataType boardDataType);

	void insertFileList(List<FileDataType> fileList);

	void insertReply(RepDataType replyDataType);

	JSONObject modifyReply(RepDataType repDataType);

	SqlSession getSqlSession();

	void deleteBoardAndFile(int boardID, SqlSession sqlSession);

	void deleteReplyOrderByReplyID(int boardID, SqlSession sqlSession);

	void deleteBoard(int boardID, SqlSession sqlSession);

	
}
