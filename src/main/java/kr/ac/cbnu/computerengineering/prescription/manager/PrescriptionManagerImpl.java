package kr.ac.cbnu.computerengineering.prescription.manager;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONObject;

import kr.ac.cbnu.computerengineering.common.datatype.ContentActionPlanDataType;
import kr.ac.cbnu.computerengineering.common.datatype.PrescriptionATCDataType;
import kr.ac.cbnu.computerengineering.common.datatype.PrescriptionDataType;
import kr.ac.cbnu.computerengineering.common.datatype.PrescriptionTemplateDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;
import kr.ac.cbnu.computerengineering.common.managers.IPrescriptionManager;
import kr.ac.cbnu.computerengineering.common.managers.dao.IPrescriptionDao;
import kr.ac.cbnu.computerengineering.prescription.manager.dao.PrescriptionDaoImpl;

public class PrescriptionManagerImpl implements IPrescriptionManager {

	IPrescriptionDao prescriptionDao;
	
	public PrescriptionManagerImpl(){
		prescriptionDao = new PrescriptionDaoImpl();
	}
	@Override
	public int insertPrescription(PrescriptionDataType prescriptionDataType) throws Exception {	
		return this.prescriptionDao.insertPrescription(prescriptionDataType);
	}

	@Override
	public void insertProhibitionTolerableUpper(List<PrescriptionATCDataType> prohibitionCodeList, List<PrescriptionATCDataType> tolerableCodeList,
			List<PrescriptionATCDataType> upperCodeList, ContentActionPlanDataType contentActionPlanDataType) throws Exception {
		SqlSession sqlSession = this.prescriptionDao.getSession();
		try{
			if(prohibitionCodeList != null){
				this.prescriptionDao.insertProhibition(sqlSession,prohibitionCodeList);
			}
			if(tolerableCodeList != null){
				this.prescriptionDao.insertTolerable(sqlSession,tolerableCodeList);
			}
			if(upperCodeList != null){
				this.prescriptionDao.insertUpper(sqlSession,upperCodeList);
			}
			if(contentActionPlanDataType != null){
				try{
					this.prescriptionDao.insertContentActionPlan(sqlSession,contentActionPlanDataType);
				}catch(Exception e){
					this.prescriptionDao.updateContentActionPlan(sqlSession,contentActionPlanDataType);
				}
			}
			sqlSession.commit();
		}catch(Exception e){
			sqlSession.rollback();
			throw new Exception(e.getMessage());
		}finally{
			sqlSession.close();
		}
	}

	@Override
	public PrescriptionDataType selectPrescription(int prescriptionID) throws Exception {
		return this.prescriptionDao.selectOnePrescriptionData(prescriptionID);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject deleteChangeProhibitionUpperToelrable(Integer iD, String medicineKinds) throws Exception {
		JSONObject json = null;
		if(medicineKinds.equals("deleteChangeProhibition")){
			this.prescriptionDao.deleteChangeProhibition(iD);	
		}else if(medicineKinds.equals("deleteChangeUpper")){
			this.prescriptionDao.deleteChangeUpper(iD);
		}else{
			this.prescriptionDao.deleteChageTolerable(iD);
		}
		json = new JSONObject();
		json.put("deleteChangeProhibitionUpperTolerableResult", true);
		return json;
	}
	
	@Override
	public JSONObject deletePrescription(int prescriptionID) throws Exception {
		SqlSession sqlSession = this.prescriptionDao.getSession();
		JSONObject json = new JSONObject();
		try {
			json = this.deletePrescription(prescriptionID, sqlSession);
			sqlSession.commit();
		} catch(Exception e) {
			e.printStackTrace();
			sqlSession.rollback();
			throw new Exception(e.getMessage());
		} finally {
			sqlSession.close();
		}
		return json;
	}

	@Override
	public List<PrescriptionDataType> selectMyPrescription(int registrationID) throws Exception {
		return this.prescriptionDao.selectMyPrescription(registrationID);
	}

	@Override
	public PrescriptionDataType selectPatientInfoRequest(String patientID) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject deletePrescription(int prescriptionID, SqlSession sqlSession) throws Exception {
		JSONObject json = null;
		try{
			this.prescriptionDao.deletePrescriptionProhibition(prescriptionID,sqlSession);
			this.prescriptionDao.deletePrescriptionUpper(prescriptionID,sqlSession);
			this.prescriptionDao.deletePrescriptionTolerable(prescriptionID,sqlSession);
			this.prescriptionDao.deletePrescriptionDetailText(prescriptionID,sqlSession);
			this.prescriptionDao.deletePrescription(prescriptionID,sqlSession);
			json = new JSONObject();
			json.put("deletePrescriptionResult", true);
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
		return json;
	}
	
	@Override
	public int countTemplateRow(SearchParam searchParam) throws Exception {
		return this.prescriptionDao.countTemplateRow(searchParam);
	}

	@Override
	public List<PrescriptionTemplateDataType> selectPrescriptionTemplateDataTypeList(SearchParam searchParam) throws Exception {
		return this.prescriptionDao.selectPrescriptionDataTypeList(searchParam);
	}

	@Override
	public void insertPrescriptionTemplate(PrescriptionTemplateDataType prescriptionTemplateDataType) throws Exception {
		this.prescriptionDao.insertPrescriptionTemplate(prescriptionTemplateDataType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject deleteTemplate(int templateID, int prescriptionID) {
		SqlSession sqlSession = this.prescriptionDao.getSession();
		JSONObject jsonObject = new JSONObject();
		try{
			this.prescriptionDao.deleteTemplate(templateID,sqlSession);
			this.deletePrescription(prescriptionID, sqlSession);
			jsonObject.put("result", "success");
			sqlSession.commit();
		}catch (Exception e) {
			jsonObject.put("result", "fail");
			sqlSession.rollback();
		} finally {
			sqlSession.close();
		} 
		return jsonObject;
	}
	@Override
	public PrescriptionTemplateDataType selectPrescriptionTemplate(int templateID) throws Exception {
		return this.prescriptionDao.selectPrescriptionTemplate(templateID);
	}
	@Override
	public void updatePrescriptionTemplate(PrescriptionTemplateDataType prescriptionTemplateDataType) throws Exception {
		this.prescriptionDao.updatePrescriptionTemplate(prescriptionTemplateDataType);
	}
	@Override
	public void updatePrescription(List<PrescriptionATCDataType> prohibitionCodeList,
			List<PrescriptionATCDataType> tolerableCodeList, List<PrescriptionATCDataType> upperCodeList,
			ContentActionPlanDataType contentActionPlanDataType, int prescriptionID) {
		SqlSession sqlSession = this.prescriptionDao.getSession();
		try{
			this.prescriptionDao.deletePrescriptionProhibition(prescriptionID,sqlSession);
			this.prescriptionDao.deletePrescriptionUpper(prescriptionID,sqlSession);
			this.prescriptionDao.deletePrescriptionTolerable(prescriptionID,sqlSession);
			this.prescriptionDao.deletePrescriptionDetailText(prescriptionID,sqlSession);
			if(prohibitionCodeList != null){
				this.prescriptionDao.insertProhibition(sqlSession,prohibitionCodeList);
			}
			if(tolerableCodeList != null){
				this.prescriptionDao.insertTolerable(sqlSession,tolerableCodeList);
			}
			if(upperCodeList != null){
				this.prescriptionDao.insertUpper(sqlSession,upperCodeList);
			}
			if(contentActionPlanDataType != null){
				try{
					this.prescriptionDao.insertContentActionPlan(sqlSession,contentActionPlanDataType);
				}catch(Exception e){
					this.prescriptionDao.updateContentActionPlan(sqlSession,contentActionPlanDataType);
				}
			}
			sqlSession.commit();
		}catch(Exception e){
			sqlSession.rollback();
		}finally {
			sqlSession.close();
		}
	}
}
