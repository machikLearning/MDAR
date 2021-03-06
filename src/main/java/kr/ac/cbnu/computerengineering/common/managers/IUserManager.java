package kr.ac.cbnu.computerengineering.common.managers;

import java.util.List;

import kr.ac.cbnu.computerengineering.common.datatype.ApprovalDataType;
import kr.ac.cbnu.computerengineering.common.datatype.LogDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;
import kr.ac.cbnu.computerengineering.common.datatype.UserDataType;
import kr.ac.cbnu.computerengineering.common.datatype.UserRoleDataType;

public interface IUserManager {
	public UserDataType getUser(UserDataType userDataType) throws Exception;
	public int insertUser(UserDataType userDataType) throws Exception;
	public UserDataType checkLogin(UserDataType userDataType) throws Exception;
	public List<UserRoleDataType> selectRoles(UserDataType userDataType) throws Exception;
	public int idCheck(UserDataType userDataType) throws Exception;
	public void changeUserInform(UserDataType userDataType, boolean isDoctor);
	public void disableAccount(UserDataType userDataType) throws Exception;
	public List<UserDataType> selectAccounts(SearchParam param) throws Exception;
	public int selectAllUserCounts() throws Exception;
	public void deleteUser(UserDataType userDataType) throws Exception;
	public void deleteUserRoleInform(UserDataType userDataType) throws Exception;
	public List<UserDataType> selectAccountById(SearchParam param) throws Exception;
	public List<UserDataType> selectAccountByName(SearchParam param) throws Exception;
	public List<ApprovalDataType> selectApprovals(SearchParam param) throws Exception;
	public int selectAllApprovalCounts() throws Exception;
	public List<ApprovalDataType> selectApprovalById(ApprovalDataType approvalDataType) throws Exception;
	public void updateApprovalById(ApprovalDataType approvalDataType) throws Exception;
	public void deleteRole(UserRoleDataType userRoleDataType) throws Exception;
	public List<UserRoleDataType> selectRole(UserRoleDataType userRoleDataType) throws Exception;
	public void deleteApproval(ApprovalDataType approvalDataType) throws Exception;
	public int cbnuCodeCheck(UserDataType userDataType) throws Exception;
	public void insertRoleAndUpdateApproval(UserRoleDataType userRoleDataType, ApprovalDataType approvalDataType) throws Exception;
	public void deleteRoleAndUpdateApproval(UserRoleDataType userRoleDataType, ApprovalDataType approvalDataType) throws Exception;
	public UserDataType selectIdByCbnuCode(UserDataType userDataType) throws Exception;
	public UserDataType selectEmailById(UserDataType userDataType) throws Exception;
	public UserDataType selectIdByEmail(UserDataType userDataType) throws Exception;
	public UserDataType selectPassword(UserDataType userDataType) throws Exception;
	public UserDataType selectMailByIdAndCode(UserDataType userDataType) throws Exception;
	public void changePassword(UserDataType userDataType) throws Exception;
	public int emailCheck(UserDataType userDataType) throws Exception;
	public List<UserDataType> selectDoctors(SearchParam param) throws Exception;
	public UserDataType selectUserList(UserDataType user) throws Exception;
//	public int updateUser(UserDataType userDataType);
	public void insertRole(UserRoleDataType userRole) throws Exception;
	public List<UserDataType> selectSearchUser(SearchParam param) throws Exception;
	public void updateUser(UserDataType userDataType) throws Exception;
	public void logQRCode(List<LogDataType> logs) throws Exception;
	public void logByLoginCheck(LogDataType logDataType) throws Exception;
	public boolean setLeaveUser(String userID);
}
