package com.admininfo.model;

import java.sql.Timestamp;
import java.util.List;

public class AdminInfoService {
	
	private AdminInfoDAOInterface dao;

	public AdminInfoService() {
		dao = new AdminInfoJDBCDAO();
	}
	

	public AdminInfoVO addAdmin(String adminMail, String adminNickname,
							String adminPassword) {
		AdminInfoVO vo = new AdminInfoVO();
		vo.setAdminMail(adminMail);
		vo.setAdminPassword(adminPassword);
		vo.setAdminNickname(adminNickname);
		vo.setAdminID(dao.insert(vo));
		
		return vo;
	}
	
	public AdminInfoVO getOneAdmin(int adminID) {
		return dao.getOne(adminID);
	}
	
	public boolean isNicknameExist(String adminNickname) {
		return dao.isNicknameExist(adminNickname);
	}

	public boolean isMailExist(String adminMail) {
		return dao.isMailExist(adminMail);
	}
	

	public int setPasswordAndPic(int adminID, String adminPassword, byte[] adminPic) {
		AdminInfoVO vo = new AdminInfoVO();
		vo.setAdminPassword(adminPassword);
		vo.setAdminPic(adminPic);
		vo.setAdminID(adminID);
		return dao.setPasswordAndPic(vo);
	}
	
	
	
//	public int updateAdmin(String adminMail, String adminNickname,
//								String adminPassword, byte[] adminPic) {
//		
//	}
//	public List<AdminInfoVO> getAll();

	
	
}
