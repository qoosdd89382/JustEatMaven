package com.friendship.model;

import java.util.*;

import com.accountinfo.model.AccountInfoVO;
import com.visit.model.VisitJDBCDAO;
import com.visit.model.VisitVO;

import java.sql.*;

public class FriendshipJDBCDAO implements FriendshipDAOInterface {
	
	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/JustEat?serverTimezone=Asia/Taipei";
	private static String userid = "DBAdmin";
	private static String password = "justeat";
	
	private static final String Insert_Stmt = "Insert into Friendship "
			+ "(account_id,friend_id,friendship_state)"
			+ "Values(?,?,?)";

	private static final String Update_Stmt = "Update Friendship set "
			+ "friendship_state=? Where account_id=? && friend_id=?";

	private static final String Delete_Stmt = "Delete From Friendship Where account_id=? && friend_id=?";
	private static final String Select_Key_Stmt = "Select * From JustEat.Friendship Where account_id=?";
	private static final String Select_All_Stmt = "Select * From JustEat.Friendship";
	
//好友頁面用
	private static final String Select_Account_Friendship = "Select * From JustEat.Friendship Where account_id=?";
	
	//透過session裡面的accountmail找他相關的好友暱稱
	private static final String Select_Account_Friendship_By_AccountID = 
			"Select * From JustEat.AccountInfo where "//用ID找暱稱
			+ "account_id in (SELECT friend_id From JustEat.Friendship where "//找那些符合條件FriendID
			+ "account_id = (select account_id From JustEat.AccountInfo where "//找那些跟session的accountmail有好友關係的friendID
			+ "account_id=?) && friendship_state=1);";
	
	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace(System.err);
		}
	}
	@Override
	public void insert(FriendshipVO friendshipVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(url,userid,password);
			pstmt= con.prepareStatement(Insert_Stmt);
			
			pstmt.setInt(1,friendshipVO.getAccountID());
			pstmt.setInt(2,friendshipVO.getFriendID());
			pstmt.setInt(3,friendshipVO.getFriendshipState());
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void update(FriendshipVO friendshipVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(url,userid,password);
			pstmt = con.prepareStatement(Update_Stmt);
			
			pstmt.setInt(1,friendshipVO.getFriendshipState());
			pstmt.setInt(2,friendshipVO.getAccountID());
			pstmt.setInt(3,friendshipVO.getFriendID());
			
			pstmt.executeUpdate();
			
			System.out.println("Friendship update completed!");
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void delete(Integer account_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Delete_Stmt);
			pstmt.setInt(1, account_id);
			pstmt.executeUpdate();

			System.out.println("Friendship delete completed!");
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
	public FriendshipVO findByPrimaryKey(Integer account_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FriendshipVO friendshipVO = null;
		
		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Select_Key_Stmt);
			
			pstmt.setInt(1, account_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				friendshipVO = new FriendshipVO();
				friendshipVO.setAccountID(rs.getInt("account_id"));
				friendshipVO.setFriendID(rs.getInt("friend_id"));
				friendshipVO.setFriendshipState(rs.getInt("friendship_state"));
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
		return friendshipVO;
	}

	@Override
	public List<FriendshipVO> getAll() {
		List<FriendshipVO> list = new ArrayList<FriendshipVO>();
		FriendshipVO friendshipVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Select_All_Stmt);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				friendshipVO = new FriendshipVO();
				friendshipVO.setAccountID(rs.getInt("account_id"));
				friendshipVO.setFriendID(rs.getInt("friend_id"));
				friendshipVO.setFriendshipState(rs.getInt("friendship_state"));
				list.add(friendshipVO);
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
	
//好友頁面用
	public FriendshipVO getAccountFriendship(Integer account_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FriendshipVO friendshipVO = null;
		
		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Select_Account_Friendship);
			
			pstmt.setInt(1, account_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				friendshipVO = new FriendshipVO();
				friendshipVO.setAccountID(rs.getInt("account_id"));
				friendshipVO.setFriendID(rs.getInt("friend_id"));
				friendshipVO.setFriendshipState(rs.getInt("friendship_state"));
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
		return friendshipVO;
	}
	
	public List<AccountInfoVO> getAccountFriendByAccountID(Integer account_id) {
		List<AccountInfoVO> list = new ArrayList<AccountInfoVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AccountInfoVO friendshipVO = null;
		
		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Select_Account_Friendship_By_AccountID);
			
			pstmt.setInt(1, account_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				friendshipVO = new AccountInfoVO();
				friendshipVO.setAccountNickname(rs.getString("account_nickname"));
				friendshipVO.setAccountID(rs.getInt("account_id"));
				
				list.add(friendshipVO);
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
		return list;
	}
//=========================================================================
	public static void main(String[] args) {
		FriendshipJDBCDAO friendshipJDBCDAO = new FriendshipJDBCDAO();
		
		FriendshipVO friendshipVO = new FriendshipVO();
		//新增 OK 要先在會員新增
//		friendshipVO.setAccountID(100001);
//		friendshipVO.setFriendshipTime(timestampdate);
//		friendshipJDBCDAO.insert(friendshipVO);
		//刪除 OK
//		friendshipJDBCDAO.delete(900001);

		//修改 
//		friendshipVO.setAccountID(100001);
//		friendshipVO.setFriendshipTime(timestampdate);
//		friendshipVO.setFriendshipID(900004);
//		friendshipJDBCDAO.update(friendshipVO);
		
		//查詢 OK
//		friendshipVO = friendshipJDBCDAO.findByPrimaryKey(900004);
//		System.out.println(friendshipVO.getAccountID()+",");
//		System.out.println(friendshipVO.getFriendshipRecord()+",");
//		System.out.println("search complete!");
		
		//查詢全部 OK
//		List<FriendshipVO> friendshipVO_list = friendshipJDBCDAO.getAll();
//		for (FriendshipVO e:friendshipVO_list) {
//			System.out.println(e.getAccountID()+",");
//			System.out.println(e.getFriendshipRecord()+",");
//		}
	}

}
