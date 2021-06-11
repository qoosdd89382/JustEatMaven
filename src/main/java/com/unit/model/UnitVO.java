package com.unit.model;

import java.io.Serializable;

public class UnitVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer unitID;
	private String unitName;

	public UnitVO() {
	}

	public Integer getUnitID() {
		return unitID;
	}

	public void setUnitID(Integer unitID) {
		this.unitID = unitID;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

}
