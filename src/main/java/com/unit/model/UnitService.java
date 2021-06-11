package com.unit.model;

import java.util.List;

public class UnitService {
	
	private UnitDAOInterface dao;

	public UnitService() {
		dao = new UnitJDBCDAO();
	}
	
	public int addUnit(Integer unitID, String unitName) {
		
		UnitVO vo = new UnitVO();
		vo.setUnitID(unitID);
		vo.setUnitName(unitName);
		
		return dao.insert(vo);
	}
	
	public int updateUnit(Integer unitID, String unitName) {
		
		UnitVO vo = new UnitVO();
		vo.setUnitID(unitID);
		vo.setUnitName(unitName);
		
		return dao.insert(vo);
	}
	
	public List<UnitVO> getAll() {
		return dao.getAll();
	}
	
	public UnitVO getOneUnit(int unitID) {
		return dao.getOne(unitID);
	}
	
	public int deleteUnit(int unitID) {
		return dao.delete(unitID);
	}
	
	public int deleteUnits(int[] unitIDs) {
		return dao.delete(unitIDs);
	}
	
	public boolean isExist(String unitName) {
		return dao.isExist(unitName);
	}
	

}
