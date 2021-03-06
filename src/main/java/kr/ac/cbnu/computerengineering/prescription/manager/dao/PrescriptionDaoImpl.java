package kr.ac.cbnu.computerengineering.prescription.manager.dao;

import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.ac.cbnu.computerengineering.common.datatype.ContentActionPlanDataType;
import kr.ac.cbnu.computerengineering.common.datatype.PrescriptionATCDataType;
import kr.ac.cbnu.computerengineering.common.datatype.PrescriptionDataType;
import kr.ac.cbnu.computerengineering.common.datatype.PrescriptionTemplateDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;
import kr.ac.cbnu.computerengineering.common.managers.dao.IPrescriptionDao;
import kr.ac.cbnu.computerengineering.common.mybatis.Mybatis;

public class PrescriptionDaoImpl implements IPrescriptionDao {

	private SqlSessionFactory sqlSessionFactory;
	
	public PrescriptionDaoImpl(){
		sqlSessionFactory = Mybatis.getSqlSessionFactory();
	}
	


	@Override
	public SqlSession getSession() {
		return this.sqlSessionFactory.openSession(false);
	}


	@Override
	public int insertPrescription(PrescriptionDataType prescriptionDataType) throws Exception {
		SqlSession sqlSession = this.sqlSessionFactory.openSession(true);
		int prescriptionNo = -1;
		try{
			sqlSession.insert("prescription.insertPrescription", prescriptionDataType);
			prescriptionNo = prescriptionDataType.getPrescriptionID();
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}finally{
			sqlSession.close();
		}
		return prescriptionNo;
	}

	@Override
	public void deleteChangeProhibition(int prohibitionID) throws Exception {
		SqlSession sqlSession = this.sqlSessionFactory.openSession(true);
		try {
			sqlSession.delete("prescription.deleteChangeProhibition",prohibitionID);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			sqlSession.close();
		}
	}


	@Override
	public void deleteChangeUpper(int prohibitionID) throws Exception {
		SqlSession sqlSession = this.sqlSessionFactory.openSession(true);
		try {
			sqlSession.delete("prescription.deleteChangeUpper",prohibitionID);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			sqlSession.close();
		}
	}


	@Override
	public void deleteChageTolerable(int prohibitionID) throws Exception {
		SqlSession sqlSession = this.sqlSessionFactory.openSession(true);
		try {
			sqlSession.delete("prescription.deleteChangeTolerable",prohibitionID);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			sqlSession.close();
		}
	}
	

	@Override
	public List<PrescriptionDataType> selectMyPrescription(int registrationID) throws Exception {
		SqlSession sqlSession = this.sqlSessionFactory.openSession(true);
		List<PrescriptionDataType> myPrescriptionList = new ArrayList<>();
		try {
			myPrescriptionList = sqlSession.selectList("prescription.selectMyPrescription", registrationID);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			sqlSession.close();
		}
		return myPrescriptionList;
	}
	

	@Override
	public int countTemplateRow(SearchParam searchParam) throws Exception {
		int num = 0;
		SqlSession sqlSession = this.sqlSessionFactory.openSession(true);
		try{
			num = sqlSession.selectOne("prescription.countTemplateRow",searchParam);
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			sqlSession.close();
		}
		return num;
	}



	@Override
	public PrescriptionTemplateDataType selectPrescriptionTemplate(int templateID) throws Exception {
		SqlSession session = this.sqlSessionFactory.openSession(true);
		PrescriptionTemplateDataType prescriptionTemplateDataType = new PrescriptionTemplateDataType();
		try {
			prescriptionTemplateDataType = session.selectOne("prescription.selectPrescriptionTemplate",templateID);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
		return prescriptionTemplateDataType;
	}



	@Override
	public void updatePrescriptionTemplate(PrescriptionTemplateDataType prescriptionTemplateDataType) throws Exception {
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		try {
			sqlSession.update("prescription.updatePrescriptionTemplate",prescriptionTemplateDataType);
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			sqlSession.close();
		}
	}


	@Override
	public List<PrescriptionTemplateDataType> selectPrescriptionDataTypeList(SearchParam searchParam) throws Exception {
		List<PrescriptionTemplateDataType> prescriptionTemplateDataTypeList;
		SqlSession sqlSession = this.sqlSessionFactory.openSession(true);
		try{
			prescriptionTemplateDataTypeList = sqlSession.selectList("prescription.selectPrescriptionDataTypeList",searchParam);
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			sqlSession.close();
		}
		return prescriptionTemplateDataTypeList;
	}


	@Override
	public void insertPrescriptionTemplate(PrescriptionTemplateDataType prescriptionTemplateDataType) throws Exception {
		SqlSession sqlSession = this.sqlSessionFactory.openSession(true);
		try{
			sqlSession.insert("prescription.insertPrescriptionTemplate", prescriptionTemplateDataType);
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public PrescriptionDataType selectOnePrescriptionData(int prescriptionID) throws Exception {
		SqlSession sqlSession = this.sqlSessionFactory.openSession(true);
		PrescriptionDataType result = new PrescriptionDataType();
		try {
			result = sqlSession.selectOne("prescription.selectOnePrescriptionData", prescriptionID);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			sqlSession.close();
		}
		return result;
	}


	@Override
	public int selectRegistrationID(SearchParam param, SqlSession sqlSession) throws Exception {
		int result = 0;
		try {
			result =sqlSession.selectOne("registration.selectRegistrationID", param); 
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return result;
	}


	@Override
	public void insertContentActionPlan(SqlSession sqlSession, ContentActionPlanDataType contentActionPlanDataType) throws Exception {
		try {
			sqlSession.insert("prescription.insertContentActionPlan", contentActionPlanDataType);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}


	@Override
	public ContentActionPlanDataType selectContentActionPlanDataType(SqlSession sqlSession, int prescriptionID) throws Exception {
		ContentActionPlanDataType result = new ContentActionPlanDataType();
		try {
			result =sqlSession.selectOne("prescription.selectContentActionPlanDataType",prescriptionID); 
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return result;
	}


	@Override
	public void updateContentActionPlan(SqlSession sqlSession, ContentActionPlanDataType contentActionPlanDataType) throws Exception {
		try {
			sqlSession.update("prescription.updateContentActionPlanDataType",contentActionPlanDataType);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}



	@Override
	public void insertProhibition(SqlSession sqlSession, List<PrescriptionATCDataType> prohibitionCodeList) throws Exception {
		try {
			sqlSession.insert("prescription.insertProhibitionDS",prohibitionCodeList);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}


	@Override
	public void insertTolerable(SqlSession sqlSession, List<PrescriptionATCDataType> tolerableCodeList) throws Exception {
		try {
			sqlSession.insert("prescription.insertTolerableDS", tolerableCodeList);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}


	@Override
	public void insertUpper(SqlSession sqlSession, List<PrescriptionATCDataType> upperCodeList) throws Exception {
		try {
			sqlSession.insert("prescription.insertUpperDS", upperCodeList);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	@Override
	public void deletePrescription(int prescriptionID, SqlSession sqlSession) throws Exception {
		try {
			sqlSession.delete("prescription.deletePrescription",prescriptionID);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}


	@Override
	public void deletePrescriptionProhibition(int prescriptionID, SqlSession sqlSession) throws Exception {
		try {
			sqlSession.delete("prescription.deletePrescriptionProhibition", prescriptionID);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}


	@Override
	public void deletePrescriptionUpper(int prescriptionID, SqlSession sqlSession) throws Exception {
		try {
			sqlSession.delete("prescription.deletePrescriptionUpper",prescriptionID);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}


	@Override
	public void deletePrescriptionTolerable(int prescriptionID,SqlSession sqlSession) throws Exception {
		try {
			sqlSession.delete("prescription.deletePrescriptionTolerable",prescriptionID);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}

	@Override
	public void deletePrescriptionDetailText(int prescriptionID, SqlSession sqlSession) throws Exception {
		try {
			sqlSession.delete("prescription.deletePrescriptionDetailText", prescriptionID);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public void deleteTemplate(int templateID, SqlSession sqlSession) throws Exception {
		try {
			sqlSession.delete("prescription.deleteTemplate",templateID);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
