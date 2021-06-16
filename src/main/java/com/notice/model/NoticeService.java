package com.notice.model;

public class NoticeService {
	private NoticeDAOInterface dao;
	
	public NoticeService() {
		dao = new NoticeJDBCDAO();
	}

}
