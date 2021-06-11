package com.ingredientandtag.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredientAndTagJDBCDAO implements IngredientAndTagDAOInterface {

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

	private static final String INSERT = "INSERT INTO IngredientAndTag(ingredientTag_id, ingredient_id) VALUES(?, ?)";
	private static final String DELETE = "DELETE FROM IngredientAndTag WHERE ingredientTag_id = ? and ingredient_id = ?";
	private static final String SELECT_ONE_BY_ID = "SELECT * FROM IngredientAndTag WHERE ingredientTag_id = ? and ingredient_id = ?";
	private static final String SELECT_ALL_BY_TAG = "SELECT * FROM IngredientAndTag WHERE ingredientTag_id = ?";
	private static final String SELECT_ALL_BY_INGREDIENT = "SELECT * FROM IngredientAndTag WHERE ingredient_id = ?";

	@Override
	public int insert(IngredientAndTagVO ingredientAndTag) {

		Connection con = null;
		PreparedStatement pstmt = null;
		int insertRow = 0;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT);

			pstmt.setInt(1, ingredientAndTag.getIngredientTagID());
			pstmt.setInt(2, ingredientAndTag.getIngredientID());

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
	public int delete(IngredientAndTagVO ingredientAndTag) {

		Connection con = null;
		PreparedStatement pstmt = null;
		int deleteRow = 0;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, ingredientAndTag.getIngredientTagID());
			pstmt.setInt(2, ingredientAndTag.getIngredientID());

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
	public boolean isExist(IngredientAndTagVO ingredientAndTag) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean existStatus = false;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ONE_BY_ID);

			pstmt.setInt(1, ingredientAndTag.getIngredientTagID());
			pstmt.setInt(2, ingredientAndTag.getIngredientID());

			rs = pstmt.executeQuery();
			if (rs.next()) {
				existStatus = true;
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
		return existStatus;
	}

	@Override
	public List<IngredientAndTagVO> getAllByIngredient(int ingredientID) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<IngredientAndTagVO> allIngredientAndTag = new ArrayList<IngredientAndTagVO>();

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ALL_BY_INGREDIENT);

			pstmt.setInt(1, ingredientID);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				IngredientAndTagVO ingredientAndTag = new IngredientAndTagVO();
				ingredientAndTag.setIngredientTagID(rs.getInt("ingredientTag_id"));
				ingredientAndTag.setIngredientID(rs.getInt("ingredient_id"));
				allIngredientAndTag.add(ingredientAndTag);
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
		return allIngredientAndTag;
	}

	@Override
	public List<IngredientAndTagVO> getAllByTag(int ingredientTagID) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<IngredientAndTagVO> allIngredientAndTag = new ArrayList<IngredientAndTagVO>();

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ALL_BY_TAG);

			pstmt.setInt(1, ingredientTagID);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				IngredientAndTagVO ingredientAndTag = new IngredientAndTagVO();
				ingredientAndTag.setIngredientTagID(rs.getInt("ingredientTag_id"));
				ingredientAndTag.setIngredientID(rs.getInt("ingredient_id"));
				allIngredientAndTag.add(ingredientAndTag);
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
		return allIngredientAndTag;
	}
	
	public static void main(String args[]) {
		IngredientAndTagVO vo = new IngredientAndTagVO();
		IngredientAndTagJDBCDAO dao = new IngredientAndTagJDBCDAO();
		List<IngredientAndTagVO> list;
		
//		// 測試 insert 成功
//		vo.setIngredientTagID(210007);
//		vo.setIngredientID(220005);
//		if (!dao.isExist(vo)) {
//			int insertSuccess = dao.insert(vo);
//			if (insertSuccess > 0)
//				System.out.println(insertSuccess + "筆資料成功新增");
//		} else {
//			System.out.println("此食材標籤與分類已配對過!");
//		}
		
//		// 測試 delete 成功
//		vo.setIngredientTagID(210001);
//		vo.setIngredientID(220009);
//		int delSuccess = dao.delete(vo);
//		if (delSuccess > 0) {
//			System.out.println(delSuccess + "筆資料成功刪除");
//		}

//		// 測試 get all by 食材，成功
//		list = dao.getAllByIngredient(220005);
//		list.forEach(one -> System.out.println("食材分類編號：" + one.getIngredientTagID() + "\n食材編號：" + one.getIngredientID() + "\n========="));
		

		// 測試 get all by 分類，成功
		list = dao.getAllByTag(210001);
		list.forEach(one -> System.out.println("食材分類編號：" + one.getIngredientTagID() + "\n食材編號：" + one.getIngredientID() + "\n========="));
		
	}
}
