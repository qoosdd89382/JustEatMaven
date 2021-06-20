package com.admininfo.model;

import java.sql.Timestamp;
import java.util.List;

public class AdminService {
	
	private AdminInfoDAOInterface dao;

	public AdminService() {
		dao = new AdminInfoJDBCDAO();
	}
	

	public int addAdmin(String adminMail, String adminNickname,
							String adminPassword, byte[] adminPic) {
		AdminInfoVO vo = new AdminInfoVO();
		vo.setAdminMail(adminMail);
		vo.setAdminPassword(adminPassword);
		vo.setAdminNickname(adminNickname);
		vo.setAdminPic(adminPic);
		
		return dao.insert(vo);
	}
	
//	public int updateString adminMail, String adminNickname,
//	String adminPassword, byte[] adminPic) {
//		
//	}
//	public AdminInfoVO getOne(int adminID);
//	public boolean isNicknameExist(String adminNickname);
//	public List<AdminInfoVO> getAll();

	
	
}
