package kr.ac.cbnu.computerengineering.user.manager.dao;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.ac.cbnu.computerengineering.common.datatype.ApprovalDataType;
import kr.ac.cbnu.computerengineering.common.datatype.LogDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;
import kr.ac.cbnu.computerengineering.common.datatype.UserDataType;
import kr.ac.cbnu.computerengineering.common.datatype.UserRoleDataType;
import kr.ac.cbnu.computerengineering.common.datatype.UserRoleType;
import kr.ac.cbnu.computerengineering.common.managers.dao.IUserDao;
import kr.ac.cbnu.computerengineering.common.mybatis.Mybatis;

public class UserDaoImpl implements IUserDao {
	private SqlSessionFactory sqlSessionFactory;
	
	public UserDaoImpl() {
		this.sqlSessionFactory = Mybatis.getSqlSessionFactory();
	}
	
	@Override
	public UserDataType detailUser(UserDataType userDataType) throws Exception {
		SqlSession session = this.sqlSessionFactory.openSession(true);
		UserDataType result = null;
		try {
			result = session.selectOne("user.detailUser",userDataType);
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
		
		return result;
	}
	
	public int idCheck(UserDataType userDataType) throws Exception {
		SqlSession session = this.sqlSessionFactory.openSession();
		int result = 0;
		try {
			
			result = session.selectOne("user.idCheck",userDataType);
			
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
		
		return result;
	}

	@Override
	public int emailCheck(UserDataType userDataType) throws Exception {
		SqlSession session = this.sqlSessionFactory.openSession();
		int result = 0;
		try {
			result = session.selectOne("user.emailCheck",userDataType);
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
		
		return result;
	}
	
	public int insert(UserDataType userDataType) throws Exception{
		SqlSession session = this.sqlSessionFactory.openSession(false);
		int result = 0;
		try {
			session.insert("user.insertUser", userDataType);
			for(UserRoleDataType userRole : userDataType.getRoles()) {
				if(userRole.getUserRole() == UserRoleType.DOCTOR) {
					session.insert("user.insertApproval", userRole);
				}
				session.insert("user.insertRoles", userRole);			
			}
			result = 1;
			session.commit();
		} catch(PersistenceException e){
			session.rollback();
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
		
		return result;
	}
	
	public void updateUserInform(UserDataType userDataType) throws Exception {
		SqlSession session = this.sqlSessionFactory.openSession(true);
		try {
			session.update("user.updateUserInform", userDataType);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			session.close();
		}
		
	}

	public void disableAccount(UserDataType userDataType) throws Exception{
		SqlSession session = this.sqlSessionFactory.openSession(true);
		try {
			session.update("user.disableAccount", userDataType);
		}catch(Exception e) {
			throw new Exception("user.disableAccount"+e.getMessage());
		} finally {
			session.close();
		}
	}
	
	@Override
	public List<UserDataType> selectAccounts(SearchParam param) throws Exception{
		SqlSession session = this.sqlSessionFactory.openSession();
		List<UserDataType> result = null;
		try {
			result = session.selectList("user.selectAccounts",param);
		}catch(Exception e) {
			throw new Exception("user.selectAccounts"+e.getMessage());
		} finally {
			session.close();
		}
		
		return result;
	}
	
	@Override
	public int selectAllUserCounts() throws Exception{
		SqlSession session = this.sqlSessionFactory.openSession();
		int result = 0;
		try {
			
			result = session.selectOne("user.selectAllUserCounts");

		}catch(Exception e) {
			throw new Exception("user.selectAllUserCounts"+e.getMessage());
		} finally {
			session.close();
		}
		return result;
	}
	public void deleteUser(UserDataType userDataType) throws Exception{
		SqlSession session = this.sqlSessionFactory.openSession(true);
		try {
			
			session.delete("user.deleteUser",userDataType);

		}catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
	}
	
	public List<UserDataType> selectAccountById(SearchParam param) throws Exception{
		SqlSession session = this.sqlSessionFactory.openSession();
		List<UserDataType> result = null;
		try {
			result = session.selectList("user.selectAccountById",param);
			
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
		
		return result;
	}
	public List<UserDataType> selectAccountByName(SearchParam param) throws Exception{
		SqlSession session = this.sqlSessionFactory.openSession();
		List<UserDataType> result = null;
		try {
			result = session.selectList("user.selectAccountByName",param);
			
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
		
		return result;
	}
	public List<ApprovalDataType> selectApprovals(SearchParam param) throws Exception{
		SqlSession session = this.sqlSessionFactory.openSession();
		List<ApprovalDataType> result = null;
		try {
			result = session.selectList("user.selectApprovals",param);
			
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
		
		return result;	
	}
	
	@Override
	public int selectAllApprovalCounts() throws Exception{
		SqlSession session = this.sqlSessionFactory.openSession();
		int result = 0;
		try {
			result = session.selectOne("user.selectAllApprovalCounts");
		}catch(Exception e) {
			throw new Exception("user.selectAllApprovalCounts" + e.getMessage());
		} finally {
			session.close();
		}
		return result;
	}
	public List<ApprovalDataType> selectApprovalById(ApprovalDataType approvalDataType) throws Exception{
		SqlSession session = this.sqlSessionFactory.openSession();
		List<ApprovalDataType> result = null;
		try {
			result = session.selectList("user.selectApprovalById", approvalDataType);
		}catch(Exception e) {
			throw new Exception("user.selectApprovalById" + e.getMessage());
		} finally {
			session.close();
		}
		return result;
	}
	public void updateApprovalById(ApprovalDataType approvalDataType) throws Exception{
		SqlSession session = this.sqlSessionFactory.openSession(true);
		try {
			
			session.update("user.updateApprovalById", approvalDataType);

		}catch(Exception e) {
			throw new Exception("user.updateApprovalById"+e.getMessage());
		} finally {
			session.close();
		}

	}
	public void deleteApproval(ApprovalDataType approvalDataType) throws Exception{
		SqlSession session = this.sqlSessionFactory.openSession(true);
		try {
			
			session.delete("user.deleteApproval",approvalDataType);

		}catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
	}
	public int cbnuCodeCheck(UserDataType userDataType) throws Exception{
		SqlSession session = this.sqlSessionFactory.openSession();
		int result = 0;
		try {
			
			result = session.selectOne("user.cbnuCodeCheck",userDataType);
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
		return result;
	}
	public void insertRoleAndUpdateApproval(UserRoleDataType userRoleDataType, ApprovalDataType approvalDataType) throws Exception{ // 이거
		SqlSession session = this.sqlSessionFactory.openSession(true);
		try {
			session.insert("user.insertRoles", userRoleDataType);
			session.update("user.updateApprovalById", approvalDataType);
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
	}
	public void deleteRoleAndUpdateApproval(UserRoleDataType userRoleDataType, ApprovalDataType approvalDataType) throws Exception{ // 이거
		SqlSession session = this.sqlSessionFactory.openSession(true);
		try {
			session.delete("user.deleteRole", userRoleDataType);
			session.update("user.updateApprovalById", approvalDataType);
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
	}
	public void deleteApprovalAndInsertRole(UserRoleDataType userRoleDataType, ApprovalDataType approvalDataType) throws Exception{ // 이거
		SqlSession session = this.sqlSessionFactory.openSession(true);
		try {
			session.delete("user.deleteApproval", approvalDataType);
			session.insert("user.insertRoles", userRoleDataType);
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
	}
	public UserDataType selectIdByCbnuCode(UserDataType userDataType) throws Exception{
		SqlSession session = this.sqlSessionFactory.openSession();
		UserDataType result = null;
		try {
			
			result = session.selectOne("user.selectIdByCbnuCode", userDataType);

		}catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
		return result;
	}
	public UserDataType selectEmailById(UserDataType userDataType) throws Exception{
		SqlSession session = this.sqlSessionFactory.openSession();
		UserDataType result = null;
		try {
			
			result = session.selectOne("user.selectEMAILById", userDataType);

		}catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
		return result;
	}
	public UserDataType selectIdByEmail(UserDataType userDataType) throws Exception{
		SqlSession session = this.sqlSessionFactory.openSession();
		UserDataType result = null;
		try {
			
			result = session.selectOne("user.selectIdByEmail", userDataType);

		}catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
		return result;
	}
	public UserDataType selectMailByIdAndCode(UserDataType userDataType) throws Exception{
		SqlSession session = this.sqlSessionFactory.openSession();
		UserDataType result = null;
		try {
			
			result = session.selectOne("user.selectMailByIdAndCode", userDataType);

		}catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
		return result;
	}
	public void changePassword(UserDataType userDataType) throws Exception{
		SqlSession session = this.sqlSessionFactory.openSession(true);
		try {
			session.update("user.changePassword", userDataType);
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public UserDataType selectPassword(UserDataType userDataType) throws Exception {
		SqlSession session = this.sqlSessionFactory.openSession();
		UserDataType result = null;
		try {
			result=session.selectOne("user.selectPassword",userDataType);
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally{
			session.close();
		}
		return result;
	}

	@Override
	public int approvalCheck(ApprovalDataType approvalDataType) throws Exception {
		SqlSession session = this.sqlSessionFactory.openSession();
		int result = 0;
		try {
			result = session.selectOne("user.approvalCheck",approvalDataType);
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
		return result;
	}

	@Override
	public List<UserDataType> selectDoctors(SearchParam param) throws Exception {
		SqlSession session = this.sqlSessionFactory.openSession();
		List<UserDataType> result = null;
		try {
			result = session.selectList("user.selectDoctors",param);
		}catch(Exception e) {
			throw new Exception("user.selectDoctors"+e.getMessage());
		} finally {
			session.close();
		}
		
		return result;
	}

	@Override
	public UserDataType selectUserList(UserDataType user) throws Exception {
		SqlSession session = this.sqlSessionFactory.openSession();
		UserDataType result = null;
		try{
			result = session.selectOne("user.selectUserList",user);
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally{
			session.close();
		}
		return result;
	}

	@Override
	public int updateUser(UserDataType userDataType) throws Exception {
		SqlSession session = this.sqlSessionFactory.openSession(false);
		int result = 0;
		try {
			session.update("user.updateUser", userDataType);
			result = 1;
			session.commit();
		} catch(PersistenceException e){
			session.rollback();
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
		
		return result;
	}

	@Override
	public SqlSession getSession() {
		return this.sqlSessionFactory.openSession(false);
	}

	@Override
	public SqlSession insertNewUser(UserDataType userDataType, SqlSession session) throws Exception {
		try{
			session.insert("user.insertUser", userDataType);
			return session;
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public void insertRoles(UserRoleDataType userRole, SqlSession session) throws Exception {
		try {
			session.insert("user.insertRoles", userRole);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public void updateApprovalById(ApprovalDataType approvalDataType, SqlSession session) throws Exception {
		try {
			session.update("user.updateApprovalById", approvalDataType);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public SqlSession deleteRole(UserDataType user, SqlSession session) throws Exception {
		try{
			session.delete("user.deleteRole2", user);
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
		return session;
	}

	@Override
	public List<UserDataType> selectSearchUSer(SearchParam param) throws Exception {
		SqlSession sqlSession = this.sqlSessionFactory.openSession(true);
		List<UserDataType> userDataTypeList = null;
		try{
			userDataTypeList = sqlSession.selectList("user.selectSearchUser",param);
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}finally{
			sqlSession.close();
		}
		return userDataTypeList;
	}

	@Override
	public void logQRCode(List<LogDataType> logs) throws Exception {
		SqlSession sqlSession = this.sqlSessionFactory.openSession(true);
		try {
			sqlSession.insert("user.insertLogQRCode", logs);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public void logByLoginCheck(LogDataType logDataType) throws Exception {
		SqlSession sqlSession = this.sqlSessionFactory.openSession(true);
		try {
			sqlSession.insert("user.insertLogByLoginCheck",logDataType);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public boolean setLeaveUser(String userID) {
		SqlSession session = this.sqlSessionFactory.openSession(true);
		boolean result = false;
		try{
			session.update("user.setLeaveUser",userID);
			result= true;
		}catch(Exception e){
			result= false;
		}finally{
			session.close();
		}
		return result;
	}

}
