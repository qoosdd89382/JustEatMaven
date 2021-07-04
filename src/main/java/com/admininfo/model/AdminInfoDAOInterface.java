package com.admininfo.model;

import java.util.List;

public interface AdminInfoDAOInterface {
	
	public int insert(AdminInfoVO adminInfo);
	public int update(AdminInfoVO adminInfo);	// 好像也不一定要回傳
	public int updateState(AdminInfoVO adminInfo);
	public int updateNickname(AdminInfoVO adminInfo);
	public AdminInfoVO getOne(int adminID);
	public AdminInfoVO getOne(String adminmail, String adminPassword);
	public AdminInfoVO getOne(String adminMail);
	public boolean isNicknameExist(String adminNickname);
	public List<AdminInfoVO> getAll();
	public int setPasswordAndPic(AdminInfoVO adminInfo);
	public int resetAuthCode(AdminInfoVO adminInfo);

}
