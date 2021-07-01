package com.recipecuisinecategory.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeCuisineCategoryJDBCDAO implements RecipeCuisineCategoryDAOInterface {

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

	private static final String INSERT = "INSERT INTO RecipeCuisineCategory(recipe_id, cuisinecategory_id) VALUES(?, ?)";
//	private static final String UPDATE = "UPDATE RecipeCuisineCategory SET cuisinecategory_id = ? WHERE recipe_id = ?";
	private static final String DELETE = "DELETE FROM RecipeCuisineCategory WHERE recipe_id = ? and cuisinecategory_id = ?";
	private static final String SELECT_ONE_BY_ID = "SELECT * FROM RecipeCuisineCategory WHERE recipe_id = ? and cuisinecategory_id = ?";
	private static final String SELECT_ALL_BY_RECIPE = "SELECT * FROM RecipeCuisineCategory WHERE recipe_id = ?";
	private static final String SELECT_ALL_BY_CUISINECATEGORY = "SELECT * FROM RecipeCuisineCategory WHERE cuisinecategory_id = ?";

	@Override
	public int insertByRecipe(RecipeCuisineCategoryVO recipeCuisineCategory, Connection con) {
			PreparedStatement pstmt = null;
			int insertRow = 0;

			try {
				pstmt = con.prepareStatement(INSERT);

				pstmt.setInt(1, recipeCuisineCategory.getRecipeID());
				pstmt.setInt(2, recipeCuisineCategory.getCuisineCategoryID());

				insertRow = pstmt.executeUpdate();

			} catch (SQLException se) {
				if (con != null) {
					try {
						System.err.print("Transaction is being rolled back by RecipeCuisineCategory.");
						// don't forget ---------------------------------------------------
						con.rollback();
					} catch (SQLException excep) {
						throw new RuntimeException("rollback error occured. "
								+ excep.getMessage());
					}
				}
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
			}
			return insertRow;
	}
	
	@Override
	public int insert(RecipeCuisineCategoryVO recipeCuisineCategory) {
//	public int insert(int recipeID, int cuisineCategoryID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int insertRow = 0;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT);

			pstmt.setInt(1, recipeCuisineCategory.getRecipeID());
//			pstmt.setInt(1, recipeID);
			pstmt.setInt(2, recipeCuisineCategory.getCuisineCategoryID());
//			pstmt.setInt(2, cuisineCategoryID);

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
//	public int update(RecipeCuisineCategoryVO recipeCuisineCategory) {
////	public int update(int recipeID, int cuisineCategoryID) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		int updateRow = 0;
//
//		try {
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(UPDATE);
//
//			pstmt.setInt(1, recipeCuisineCategory.getCuisineCategoryID());
////			pstmt.setInt(1, recipeID);
//			pstmt.setInt(2, recipeCuisineCategory.getRecipeID());
////			pstmt.setInt(2, cuisineCategoryID);
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
	public int delete(RecipeCuisineCategoryVO recipeCuisineCategory) {
//	public int delete(int recipeID, int cuisineCategoryID) {

		Connection con = null;
		PreparedStatement pstmt = null;
		int deleteRow = 0;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, recipeCuisineCategory.getRecipeID());
//			pstmt.setInt(1, recipeID);
			pstmt.setInt(2, recipeCuisineCategory.getCuisineCategoryID());
//			pstmt.setInt(2, cuisineCategoryID);

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
	public boolean isExist(RecipeCuisineCategoryVO recipeCuisineCategory) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean existStatus = false;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ONE_BY_ID);

			pstmt.setInt(1, recipeCuisineCategory.getRecipeID());
			pstmt.setInt(2, recipeCuisineCategory.getCuisineCategoryID());

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
	public List<RecipeCuisineCategoryVO> getAllByRecipe(int recipeID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RecipeCuisineCategoryVO> allRecipeCuisineCategory = new ArrayList<RecipeCuisineCategoryVO>();
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ALL_BY_RECIPE);

			pstmt.setInt(1, recipeID);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				RecipeCuisineCategoryVO recipeCuisineCategory = new RecipeCuisineCategoryVO();
				recipeCuisineCategory.setRecipeID(rs.getInt("recipe_id"));
				recipeCuisineCategory.setCuisineCategoryID(rs.getInt("cuisinecategory_id"));
				allRecipeCuisineCategory.add(recipeCuisineCategory);
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
		return allRecipeCuisineCategory;
	}

	@Override
	public List<RecipeCuisineCategoryVO> getAllByCuisineCategory(int cuisineCategoryID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RecipeCuisineCategoryVO> allRecipeCuisineCategory = new ArrayList<RecipeCuisineCategoryVO>();
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ALL_BY_CUISINECATEGORY);

			pstmt.setInt(1, cuisineCategoryID);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				RecipeCuisineCategoryVO recipeCuisineCategory = new RecipeCuisineCategoryVO();
				recipeCuisineCategory.setRecipeID(rs.getInt("recipe_id"));
				recipeCuisineCategory.setCuisineCategoryID(rs.getInt("cuisinecategory_id"));
				allRecipeCuisineCategory.add(recipeCuisineCategory);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("資料庫發生錯誤" + e.getMessage());
		} finally {
		
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					throw new RuntimeException("PrepareStatement關閉錯誤" + e.getMessage());
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					throw new RuntimeException("資料庫連線關閉錯誤" + e.getMessage());
				}
			}
		}
		return allRecipeCuisineCategory;
	}

	public static void main(String args[]) {
		RecipeCuisineCategoryVO vo = new RecipeCuisineCategoryVO();
		RecipeCuisineCategoryJDBCDAO dao = new RecipeCuisineCategoryJDBCDAO();
		List<RecipeCuisineCategoryVO> all = new ArrayList<RecipeCuisineCategoryVO>();
		
//		// 測試 insert 成功
//		vo.setRecipeID(200002);
//		vo.setCuisineCategoryID(250003);
//		if (dao.insert(vo) > 0) {
//			System.out.println("新增成功!");
//		}

//		// 測試 update 成功
//		vo.setRecipeID(200001);
//		vo.setCuisineCategoryID(250002);
//		if (dao.update(vo) > 0) {
//			System.out.println("更新成功!");
//		}
	
//		// 測試 delete 成功
//		vo.setRecipeID(200001);
//		vo.setCuisineCategoryID(250002);
//		if (dao.delete(vo) > 0) {
//			System.out.println("刪除成功!");
//		}

		// 測試 get all by recipe 成功
		all = dao.getAllByRecipe(200007);
		for (RecipeCuisineCategoryVO one : all) {
			System.out.println("食譜編號：" + one.getRecipeID() + "\n料理分類編號：" + one.getCuisineCategoryID() + "\n==================");
		}
//		
//		// 測試 get all by cuisine category 成功
//		all = dao.getAllByCuisineCategory(250001);
//		for (RecipeCuisineCategoryVO one : all) {
//			System.out.println("料理分類編號：" + one.getCuisineCategoryID() + "\n食譜編號：" + one.getRecipeID() + "\n==================");
//		}
		
//		// 測試 isExist 成功
//		vo.setRecipeID(200001);
//		vo.setCuisineCategoryID(250001);
//		System.out.println(dao.isExist(vo));
		
		
	}
	@Override
	public int deleteByRecipe(RecipeCuisineCategoryVO recipeCuisineCategory, Connection con) {
		PreparedStatement pstmt = null;
		int deleteRow = 0;

		try {
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, recipeCuisineCategory.getRecipeID());
			pstmt.setInt(2, recipeCuisineCategory.getCuisineCategoryID());

			deleteRow = pstmt.executeUpdate();

		} catch (SQLException se) {
			if (con != null) {
				try {
					System.err.print("Transaction is being rolled back by RecipeCuisineCategory.");
					// don't forget ---------------------------------------------------
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
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
		}
		return deleteRow;
	}

}
