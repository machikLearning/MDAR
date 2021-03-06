package kr.ac.cbnu.computerengineering.user.manager.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.ac.cbnu.computerengineering.common.datatype.ApprovalDataType;
import kr.ac.cbnu.computerengineering.common.datatype.UserDataType;
import kr.ac.cbnu.computerengineering.common.datatype.UserRoleDataType;
import kr.ac.cbnu.computerengineering.common.datatype.UserRoleType;
import kr.ac.cbnu.computerengineering.common.managers.dao.IRoleDao;
import kr.ac.cbnu.computerengineering.common.mybatis.Mybatis;

public class RoleDaoImpl implements IRoleDao {

	private SqlSessionFactory sqlSessionFactory;
	
	public RoleDaoImpl(){
		this.sqlSessionFactory = Mybatis.getSqlSessionFactory();
	}
	
	public void deleteUserRoleInform(UserDataType userDataType) throws Exception{
		SqlSession session = this.sqlSessionFactory.openSession(true);
		try {
			session.delete("user.deleteUserRoleInform", userDataType);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
		
	}
	public List<UserRoleDataType> selectRole(UserRoleDataType userRoleDataType) throws Exception{
		SqlSession session = this.sqlSessionFactory.openSession(true);
		List<UserRoleDataType> result = null;
		try {
			result = session.selectList("user.selectRole", userRoleDataType);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally{
			session.close();
		}
		return result;
	}
	
	public List<UserRoleDataType> detailsRoles(UserDataType userDataType) throws Exception{
		SqlSession session = this.sqlSessionFactory.openSession(true);
		List<UserRoleDataType> result=null;
		try{
			result = session.selectList("user.detailUserRole", userDataType);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally{
			session.close();
		}
		return result;
	}

	@Override
	public void insertRole(UserRoleDataType userRole) throws Exception {
		SqlSession session = this.sqlSessionFactory.openSession(true);
		try {
			session.insert("user.insertRoles", userRole);
			if(userRole.getUserRole() == UserRoleType.DOCTOR) {
				session.insert("user.insertApproval", userRole);
			}
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally{
			session.close();
		}
	}


	@Override
	public SqlSession deleteApproval(UserRoleDataType userRoleDataType, SqlSession session) throws Exception {
		try{
			session.delete("user.deleteApproval", userRoleDataType);
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
		return session;
	}
	
	@Override
	public SqlSession insertRoles(UserRoleDataType userRole, SqlSession session) throws Exception {
		try{
			session.insert("user.insertRoles", userRole);
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
		return session;
	}

	@Override
	public SqlSession insertApproval(UserRoleDataType userRole, SqlSession session) throws Exception {
		try{
			session.insert("user.insertApproval", userRole);
			return session;
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
	}
	
	@Override
	public SqlSession getSession() {
		return this.sqlSessionFactory.openSession(false);
	}

	@Override
	public void updateApprovalById(ApprovalDataType approvalDataType, SqlSession session) throws Exception {
		try {
			session.update("user.updateApprovalById", approvalDataType);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public void deleteRole(UserRoleDataType userRole, SqlSession session) throws Exception{
		try {
			session.delete("user.deleteRole", userRole);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	
}
