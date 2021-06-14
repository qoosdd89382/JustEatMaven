package com.admininfo.model;

import java.io.*;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AdminInfoJDBCDAO implements AdminInfoDAOInterface {
	

	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/JustEat?serverTimezone=Asia/Taipei"
			+ "&rewriteBatchedStatements=true";
	private static String userid = "DBAdmin";
	private static String passwd = "justeat";

	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	// 是否需要註冊日期?
	private static final String INSERT = "INSERT INTO AdminInfo(admin_mail, admin_nickname, admin_password, admin_pic, admin_state, admin_register_time) VALUES(?, ?, ?, ?, ?, NOW())";
	// 順序看要寫死還是後端判斷
	private static final String SELECT_ONE = "SELECT * FROM AdminInfo WHERE admin_id = ?";
	private static final String SELECT_ALL = "SELECT * FROM AdminInfo";
	private static final String UPDATE = "UPDATE AdminInfo SET admin_mail = ?, admin_nickname = ?, admin_password = ?, admin_pic = ?, admin_state = ? WHERE admin_id = ?";
	private static final String SELECT_ONE_BY_NAME = "SELECT * FROM AdminInfo WHERE admin_nickname = ?";

	@Override
	public int insert(AdminInfoVO adminInfo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int insertRow = 0;
		String[] autoGeneratedCol = { "admin_id" };
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT, autoGeneratedCol);
			
			pstmt.setString(1, adminInfo.getAdminMail());
			pstmt.setString(2, adminInfo.getAdminNickname());
			pstmt.setString(3, adminInfo.getAdminPassword());
			pstmt.setBytes(4, adminInfo.getAdminPic());
			pstmt.setBoolean(5, adminInfo.getAdminState());
						
			insertRow = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}			
		}
		return insertRow;
	}

	@Override
	public int update(AdminInfoVO adminInfo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int updateRow = 0;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, adminInfo.getAdminMail());
			pstmt.setString(2, adminInfo.getAdminNickname());
			pstmt.setString(3, adminInfo.getAdminPassword());
			pstmt.setBytes(4, adminInfo.getAdminPic());
			pstmt.setBoolean(5, adminInfo.getAdminState());
			pstmt.setInt(6, adminInfo.getAdminID());

			updateRow = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return updateRow;
	}

	@Override
	public AdminInfoVO getOne(int adminID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AdminInfoVO adminInfo = null;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ONE);
			
			pstmt.setInt(1, adminID);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				adminInfo = new AdminInfoVO();
				adminInfo.setAdminID(rs.getInt("admin_id"));
				adminInfo.setAdminMail(rs.getString("admin_mail"));
				adminInfo.setAdminNickname(rs.getString("admin_nickname"));
				adminInfo.setAdminPassword(rs.getString("admin_password"));
				adminInfo.setAdminPic(rs.getBytes("admin_pic"));
				adminInfo.setAdminRegisterTime(rs.getTimestamp("admin_register_time"));
				adminInfo.setAdminState(rs.getBoolean("admin_state"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return adminInfo;
	}

	@Override
	public List<AdminInfoVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AdminInfoVO> allAdminInfo = new ArrayList<AdminInfoVO>();
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ALL);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				AdminInfoVO adminInfo = new AdminInfoVO();
				adminInfo.setAdminID(rs.getInt("admin_id"));
				adminInfo.setAdminMail(rs.getString("admin_mail"));
				adminInfo.setAdminNickname(rs.getString("admin_nickname"));
				adminInfo.setAdminPassword(rs.getString("admin_password"));
				adminInfo.setAdminPic(rs.getBytes("admin_pic"));
				adminInfo.setAdminRegisterTime(rs.getTimestamp("admin_register_time"));
				adminInfo.setAdminState(rs.getBoolean("admin_state"));
				allAdminInfo.add(adminInfo);				
			}		
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return allAdminInfo;
	}
	
	// 測試用
	public static void main(String args[]) {
		AdminInfoVO admin = new AdminInfoVO();
		AdminInfoJDBCDAO dao = new AdminInfoJDBCDAO();

		// 測試 insert 成功
		admin.setAdminMail("peter@admin.com");
		admin.setAdminNickname("管理員彼得");
		admin.setAdminPassword("123456");
		InputStream is = null;
		try {
			is = new FileInputStream("C:\\Users\\Tibame_T14\\Downloads\\Apache_Tomcat_logo.png");
			byte[] buffer = new byte[is.available()];
			is.read(buffer);
			admin.setAdminPic(buffer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		admin.setAdminState(true);
		int insertSuccess = dao.insert(admin);
		if (insertSuccess == 1)
			System.out.println("成功");
		
		// 測試 get one 成功
//		AdminInfoVO vo = dao.getOne(120001);
//		System.out.println(vo.getAdminID() + "\n" + vo.getAdminMail() + "\n============");
		
		// 測試 get all 成功
		List<AdminInfoVO> all = dao.getAll();
		for (AdminInfoVO ele : all) {
			System.out.println(ele.getAdminID() + "\n" + ele.getAdminMail() + "\n============");
		}
		
//		// 測試 update 成功
//		AdminInfoVO vo = dao.getOne(120003);
//		vo.setAdminNickname("Admin David");
//		int updateSuccess = dao.update(vo);
//		System.out.println("共有" + updateSuccess + "筆資料更新成功");
	}

	@Override
	public boolean isNicknameExist(String adminNickname) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean ExistStatus = false;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ONE_BY_NAME);

			pstmt.setString(1, adminNickname);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				ExistStatus = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			if (rs != null)
				try {
					rs.close();
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return ExistStatus;
	}

}

