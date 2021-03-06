package kr.ac.cbnu.computerengineering.common.datatype;

public class UserRoleDataType {
	private String userId;
	private UserRoleType userRole;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public UserRoleType getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRoleType userRole) {
		this.userRole = userRole;
	}
	
}
