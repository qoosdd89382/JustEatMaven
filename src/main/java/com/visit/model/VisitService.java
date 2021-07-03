package com.visit.model;

public class VisitService {
	private VisitDAOInterface dao;
	
	public VisitService() {
		dao = new VisitJDBCDAO();
	}
	
	public void insertVisitRecordByAccountID(Integer accountID) {
		dao.insertVisitRecordByAccountID(accountID);
	}
	
	public VisitVO getLatestVisitRecordByAccountID(Integer accountID) {
		return dao.getLatestVisitRecordByAccountID(accountID);
	}

	public VisitVO getLastVisitRecordByAccountID(Integer accountID) {
		return dao.getLastVisitRecordByAccountID(accountID);
	}
	
	public VisitVO getPeriodVisitRecordByAccountID(Integer accountID) {
		return dao.getPeriodVisitRecordByAccountID(accountID);
	}
}
