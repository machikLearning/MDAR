package kr.ac.cbnu.computerengineering.board.common.datatype;

import kr.ac.cbnu.computerengineering.common.datatype.UserRoleType;

public class BoardRole {
	private UserRoleType userRole;
	private boolean function;
	private boolean role;
	
	public BoardRole(UserRoleType userRole, String function, String role) {
		// TODO Auto-generated constructor stub
		this.userRole = userRole;
		if(function.equals("write")){
			this.function = true;
		}else{
			this.function = false;
		}
		
		if(role.equals("true")){
			this.role = true;
		}else{
			this.role = false;
		}
		 
	}

	public UserRoleType getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRoleType userRole) {
		this.userRole = userRole;
	}

	public boolean isFunction() {
		return function;
	}

	public void setFunction(boolean function) {
		this.function = function;
	}

	public boolean isRole() {
		return role;
	}

	public void setRole(boolean role) {
		this.role = role;
	}

	
}
