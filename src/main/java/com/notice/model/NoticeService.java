package com.notice.model;

import java.sql.Timestamp;
import java.util.Date;
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
	
	// ---------- 其他功能若需要通知會員，則在其他功能 Servlet 內呼叫以下方法，並記得傳給 SocketServletNotify ----------

	public List<NoticeVO> getAllByAccountID(int accountID) {
		return dao.getAllByAccountID(accountID);
	}
	
	public NoticeVO addNotice(
			Integer accountID, Integer noticeType, String noticeText
			) {
//			, Timestamp noticeTime, Integer noticeState) {
		NoticeVO vo = new NoticeVO();
		vo.setAccountID(accountID);
		vo.setNoticeType(noticeType);
		vo.setNoticeText(noticeText);
		vo.setNoticeTime(new Timestamp(new Date().getTime()));	// 預設為現在
		vo.setNoticeState(0);	// 預設未讀
		
		dao.insert(vo);
		
		return vo;
	}
	
	
}
