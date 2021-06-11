package com.recipestep.model;

import java.io.*;
import java.sql.*;
import java.util.*;

public class RecipeStepJDBCDAO implements RecipeStepDAOInterface {

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

	private static final String INSERT = "INSERT INTO RecipeStep(recipe_id, recipe_step_order, recipe_step_text, recipe_step_pic) VALUES(?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE RecipeStep SET recipe_step_text = ?, recipe_step_pic = ? WHERE recipe_step_id = ?";
	private static final String UPDATE_ORDER = "UPDATE RecipeStep SET recipe_step_order = ? WHERE recipe_step_id = ?";
	private static final String DELETE = "DELETE FROM RecipeStep WHERE recipe_step_id = ?";
	private static final String SELECT_ONE_BY_ID = "SELECT * FROM RecipeStep WHERE recipe_step_id = ?";
	// 順序必須由指令直接決定
	private static final String SELECT_ALL_BY_RECIPE = "SELECT * FROM RecipeStep WHERE recipe_id = ? ORDER BY recipe_step_order";

	@Override public RecipeStepVO insertByRecipe(RecipeStepVO recipeStep, Connection con) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] autoGeneratedCol = { "recipe_step_id" };

		try {
			pstmt = con.prepareStatement(INSERT, autoGeneratedCol);

			pstmt.setInt(1, recipeStep.getRecipeID());
			pstmt.setInt(2, recipeStep.getRecipeStepOrder());
			pstmt.setString(3, recipeStep.getRecipeStepText());
			pstmt.setBytes(4, recipeStep.getRecipeStepPic());

			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				recipeStep.setRecipeStepID(rs.getInt(1));
			}

		} catch (SQLException se) {
			if (con != null) {
				try {
					System.err.print("Transaction is being rolled back by RecipeStep.");
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
		return recipeStep;
	}
	
	@Override
	public RecipeStepVO insert(RecipeStepVO recipeStep) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] autoGeneratedCol = { "recipe_step_id" };

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT, autoGeneratedCol);

			pstmt.setInt(1, recipeStep.getRecipeID());
			pstmt.setInt(2, recipeStep.getRecipeStepOrder());
			pstmt.setString(3, recipeStep.getRecipeStepText());
			pstmt.setBytes(4, recipeStep.getRecipeStepPic());

			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				recipeStep.setRecipeStepID(rs.getInt(1));
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
		return recipeStep;
	}

	@Override
	public int update(RecipeStepVO recipeStep) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int updateRow = 0;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, recipeStep.getRecipeStepText());
			pstmt.setBytes(2, recipeStep.getRecipeStepPic());
			pstmt.setInt(3, recipeStep.getRecipeStepID());

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
	public int refreshOrder(int[] recipeStepIDs) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int updateRows = 0;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_ORDER);

			int updateOrderCount = 0;
			for (int recipeStepID : recipeStepIDs) {
				pstmt.setInt(1, ++updateOrderCount);
				pstmt.setInt(2, recipeStepID);
				pstmt.addBatch();
			}
			updateRows = pstmt.executeBatch().length;
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
		return updateRows;
	}

	@Override
	public int exchangeOrder(RecipeStepVO firstRecipeStepVO, RecipeStepVO secondRecipeStepVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int updateRows = 0;
		
		int firstOrder = firstRecipeStepVO.getRecipeStepOrder();
		int secondOrder = secondRecipeStepVO.getRecipeStepOrder();
	
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_ORDER);

			pstmt.setInt(1, secondOrder);
			pstmt.setInt(2, firstRecipeStepVO.getRecipeStepID());
			pstmt.addBatch();
			
			pstmt.setInt(1, firstOrder);
			pstmt.setInt(2, secondRecipeStepVO.getRecipeStepID());
			pstmt.addBatch();
			
			updateRows = pstmt.executeBatch().length;
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
		return updateRows;
	}

	@Override
	public RecipeStepVO getOneByID(int recipeStepID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RecipeStepVO recipeStep = null;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ONE_BY_ID);

			pstmt.setInt(1, recipeStepID);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				recipeStep = new RecipeStepVO();
				recipeStep.setRecipeStepID(rs.getInt("recipe_step_id"));
				recipeStep.setRecipeID(rs.getInt("recipe_id"));
				recipeStep.setRecipeStepOrder(rs.getInt("recipe_step_order"));
				recipeStep.setRecipeStepText(rs.getString("recipe_step_text"));
				recipeStep.setRecipeStepPic(rs.getBytes("recipe_step_pic"));
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
		return recipeStep;
	}

	@Override
	public void delete(int recipeStepID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, recipeStepID);
			pstmt.executeUpdate();

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

	@Override
	public List<RecipeStepVO> getAllByRecipe(int recipeID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RecipeStepVO> allRecipeSteps = new ArrayList<RecipeStepVO>();

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ALL_BY_RECIPE);

			pstmt.setInt(1, recipeID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				RecipeStepVO recipeStep = new RecipeStepVO();
				recipeStep.setRecipeStepID(rs.getInt("recipe_step_id"));
				recipeStep.setRecipeID(rs.getInt("recipe_id"));
				recipeStep.setRecipeStepOrder(rs.getInt("recipe_step_order"));
				recipeStep.setRecipeStepText(rs.getString("recipe_step_text"));
				recipeStep.setRecipeStepPic(rs.getBytes("recipe_step_pic"));
				allRecipeSteps.add(recipeStep);
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
		return allRecipeSteps;
	}

	public static void main(String args[]) {
		RecipeStepVO recipeStep = new RecipeStepVO();
		RecipeStepJDBCDAO dao = new RecipeStepJDBCDAO();

//		// 測試 insert 成功
//		for (int i = 1; i <= 5; i++) {
//			recipeStep = new RecipeStepVO();
//			recipeStep.setRecipeID(200002);
//			recipeStep.setRecipeStepOrder(i);
//			recipeStep.setRecipeStepText("第" + i + "步");
//			InputStream is = null;
//			try {
//				is = new FileInputStream("C:\\Users\\Tibame_T14\\Downloads\\test\\" + i + ".jpg");
//				byte[] picBuffer = new byte[is.available()];
//				is.read(picBuffer);
//				recipeStep.setRecipeStepPic(picBuffer);		
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//				if (is != null) {
//					try {
//						is.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//			recipeStep = dao.insert(recipeStep);
//			System.out.println("成功新增流水號為" + recipeStep.getRecipeStepID() + "之食譜步驟");
//		}

		// 測試 get all 圖片以外(圖片另於 JSP 測)
		List<RecipeStepVO> all = dao.getAllByRecipe(200002);
		for (RecipeStepVO one : all) {
			System.out.println(one.getRecipeStepID());
			if (one.getRecipeStepOrder() == 1) {
				recipeStep = one;
			}
		}
		System.out.println("===============================");

//		// 測試 update 成功
//		recipeStep.setRecipeStepText("第一步");
//		int updateSuccess = dao.update(recipeStep);
//		if (updateSuccess != 0) {
//			System.out.println("成功");
//		}

//		// 測試 get one by ID 成功
//		System.out.println(dao.getOneByID(270006).getRecipeStepText());
		
//		// 測試 exchange
//		RecipeStepVO firstVO = dao.getOneByID(270006);
//		RecipeStepVO secondVO = dao.getOneByID(270007);
//		
//		int updateSuccess = dao.exchangeOrder(firstVO, secondVO);
//		if (updateSuccess != 0) {
//			System.out.println("成功");
//		}
		

	}

}
