package kr.ac.cbnu.computerengineering.common.datatype;

public enum UserRoleType {
	ADMIN(1), DOCTOR(2), PATIENT(3);
	
	private final int value;
	
	private UserRoleType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public boolean isCorrect(UserRoleType userRoleType){
		if(this.value == userRoleType.getValue()){
			return true;
		}
		return false;
	}
	
}
