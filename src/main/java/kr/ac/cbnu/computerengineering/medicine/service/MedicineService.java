package kr.ac.cbnu.computerengineering.medicine.service;

import kr.ac.cbnu.computerengineering.common.datatype.AtcDataType;
import kr.ac.cbnu.computerengineering.common.datatype.MedicineDataType;
import kr.ac.cbnu.computerengineering.common.datatype.PagingDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;
import kr.ac.cbnu.computerengineering.common.managers.IMedicineManager;
import kr.ac.cbnu.computerengineering.common.service.IMedicineService;
import kr.ac.cbnu.computerengineering.common.util.Utils;
import kr.ac.cbnu.computerengineering.medicine.manager.MedicineManagerImpl;

import java.util.List;
/*
	UserService와 PrescriptionService에서 사용
	왠만하면 건들지 말것
 */
public class MedicineService implements IMedicineService {
	private IMedicineManager medicineManager;
	
	public MedicineService() {
		this.medicineManager = new MedicineManagerImpl(); 
	}

	@Override
	public PagingDataType getATCPagingInfo(SearchParam param, int page) throws Exception {
		PagingDataType result = Utils.computePagingData(this.medicineManager.countATCRow(param), page);
		return result;
	}

	@Override
	public List<AtcDataType> getATCList(SearchParam searchParam) throws Exception {
		return this.medicineManager.getATCList(searchParam);
	}

	@Override
	public PagingDataType getMedicinePagingInfo(SearchParam param, int page) throws Exception {
		PagingDataType result = Utils.computePagingData(this.medicineManager.countMedicineRow(param), page);
		return result;
	}

	@Override
	public List<MedicineDataType> getMedicineList(SearchParam param) throws Exception {
		return this.medicineManager.getMedicineList(param);
	}

	@Override
	public int countRow(SearchParam searchParam) throws Exception {
		return this.medicineManager.countATCRow(searchParam);
	}

	@Override
	public int countMedicineRow(SearchParam searchParam) throws Exception {
		return this.medicineManager.countMedicineRow(searchParam);
	}

	@Override
	public List<MedicineDataType> getMedicineNoLimitList(SearchParam searchParam) throws Exception {
		return this.medicineManager.getMedicineNoLimitList(searchParam);
	}

}
