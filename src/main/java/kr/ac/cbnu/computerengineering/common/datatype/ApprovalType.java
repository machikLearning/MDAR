package kr.ac.cbnu.computerengineering.common.datatype;

public enum ApprovalType {
	APPROVAL(1), REJECTION(2), WAITING(3);
	
	private final int value;
	
	private ApprovalType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
}
