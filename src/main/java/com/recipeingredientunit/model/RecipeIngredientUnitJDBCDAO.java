package com.recipeingredientunit.model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeIngredientUnitJDBCDAO implements RecipeIngredientUnitDAOInterface {

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

	private static final String INSERT = "INSERT INTO RecipeIngredientUnit(recipe_id, ingredient_id, unit_id, unit_amount) VALUES(?, ?, ?, ?)";
//	private static final String UPDATE = "UPDATE RecipeIngredientUnit SET recipe_id = ?, ingredient_id = ?, unit_id = ?, unit_amount = ? WHERE recipeIngredientUnit_id = ?";
	private static final String UPDATE = "UPDATE RecipeIngredientUnit SET unit_id = ?, unit_amount = ? WHERE recipe_id = ? and ingredient_id = ?";
	private static final String DELETE = "DELETE FROM RecipeIngredientUnit WHERE recipeIngredientUnit_id = ?";
	private static final String DELETE_BY_FK = "DELETE FROM RecipeIngredientUnit WHERE recipe_id = ? and ingredient_id = ?";
	private static final String SELECT_ONE_BY_ID = "SELECT * FROM RecipeIngredientUnit WHERE recipeIngredientUnit_id = ?";
	private static final String SELECT_ALL_BY_RECIPE = "SELECT * FROM RecipeIngredientUnit WHERE recipe_id = ?";
	private static final String SELECT_ALL_BY_INGREDIENT = "SELECT * FROM RecipeIngredientUnit WHERE ingredient_id = ?";

	@Override
	public int insertByRecipe(RecipeIngredientUnitVO recipeIngredientUnit, Connection con) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] autoGeneratedCol = { "recipeIngredientUnit_id" };
		String autoGeneratedKey = null;

		try {
			pstmt = con.prepareStatement(INSERT, autoGeneratedCol);

			pstmt.setInt(1, recipeIngredientUnit.getRecipeID());
			pstmt.setInt(2, recipeIngredientUnit.getIngredientID());
			pstmt.setInt(3, recipeIngredientUnit.getUnitID());
			pstmt.setBigDecimal(4, recipeIngredientUnit.getUnitAmount()); // 在外面包裝之前要先處理成BigDecimal

			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				autoGeneratedKey = rs.getString(1);
			}

		} catch (SQLException se) {
			if (con != null) {
				try {
					System.err.print("Transaction is being rolled back by RecipeIngredientUnit.");
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
		return new Integer(autoGeneratedKey);
	}

	@Override
	public int insert(RecipeIngredientUnitVO recipeIngredientUnit) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] autoGeneratedCol = { "recipeIngredientUnit_id" };
		int autoGeneratedKey = 0;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT, autoGeneratedCol);

			pstmt.setInt(1, recipeIngredientUnit.getRecipeID());
			pstmt.setInt(2, recipeIngredientUnit.getIngredientID());
			pstmt.setInt(3, recipeIngredientUnit.getUnitID());
			pstmt.setBigDecimal(4, recipeIngredientUnit.getUnitAmount()); // 在外面包裝之前要先處理成BigDecimal

			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				autoGeneratedKey = rs.getInt(1);
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
		return autoGeneratedKey;
	}

	@Override
	public int update(RecipeIngredientUnitVO recipeIngredientUnit) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int updateRow = 0;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, recipeIngredientUnit.getUnitID());
			pstmt.setBigDecimal(2, recipeIngredientUnit.getUnitAmount()); // 在外面包裝之前要先處理成BigDecimal
			pstmt.setInt(3,  recipeIngredientUnit.getRecipeID());
			pstmt.setInt(4, recipeIngredientUnit.getIngredientID());
//			pstmt.setInt(5, recipeIngredientUnit.getRecipeIngredientUnitID());
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
	public int delete(int recipeIngredientUnitID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int deleteRow = 0;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, recipeIngredientUnitID);

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
	public List<RecipeIngredientUnitVO> getAllByRecipe(int recipeID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RecipeIngredientUnitVO> allRecipeIngredientUnit = new ArrayList<RecipeIngredientUnitVO>();

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ALL_BY_RECIPE);

			pstmt.setInt(1, recipeID);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				RecipeIngredientUnitVO recipeIngredientUnit = new RecipeIngredientUnitVO();
				recipeIngredientUnit.setRecipeIngredientUnitID(rs.getInt("recipeIngredientUnit_id"));
				recipeIngredientUnit.setRecipeID(rs.getInt("recipe_id"));
				recipeIngredientUnit.setIngredientID(rs.getInt("ingredient_id"));
				recipeIngredientUnit.setUnitID(rs.getInt("unit_id"));
				recipeIngredientUnit.setUnitAmount(rs.getBigDecimal("unit_amount"));
				allRecipeIngredientUnit.add(recipeIngredientUnit);
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
		return allRecipeIngredientUnit;
	}

	@Override
	public RecipeIngredientUnitVO getOneByID(int recipeIngredientUnitID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RecipeIngredientUnitVO recipeIngredientUnit = null;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ONE_BY_ID);

			pstmt.setInt(1, recipeIngredientUnitID);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				recipeIngredientUnit = new RecipeIngredientUnitVO();
				recipeIngredientUnit.setRecipeIngredientUnitID(rs.getInt("recipeIngredientUnit_id"));
				recipeIngredientUnit.setRecipeID(rs.getInt("recipe_id"));
				recipeIngredientUnit.setIngredientID(rs.getInt("ingredient_id"));
				recipeIngredientUnit.setUnitID(rs.getInt("unit_id"));
				recipeIngredientUnit.setUnitAmount(rs.getBigDecimal("unit_amount"));
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
		return recipeIngredientUnit;
	}
	
	// 測試用
	public static void main(String args[]) {
		RecipeIngredientUnitVO vo = new RecipeIngredientUnitVO();
		RecipeIngredientUnitJDBCDAO dao = new RecipeIngredientUnitJDBCDAO();
		List<RecipeIngredientUnitVO> list;
		
		// 好難測，完全不知道自己在塞什麼XD
		
//		// 測試 insert 成功
//		vo.setRecipeID(200001);
//		vo.setIngredientID(220011);
//		vo.setUnitID(230001);
//		vo.setUnitAmount(BigDecimal.valueOf(600));
//		int id = dao.insert(vo);
//		if (id != 0) {
//			System.out.println(id);
//		}
		
//		// 測試 get one 成功
//		vo = dao.getOneByID(260002);
//		System.out.println(vo.getUnitAmount());
//		
//		// 測試 update 成功
//		vo.setUnitAmount(BigDecimal.valueOf(300));
//		if (dao.update(vo) > 0) {
//			System.out.println("更新成功!");
//		}
		
//		// 測試 get all by recipe 成功
//		list = dao.getAllByRecipe(200001);
//		for (RecipeIngredientUnitVO one : list) {
//			System.out.println("編號：" + one.getRecipeIngredientUnitID() + "\n單位數量：" + one.getUnitAmount() + "\n============================");
//		}		
		
	}

	@Override
	public List<RecipeIngredientUnitVO> getAllByIngredient(int ingredientID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RecipeIngredientUnitVO> allRecipeIngredientUnit = new ArrayList<RecipeIngredientUnitVO>();

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ALL_BY_INGREDIENT);

			pstmt.setInt(1, ingredientID);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				RecipeIngredientUnitVO recipeIngredientUnit = new RecipeIngredientUnitVO();
				recipeIngredientUnit.setRecipeIngredientUnitID(rs.getInt("recipeIngredientUnit_id"));
				recipeIngredientUnit.setRecipeID(rs.getInt("recipe_id"));
				recipeIngredientUnit.setIngredientID(rs.getInt("ingredient_id"));
				recipeIngredientUnit.setUnitID(rs.getInt("unit_id"));
				recipeIngredientUnit.setUnitAmount(rs.getBigDecimal("unit_amount"));
				allRecipeIngredientUnit.add(recipeIngredientUnit);
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
		return allRecipeIngredientUnit;
	}

	@Override
	public int delete(RecipeIngredientUnitVO recipeIngredientUnit) {

		Connection con = null;
		PreparedStatement pstmt = null;
		int deleteRow = 0;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_BY_FK);

			pstmt.setInt(1, recipeIngredientUnit.getRecipeID());
			pstmt.setInt(2, recipeIngredientUnit.getIngredientID());

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
	public int deleteByRecipe(RecipeIngredientUnitVO recipeIngredientUnit, Connection con) {
		PreparedStatement pstmt = null;
		int deleteRow = 0;

		try {
			pstmt = con.prepareStatement(DELETE_BY_FK);

			pstmt.setInt(1, recipeIngredientUnit.getRecipeID());
			pstmt.setInt(2, recipeIngredientUnit.getIngredientID());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			if (con != null) {
				try {
					System.err.print("Transaction is being rolled back by RecipeIngredientUnit.");
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
