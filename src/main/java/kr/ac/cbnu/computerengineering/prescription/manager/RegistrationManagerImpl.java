package kr.ac.cbnu.computerengineering.prescription.manager;

import kr.ac.cbnu.computerengineering.common.datatype.PrescriptionDataType;
import kr.ac.cbnu.computerengineering.common.datatype.RegistrationDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;
import kr.ac.cbnu.computerengineering.common.datatype.UserDataType;
import kr.ac.cbnu.computerengineering.common.managers.IPrescriptionManager;
import kr.ac.cbnu.computerengineering.common.managers.IRegistrationManager;
import kr.ac.cbnu.computerengineering.common.managers.dao.IRegistrationDao;
import kr.ac.cbnu.computerengineering.prescription.manager.dao.RegistrationDaoImpl;
import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONObject;

import java.util.List;

public class RegistrationManagerImpl implements IRegistrationManager {
	
	IRegistrationDao registrationDao;
			
	public RegistrationManagerImpl(){
		this.registrationDao = new RegistrationDaoImpl();
	}
	
	@Override
	public List<RegistrationDataType> detailPatientList(UserDataType userDataType) throws Exception{
		return this.registrationDao.detailPatientList(userDataType);
	}
	
	@Override
	public UserDataType searchPatient(SearchParam param) throws Exception{
		return this.registrationDao.searchPatient(param);
	}
	
	@Override
	public List<RegistrationDataType> selectRegistrationIdxByPatientId(UserDataType userDataType) throws Exception{
		return this.registrationDao.selectRegistrationIdxByPatientId(userDataType);
	}
	
	@Override
	public void insertRegistration(SearchParam param) throws Exception{
		this.registrationDao.insertRegistration(param);
	}
	
// 여기부터

	@Override
	public List<RegistrationDataType> selectPatientList(SearchParam param) throws Exception {
		return this.registrationDao.selectPatientList(param);
	}

	@Override
	public int checkPatient(SearchParam param) throws Exception {
		return this.registrationDao.checkPatient(param);
	}

	@Override
	public List<UserDataType> selectSearchUser(SearchParam param) throws Exception {
		return this.registrationDao.selectSearchUser(param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject insertRegistration(RegistrationDataType registration) {
		JSONObject json = null;
		try{
			int registrationID = this.registrationDao.insertRegistration(registration);
			json = new JSONObject();
			json.put("registrationID", registrationID);
		} catch(Exception e){
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public void deletePatient(RegistrationDataType registration) throws Exception {
		this.registrationDao.deletePatient(registration);
		
	}

	@Override
	public int selectPatientCounts(SearchParam searchParam) throws Exception {
		return this.registrationDao.selectPatientCounts(searchParam);
	}

	@Override
	public List<RegistrationDataType> selectAllRegistration(String patientID) throws Exception {
		return this.registrationDao.selectAllRegistration(patientID);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject deletePatient(int registrationID, IPrescriptionManager prescriptionManager,List<PrescriptionDataType> prescriptionList) throws Exception {
		JSONObject json = null;
		SqlSession sqlSession = this.registrationDao.getSqlSession();
		try{
			for(PrescriptionDataType prescriptionDataType : prescriptionList){
				prescriptionManager.deletePrescription(prescriptionDataType.getPrescriptionID(),sqlSession);
			}
			this.registrationDao.deletePatient(registrationID,sqlSession);
			sqlSession.commit();
			json = new JSONObject();
			json.put("result", true);
		}catch(Exception e){
			sqlSession.rollback();
			throw new Exception(e.getMessage());
		}finally{
			sqlSession.close();
		}
		return json;
	}

	@Override
	public RegistrationDataType selectMyself(SearchParam searchParam) throws Exception {
		return this.registrationDao.selectDoctorIdAndPatientID(searchParam);
	}

}
