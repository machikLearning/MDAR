package kr.ac.cbnu.computerengineering.admin.service;

import java.util.List;

import kr.ac.cbnu.computerengineering.admin.manager.EctManagerImpl;
import kr.ac.cbnu.computerengineering.common.datatype.HospitalDatatype;
import kr.ac.cbnu.computerengineering.common.managers.IEctManager;
import kr.ac.cbnu.computerengineering.common.service.IEctService;

public class EtcServiceImpl implements IEctService {
	private IEctManager ectManager;
	
	public EtcServiceImpl() {
		this.ectManager = new EctManagerImpl();
	}
	
	@Override
	public void addHospital(HospitalDatatype hospital) throws Exception {
		this.ectManager.addHospital(hospital);
	}

	@Override
	public List<HospitalDatatype> getHospitals() throws Exception {
		return this.ectManager.getHospitals();
	}

	@Override
	public void deleteHospital(int id) throws Exception {
		//TODO 사용자에 등록되어 있으면 삭제 불가하도록 조치
		this.ectManager.deleteHospital(id);
	}

	@Override
	public void modifyHospital(HospitalDatatype hospital) throws Exception {
		this.ectManager.modifyHospital(hospital);
	}

	@Override
	public HospitalDatatype detailHospital(int idx) throws Exception {
		return this.ectManager.detailHospital(idx);
	}
}
