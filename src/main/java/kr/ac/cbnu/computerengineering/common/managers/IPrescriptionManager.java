package kr.ac.cbnu.computerengineering.common.managers;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONObject;
import kr.ac.cbnu.computerengineering.common.datatype.ContentActionPlanDataType;
import kr.ac.cbnu.computerengineering.common.datatype.PrescriptionATCDataType;
import kr.ac.cbnu.computerengineering.common.datatype.PrescriptionDataType;
import kr.ac.cbnu.computerengineering.common.datatype.PrescriptionTemplateDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;

public interface IPrescriptionManager {
	public int insertPrescription(PrescriptionDataType prescriptionDataTypeDS) throws Exception;
	public void insertProhibitionTolerableUpper(List<PrescriptionATCDataType> prohibitionCodeList, List<PrescriptionATCDataType> tolerableCodeList,
			List<PrescriptionATCDataType> upperCodeList, ContentActionPlanDataType contentActionPlanDataType) throws Exception;
	public PrescriptionDataType selectPrescription(int prescriptionID) throws Exception;
	public JSONObject deleteChangeProhibitionUpperToelrable(Integer iD, String medicineKinds) throws Exception;
	public JSONObject deletePrescription(int prescriptionID) throws Exception;
	public PrescriptionDataType selectPatientInfoRequest(String patientID);
	public List<PrescriptionDataType> selectMyPrescription(int registrationID) throws Exception;
	public JSONObject deletePrescription(int prescriptionID, SqlSession sqlSession) throws Exception;
	public int countTemplateRow(SearchParam searchParam) throws Exception;
	public List<PrescriptionTemplateDataType> selectPrescriptionTemplateDataTypeList(SearchParam searchParam) throws Exception;
	public void insertPrescriptionTemplate(PrescriptionTemplateDataType prescriptionTemplateDataType) throws Exception;
	public JSONObject deleteTemplate(int templateID, int prescriptionID);
	public PrescriptionTemplateDataType selectPrescriptionTemplate(int templateID) throws Exception;
	public void updatePrescriptionTemplate(PrescriptionTemplateDataType prescriptionTemplateDataType) throws Exception;
	public void updatePrescription(List<PrescriptionATCDataType> prohibitionCodeList,
			List<PrescriptionATCDataType> tolerableCodeList, List<PrescriptionATCDataType> upperCodeList,
			ContentActionPlanDataType contentActionPlanDataType, int prescriptionID);
}
