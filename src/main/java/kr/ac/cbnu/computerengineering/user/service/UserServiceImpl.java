package kr.ac.cbnu.computerengineering.user.service;

import java.util.Date;
import java.util.List;
import org.json.simple.JSONObject;

import kr.ac.cbnu.computerengineering.common.datatype.ApprovalDataType;
import kr.ac.cbnu.computerengineering.common.datatype.LogDataType;
import kr.ac.cbnu.computerengineering.common.datatype.PagingDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;
import kr.ac.cbnu.computerengineering.common.datatype.UserDataType;
import kr.ac.cbnu.computerengineering.common.datatype.UserRoleType;
import kr.ac.cbnu.computerengineering.common.managers.IUserManager;
import kr.ac.cbnu.computerengineering.common.service.IUserService;
import kr.ac.cbnu.computerengineering.common.util.Utils;
import kr.ac.cbnu.computerengineering.user.manager.UserManagerImpl;

public class UserServiceImpl implements IUserService {

	private IUserManager userManager;
	
	public UserServiceImpl(){
		this.userManager = new UserManagerImpl();
	}

	@Override
	public UserDataType checkLogin(UserDataType userDataType) throws Exception {
		return  this.userManager.checkLogin(userDataType);
	}

	@Override
	public boolean insertUser(UserDataType userDataType) throws Exception {
		if(this.userManager.insertUser(userDataType) == 1){
			return true;
		}
		return false;
	}

	@Override
	public List<UserRoleType> selectRoles(UserDataType userDataType) throws Exception {
		return Utils.getRoles(this.userManager.selectRoles(userDataType));
	}

	@Override
	public boolean updateUser(UserDataType userDataType) {
		boolean result = false;
		try{
			this.userManager.updateUser(userDataType);
			result = true;
		}catch(Exception e){
			e.printStackTrace();
			result =  false;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject makeJson(UserDataType userDataType, String flag) throws Exception {
		JSONObject json = new JSONObject();
		if(flag.equals("email")){
			if(this.userManager.emailCheck(userDataType) != 0){
				json.put("result", true);
			}else{
				json.put("result", false);
			}
		}else if(flag.equals("id")){
			if(this.userManager.idCheck(userDataType) == 0){
				json.put("result", false);
			}
			else{
				json.put("result", true);
			}
		}
		return json;
	}

	@Override
	public UserDataType selectIdByEmail(UserDataType userDataType) throws Exception {
		return this.userManager.selectIdByEmail(userDataType);
	}

	@Override
	public String makeMailContents(String content, String flag) {
		String result = null;
		String schoolName = "충북대학교";
		if(content != null){
			result = schoolName + " "+ flag + "는 " + content + " 입니다";
		}
		return result;
	}

	@Override
	public UserDataType selectID(UserDataType userDataType) throws Exception {
		UserDataType temp = this.userManager.selectEmailById(userDataType);
		if(temp != null){
			userDataType.setEmail(temp.getEmail());
			userDataType.setCbnuCode(temp.getCbnuCode());
		}
		return userDataType;
	}

	@Override
	public String makeTempPassWord(UserDataType userDataType) throws Exception {
		String random = Utils.base64(userDataType.getUserId() + userDataType.getCbnuCode());
		String pw = random.substring(0,5);
		userDataType.setPassword(Utils.encryptSHA256(pw));
		this.userManager.changePassword(userDataType);
		return pw;
	}

	@Override
	public PagingDataType getUserListPagingInfo(int page) throws Exception {
		return Utils.computePagingData(this.userManager.selectAllUserCounts(), page);
	}

	@Override
	public List<UserDataType> selectAccounts(SearchParam param) throws Exception {
		return this.userManager.selectAccounts(param);
	}

	@Override
	public PagingDataType getApprovalUserPagingInfo(int page) throws Exception {
		return Utils.computePagingData(this.userManager.selectAllApprovalCounts(), page);
	}

	@Override
	public List<UserDataType> selectDoctors(SearchParam param) throws Exception {
		return this.userManager.selectDoctors(param);
	}

	@Override
	public void disableAccount(UserDataType userDataType) throws Exception {
		this.userManager.disableAccount(userDataType);		
	}

	@Override
	public void updateApprovalById(ApprovalDataType approvalDataType) throws Exception {
		this.userManager.updateApprovalById(approvalDataType);
	}

	@Override
	public List<UserDataType> selectSearchUser(SearchParam param) throws Exception {
		return this.userManager.selectSearchUser(param);
	}

	@Override
	public String createUser(String ID, String password, String name, String disable, String email, String CBNUCode,
			String[] roles, Date date, int hospitalId) throws Exception {
		UserDataType userDataType = Utils.makeUserDataType(ID, 
				password, name, disable, email, CBNUCode, roles,date, hospitalId, null);
		String result = "";
		if(this.insertUser(userDataType)){
			result += "JOIN_SUCCESS";
		} else {
			result += "REJECT";
		}
		return result;
	}

	@Override
	public void logQRCode(List<LogDataType> logs) throws Exception {
		this.userManager.logQRCode(logs);
	}

	@Override
	public void logByLoginCheck(LogDataType logDataType) throws Exception {
		this.userManager.logByLoginCheck(logDataType);
	}

	@Override
	public UserDataType detailUserByID(String iD) throws Exception {
		UserDataType user = new UserDataType();
		user.setUserId(iD);
		return this.userManager.selectUserList(user);
	}

	@Override
	public boolean setLeaveUser(String userID) {
		// TODO Auto-generated method stub
		return this.userManager.setLeaveUser(userID);
	}

}
