package com.dislikeingredient.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DislikeIngredientJDBCDAO implements DislikeIngredientDAOInterface {

	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/JustEat?serverTimezone=Asia/Taipei"
			+ "&rewriteBatchedStatements=true";
	private static String userid = "DBAdmin";
	private static String passwd = "justeat";

	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("找不到驅動(driver)" + ce.getMessage());
		}
	}

	private static final String INSERT = "INSERT INTO DislikeIngredient(account_id, dislike_ingredient_id) VALUES(?, ?)";
//	private static final String UPDATE = "UPDATE DislikeIngredient SET dislike_ingredient_id = ? WHERE account_id = ?";
	private static final String DELETE = "DELETE FROM DislikeIngredient WHERE account_id = ? and dislike_ingredient_id = ?";
	private static final String SELECT_ONE_BY_ID = "SELECT * FROM DislikeIngredient WHERE account_id = ? and dislike_ingredient_id = ?";
	private static final String SELECT_ALL_BY_ACCOUNT = "SELECT * FROM DislikeIngredient WHERE account_id = ?";
	private static final String SELECT_ALL_BY_INGREDIENT = "SELECT * FROM DislikeIngredient WHERE dislike_ingredient_id = ?";


	@Override
	public int insert(DislikeIngredientVO dislikeIngredient) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int insertRow = 0;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT);

			pstmt.setInt(1, dislikeIngredient.getAccountID());
			pstmt.setInt(2, dislikeIngredient.getDislikeIngredientID());

			insertRow = pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException("資料庫發生錯誤" + e.getMessage());
		} finally {

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					throw new RuntimeException("PrepareStatement發生錯誤" + se.getMessage());
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					throw new RuntimeException("資料庫連線發生錯誤" + e.getMessage());
				}
			}

		}
		return insertRow;
	}

	@Override
	public int delete(DislikeIngredientVO dislikeIngredient) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int deleteRow = 0;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, dislikeIngredient.getAccountID());
			pstmt.setInt(2, dislikeIngredient.getDislikeIngredientID());

			deleteRow = pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException("資料庫發生錯誤" + e.getMessage());
		} finally {

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					throw new RuntimeException("PrepareStatement發生錯誤" + se.getMessage());
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					throw new RuntimeException("資料庫連線發生錯誤" + e.getMessage());
				}
			}

		}
		return deleteRow;
	}

	@Override
	public boolean isExist(DislikeIngredientVO dislikeIngredient) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean existStatus = false;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ONE_BY_ID);

			pstmt.setInt(1, dislikeIngredient.getAccountID());
			pstmt.setInt(2, dislikeIngredient.getDislikeIngredientID());

			rs = pstmt.executeQuery();
			if (rs.next()) {
				existStatus = true;
			}

		} catch (SQLException e) {
			throw new RuntimeException("資料庫發生錯誤" + e.getMessage());
		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					throw new RuntimeException("ResultSet發生錯誤" + se.getMessage());
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					throw new RuntimeException("PrepareStatement發生錯誤" + se.getMessage());
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					throw new RuntimeException("資料庫連線發生錯誤" + e.getMessage());
				}
			}

		}
		return existStatus;
	}

	@Override
	public List<DislikeIngredientVO> getAllByAccount(int accountID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<DislikeIngredientVO> alldislikeIngredient = new ArrayList<DislikeIngredientVO>();
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ALL_BY_ACCOUNT);

			pstmt.setInt(1,accountID);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				DislikeIngredientVO dislikeIngredient = new DislikeIngredientVO();
				dislikeIngredient.setAccountID(rs.getInt("account_id"));
				dislikeIngredient.setDislikeIngredientID(rs.getInt("dislike_ingredient_id"));
				alldislikeIngredient.add(dislikeIngredient);
			}

		} catch (SQLException e) {
			throw new RuntimeException("資料庫發生錯誤" + e.getMessage());
		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					throw new RuntimeException("ResultSet發生錯誤" + se.getMessage());
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					throw new RuntimeException("PrepareStatement發生錯誤" + se.getMessage());
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					throw new RuntimeException("資料庫連線發生錯誤" + e.getMessage());
				}
			}

		}
		return alldislikeIngredient;
	}

	@Override
	public List<DislikeIngredientVO> getAllByIngredient(int dislikeIngredientID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<DislikeIngredientVO> alldislikeIngredient = new ArrayList<DislikeIngredientVO>();
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ALL_BY_INGREDIENT);

			pstmt.setInt(1, dislikeIngredientID);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				DislikeIngredientVO dislikeIngredient = new DislikeIngredientVO();
				dislikeIngredient.setAccountID(rs.getInt("account_id"));
				dislikeIngredient.setDislikeIngredientID(rs.getInt("dislike_ingredient_id"));
				alldislikeIngredient.add(dislikeIngredient);
			}

		} catch (SQLException e) {
			throw new RuntimeException("資料庫發生錯誤" + e.getMessage());
		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					throw new RuntimeException("ResultSet發生錯誤" + se.getMessage());
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					throw new RuntimeException("PrepareStatement發生錯誤" + se.getMessage());
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					throw new RuntimeException("資料庫連線發生錯誤" + e.getMessage());
				}
			}

		}
		return alldislikeIngredient;
	}
	
	public static void main(String args[]) {
		DislikeIngredientVO vo = new DislikeIngredientVO();
		DislikeIngredientJDBCDAO dao = new DislikeIngredientJDBCDAO();
		List<DislikeIngredientVO> list;
		
//		// 測試 isExist & insert（必定同時出現）成功！
		vo.setAccountID(100001);
		vo.setDislikeIngredientID(220010);
		boolean exist = dao.isExist(vo);
		if (exist == false) {
			if (dao.insert(vo) > 0) {
				System.out.println("新增成功");
			}
		} else {
			System.out.println("已存在!");
		}

//		// 測試 get all by account 成功
		list = dao.getAllByAccount(100002);
		for (DislikeIngredientVO one : list) {
			System.out.println("使用者" + one.getAccountID() + "不喜歡" + one.getDislikeIngredientID() + "食材\n========");
		}
		
		// 測試 get all by ingredient 成功
		list = dao.getAllByIngredient(220011);
		for (DislikeIngredientVO one : list) {
			System.out.println("食材" + one.getDislikeIngredientID() + "被使用者" + one.getAccountID() + "不喜歡\n========");
		}
		
	}

}
