package kr.ac.cbnu.computerengineering.common.managers.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import kr.ac.cbnu.computerengineering.common.datatype.ContentActionPlanDataType;
import kr.ac.cbnu.computerengineering.common.datatype.PrescriptionATCDataType;
import kr.ac.cbnu.computerengineering.common.datatype.PrescriptionDataType;
import kr.ac.cbnu.computerengineering.common.datatype.PrescriptionTemplateDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;

public interface IPrescriptionDao {
	public SqlSession getSession();
	public int insertPrescription(PrescriptionDataType prescriptionDataType) throws Exception;
	public void insertProhibition(SqlSession sqlSession, List<PrescriptionATCDataType> prohibitionCodeList) throws Exception;
	public void insertTolerable(SqlSession sqlSession, List<PrescriptionATCDataType> tolerableCodeList) throws Exception;
	public void insertUpper(SqlSession sqlSession, List<PrescriptionATCDataType> upperCodeList) throws Exception;
	public void deleteChangeProhibition(int iD) throws Exception;
	public void deleteChangeUpper(int iD) throws Exception;
	public void deleteChageTolerable(int iD) throws Exception;
	void deletePrescription(int prescriptionID, SqlSession sqlSession) throws Exception;
	void deletePrescriptionProhibition(int prescriptionID, SqlSession sqlSession) throws Exception;
	void deletePrescriptionUpper(int prescriptionID, SqlSession sqlSession) throws Exception;
	void deletePrescriptionTolerable(int prescriptionID, SqlSession sqlSession) throws Exception;
	public List<PrescriptionDataType> selectMyPrescription(int registrationID) throws Exception;
	public int selectRegistrationID(SearchParam param, SqlSession sqlSession) throws Exception;
	public void insertContentActionPlan(SqlSession sqlSession, ContentActionPlanDataType contentActionPlanDataType) throws Exception;
	public ContentActionPlanDataType selectContentActionPlanDataType(SqlSession sqlSession, int prescriptionID) throws Exception;
	public void updateContentActionPlan(SqlSession sqlSession, ContentActionPlanDataType contentActionPlanDataType) throws Exception;
	public PrescriptionDataType selectOnePrescriptionData(int prescriptionID) throws Exception;
	public void deletePrescriptionDetailText(int prescriptionID, SqlSession sqlSession) throws Exception;
	public int countTemplateRow(SearchParam searchParam) throws Exception;
	public List<PrescriptionTemplateDataType> selectPrescriptionDataTypeList(SearchParam searchParam) throws Exception;
	public void insertPrescriptionTemplate(PrescriptionTemplateDataType prescriptionTemplateDataType) throws Exception;
	public void deleteTemplate(int templateID, SqlSession sqlSession) throws Exception;
	public PrescriptionTemplateDataType selectPrescriptionTemplate(int templateID) throws Exception;
	public void updatePrescriptionTemplate(PrescriptionTemplateDataType prescriptionTemplateDataType) throws Exception;
}
