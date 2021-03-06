package kr.ac.cbnu.computerengineering.admin.manager;

import java.util.List;

import kr.ac.cbnu.computerengineering.admin.manager.dao.EctDaoImpl;
import kr.ac.cbnu.computerengineering.common.datatype.HospitalDatatype;
import kr.ac.cbnu.computerengineering.common.managers.IEctManager;
import kr.ac.cbnu.computerengineering.common.managers.dao.IEctDao;

public class EctManagerImpl implements IEctManager {
	private IEctDao ectDao;
	
	public EctManagerImpl() {
		this.ectDao = new EctDaoImpl();
	}

	@Override
	public void addHospital(HospitalDatatype hospital) throws Exception {
		this.ectDao.addHospital(hospital);
	}

	@Override
	public List<HospitalDatatype> getHospitals() throws Exception {
		return this.ectDao.getHospitals();
	}

	@Override
	public void deleteHospital(int id) throws Exception {
		this.ectDao.deleteHospital(id);
	}

	@Override
	public void modifyHospital(HospitalDatatype hospital) throws Exception {
		this.ectDao.modifyHospital(hospital);
	}

	@Override
	public HospitalDatatype detailHospital(int idx) throws Exception {
		return this.ectDao.detailHospital(idx);
	}

}
