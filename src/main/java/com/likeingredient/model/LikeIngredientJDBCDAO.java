package com.likeingredient.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ingredient.model.IngredientVO;

public class LikeIngredientJDBCDAO implements LikeIngredientDAOInterface {

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

	private static final String INSERT = "INSERT INTO LikeIngredient(account_id, like_ingredient_id) VALUES(?, ?)";
//	private static final String UPDATE = "UPDATE LikeIngredient SET like_ingredient_id = ? WHERE account_id = ?";
	private static final String DELETE = "DELETE FROM LikeIngredient WHERE account_id = ? and like_ingredient_id = ?";
	private static final String SELECT_ONE_BY_ID = "SELECT * FROM LikeIngredient WHERE account_id = ? and like_ingredient_id = ?";
	private static final String SELECT_ALL_BY_ACCOUNT = "SELECT * FROM LikeIngredient WHERE account_id = ?";
	private static final String SELECT_ALL_BY_INGREDIENT = "SELECT * FROM LikeIngredient WHERE like_ingredient_id = ?";

	
	
	@Override
	public int insert(LikeIngredientVO likeIngredient) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int insertRow = 0;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT);

			pstmt.setInt(1, likeIngredient.getAccountID());
			pstmt.setInt(2, likeIngredient.getLikeIngredientID());

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
	public int delete(LikeIngredientVO likeIngredient) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int deleteRow = 0;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, likeIngredient.getAccountID());
			pstmt.setInt(2, likeIngredient.getLikeIngredientID());

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
	public boolean isExist(LikeIngredientVO likeIngredient) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean existStatus = false;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ONE_BY_ID);

			pstmt.setInt(1, likeIngredient.getAccountID());
			pstmt.setInt(2, likeIngredient.getLikeIngredientID());

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
	public List<LikeIngredientVO> getAllByAccount(int accountID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<LikeIngredientVO> allLikeIngredient = new ArrayList<LikeIngredientVO>();
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ALL_BY_ACCOUNT);

			pstmt.setInt(1,accountID);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				LikeIngredientVO likeIngredient = new LikeIngredientVO();
				likeIngredient.setAccountID(rs.getInt("account_id"));
				likeIngredient.setLikeIngredientID(rs.getInt("like_ingredient_id"));
				allLikeIngredient.add(likeIngredient);
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
		return allLikeIngredient;
	}

	@Override
	public List<LikeIngredientVO> getAllByIngredient(int likeIngredientID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<LikeIngredientVO> allLikeIngredient = new ArrayList<LikeIngredientVO>();
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ALL_BY_INGREDIENT);

			pstmt.setInt(1, likeIngredientID);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				LikeIngredientVO likeIngredient = new LikeIngredientVO();
				likeIngredient.setAccountID(rs.getInt("account_id"));
				likeIngredient.setLikeIngredientID(rs.getInt("like_ingredient_id"));
				allLikeIngredient.add(likeIngredient);
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
		return allLikeIngredient;
	}
	
	@Override
	public void addAccountLikeIngredient(List<IngredientVO> likeIngredientVOs,Integer accountID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT);
			
			for(IngredientVO ingredientVO : likeIngredientVOs) {
				pstmt.setInt(1,accountID);
				pstmt.setInt(2,ingredientVO.getIngredientID());
				pstmt.executeUpdate();
			}
			
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
	}
	
	
	
	public static void main(String args[]) {
		LikeIngredientVO vo = new LikeIngredientVO();
		LikeIngredientJDBCDAO dao = new LikeIngredientJDBCDAO();
		List<LikeIngredientVO> list;
		
//		// 測試 isExist & insert（必定同時出現）成功！
//		vo.setAccountID(100001);
//		vo.setLikeIngredientID(220010);
//		boolean exist = dao.isExist(vo);
//		if (exist == false) {
//			if (dao.insert(vo) > 0) {
//				System.out.println("新增成功");
//			}
//		} else {
//			System.out.println("已存在!");
//		}

//		// 測試 get all by account 成功
//		list = dao.getAllByAccount(100002);
//		for (LikeIngredientVO one : list) {
//			System.out.println("使用者" + one.getAccountID() + "喜歡" + one.getLikeIngredientID() + "食材\n========");
//		}
		
		// 測試 get all by ingredient 成功
		list = dao.getAllByIngredient(220011);
		for (LikeIngredientVO one : list) {
			System.out.println("食材" + one.getLikeIngredientID() + "被使用者" + one.getAccountID() + "喜歡\n========");
		}
		
	}
}
