package com.unit.model;

import java.util.List;

public interface UnitDAOInterface {
	
	public int insert(UnitVO unit);
	public int update(UnitVO unit);
	public List<UnitVO> getAll();
	public UnitVO getOne(int unitID);
	public int delete(int unitID);
	public int delete(int[] unitIDs);
	public boolean isExist(String unitName);
	

}
