package com.accountinfo.model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountInfoJDBCDAO implements AccountInfoDAOInterface {
	
	//建立連線
	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/JustEat?serverTimezone=Asia/Taipei";
	private static String userid = "DBAdmin";
	private static String password = "justeat";
	
	//CRUD指令，五個一排，共14個
	private static final String Insert_Stmt = "Insert into AccountInfo "//空格
			+ "(account_mail,account_nickname,account_password,account_state,account_level,"
			+ "account_name,account_gender,account_birth,account_phone,account_pic,"
			+ "account_idcard_front,account_idcard_back,account_text,account_register_time)"
			+ "Values(?,?,?,?,?,?,?,?,?,?,"
			+ "?,?,?,?)";

	private static final String Update_Stmt = "Update AccountInfo set "//空格
			+ "account_mail=?,account_nickname=?,account_password=?,account_state=?,account_level=?,"
			+ "account_name=?,account_gender=?,account_birth=?,account_phone=?,account_pic=?,"
			+ "account_idcard_front=?,account_idcard_back=?,account_text=?,account_register_time=?"
			+ "Where account_id=?";
	private static final String Delete_Stmt = "Delete From AccountInfo Where account_id=?";
	private static final String Select_Key_Stmt = "Select * From JustEat.AccountInfo Where account_id=?";
	private static final String Select_All_Stmt = "Select * From JustEat.AccountInfo";
	//登入用
	private static final String Select_Account_Login = "Select * From JustEat.AccountInfo Where account_mail=? && account_password=?";
	private static final String Select_Account_Mail = "Select * From JustEat.AccountInfo Where account_mail=?";
	private static final String Select_Account_Password_By_AccoutMail = "Select account_password From JustEat.AccountInfo Where account_mail=?";
	
	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace(System.err);
		}
	}
	
	@Override
	public void insert(AccountInfoVO accountInfoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] autoGeneratedCol = {"account_id"};
		
		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Insert_Stmt, autoGeneratedCol);
			
			pstmt.setString(1,accountInfoVO.getAccountMail());
			pstmt.setString(2,accountInfoVO.getAccountNickname());
			pstmt.setString(3,accountInfoVO.getAccountPassword());
			pstmt.setBoolean(4,accountInfoVO.getAccountState());
			pstmt.setInt(5,accountInfoVO.getAccountLevel());
			
			pstmt.setString(6,accountInfoVO.getAccountName());
			pstmt.setInt(7,accountInfoVO.getAccountGender());
			pstmt.setDate(8,accountInfoVO.getAccountBirth());
			pstmt.setString(9,accountInfoVO.getAccountPhone());
			pstmt.setBytes(10,accountInfoVO.getAccountPic());
			
			pstmt.setBytes(11,accountInfoVO.getAccountIDcardFront());
			pstmt.setBytes(12,accountInfoVO.getAccountIDcardBack());
			pstmt.setString(13,accountInfoVO.getAccountText());
			pstmt.setTimestamp(14,accountInfoVO.getAccountRegistTime());
			
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				Integer autoGeneratedkey = rs.getInt(1);
				System.out.println("AccountInfo insert completed!account_id="+autoGeneratedkey);
			}else {
				System.out.println("AccountInfo insert failed!");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
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
	}

	@Override
	public void update(AccountInfoVO accountInfoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Update_Stmt);
			
			pstmt.setString(1,accountInfoVO.getAccountMail());
			pstmt.setString(2,accountInfoVO.getAccountNickname());
			pstmt.setString(3,accountInfoVO.getAccountPassword());
			pstmt.setBoolean(4,accountInfoVO.getAccountState());
			pstmt.setInt(5,accountInfoVO.getAccountLevel());
			pstmt.setString(6,accountInfoVO.getAccountName());
			pstmt.setInt(7,accountInfoVO.getAccountGender());
			pstmt.setDate(8,accountInfoVO.getAccountBirth());
			pstmt.setString(9,accountInfoVO.getAccountPhone());
			pstmt.setBytes(10,accountInfoVO.getAccountPic());
			pstmt.setBytes(11,accountInfoVO.getAccountIDcardFront());
			pstmt.setBytes(12,accountInfoVO.getAccountIDcardBack());
			pstmt.setString(13,accountInfoVO.getAccountText());
			pstmt.setTimestamp(14,accountInfoVO.getAccountRegistTime());
			
			pstmt.setInt(15,accountInfoVO.getAccountID());
			
			pstmt.executeUpdate();
			
			System.out.println("AccountInfo update completed!");
			
		}catch(Exception e){
			e.printStackTrace();	
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
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
	}

	@Override
	public void delete(Integer accountID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Delete_Stmt);
			pstmt.setInt(1, accountID);
			pstmt.executeUpdate();

			System.out.println("AccountInfo delete completed!");
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
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
	}

	@Override
	public AccountInfoVO findByPrimaryKey(Integer accountID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AccountInfoVO accountInfoVO = null;
		
		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Select_Key_Stmt);
			
			pstmt.setInt(1, accountID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				accountInfoVO = new AccountInfoVO();
				accountInfoVO.setAccountID(rs.getInt("account_id"));
				
				accountInfoVO.setAccountMail(rs.getString("account_mail"));
				accountInfoVO.setAccountNickname(rs.getString("account_nickname"));
				accountInfoVO.setAccountPassword(rs.getString("account_password"));
				accountInfoVO.setAccountState(rs.getBoolean("account_state"));
				accountInfoVO.setAccountLevel(rs.getInt("account_level"));
				
				accountInfoVO.setAccountName(rs.getString("account_name"));
				accountInfoVO.setAccountGender(rs.getInt("account_gender"));
				accountInfoVO.setAccountBirth(rs.getDate("account_birth"));
				accountInfoVO.setAccountPhone(rs.getString("account_phone"));
				accountInfoVO.setAccountPic(rs.getBytes("account_pic"));
				
				accountInfoVO.setAccountIDcardFront(rs.getBytes("account_idcard_front"));
				accountInfoVO.setAccountIDcardBack(rs.getBytes("account_idcard_back"));
				accountInfoVO.setAccountText(rs.getString("account_text"));
				accountInfoVO.setAccountRegistTime(rs.getTimestamp("account_register_time"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(rs != null) {
				try {
					rs.close();					
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
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
		return accountInfoVO;
	}
	
	@Override
	public List<AccountInfoVO> getAll() {
		List<AccountInfoVO> list = new ArrayList<AccountInfoVO>();
		AccountInfoVO accountInfoVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Select_All_Stmt);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				accountInfoVO = new AccountInfoVO();
				accountInfoVO.setAccountID(rs.getInt("account_id"));
				
				accountInfoVO.setAccountMail(rs.getString("account_mail"));
				accountInfoVO.setAccountNickname(rs.getString("account_nickname"));
				accountInfoVO.setAccountPassword(rs.getString("account_password"));
				accountInfoVO.setAccountState(rs.getBoolean("account_state"));
				accountInfoVO.setAccountLevel(rs.getInt("account_level"));
				
				accountInfoVO.setAccountName(rs.getString("account_name"));
				accountInfoVO.setAccountGender(rs.getInt("account_gender"));
				accountInfoVO.setAccountBirth(rs.getDate("account_birth"));
				accountInfoVO.setAccountPhone(rs.getString("account_phone"));
				accountInfoVO.setAccountPic(rs.getBytes("account_pic"));
				
				accountInfoVO.setAccountIDcardFront(rs.getBytes("account_idcard_front"));
				accountInfoVO.setAccountIDcardBack(rs.getBytes("account_idcard_back"));
				accountInfoVO.setAccountText(rs.getString("account_text"));
				accountInfoVO.setAccountRegistTime(rs.getTimestamp("account_register_time"));
				
				list.add(accountInfoVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
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
		return list;
	}
	
//登入用
	@Override
	public AccountInfoVO getAccountLogin(String accountMail,String accountPassword) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AccountInfoVO accountInfoVO = null;
		
		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Select_Account_Login);
			
			pstmt.setString(1, accountMail);
			pstmt.setString(2, accountPassword);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				accountInfoVO = new AccountInfoVO();
				accountInfoVO.setAccountID(rs.getInt("account_id"));
				
				accountInfoVO.setAccountMail(rs.getString("account_mail"));
				accountInfoVO.setAccountNickname(rs.getString("account_nickname"));
				accountInfoVO.setAccountPassword(rs.getString("account_password"));
				accountInfoVO.setAccountState(rs.getBoolean("account_state"));
				accountInfoVO.setAccountLevel(rs.getInt("account_level"));
				
				accountInfoVO.setAccountName(rs.getString("account_name"));
				accountInfoVO.setAccountGender(rs.getInt("account_gender"));
				accountInfoVO.setAccountBirth(rs.getDate("account_birth"));
				accountInfoVO.setAccountPhone(rs.getString("account_phone"));
				accountInfoVO.setAccountPic(rs.getBytes("account_pic"));
				
				accountInfoVO.setAccountIDcardFront(rs.getBytes("account_idcard_front"));
				accountInfoVO.setAccountIDcardBack(rs.getBytes("account_idcard_back"));
				accountInfoVO.setAccountText(rs.getString("account_text"));
				accountInfoVO.setAccountRegistTime(rs.getTimestamp("account_register_time"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(rs != null) {
				try {
					rs.close();					
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
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
		return accountInfoVO;
	}
	
	@Override
	public AccountInfoVO getAccountMail(String accountMail) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AccountInfoVO accountInfoVO = null;
		
		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Select_Account_Mail);
			
			pstmt.setString(1, accountMail);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				accountInfoVO = new AccountInfoVO();
				
				accountInfoVO.setAccountMail(rs.getString("account_mail"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(rs != null) {
				try {
					rs.close();					
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
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
		return accountInfoVO;
	}
	
	@Override
	public AccountInfoVO getAccountPassword(String accountMail) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AccountInfoVO accountInfoVO = null;
		
		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Select_Account_Password_By_AccoutMail);
			
			pstmt.setString(1, accountMail);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				accountInfoVO = new AccountInfoVO();
				
				accountInfoVO.setAccountPassword(rs.getString("account_password"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(rs != null) {
				try {
					rs.close();					
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
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
		return accountInfoVO;
	}
	
	
	
	
	
//=============================================================
	public static void main(String[] args) {
		String date = "2021-02-03 14:00";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime localDateTime = LocalDateTime.parse(date,formatter);
		Timestamp timestampdate = Timestamp.valueOf(localDateTime);
		
		AccountInfoJDBCDAO accountInfoJDBCDAO = new AccountInfoJDBCDAO();
	
	//新增 OK
//		AccountInfoVO accountInfoVO1 = new AccountInfoVO();
//		accountInfoVO1.setAccountMail("JerryMouse@gmail.com");
//		accountInfoVO1.setAccountNickname("王曉明");
//		accountInfoVO1.setAccountPassword("1q2w3e4r5t");
//		accountInfoVO1.setAccountState(new Boolean("1"));
//		
//		accountInfoVO1.setAccountName("王陽明");
//		accountInfoVO1.setAccountLevel(new Integer(1));
//		accountInfoVO1.setAccountGender(new Integer(1));
//		accountInfoVO1.setAccountBirth(java.sql.Date.valueOf("1990-01-01"));
//		accountInfoVO1.setAccountPhone("0912345678");
//		//會員照片
//		InputStream isAccountPic = null;
//		try {
//			isAccountPic = new FileInputStream("C:\\Users\\Tibame_T14\\Downloads\\project\\accountTest.jpg");
//			byte[] picBuffer = new byte[isAccountPic.available()];
//			isAccountPic.read(picBuffer);
//			accountInfoVO1.setAccountPic(picBuffer);			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (isAccountPic != null) {
//				try {
//					isAccountPic.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		//身分證正面
//		InputStream isAccountIDcardFront = null;
//		try {
//			isAccountIDcardFront = new FileInputStream("C:\\Users\\Tibame_T14\\Downloads\\project\\accountIDFTest.jpg");
//			byte[] picBuffer = new byte[isAccountIDcardFront.available()];
//			isAccountIDcardFront.read(picBuffer);
//			accountInfoVO1.setAccountIDcardFront(picBuffer);			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (isAccountIDcardFront != null) {
//				try {
//					isAccountIDcardFront.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		
//		//身分證背面
//		InputStream isAccountIDcardBack = null;
//		try {
//			isAccountIDcardBack = new FileInputStream("C:\\Users\\Tibame_T14\\Downloads\\project\\accountIDBTest.jpg");
//			byte[] picBuffer = new byte[isAccountIDcardBack.available()];
//			isAccountIDcardBack.read(picBuffer);
//			accountInfoVO1.setAccountIDcardBack(picBuffer);			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (isAccountIDcardBack != null) {
//				try {
//					isAccountIDcardBack.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		
//		accountInfoVO1.setAccountText("我是一個普通會員，ㄏㄏㄏ，MIC TEST!!!");
//		accountInfoVO1.setAccountRegistTime(timestampdate);
//		
//		accountInfoJDBCDAO.insert(accountInfoVO1);
//		
//		System.out.println("accountInfoVO1 insert complete!");
//		
//=============================================================
	//修改 OK
//		AccountInfoVO accountInfoVO2 = new AccountInfoVO(); 
//		
//		accountInfoVO2.setAccountMail("TigerWu@gmail.com");
//		accountInfoVO2.setAccountNickname("陳小美");
//		accountInfoVO2.setAccountPassword("1q2w3e4r5t6y7u");
//		accountInfoVO2.setAccountState(new Boolean("1"));
//		accountInfoVO2.setAccountLevel(new Integer(1));
//		
//		accountInfoVO2.setAccountName("陳大美");
//		accountInfoVO2.setAccountGender(new Integer(1));
//		accountInfoVO2.setAccountBirth(java.sql.Date.valueOf("1990-01-01"));
//		accountInfoVO2.setAccountPhone("0912840222");
//		//會員照片
//		InputStream isAccountPic = null;
//		try {
//			isAccountPic = new FileInputStream("C:\\Users\\Tibame_T14\\Downloads\\project\\accountTest.jpg");
//			byte[] picBuffer = new byte[isAccountPic.available()];
//			isAccountPic.read(picBuffer);
//			accountInfoVO2.setAccountPic(picBuffer);			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (isAccountPic != null) {
//				try {
//					isAccountPic.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		//身分證正面
//		InputStream isAccountIDcardFront = null;
//		try {
//			isAccountIDcardFront = new FileInputStream("C:\\Users\\Tibame_T14\\Downloads\\project\\accountIDFTest.jpg");
//			byte[] picBuffer = new byte[isAccountIDcardFront.available()];
//			isAccountIDcardFront.read(picBuffer);
//			accountInfoVO2.setAccountIDcardFront(picBuffer);			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (isAccountIDcardFront != null) {
//				try {
//					isAccountIDcardFront.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		
//		//身分證背面
//		InputStream isAccountIDcardBack = null;
//		try {
//			isAccountIDcardBack = new FileInputStream("C:\\Users\\Tibame_T14\\Downloads\\project\\accountIDBTest.jpg");
//			byte[] picBuffer = new byte[isAccountIDcardBack.available()];
//			isAccountIDcardBack.read(picBuffer);
//			accountInfoVO2.setAccountIDcardBack(picBuffer);			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (isAccountIDcardBack != null) {
//				try {
//					isAccountIDcardBack.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		
//		accountInfoVO2.setAccountText("我是一個更普通的會員，ㄏㄏㄏ，MIC TEST!!!");
//		accountInfoVO2.setAccountRegistTime(timestampdate);
//		accountInfoVO2.setAccountID(100001);
//		
//		accountInfoJDBCDAO.update(accountInfoVO2);
//		
//		System.out.println("AccountInfo update complete!");
		
//=============================================================
		//刪除 OK
//		accountInfoJDBCDAO.delete(100001);
//=============================================================
		//查詢 OK
//		AccountInfoVO accountInfoVO3 = new AccountInfoVO();
//		accountInfoVO3 = accountInfoJDBCDAO.findByPrimaryKey(100001);
//		System.out.println(accountInfoVO3.getAccountMail()+",");
//		System.out.println(accountInfoVO3.getAccountNickname()+",");
//		System.out.println(accountInfoVO3.getAccountPassword()+",");
//		System.out.println(accountInfoVO3.getAccountState()+",");
//		System.out.println(accountInfoVO3.getAccountLevel()+",");
//		
//		System.out.println(accountInfoVO3.getAccountName()+",");
//		System.out.println(accountInfoVO3.getAccountGender()+",");
//		System.out.println(accountInfoVO3.getAccountBirth()+",");
//		System.out.println(accountInfoVO3.getAccountPhone()+",");
//		System.out.println(accountInfoVO3.getAccountPic()+",");
//		
//		System.out.println(accountInfoVO3.getAccountIDcardFront()+",");
//		System.out.println(accountInfoVO3.getAccountIDcardBack()+",");
//		System.out.println(accountInfoVO3.getAccountText()+",");
//		System.out.println(accountInfoVO3.getAccountRegistTime()+",");
//		
//		System.out.println("search complete!");
		
//=============================================================
	//查詢全部 OK
//	List<AccountInfoVO> accountInfoVO_list = accountInfoJDBCDAO.getAll();
//	for(AccountInfoVO e:accountInfoVO_list) {
//		System.out.println(e.getAccountMail()+",");
//		System.out.println(e.getAccountNickname()+",");
//		System.out.println(e.getAccountPassword()+",");
//		System.out.println(e.getAccountState()+",");
//		System.out.println(e.getAccountLevel()+",");
//		
//		System.out.println(e.getAccountName()+",");
//		System.out.println(e.getAccountGender()+",");
//		System.out.println(e.getAccountBirth()+",");
//		System.out.println(e.getAccountPhone()+",");
//		System.out.println(e.getAccountPic()+",");
//		
//		System.out.println(e.getAccountIDcardFront()+",");
//		System.out.println(e.getAccountIDcardBack()+",");
//		System.out.println(e.getAccountText()+",");
//		System.out.println(e.getAccountRegistTime()+",");
//	}
	
//最後分隔線===================================================
//BUG:
//新增不成功流水號會跟著增加
	}
}



