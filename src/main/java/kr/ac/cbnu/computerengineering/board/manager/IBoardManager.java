package kr.ac.cbnu.computerengineering.board.manager;

import java.util.List;

import org.json.simple.JSONObject;

import kr.ac.cbnu.computerengineering.board.common.datatype.BoardDataType;
import kr.ac.cbnu.computerengineering.board.common.datatype.BoardRole;
import kr.ac.cbnu.computerengineering.board.common.datatype.FileDataType;
import kr.ac.cbnu.computerengineering.board.common.datatype.RepDataType;
import kr.ac.cbnu.computerengineering.common.datatype.AtcDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;

public interface IBoardManager {

	
	void setBoardRole(List<BoardRole> boardRoleList);
	BoardDataType loadBoardBody(int boardID);
	int countRow(SearchParam searchParam);
	List<BoardDataType> getBoardList(SearchParam searchParam);
	void removeBoardContent(int boardID);
	void insertBody(BoardDataType boardDataType, String[] attachFile);
	BoardDataType selectBoardBody(int boardID);
	void deleteBoardContent(int boardID);
	void insertReply(RepDataType replyDataType);
	JSONObject modifyReply(RepDataType repDataType);
	
}
