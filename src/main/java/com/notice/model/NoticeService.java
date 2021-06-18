package com.notice.model;

import java.util.List;

import com.accountinfo.model.AccountInfoVO;

public class NoticeService {
	private NoticeDAOInterface dao;
	
	public NoticeService() {
		dao = new NoticeJDBCDAO();
	}
	public List<NoticeVO> getAccountNoticeByAccountMail(String account_mail) {
		return dao.getAccountNoticeByAccountMail(account_mail);
	}
}
