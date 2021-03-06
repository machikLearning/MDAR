package kr.ac.cbnu.computerengineering.common.service;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import org.json.simple.JSONObject;

import kr.ac.cbnu.computerengineering.common.datatype.ApprovalDataType;
import kr.ac.cbnu.computerengineering.common.datatype.LogDataType;
import kr.ac.cbnu.computerengineering.common.datatype.PagingDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;
import kr.ac.cbnu.computerengineering.common.datatype.UserDataType;
import kr.ac.cbnu.computerengineering.common.datatype.UserRoleDataType;
import kr.ac.cbnu.computerengineering.common.datatype.UserRoleType;

public interface IUserService {

	abstract public JSONObject makeJson(UserDataType userDataType,String flag) throws Exception;
	abstract public UserDataType checkLogin(UserDataType userDataType) throws Exception;
	public abstract boolean insertUser(UserDataType userDataType) throws Exception;
	public abstract List<UserRoleType> selectRoles(UserDataType userDataType) throws Exception;
	public abstract boolean updateUser(UserDataType userDataType);
	public abstract UserDataType selectIdByEmail(UserDataType userDataType) throws Exception;
	public abstract String makeMailContents(String content, String flag);
	public abstract UserDataType selectID(UserDataType userDataType) throws Exception;
	public abstract String makeTempPassWord(UserDataType userDataType) throws Exception;
	public PagingDataType getUserListPagingInfo(int page) throws Exception;
	public abstract List<UserDataType> selectAccounts(SearchParam param) throws Exception;
	public abstract PagingDataType getApprovalUserPagingInfo(int page) throws Exception;
	public abstract List<UserDataType> selectDoctors(SearchParam param) throws Exception;
	public abstract void disableAccount(UserDataType userDataType) throws Exception;
	public abstract void updateApprovalById(ApprovalDataType approvalDataType) throws Exception;
	public abstract List<UserDataType> selectSearchUser(SearchParam param) throws Exception;
	public abstract String createUser(String ID, String password, String name, String disable,
			String email, String CBNUCode, String[] roles, Date date, int hospitalId) throws NoSuchAlgorithmException, Exception;
	public abstract void logQRCode(List<LogDataType> logs) throws Exception;
	public abstract void logByLoginCheck(LogDataType logDataType) throws Exception;
	public abstract UserDataType detailUserByID(String iD) throws Exception;
	public abstract boolean setLeaveUser(String userId);
}
