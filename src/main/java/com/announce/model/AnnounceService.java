package com.announce.model;

import java.util.List;

public class AnnounceService {
	private AnnounceDAOInterface dao;
	
	public AnnounceService() {
		dao = new AnnounceJDBCDAO();	
	}
	
	public List<AnnounceVO> getAnnounce(){
		return dao.getAnnounce();
	}
	
}
