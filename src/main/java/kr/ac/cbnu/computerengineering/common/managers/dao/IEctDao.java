package kr.ac.cbnu.computerengineering.common.managers.dao;

import java.util.List;

import kr.ac.cbnu.computerengineering.common.datatype.HospitalDatatype;

public interface IEctDao {
	public abstract void addHospital(HospitalDatatype hospital) throws Exception;
	public abstract List<HospitalDatatype> getHospitals() throws Exception;
	public abstract void deleteHospital(int id) throws Exception;
	public abstract void modifyHospital(HospitalDatatype hospital) throws Exception;
	public abstract HospitalDatatype detailHospital(int idx) throws Exception;
}
