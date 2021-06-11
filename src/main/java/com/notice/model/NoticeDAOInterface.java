package com.notice.model;

import java.util.List;

import com.notice.model.NoticeVO;

public interface NoticeDAOInterface {
    public void insert(NoticeVO noticeVO);
    public void update(NoticeVO noticeVO);
    public void delete(Integer noticeID);
    public NoticeVO findByPrimaryKey(Integer noticeID);
    public List<NoticeVO> getAll();
}
