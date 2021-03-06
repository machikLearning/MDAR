package kr.ac.cbnu.computerengineering.common.datatype;

public enum PatientRequestLogDataType {
	CheckMedicine(1),
	QRSearchMedicine(2),
	CheckActionPlan(3),
	SearchMedicineResult(4);
	
	private final int value;
	
	PatientRequestLogDataType(int value){
		this.value = value;
	}
	
	public int getValue(){
		return this.value;
	}
}
