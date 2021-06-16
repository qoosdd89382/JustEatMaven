package com.announce.model;

public class AnnounceService {
	private AnnounceDAOInterface dao;
	
	public AnnounceService() {
		dao = new AnnounceJDBCDAO();	
	}
	
	
}
