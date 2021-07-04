package com.announce.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class AnnounceService {
	private AnnounceDAOInterface dao;
	
	public AnnounceService() {
		dao = new AnnounceJDBCDAO();	
	}
	
	public List<AnnounceVO> getAnnounce(){
		return dao.getAnnounce();
	}
	
	public List<AnnounceVO> getAll(){
		return dao.getAll();
	}
	
	public AnnounceVO addAnnounce(String announceText) {
		AnnounceVO vo = new AnnounceVO();
		vo.setAnnounceText(announceText);
		vo.setAnnounceTime(new Timestamp(new Date().getTime()));
		vo.setAnnounceID(dao.insert(vo));
		return vo; 
	}
	
	
}
