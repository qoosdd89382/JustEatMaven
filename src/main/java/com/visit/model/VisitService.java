package com.visit.model;

public class VisitService {
	private VisitDAOInterface dao;
	
	public VisitService() {
		dao = new VisitJDBCDAO();
	}

}
