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
	
	public AdminInfoVO getOneAdmin(String adminEmail, String adminPassword) {
		return dao.getOne(adminEmail, adminPassword);
	}
	
	public AdminInfoVO getOneAdmin(String adminMail) {
		return dao.getOne(adminMail);
	}
	
	public boolean isNicknameExist(String adminNickname) {
		return dao.isNicknameExist(adminNickname);
	}
	
	public int setPasswordAndPic(int adminID, String adminPassword, byte[] adminPic) {
		AdminInfoVO vo = new AdminInfoVO();
		vo.setAdminPassword(adminPassword);
		vo.setAdminPic(adminPic);
		vo.setAdminID(adminID);
		return dao.setPasswordAndPic(vo);
	}
	
	public List<AdminInfoVO> getAll() {
		return dao.getAll();
	}

	public int updateNickname(int adminID, String adminNickname) {
		AdminInfoVO vo = new AdminInfoVO();
		vo.setAdminNickname(adminNickname);
		vo.setAdminID(adminID);
		
		return dao.updateNickname(vo);		
	}
	
	public int updateState(int adminID, int adminState) {
		AdminInfoVO vo = new AdminInfoVO();
		vo.setAdminState(adminState);
		vo.setAdminID(adminID);
		
		return dao.updateState(vo);		
	}
	
	public int resetAuthCode(int adminID, String adminPassword) {
		AdminInfoVO vo = new AdminInfoVO();
		vo.setAdminPassword(adminPassword);
		vo.setAdminID(adminID);
		
		return dao.resetAuthCode(vo);		
	}

	
	
}
