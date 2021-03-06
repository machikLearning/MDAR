package kr.ac.cbnu.computerengineering.user.manager;

import java.util.List;
import org.apache.ibatis.session.SqlSession;

import kr.ac.cbnu.computerengineering.common.datatype.ApprovalDataType;
import kr.ac.cbnu.computerengineering.common.datatype.LogDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;
import kr.ac.cbnu.computerengineering.common.datatype.UserDataType;
import kr.ac.cbnu.computerengineering.common.datatype.UserRoleDataType;
import kr.ac.cbnu.computerengineering.common.datatype.UserRoleType;
import kr.ac.cbnu.computerengineering.common.managers.IUserManager;
import kr.ac.cbnu.computerengineering.common.managers.dao.IRoleDao;
import kr.ac.cbnu.computerengineering.common.managers.dao.IUserDao;
import kr.ac.cbnu.computerengineering.user.manager.dao.RoleDaoImpl;
import kr.ac.cbnu.computerengineering.user.manager.dao.UserDaoImpl;

public class UserManagerImpl implements IUserManager {
	private IUserDao userDao;
	private IRoleDao roleDao;
	
	public UserManagerImpl() {
		this.userDao = new UserDaoImpl();
		this.roleDao = new RoleDaoImpl();
	}
	@Override
	public UserDataType getUser(UserDataType userDataType) throws Exception {
		return this.userDao.detailUser(userDataType);
	}
	
	public int insertUser(UserDataType userDataType) throws Exception{
		SqlSession session = this.userDao.getSession();
		int result = 0;
		try{
			this.userDao.insertNewUser(userDataType,session);
			for(UserRoleDataType userRole : userDataType.getRoles()){
				if(userRole.getUserRole() == UserRoleType.DOCTOR){
					this.roleDao.insertApproval(userRole,session);
				}
				this.roleDao.insertRoles(userRole, session);
			}
			result =1;
			session.commit();
		}catch(Exception e){
			session.rollback();
			throw new Exception(e.getMessage());
		}finally{
			session.close();
		}
		
		return result;
	}
	public UserDataType checkLogin(UserDataType userDataType) throws Exception{
		return this.userDao.detailUser(userDataType);
	}
	public List<UserRoleDataType> selectRoles(UserDataType userDataType) throws Exception{
		return this.roleDao.detailsRoles(userDataType);
	}
	public int idCheck(UserDataType userDataType) throws Exception{
		return this.userDao.idCheck(userDataType);
	}
	@Override
	public int emailCheck(UserDataType userDataType) throws Exception {
		return this.userDao.emailCheck(userDataType);
	}
	public void changeUserInform(UserDataType userDataType, boolean isDoctor){
		// TODO 의사이고 수정한게 의사이면 지우지 않기
//		this.roleDao.deleteUserRoleInform(userDataType);
//		this.userDao.updateUserInform(userDataType);
//		ApprovalDataType approvalDataType = new ApprovalDataType();
//		approvalDataType.setUserId(userDataType.getUserId());
//		
//		boolean doctorSameFlag = false;
//		for(UserRoleDataType type : userDataType.getRoles()) {
//			if(isDoctor == true)
//			{
//				if(type.getUserRole().equals(UserRoleType.DOCTOR)){
//					doctorSameFlag = true;
//				}
//				this.roleDao.insert(type);
//
//			}
//			else{
//				if(type.getUserRole().equals(UserRoleType.DOCTOR)){
//					this.roleDao.insertApproval(type);
//				}
//				else{
//					this.roleDao.insert(type);
//				}
//			}
//		}
//		
//		if(isDoctor == true && doctorSameFlag == false){
//			this.userDao.deleteApproval(approvalDataType);
//		}
	}
	public void disableAccount(UserDataType userDataType) throws Exception{
		this.userDao.disableAccount(userDataType);
	}
	@Override
	public List<UserDataType> selectAccounts(SearchParam param) throws Exception {
		return this.userDao.selectAccounts(param);
	}
	@Override
	public int selectAllUserCounts() throws Exception {
		return this.userDao.selectAllUserCounts();
	}
	public void deleteUser(UserDataType userDataType) throws Exception{
		this.userDao.deleteUser(userDataType);
	}
	public void deleteUserRoleInform(UserDataType userDataType) throws Exception{
		this.roleDao.deleteUserRoleInform(userDataType);
	}
	public List<UserDataType> selectAccountById(SearchParam param) throws Exception{
		return this.userDao.selectAccountById(param);
	}
	public List<UserDataType> selectAccountByName(SearchParam param) throws Exception{
		return this.userDao.selectAccountByName(param);
	}
	public List<ApprovalDataType> selectApprovals(SearchParam param) throws Exception{
		return this.userDao.selectApprovals(param);
	}
	public int selectAllApprovalCounts() throws Exception{
		return this.userDao.selectAllApprovalCounts();
	}
	public List<ApprovalDataType> selectApprovalById(ApprovalDataType approvalDataType) throws Exception{
		return this.userDao.selectApprovalById(approvalDataType);
	}
	public void updateApprovalById(ApprovalDataType approvalDataType) throws Exception{
		this.userDao.updateApprovalById(approvalDataType);
	}
	public void deleteRole(UserRoleDataType userRoleDataType) throws Exception{
		SqlSession session = this.roleDao.getSession();
		try{
			this.roleDao.deleteRole(userRoleDataType,session);
			if(userRoleDataType.getUserRole() == UserRoleType.DOCTOR) {
				this.roleDao.deleteApproval(userRoleDataType,session);
			}
			session.commit();
		}catch(Exception e){
			session.rollback();
			throw new Exception(e.getMessage());
		}finally{
			session.close();
		}
	}
	public List<UserRoleDataType> selectRole(UserRoleDataType userRoleDataType) throws Exception{
		return this.roleDao.selectRole(userRoleDataType);
	}
	public void deleteApproval(ApprovalDataType approvalDataType) throws Exception{
		this.userDao.deleteApproval(approvalDataType);
	}
	public int cbnuCodeCheck(UserDataType userDataType) throws Exception{
		return this.userDao.cbnuCodeCheck(userDataType);
	}
	
	public void insertRoleAndUpdateApproval(UserRoleDataType userRoleDataType, ApprovalDataType approvalDataType) throws Exception{
		SqlSession session = this.userDao.getSession();
		try{
			this.userDao.insertRoles(userRoleDataType, session);
			this.userDao.updateApprovalById(approvalDataType,session);
			session.commit();
		}catch(Exception e){
			session.rollback();
			throw new Exception(e.getMessage());
		}finally{
			session.close();
		}
	}
	public void deleteRoleAndUpdateApproval(UserRoleDataType userRoleDataType, ApprovalDataType approvalDataType) throws Exception{
		SqlSession session = this.userDao.getSession();
		try{
			this.roleDao.deleteRole(userRoleDataType, session);
			this.roleDao.updateApprovalById(approvalDataType, session);
			session.commit();
		}catch(Exception e){
			session.rollback();
			throw new Exception(e.getMessage());
		}finally{
			session.close();
		}
		
	}
	public UserDataType selectIdByCbnuCode(UserDataType userDataType) throws Exception{
		return this.userDao.selectIdByCbnuCode(userDataType);
	}
	public UserDataType selectEmailById(UserDataType userDataType) throws Exception{
		return this.userDao.selectEmailById(userDataType);
	}
	public UserDataType selectIdByEmail(UserDataType userDataType) throws Exception{
		return this.userDao.selectIdByEmail(userDataType);
	}
	public UserDataType selectMailByIdAndCode(UserDataType userDataType) throws Exception{
		return this.userDao.selectMailByIdAndCode(userDataType);
	}
	public void changePassword(UserDataType userDataType) throws Exception{
		this.userDao.changePassword(userDataType);
	}
	public UserDataType selectPassword(UserDataType userDataType) throws Exception{
		return this.userDao.selectPassword(userDataType);
	}
	public List<UserDataType> selectDoctors(SearchParam param) throws Exception {
		return this.userDao.selectDoctors(param);
	}
	
	@Override
	public UserDataType selectUserList(UserDataType user) throws Exception {
		return this.userDao.selectUserList(user);
	}
	@Override
	public void insertRole(UserRoleDataType userRole) throws Exception {	
		this.roleDao.insertRole(userRole);
	}
	@Override
	public List<UserDataType> selectSearchUser(SearchParam param) throws Exception {
		return this.userDao.selectSearchUSer(param);
	}
	@Override
	public void updateUser(UserDataType userDataType) throws Exception {
		SqlSession sqlSession = this.userDao.getSession();
		try{
			this.userDao.updateUser(userDataType);
			this.userDao.deleteRole(userDataType, sqlSession);
			for(UserRoleDataType addRole : userDataType.getRoles()){
				this.userDao.insertRoles(addRole, sqlSession);
			}
			sqlSession.commit();
		}catch(Exception e){
			sqlSession.rollback();
			throw new Exception();
		}finally{
			sqlSession.close();
		}
		
	}
	@Override
	public void logQRCode(List<LogDataType> logs) throws Exception {
		this.userDao.logQRCode(logs);
	}
	@Override
	public void logByLoginCheck(LogDataType logDataType) throws Exception {
		this.userDao.logByLoginCheck(logDataType);
	}
	@Override
	public boolean setLeaveUser(String userID) {
		// TODO Auto-generated method stub
		return this.userDao.setLeaveUser(userID);
	}
}
