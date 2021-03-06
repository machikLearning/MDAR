package kr.ac.cbnu.computerengineering.admin.manager.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.ac.cbnu.computerengineering.common.datatype.HospitalDatatype;
import kr.ac.cbnu.computerengineering.common.managers.dao.IEctDao;
import kr.ac.cbnu.computerengineering.common.mybatis.Mybatis;

public class EctDaoImpl implements IEctDao {
	
	private SqlSessionFactory sessionFactory;
	
	public EctDaoImpl() {
		this.sessionFactory = Mybatis.getSqlSessionFactory();
	}

	@Override
	public void addHospital(HospitalDatatype hospital) throws Exception {
		SqlSession session = this.sessionFactory.openSession(true);
		
		try {
			session.insert("etc.addHospital", hospital);
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			session.close();
		}
	}

	@Override
	public List<HospitalDatatype> getHospitals() throws Exception {
		List<HospitalDatatype> hospitals = new ArrayList<>();
		SqlSession session = this.sessionFactory.openSession(true);
		try {
			hospitals = session.selectList("etc.getHospitals");
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			session.close();
		}
		
		return hospitals;
	}

	@Override
	public void deleteHospital(int id) throws Exception {
		SqlSession session = this.sessionFactory.openSession(true);
		try {
			session.delete("etc.deleteHospital", id);
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			session.close();
		}
	}

	@Override
	public void modifyHospital(HospitalDatatype hospital) throws Exception {
		SqlSession session = this.sessionFactory.openSession(true);
		try {
			session.update("etc.modifyHospital", hospital);
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			session.close();
		}
	}

	@Override
	public HospitalDatatype detailHospital(int idx) throws Exception {
		SqlSession session = this.sessionFactory.openSession();
		HospitalDatatype hospital = new HospitalDatatype();
		try {
			hospital = session.selectOne("etc.detailHospital", idx);
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			session.close();
		}
		return hospital;
	}

}
