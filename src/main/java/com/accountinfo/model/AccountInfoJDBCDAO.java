package com.accountinfo.model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Array;
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
	
	//後臺用，CRUD指令，五個一排，共15個
	private static final String Insert_Stmt = "Insert into JustEat.AccountInfo "
			+ "(account_mail,account_nickname,account_password,account_state,account_level,"
			+ "account_name,account_gender,account_birth,account_phone,account_pic,"
			+ "account_idcard_front,account_idcard_back,account_text,account_register_time,account_code)"
			+ "Values(?,?,?,?,?,?,?,?,?,?,"
			+ "?,?,?,NOW(),?)";
	private static final String Update_Stmt = "Update AccountInfo set "
			+ "account_mail=?,account_nickname=?,account_password=?,account_state=?,account_level=?,"
			+ "account_name=?,account_gender=?,account_birth=?,account_phone=?,account_pic=?,"
			+ "account_idcard_front=?,account_idcard_back=?,account_text=?"
			+ "Where account_id=?";
	private static final String Delete_Stmt = "Delete From AccountInfo Where account_id=?";
	private static final String Select_Key_Stmt = "Select * From JustEat.AccountInfo Where account_id=?";
	private static final String Select_All_Stmt = "Select * From JustEat.AccountInfo";
	
	//停權帳號
	private static final String Update_FreezeAccountInfo_Stmt = "Update JustEat.AccountInfo set "
			+ "account_state=? Where account_id=?";
	//啟用帳號
	private static final String Update_ActiveAccountInfo_Stmt = "Update JustEat.AccountInfo set "
			+ "account_state=? Where account_id=?";
	//1review0622
	
	
	//登入用
	private static final String Select_Account_Login = "Select * From JustEat.AccountInfo Where account_mail=? && account_password=?";
	
	
	private static final String Select_Account_Mail = "Select * From JustEat.AccountInfo Where account_mail=?";
	private static final String Select_Account_Nickname = "Select * From JustEat.AccountInfo Where account_nickname=?";
	
	private static final String Select_Account_ID_By_AccountMail = "Select account_id From JustEat.AccountInfo Where account_mail=?";
	private static final String Select_Account_Nickname_By_AccountMail = "Select account_nickname From JustEat.AccountInfo Where account_mail=?";
	private static final String Select_Account_Level_By_AccountMail = "Select account_level From JustEat.AccountInfo Where account_mail=?";
	private static final String Select_Account_Password_By_AccoutMail = "Select account_password From JustEat.AccountInfo Where account_mail=?";
	private static final String select_Account_Code_By_AccountMail = "Select account_code From JustEat.AccountInfo Where account_mail=?";
	
	//會員修改資料用
	private static final String Update_Account_Info_From_Change = "Update JustEat.AccountInfo set "
			+ "account_password=?,"
			+ "account_name=?,account_gender=?,account_birth=?,account_phone=?,"
			+ "account_pic=?,"
			+ "account_text=?"
			+ "Where account_id=?";
	//會員忘記密碼
	//會員寄出認證信找回密碼
	private static final String Update_Account_Code_From_Forget = "Update JustEat.AccountInfo set "
			+ "account_code=?"
			+ "Where account_id=?";
	
	//註冊用
	//註冊空白會員
	private static final String Insert_Blank_Account_From_Register = "Insert into AccountInfo "
			+ "(account_mail,account_nickname,account_password,account_state,account_level,"
			+ "account_name,account_gender,account_birth,account_phone,account_pic,"
			+ "account_idcard_front,account_idcard_back,account_text,account_register_time,account_code)"
			+ "Values(?,?,?,?,?,?,?,?,?,?,"
			+ "?,?,?,NOW(),?)";
	//更換帳號驗證碼
	private static final String Update_Account_Code_From_Register = "Update JustEat.AccountInfo set "
			+ "account_code=? "
			+ "Where account_mail=?";
	
	//註冊第一層級會員
	private static final String Update_LevelOne_Account_From_Register = "Update JustEat.AccountInfo set "
			+ "account_nickname=?,account_password=?,account_state=?,account_level=?,"
			+ "account_name=?,account_gender=?,account_birth=?,"
			+ "account_text=? "
			+ "Where account_id=?";
	//暫時沒用到
	private static final String Insert_LevelTwo_Account_From_Register = "Insert into AccountInfo "
			+ "(account_mail,account_nickname,account_password,account_name,"
			+ "account_gender,account_birth,"
			+ "account_text,"
			+ "account_register_time) Values(?,?,?,?,?,?,?,?,?,NOW())";
	//註冊第三層級會員
	private static final String Update_LevelThree_Account_From_Register = "Update JustEat.AccountInfo set "
			+ "account_phone=?,account_level=?,"
			+ "account_pic=?,account_idcard_front=?,account_idcard_back=?"
			+ "Where account_id=?";
	

//建立連線池=====
	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace(System.err);
		}
	}
//建立連線池=====	
	
//指令詳細區==============================================================================
//後臺用 insert 
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
			pstmt.setString(14,accountInfoVO.getAccountCode());
			
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				Integer autoGeneratedkey = rs.getInt(1);
				System.out.println("AccountInfo insert completed!account_id="+autoGeneratedkey);
			}else {
				System.out.println("AccountInfo insert failed!");
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	
//後臺用 update
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
			
			pstmt.setInt(14,accountInfoVO.getAccountID());
			
			pstmt.executeUpdate();
			
			System.out.println("AccountInfo update completed!");
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());	
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
	
//後臺用 delete
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
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
//後臺用 selectOneAccountInfo
	@Override
	public AccountInfoVO selectOneAccountInfo(Integer accountID) {
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
				accountInfoVO.setAccountRegisterTime(rs.getTimestamp("account_register_time"));
				accountInfoVO.setAccountCode(rs.getString("account_code"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
//後臺用 selectAllAccountInfo
	@Override
	public List<AccountInfoVO> selectAllAccountInfo() {
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
				accountInfoVO.setAccountRegisterTime(rs.getTimestamp("account_register_time"));
				accountInfoVO.setAccountCode(rs.getString("account_code"));
				
				list.add(accountInfoVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
//後台用 停權
	public void freezeAccountInfo(Integer accountID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Update_FreezeAccountInfo_Stmt);	
			
			pstmt.setInt(1,new Integer(0));
			pstmt.setInt(2,accountID);
			
			pstmt.executeUpdate();
			
			System.out.println("AccountInfo freeze completed!");

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
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
	}
//後台用 啟用
public void activeAccountInfo(Integer accountID) {
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = DriverManager.getConnection(url, userid, password);
				pstmt = con.prepareStatement(Update_ActiveAccountInfo_Stmt);	
				
				pstmt.setInt(1,new Integer(1));
				pstmt.setInt(2,accountID);
				
				pstmt.executeUpdate();
				
				System.out.println("AccountInfo active completed!");

			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
			} finally {
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
		}
	
	
	
//=======================================================================================
//review
//登入用
	@Override
	//傳入 信箱 密碼 回傳 含所有資料的 VO物件
	public AccountInfoVO getAccountMailPasswordForLogin(String accountMail,String accountPassword) {
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
				//無回傳 ID 驗證碼
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
				accountInfoVO.setAccountRegisterTime(rs.getTimestamp("account_register_time"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	//輸入 信箱值 回傳 含信箱的 VO物件 = 檢查資料庫有沒有這個信箱
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
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	//輸入 暱稱值 回傳 含信箱的 VO物件 = 檢查資料庫有沒有這個暱稱
	public AccountInfoVO getAccountNickname(String accountNickname) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AccountInfoVO accountInfoVO = null;

		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Select_Account_Nickname);

			pstmt.setString(1, accountNickname);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				accountInfoVO = new AccountInfoVO();
				
				accountInfoVO.setAccountNickname(rs.getString("account_nickname"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	//輸入 暱稱值 回傳 含信箱的 VO物件 = 檢查資料庫有沒有這個密碼
	public AccountInfoVO getAccountCodeByAccountMail(String accountMail) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AccountInfoVO accountInfoVO = null;

		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(select_Account_Code_By_AccountMail);

			pstmt.setString(1, accountMail);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				accountInfoVO = new AccountInfoVO();
				
				accountInfoVO.setAccountCode(rs.getString("account_code"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	//輸入 信箱值 回傳 含 ID 的 VO物件
	public AccountInfoVO getAccountIDByAccountMail(String accountMail) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AccountInfoVO accountInfoVO = null;
		
		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Select_Account_ID_By_AccountMail);
			
			pstmt.setString(1, accountMail);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				accountInfoVO= new AccountInfoVO();
				accountInfoVO.setAccountID(rs.getInt("account_id"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	//輸入 信箱值 回傳 含暱稱的 VO物件 = 資料庫有該會員，用來確認輸入密碼是否符合資料庫
	public AccountInfoVO getAccountNicknameByAccountMail(String accountMail) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AccountInfoVO accountInfoVO = null;
		
		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Select_Account_Nickname_By_AccountMail);
			
			pstmt.setString(1, accountMail);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				accountInfoVO = new AccountInfoVO();
				
				accountInfoVO.setAccountNickname(rs.getString("account_nickname"));
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
	//輸入 信箱值 回傳 含暱稱的 VO物件 = 資料庫有該會員，用來確認輸入密碼是否符合資料庫
	public AccountInfoVO getAccountLevelByAccountMail(String accountMail) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AccountInfoVO accountInfoVO = null;
		
		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Select_Account_Level_By_AccountMail);
			
			pstmt.setString(1, accountMail);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				accountInfoVO = new AccountInfoVO();
				
				accountInfoVO.setAccountLevel(rs.getInt("account_level"));
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
	//輸入 信箱值 回傳 含密碼 的 VO物件 = 資料庫有該會員，用來確認輸入密碼是否符合資料庫
	public AccountInfoVO getAccountPasswordByAccountMail(String accountMail) {
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
	
//會員修改資料用 review06261700
	public void updateAccountInfoFromChange(AccountInfoVO accountInfoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Update_Account_Info_From_Change);
			
			pstmt.setString(1,accountInfoVO.getAccountPassword());
			pstmt.setString(2,accountInfoVO.getAccountName());
			pstmt.setInt(3,accountInfoVO.getAccountGender());
			pstmt.setDate(4,(java.sql.Date)accountInfoVO.getAccountBirth());
			pstmt.setString(5,accountInfoVO.getAccountPhone());
			pstmt.setBytes(6,accountInfoVO.getAccountPic());
			pstmt.setString(7,accountInfoVO.getAccountText());

			pstmt.setInt(8,accountInfoVO.getAccountID());
			
			pstmt.executeUpdate();
			
			System.out.println("updateAccountInfoFromChange completed!");
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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

	
//忘記密碼
	//收到會員忘記密碼寄出驗證信
	public void updateAccountCodeFromForget(AccountInfoVO accountInfoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Update_Account_Code_From_Forget);
			
			pstmt.setString(1,accountInfoVO.getAccountCode());
			pstmt.setInt(2,accountInfoVO.getAccountID());
			
			pstmt.executeUpdate();

			System.out.println("updateAccountCodeFromForget completed!");
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	
//註冊用
	//收到信箱 暱稱驗證碼存到資料庫
	@Override
	public void setBlankAccountInfoFromRegister(AccountInfoVO accountInfoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] autoGeneratedCol = {"account_id"};

		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Insert_Blank_Account_From_Register, autoGeneratedCol);

			pstmt.setString(1,accountInfoVO.getAccountMail());//傳入
			pstmt.setString(2,"使用者暱稱");
			pstmt.setString(3,new String("1234"));//預設密碼
			pstmt.setBoolean(4,false);
			pstmt.setInt(5,0);//會員層級零
			
			pstmt.setString(6,"使用者姓名");
			pstmt.setInt(7,new Integer(1));//性別預設男
			pstmt.setDate(8,java.sql.Date.valueOf("2000-01-01"));
			pstmt.setString(9,null);
			pstmt.setBytes(10,null);
			
			pstmt.setBytes(11,null);
			pstmt.setBytes(12,null);
			pstmt.setString(13,null);
			
			pstmt.setString(14,accountInfoVO.getAccountCode());
			
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				Integer autoGeneratedkey = rs.getInt(1);
				System.out.println("AccountInfo Register LevelOne completed!account_id="+autoGeneratedkey);
			}else {
				System.out.println("AccountInfo Register LevelOne failed!");
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	
	//一般會員註冊，從前台傳入檢查過之資料並設定該會員的基本數值
	//review0625OK
	@Override
	public void setLevelOneAccountInfoFromRegister(AccountInfoVO accountInfoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Update_LevelOne_Account_From_Register);

			pstmt.setString(1,accountInfoVO.getAccountNickname());
			pstmt.setString(2,accountInfoVO.getAccountPassword());//傳入
			pstmt.setBoolean(3,true);
			pstmt.setInt(4,new Integer(1));
			
			pstmt.setString(5,accountInfoVO.getAccountName());//傳入
			pstmt.setInt(6,accountInfoVO.getAccountGender());//傳入
			pstmt.setDate(7,accountInfoVO.getAccountBirth());//傳入

			pstmt.setString(8,accountInfoVO.getAccountText());//傳入
			
			pstmt.setInt(9, accountInfoVO.getAccountID());
			
			pstmt.executeUpdate();
			
			System.out.println("setLevelOneAccountInfoFromRegister completed!");

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void updateBlankAccountCodeFromRegister(AccountInfoVO accountInfoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Update_Account_Code_From_Register);
			
			pstmt.setString(1,accountInfoVO.getAccountCode());
			pstmt.setString(2,accountInfoVO.getAccountMail());
			
			pstmt.executeUpdate();

			System.out.println("updateBlankAccountCodeFromRegister completed!");
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void setLevelTwoAccountInfoFromRegister(String accountMail, String accountNickname, String accountPassword,
			String accountName, Integer accountGender, Date accountBirth, String accountPhone, String accountText) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void setLevelThreeAccountInfoFromRegister(AccountInfoVO accountInfoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Update_LevelThree_Account_From_Register);
			
			pstmt.setString(1,accountInfoVO.getAccountPhone());
			pstmt.setInt(2,new Integer(1));
			
			pstmt.setBytes(3, accountInfoVO.getAccountPic());
			pstmt.setBytes(4, accountInfoVO.getAccountIDcardFront());
			pstmt.setBytes(5, accountInfoVO.getAccountIDcardBack());

			pstmt.setInt(6,accountInfoVO.getAccountID());
			
			pstmt.executeUpdate();

			System.out.println("setLevelThreeAccountInfoFromRegister completed!");

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
	
	
	
//=============================================================
	public static void main(String[] args) {
		String date = "2021-02-03 14:00";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime localDateTime = LocalDateTime.parse(date,formatter);
		Timestamp timestampdate = Timestamp.valueOf(localDateTime);
		
		AccountInfoJDBCDAO accountInfoJDBCDAO = new AccountInfoJDBCDAO();
	
		//新增10名會員
//		String[] aMail = {
//			"user1@gmail.com","user2@gmail.com","user3@gmail.com","user4@gmail.com","user5@gmail.com",
//			"user6@gmail.com","user7@gmail.com","user8@gmail.com","user9@gmail.com","user10@gmail.com"};
//		String[] aNickname = {
//				"使用者暱稱1","使用者暱稱2","使用者暱稱3","使用者暱稱4","使用者暱稱5",
//				"使用者暱稱6","使用者暱稱7","使用者暱稱8","使用者暱稱9","使用者暱稱10"};
//		String[] aPassword = {
//				"1111aaaa","2222bbbb","3333cccc","4444dddd","55555dddd",
//				"6666eeee","7777ffff","8888gggg","9999hhhh","0000iiii"};
//		Boolean[] aSate = {
//				true,true,true,true,true,
//				true,true,true,true,true};
//		String[] aName = {
//				"使用者名稱1","使用者名稱2","使用者名稱3","使用者名稱4","使用者名稱5",
//				"使用者名稱6","使用者名稱7","使用者名稱8","使用者名稱9","使用者名稱10"};
//		Integer[] aLevel = {
//				1,1,1,1,1,
//				1,1,1,1,1};
//		Integer[] aGender = {
//				1,2,1,2,1,
//				2,1,2,1,2};
//		String[] aBirth = {
//			"1990-01-01","1990-01-02","1990-01-03","1990-01-04","1990-01-05",
//			"1990-01-06","1990-01-07","1990-01-08","1990-01-09","1990-01-10"};
//		String[] aPhone = {
//				"0912345678","0923456789","0934567891","0945678912","0956789123",
//				"0967891234","0978912345","0978912345","0989123456","0991234567"};
//		for(int i =0;i<10;i++) {
//			
//		AccountInfoVO accountInfoVO1 = new AccountInfoVO();
//		accountInfoVO1.setAccountMail(aMail[i]);
//		accountInfoVO1.setAccountNickname(aNickname[i]);
//		accountInfoVO1.setAccountPassword(aPassword[i]);
//		accountInfoVO1.setAccountState(aSate[i]);
//		
//		accountInfoVO1.setAccountName(aName[i]);
//		accountInfoVO1.setAccountLevel(aLevel[i]);
//		accountInfoVO1.setAccountGender(aGender[i]);
//		accountInfoVO1.setAccountBirth(java.sql.Date.valueOf(aBirth[i]));
//		accountInfoVO1.setAccountPhone(aPhone[i]);
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
//		accountInfoVO1.setAccountText("我是一個普通會員代號"+aName[i]+"，ㄏㄏㄏ，MIC TEST!!!");
//		accountInfoVO1.setAccountRegistTime(timestampdate);
//		
//		accountInfoJDBCDAO.insert(accountInfoVO1);
//		
//		System.out.println("accountInfoVO1"+i+"insert complete!");
//		}//for
		
//===================================================================
	//新增一名會員 OK
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



