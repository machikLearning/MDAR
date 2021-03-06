package kr.ac.cbnu.computerengineering.common.service;

import java.util.List;

import kr.ac.cbnu.computerengineering.common.datatype.HospitalDatatype;

public interface IEctService {
	public abstract void addHospital(HospitalDatatype hospital) throws Exception;
	public abstract List<HospitalDatatype> getHospitals() throws Exception;
	public abstract void deleteHospital(int id) throws Exception;
	public abstract void modifyHospital(HospitalDatatype hospital) throws Exception;
	public abstract HospitalDatatype detailHospital(int idx) throws Exception;
}
