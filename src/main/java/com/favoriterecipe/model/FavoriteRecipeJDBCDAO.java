package com.favoriterecipe.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class FavoriteRecipeJDBCDAO implements FavoriteRecipeDAOInterface {

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

	private static final String INSERT = "INSERT INTO FavoriteRecipe(account_id, fav_recipe_id, fav_time) VALUES(?, ?, NOW())";
//	private static final String UPDATE = "UPDATE FavoriteRecipe SET fav_recipe_id = ?, fav_time = NOW() WHERE account_id = ?";
	private static final String UPDATE_TIME = "UPDATE FavoriteRecipe SET fav_time = NOW() WHERE account_id = ? and fav_recipe_id = ?";
	private static final String DELETE = "DELETE FROM FavoriteRecipe WHERE account_id = ? and fav_recipe_id = ?";
	private static final String SELECT_ONE_BY_ID = "SELECT * FROM FavoriteRecipe WHERE account_id = ? and fav_recipe_id = ?";
	// 順序寫死，時間降冪；前端可依抓出屬性排列?
	private static final String SELECT_ALL_BY_ACCOUNT = "SELECT * FROM FavoriteRecipe WHERE account_id = ? ORDER BY fav_time DESC";
	private static final String SELECT_ALL_BY_RECIPE = "SELECT * FROM FavoriteRecipe WHERE fav_recipe_id = ? ORDER BY fav_time DESC";
	private static final String COUNT_ALL_BY_RECIPE = "SELECT COUNT(*) FROM FavoriteRecipe WHERE fav_recipe_id = ?";
	private static final String SELECT_ALL_IN_TIME = 
			"SELECT fav_recipe_id, COUNT(*) as count " + 
			"FROM JustEat.FavoriteRecipe " + 
			"WHERE TO_DAYS(NOW()) - TO_DAYS(fav_time) <= ? " + 
			"GROUP BY fav_recipe_id " + 
			"ORDER BY count DESC LIMIT ?";
	
	@Override
	public int insert(FavoriteRecipeVO favoriteRecipe) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int insertRow = 0;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT);

			pstmt.setInt(1, favoriteRecipe.getAccountID());
			pstmt.setInt(2, favoriteRecipe.getFavRecipeID());

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

//	@Override
//	public int update(FavoriteRecipeVO favoriteRecipe) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		int updateRow = 0;
//
//		try {
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(UPDATE);
//
//			pstmt.setInt(1, favoriteRecipe.getFavRecipeID());
////			pstmt.setTimestamp(2, favoriteRecipe.getFavTime());
//			pstmt.setInt(2, favoriteRecipe.getAccountID());
//
//			updateRow = pstmt.executeUpdate();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//
//		}
//		return updateRow;
//	}

	@Override
	public int delete(FavoriteRecipeVO favoriteRecipe) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int deleteRow = 0;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, favoriteRecipe.getAccountID());
			pstmt.setInt(2, favoriteRecipe.getFavRecipeID());

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
	public List<FavoriteRecipeVO> getAllByAccount(int accountID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<FavoriteRecipeVO> allFavoriteRecipe = new ArrayList<FavoriteRecipeVO>();

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ALL_BY_ACCOUNT);

			pstmt.setInt(1, accountID);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				FavoriteRecipeVO favoriteRecipe = new FavoriteRecipeVO();
				favoriteRecipe.setAccountID(rs.getInt("account_id"));
				favoriteRecipe.setFavRecipeID(rs.getInt("fav_recipe_id"));
				favoriteRecipe.setFavTime(rs.getTimestamp("fav_time"));
				allFavoriteRecipe.add(favoriteRecipe);
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
		return allFavoriteRecipe;
	}

	@Override
	public List<FavoriteRecipeVO> getAllByRecipe(int favRecipeID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<FavoriteRecipeVO> allFavoriteRecipe = new ArrayList<FavoriteRecipeVO>();

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ALL_BY_RECIPE);

			pstmt.setInt(1, favRecipeID);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				FavoriteRecipeVO favoriteRecipe = new FavoriteRecipeVO();
				favoriteRecipe.setAccountID(rs.getInt("account_id"));
				favoriteRecipe.setFavRecipeID(rs.getInt("fav_recipe_id"));
				favoriteRecipe.setFavTime(rs.getTimestamp("fav_time"));
				allFavoriteRecipe.add(favoriteRecipe);
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
		return allFavoriteRecipe;
	}

	@Override
	public Timestamp isExist(FavoriteRecipeVO favoriteRecipe) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Timestamp favTime = null;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ONE_BY_ID);

			pstmt.setInt(1, favoriteRecipe.getAccountID());
			pstmt.setInt(2, favoriteRecipe.getFavRecipeID());

			rs = pstmt.executeQuery();
			if (rs.next()) {
//				favoriteRecipe.setAccountID(rs.getInt("account_id"));
//				favoriteRecipe.setFavRecipeID(rs.getInt("fav_recipe_id"));
//				favoriteRecipe.setFavTime(rs.getTimestamp("fav_time"));
				favTime = rs.getTimestamp("fav_time");
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
		return favTime;
	}

	@Override
	public int updateTime(FavoriteRecipeVO favoriteRecipe) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int updateRow = 0;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_TIME);

//			pstmt.setTimestamp(1, favoriteRecipe.getFavTime());
			pstmt.setInt(1, favoriteRecipe.getAccountID());
			pstmt.setInt(2, favoriteRecipe.getFavRecipeID());
			
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

	// 測試用
	public static void main(String args[]) {
		FavoriteRecipeVO vo = new FavoriteRecipeVO();
		FavoriteRecipeJDBCDAO dao = new FavoriteRecipeJDBCDAO();
		List<FavoriteRecipeVO> list;

//		// 測試 isExist & insert / updateTime（必定同時出現）成功！
//		vo.setAccountID(100002);
//		vo.setFavRecipeID(200002);
//		Timestamp time = dao.isExist(vo);
//		if (time == null) {
//			if (dao.insert(vo) > 0) {
//				System.out.println("新增成功");
//			}
//		} else {
////		vo.setFavTime(time);
////		System.out.println("使用者" + vo.getAccountID() + "已於" + vo.getFavTime() + "收藏過食譜" + vo.getFavRecipeID());
//			if (dao.updateTime(vo) > 0) {
//				System.out.println("修改成功");
//			}
//		}

//		// 測試 get all by account 成功
//		list = dao.getAllByAccount(100002);
//		for (FavoriteRecipeVO one : list) {
//			System.out.println("使用者" + one.getAccountID() + "於" + one.getFavTime() + "收藏食譜" + one.getFavRecipeID() + "\n========");
//		}
		
		// 測試 get all by recipe 成功
		list = dao.getAllByRecipe(200001);
		for (FavoriteRecipeVO one : list) {
			System.out.println("食譜" + one.getFavRecipeID() + "於" + one.getFavTime() + "被使用者" + one.getAccountID() + "收藏\n========");
		}

	}

	@Override
	public int countAllByRecipe(int favRecipeID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(COUNT_ALL_BY_RECIPE);

			pstmt.setInt(1, favRecipeID);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
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
		return count;
	}

	@Override
	public List<FavoriteRecipeVO> getAllByCountInDays(int days, int limit){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<FavoriteRecipeVO> allFavoriteRecipe = new ArrayList<FavoriteRecipeVO>();

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ALL_IN_TIME);

			pstmt.setInt(1, days);
			pstmt.setInt(2, limit);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				FavoriteRecipeVO favoriteRecipe = new FavoriteRecipeVO();
				favoriteRecipe.setFavRecipeID(rs.getInt("fav_recipe_id"));
				favoriteRecipe.setTempCount(rs.getInt("count"));
				allFavoriteRecipe.add(favoriteRecipe);
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
		return allFavoriteRecipe;
	}

}
