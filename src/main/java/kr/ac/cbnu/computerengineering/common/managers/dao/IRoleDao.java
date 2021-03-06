package kr.ac.cbnu.computerengineering.common.managers.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.ac.cbnu.computerengineering.common.datatype.ApprovalDataType;
import kr.ac.cbnu.computerengineering.common.datatype.UserDataType;
import kr.ac.cbnu.computerengineering.common.datatype.UserRoleDataType;

public interface IRoleDao {
	public List<UserRoleDataType> detailsRoles(UserDataType userDataType) throws Exception;
	public void deleteUserRoleInform(UserDataType userDataType) throws Exception;
	public void deleteRole(UserRoleDataType userRole, SqlSession session) throws Exception;
	public List<UserRoleDataType> selectRole(UserRoleDataType userRoleDataType) throws Exception;
	public void insertRole(UserRoleDataType userRole) throws Exception;
	public SqlSession getSession();
	public SqlSession deleteApproval(UserRoleDataType userRoleDataType, SqlSession session) throws Exception;
	public SqlSession insertRoles(UserRoleDataType userRole, SqlSession session) throws Exception;
	public SqlSession insertApproval(UserRoleDataType userRole, SqlSession session) throws Exception;
	public void updateApprovalById(ApprovalDataType approvalDataType, SqlSession session) throws Exception;
}

