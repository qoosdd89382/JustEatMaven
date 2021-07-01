package com.notice.model;

import java.util.List;

import com.accountinfo.model.AccountInfoVO;
import com.notice.model.NoticeVO;

public interface NoticeDAOInterface {
    public int insert(NoticeVO noticeVO);
    public void update(NoticeVO noticeVO);
    public void updateState(int noticeID);
    public void delete(Integer noticeID);
    public NoticeVO findByPrimaryKey(Integer noticeID);
    public List<NoticeVO> getAll();
    
    public List<NoticeVO> getAccountNoticeByAccountMail(String account_mail);
    
    // ----- WebSocket -----
    public List<NoticeVO> getAllByAccountID(int accountID);
}
