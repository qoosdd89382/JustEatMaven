package com.thumbsuprecipe.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ThumbsupRecipeJDBCDAO implements ThumbsupRecipeDAOInterface {

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

	private static final String INSERT = "INSERT INTO ThumbsupRecipe(account_id, thmup_recipe_id, thmup_time) VALUES(?, ?, NOW())";
	private static final String UPDATE_TIME = "UPDATE ThumbsupRecipe SET thmup_time = NOW() WHERE account_id = ? and thmup_recipe_id = ?";
	private static final String DELETE = "DELETE FROM ThumbsupRecipe WHERE account_id = ? and thmup_recipe_id = ?";
	private static final String SELECT_ONE_BY_ID = "SELECT * FROM ThumbsupRecipe WHERE account_id = ? and thmup_recipe_id = ?";
	// 順序寫死，時間降冪；前端可依抓出屬性排列?
	private static final String SELECT_ALL_BY_ACCOUNT = "SELECT * FROM ThumbsupRecipe WHERE account_id = ? ORDER BY thmup_time DESC";
	private static final String SELECT_ALL_BY_RECIPE = "SELECT * FROM ThumbsupRecipe WHERE thmup_recipe_id = ? ORDER BY thmup_time DESC";
	
	@Override
	public int insert(ThumbsupRecipeVO thumbsupRecipe) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int insertRow = 0;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT);

			pstmt.setInt(1, thumbsupRecipe.getAccountID());
			pstmt.setInt(2, thumbsupRecipe.getThmupRecipeID());

			insertRow = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
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
		return insertRow;
	}
	


	@Override
	public int delete(ThumbsupRecipeVO thumbsupRecipe) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int deleteRow = 0;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, thumbsupRecipe.getAccountID());
			pstmt.setInt(2, thumbsupRecipe.getThmupRecipeID());

			deleteRow = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
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
		return deleteRow;
	}
	
	@Override
	public Timestamp isExist(ThumbsupRecipeVO thumbsupRecipe) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Timestamp thmupTime = null;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ONE_BY_ID);

			pstmt.setInt(1, thumbsupRecipe.getAccountID());
			pstmt.setInt(2, thumbsupRecipe.getThmupRecipeID());

			rs = pstmt.executeQuery();
			if (rs.next()) {
				thmupTime = rs.getTimestamp("thmup_time");
			}

		} catch (SQLException e) {
			e.printStackTrace();
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
		return thmupTime;
	}
	
	@Override
	public List<ThumbsupRecipeVO> getAllByAccount(int accountID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ThumbsupRecipeVO> allThumbsupRecipe = new ArrayList<ThumbsupRecipeVO>();

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ALL_BY_ACCOUNT);

			pstmt.setInt(1, accountID);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				ThumbsupRecipeVO thumbsupRecipe = new ThumbsupRecipeVO();
				thumbsupRecipe.setAccountID(rs.getInt("account_id"));
				thumbsupRecipe.setThmupRecipeID(rs.getInt("thmup_recipe_id"));
				thumbsupRecipe.setThmupTime(rs.getTimestamp("thmup_time"));
				allThumbsupRecipe.add(thumbsupRecipe);
			}

		} catch (SQLException e) {
			e.printStackTrace();
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
		return allThumbsupRecipe;
	}

	@Override
	public int updateTime(ThumbsupRecipeVO thumbsupRecipe) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int updateRow = 0;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_TIME);

			pstmt.setInt(1, thumbsupRecipe.getAccountID());
			pstmt.setInt(2, thumbsupRecipe.getThmupRecipeID());
			
			updateRow = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();

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
		return updateRow;
	}



	@Override
	public List<ThumbsupRecipeVO> getAllByRecipe(int thmupRecipeID) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ThumbsupRecipeVO> allThumbsupRecipe = new ArrayList<ThumbsupRecipeVO>();

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ALL_BY_RECIPE);

			pstmt.setInt(1, thmupRecipeID);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				ThumbsupRecipeVO thumbsupRecipe = new ThumbsupRecipeVO();
				thumbsupRecipe.setAccountID(rs.getInt("account_id"));
				thumbsupRecipe.setThmupRecipeID(rs.getInt("thmup_recipe_id"));
				thumbsupRecipe.setThmupTime(rs.getTimestamp("thmup_time"));
				allThumbsupRecipe.add(thumbsupRecipe);
			}

		} catch (SQLException e) {
			e.printStackTrace();
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
		return allThumbsupRecipe;
	}

	public static void main(String args[]) {
		ThumbsupRecipeVO vo = new ThumbsupRecipeVO();
		ThumbsupRecipeJDBCDAO dao = new ThumbsupRecipeJDBCDAO();
		List<ThumbsupRecipeVO> list;

		// 測試 isExist & insert / updateTime（必定同時出現）成功！
		vo.setAccountID(100002);
		vo.setThmupRecipeID(200003);
		Timestamp time = dao.isExist(vo);
		if (time == null) {
			if (dao.insert(vo) > 0) {
				System.out.println("新增成功");
			}
		} else {
//			vo.setThmupTime(time);
			if (dao.updateTime(vo) > 0) {
				System.out.println("修改成功");
			}
		}

//		// 測試 get all by account 成功
//		list = dao.getAllByAccount(100002);
//		for (ThumbsupRecipeVO one : list) {
//			System.out.println("使用者" + one.getAccountID() + "於" + one.getThmupTime() + "按食譜" + one.getThmupRecipeID() + "讚\n========");
//		}
		
//		// 測試 get all by recipe 成功
//		list = dao.getAllByRecipe(200002);
//		for (ThumbsupRecipeVO one : list) {
//			System.out.println("食譜" + one.getThmupRecipeID() + "於" + one.getThmupTime() + "被使用者" + one.getAccountID() + "按讚\n========");
//		}
		
	}
}
