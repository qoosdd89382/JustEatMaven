package com.dashboardnotice.model;

import java.util.List;

public interface DashboardNoticeDAOInterface {

	public int insert(DashboardNoticeVO dashboardNotice);
	public int updateState(DashboardNoticeVO dashboardNotice);	// 
	public DashboardNoticeVO getOneByID(int dashboardNoticeID);
	public List<DashboardNoticeVO> getAll();
	public List<DashboardNoticeVO> getAllByType(int dashboardNoticeType);
	public int delete(int dashboardNoticeID);	// 搭配getAll迭代，檢查超過___時間後刪除?
												// 使用大吳老師的每小時計時器Servlet，去檢查超過了沒
	
}
