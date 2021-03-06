package kr.ac.cbnu.computerengineering.medicine.manager;

import java.util.List;

import kr.ac.cbnu.computerengineering.common.datatype.AtcDataType;
import kr.ac.cbnu.computerengineering.common.datatype.MedicineDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;
import kr.ac.cbnu.computerengineering.common.managers.IMedicineManager;
import kr.ac.cbnu.computerengineering.common.managers.dao.IATCDao;
import kr.ac.cbnu.computerengineering.common.managers.dao.IMedicineDao;
import kr.ac.cbnu.computerengineering.medicine.manager.dao.ATCDaoImpl;
import kr.ac.cbnu.computerengineering.medicine.manager.dao.MedicineDaoImpl;



public class MedicineManagerImpl implements IMedicineManager {
	private IMedicineDao medicineDao;
	private IATCDao atcDao;
	
	public MedicineManagerImpl() {
		this.medicineDao = new MedicineDaoImpl();
		this.atcDao = new ATCDaoImpl();
	}
	
	@Override
	public int countATCRow(SearchParam param) throws Exception{
		return this.atcDao.countRow(param);
	}
	
	@Override
	public List<AtcDataType> getATCList(SearchParam searchParam) throws Exception {
		return this.atcDao.selectATCList(searchParam);
	}
	@Override
	public int countMedicineRow(SearchParam param) throws Exception{
		return this.medicineDao.countRow(param);
	}
	@Override
	public List<MedicineDataType> getMedicineList(SearchParam param) throws Exception {
		return this.medicineDao.selectMedicineList(param);
	}
	@Override
	public List<MedicineDataType> selectATCByMedicineName(SearchParam param) throws Exception{
		return this.medicineDao.selectATCByMedicineName(param);
	}
	@Override
	public MedicineDataType selectMedicineByMedicineCode(SearchParam param) throws Exception {
		return this.medicineDao.selectMedicineByMedicineCode(param);
	}
	@Override
	public List<MedicineDataType> searchMedicineList(SearchParam param) throws Exception {
		return this.medicineDao.getMedicineNoLimitList(param);
	}

	@Override
	public List<MedicineDataType> getMedicineNoLimitList(SearchParam searchParam) throws Exception {	
		return this.medicineDao.getMedicineNoLimitList(searchParam);
	}
}
