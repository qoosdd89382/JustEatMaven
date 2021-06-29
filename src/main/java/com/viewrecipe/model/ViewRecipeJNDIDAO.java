package com.viewrecipe.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.thumbsuprecipe.model.ThumbsupRecipeVO;

public class ViewRecipeJNDIDAO implements ViewRecipeDAOInterface {
	private static DataSource ds = null;
	// view_recipe_id, account_id, recipe_id, view_time
	private static final String INSERT = "INSERT INTO ViewRecipe(account_id, recipe_id, view_time) VALUES(?, ?, NOW())";
	private static final String SELECT_ALL_BY_ACCOUNT = "SELECT * FROM ViewRecipe WHERE account_id = ? ORDER BY view_time DESC";
	private static final String SELECT_ALL_BY_RECIPE = "SELECT * FROM ViewRecipe WHERE recipe_id = ? ORDER BY view_time DESC";
	private static final String COUNT_ALL_BY_RECIPE = "SELECT COUNT(*) FROM ViewRecipe WHERE recipe_id = ?";
	private static final String HOTTEST_TODAY = "SELECT COUNT(*) count WHERE YEARWEEK(date_format(view_time,'%Y-%m-%d')) = YEARWEEK(now()) GROUP BY recipe_id ORDER BY count DESC ";
	private static final String HOTTEST_YESTERDAY = "SELECT COUNT(*) count WHERE YEARWEEK(date_format(view_time,'%Y-%m-%d')) = YEARWEEK(now())-1 GROUP BY recipe_id ORDER BY count DESC ";
	private static final String BEST_LAST_WEEK = "SELECT COUNT(*) count WHERE TO_DAYS(NOW()) - TO_DAYS(view_time) <= 7  GROUP BY recipe_id ORDER BY count DESC ";
	private static final String LIMIT = " Limit 10 ";
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}


	@Override
	public Map<Integer, Integer> getSomeHot() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<Integer, Integer> all = new HashMap<Integer, Integer>();

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(HOTTEST_TODAY + LIMIT);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				all.put(rs.getInt(2), rs.getInt(1));	// key = recipeID, value = count 
			}

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
		return all;
	}
	
	
	@Override
	public int insert(ViewRecipeVO viewRecipe) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String[] autoGereatedCols = { "view_recipe_id" };
		ResultSet rs = null;
		int autoGereatedKey = 0;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT, autoGereatedCols);

			pstmt.setInt(1, viewRecipe.getAccountID());
			pstmt.setInt(2, viewRecipe.getRecipeID());

			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				autoGereatedKey = rs.getInt(1);
			}

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
		return autoGereatedKey;
	}

	@Override
	public List<ViewRecipeVO> getAllByAccount(int accountID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ViewRecipeVO> all = new ArrayList<ViewRecipeVO>();

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SELECT_ALL_BY_ACCOUNT);

			pstmt.setInt(1, accountID);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				ViewRecipeVO vo = new ViewRecipeVO();
				vo.setViewRecipeID(rs.getInt("view_recipe_id"));
				vo.setAccountID(rs.getInt("account_id"));
				vo.setRecipeID(rs.getInt("recipe_id"));
				vo.setViewTime(rs.getTimestamp("view_time"));
				all.add(vo);
			}

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
		return all;
	}

	@Override
	public List<ViewRecipeVO> getAllByRecipe(int recipeID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ViewRecipeVO> all = new ArrayList<ViewRecipeVO>();

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SELECT_ALL_BY_RECIPE);

			pstmt.setInt(1, recipeID);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				ViewRecipeVO vo = new ViewRecipeVO();
				vo.setViewRecipeID(rs.getInt("view_recipe_id"));
				vo.setAccountID(rs.getInt("account_id"));
				vo.setRecipeID(rs.getInt("recipe_id"));
				vo.setViewTime(rs.getTimestamp("view_time"));
				all.add(vo);
			}

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
		return all;
	}

	@Override
	public int countAllByRecipe(int recipeID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(COUNT_ALL_BY_RECIPE);

			pstmt.setInt(1, recipeID);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}

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
		return count;
	}

}
