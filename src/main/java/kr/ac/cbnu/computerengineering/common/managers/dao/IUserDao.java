package kr.ac.cbnu.computerengineering.common.managers.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.ac.cbnu.computerengineering.common.datatype.ApprovalDataType;
import kr.ac.cbnu.computerengineering.common.datatype.LogDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;
import kr.ac.cbnu.computerengineering.common.datatype.UserDataType;
import kr.ac.cbnu.computerengineering.common.datatype.UserRoleDataType;

public interface IUserDao {
	public UserDataType detailUser(UserDataType userDataType) throws Exception;
	public int insert(UserDataType userDataType) throws Exception;
	public int idCheck(UserDataType userDataType) throws Exception;
	public void updateUserInform(UserDataType userDataType) throws Exception;
	public void disableAccount(UserDataType userDataType) throws Exception;
	public List<UserDataType> selectAccounts(SearchParam param) throws Exception;
	public int selectAllUserCounts() throws Exception;
	public void deleteUser(UserDataType userDataType) throws Exception;
	public List<UserDataType> selectAccountById(SearchParam param) throws Exception;
	public List<UserDataType> selectAccountByName(SearchParam param) throws Exception;
	public List<ApprovalDataType> selectApprovals(SearchParam param) throws Exception;
	public int selectAllApprovalCounts() throws Exception;
	public List<ApprovalDataType> selectApprovalById(ApprovalDataType approvalDataType) throws Exception;
	public void updateApprovalById(ApprovalDataType approvalDataType) throws Exception;
	public void deleteApproval(ApprovalDataType approvalDataType) throws Exception;
	public int cbnuCodeCheck(UserDataType userDataType) throws Exception;
	public void insertRoleAndUpdateApproval(UserRoleDataType userRoleDataType, ApprovalDataType approvalDataType) throws Exception;
	public void deleteRoleAndUpdateApproval(UserRoleDataType userRoleDataType, ApprovalDataType approvalDataType) throws Exception;
	public void deleteApprovalAndInsertRole(UserRoleDataType userRoleDataType, ApprovalDataType approvalDataType) throws Exception;
	public UserDataType selectIdByCbnuCode(UserDataType userDataType) throws Exception;
	public UserDataType selectEmailById(UserDataType userDataType) throws Exception;
	public UserDataType selectIdByEmail(UserDataType userDataType) throws Exception;
	public UserDataType selectMailByIdAndCode(UserDataType userDataType) throws Exception;
	public void changePassword(UserDataType userDataType) throws Exception;
	public int emailCheck(UserDataType userDataType) throws Exception;
	public UserDataType selectPassword(UserDataType userDataType) throws Exception;
	public int approvalCheck(ApprovalDataType approvalDataType) throws Exception;
	public List<UserDataType> selectDoctors(SearchParam param) throws Exception;
	public UserDataType selectUserList(UserDataType user) throws Exception;
	public int updateUser(UserDataType userDataType) throws Exception;
	public SqlSession getSession();
	public SqlSession insertNewUser(UserDataType userDataType, SqlSession session) throws Exception;
	public void insertRoles(UserRoleDataType userRole, SqlSession session) throws Exception;
	public void updateApprovalById(ApprovalDataType approvalDataType, SqlSession session) throws Exception;
	public SqlSession deleteRole(UserDataType user, SqlSession session) throws Exception;
	public List<UserDataType> selectSearchUSer(SearchParam param) throws Exception;
	public void logQRCode(List<LogDataType> logs) throws Exception;
	public void logByLoginCheck(LogDataType logDataType) throws Exception;
	public boolean setLeaveUser(String userID);
}
