package com.recipe.model;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.recipecuisinecategory.model.RecipeCuisineCategoryJDBCDAO;
import com.recipecuisinecategory.model.RecipeCuisineCategoryVO;
import com.recipeingredientunit.model.RecipeIngredientUnitJDBCDAO;
import com.recipeingredientunit.model.RecipeIngredientUnitVO;
import com.recipestep.model.RecipeStepJDBCDAO;
import com.recipestep.model.RecipeStepVO;


public class RecipeJDBCDAO implements RecipeDAOInterface {
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

	private static final String INSERT = "INSERT INTO Recipe(recipe_name, recipe_introduction, recipe_pic_top, recipe_serve, recipe_time, account_id)"
			+ "VALUES(?, ?, ?, ?, NOW(), ?)";
	// 排序看是要寫死還是程式判斷
	private static final String SELECT_ONE_BY_PK = "SELECT * FROM Recipe WHERE recipe_id = ?";
	private static final String SELECT_ONE_BY_WRITER = "SELECT * FROM Recipe WHERE account_id = ?";
	private static final String UPDATE = "UPDATE Recipe SET recipe_name = ?, recipe_introduction = ?, recipe_pic_top = ?, recipe_serve = ? WHERE recipe_id = ?";
	private static final String UPDATE_VIEW_COUNT = "UPDATE Recipe SET recipe_view_count = ? WHERE recipe_id = ?";
	private static final String DELETE = "DELETE FROM Recipe WHERE recipe_id = ?";
	private static final String SELECT_ALL = "SELECT * FROM Recipe ";
	
	private static final String NEWEST = " ORDER BY recipe_time DESC ";
//	private static final String HOTTEST_YESTERDAY = " WHERE YEARWEEK(date_format(recipe_time,'%Y-%m-%d')) = YEARWEEK(now())-1 ORDER BY recipe_view_count DESC ";
//	private static final String BEST_LAST_WEEK = " WHERE TO_DAYS(NOW()) - TO_DAYS(recipe_time) <= 1 ORDER BY SUM(recipe_view_count + recipe_like_count + recipe_collect_count) DESC ";
	private static final String LIMIT = " Limit 10 ";
	
	@Override
	public int insertWithDetails(RecipeVO recipe, List<RecipeCuisineCategoryVO> catList,
												  List<RecipeIngredientUnitVO> ingUnitList,
												  List<RecipeStepVO> stepList) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] autoGeneratedCol = { "recipe_id" };
		String autoGeneratedKey = null;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			
    		// before ---------------------------------------------------
    		con.setAutoCommit(false);
    		
    		// step 1 ---------------------------------------------------
			pstmt = con.prepareStatement(INSERT , autoGeneratedCol);	
			pstmt.setString(1, recipe.getRecipeName());
			pstmt.setString(2, recipe.getRecipeIntroduction());
			pstmt.setBytes(3, recipe.getRecipePicTop());
			pstmt.setInt(4, recipe.getRecipeServe());
			pstmt.setInt(5, recipe.getAccountID());

			pstmt.executeUpdate();

			// step 2 ---------------------------------------------------
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				autoGeneratedKey = rs.getString(1); // PK
			}
			rs.close();
			
				// (1) RecipeCuisineCategoryVO
			RecipeCuisineCategoryJDBCDAO catDao = new RecipeCuisineCategoryJDBCDAO();
			for (RecipeCuisineCategoryVO cat : catList) {
				cat.setRecipeID(new Integer(autoGeneratedKey));
				catDao.insertByRecipe(cat, con);
			}
			
				// (2) RecipeIngredientUnitVO
			RecipeIngredientUnitJDBCDAO ingUnitDao = new RecipeIngredientUnitJDBCDAO();
			for (RecipeIngredientUnitVO ingUnit : ingUnitList) {
				ingUnit.setRecipeID(new Integer(autoGeneratedKey));
				ingUnitDao.insertByRecipe(ingUnit, con);
			}

				// (3) RecipeStepVO
			RecipeStepJDBCDAO stepDao = new RecipeStepJDBCDAO();
			for (RecipeStepVO step : stepList) {
				step.setRecipeID(new Integer(autoGeneratedKey));
				stepDao.insertByRecipe(step, con);
			}
			
			// after ---------------------------------------------------
			con.commit();
			con.setAutoCommit(true);
			
		} catch (SQLException se) {		// Handle any SQL errors
			if (con != null) {
				try {
					System.err.print("Transaction is being rolled back by Recipe.");
					// don't forget ---------------------------------------------------
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {		// Clean up JDBC resources
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
		return new Integer(autoGeneratedKey);
	}

	@Override
	public int insert(RecipeVO recipe) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String[] autoGeneratedCol = { "recipe_id" };
		ResultSet rs = null;
		int autoGeneratedKey = 0;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT, autoGeneratedCol);

			pstmt.setString(1, recipe.getRecipeName());
			pstmt.setString(2, recipe.getRecipeIntroduction());
			pstmt.setBytes(3, recipe.getRecipePicTop());
			pstmt.setInt(4, recipe.getRecipeServe());
			pstmt.setInt(5, recipe.getAccountID());

			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				autoGeneratedKey = rs.getInt(1); // PK
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
		return autoGeneratedKey;
	}
	
	@Override
	public RecipeVO getOneByPK(int recipeID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RecipeVO recipe = null;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ONE_BY_PK);

			pstmt.setInt(1, recipeID);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				recipe = new RecipeVO();
				recipe.setRecipeID(rs.getInt("recipe_id"));
				recipe.setRecipeName(rs.getString("recipe_name"));
				recipe.setRecipeIntroduction(rs.getString("recipe_introduction"));
				recipe.setRecipePicTop(rs.getBytes("recipe_pic_top"));
				recipe.setRecipeServe(rs.getInt("recipe_serve"));
				recipe.setRecipeTime(rs.getTimestamp("recipe_time"));
				recipe.setRecipeViewCount(rs.getInt("recipe_view_count"));
//				recipe.setRecipeLikeCount(rs.getInt("recipe_like_count"));
//				recipe.setRecipeCollectCount(rs.getInt("recipe_collect_count"));
				recipe.setAccountID(rs.getInt("account_id"));
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

		return recipe;
	}

	@Override
	public int delete(int recipeID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int deletedRow = 0;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, recipeID);
			deletedRow = pstmt.executeUpdate();

		} catch (SQLException se) {
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
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}

		}
		return deletedRow;
	}

	@Override
	public int delete(int[] recipeIDs) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int deletedRows = 0;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			for (int recipeID : recipeIDs) {
				pstmt.setInt(1, recipeID);
				deletedRows += pstmt.executeUpdate();
			}
		} catch (SQLException se) {
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
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}

		}
		return deletedRows;
	}

	@Override
	public void update(RecipeVO recipe) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, recipe.getRecipeName());
			pstmt.setString(2, recipe.getRecipeIntroduction());
			pstmt.setBytes(3, recipe.getRecipePicTop());
			pstmt.setInt(4, recipe.getRecipeServe());
			pstmt.setInt(5, recipe.getRecipeID());

			pstmt.executeUpdate();

		} catch (SQLException se) {
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
	public List<RecipeVO> getAllByWriter(int accountID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RecipeVO> recipes = new ArrayList<RecipeVO>();

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ONE_BY_WRITER);

			pstmt.setInt(1, accountID);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				RecipeVO recipe = new RecipeVO();
				recipe.setRecipeID(rs.getInt("recipe_id"));
				recipe.setRecipeName(rs.getString("recipe_name"));
				recipe.setRecipeIntroduction(rs.getString("recipe_introduction"));
				recipe.setRecipePicTop(rs.getBytes("recipe_pic_top"));
				recipe.setRecipeServe(rs.getInt("recipe_serve"));
				recipe.setRecipeTime(rs.getTimestamp("recipe_time"));
				recipe.setRecipeViewCount(rs.getInt("recipe_view_count"));
//				recipe.setRecipeLikeCount(rs.getInt("recipe_like_count"));
//				recipe.setRecipeCollectCount(rs.getInt("recipe_collect_count"));
				recipe.setAccountID(rs.getInt("account_id"));
				recipes.add(recipe);
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
		return recipes;
	}
	

	@Override
	public List<RecipeVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RecipeVO> allRecipe = new ArrayList<RecipeVO>();

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ALL + NEWEST);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				RecipeVO recipe = new RecipeVO();
				recipe.setRecipeID(rs.getInt("recipe_id"));
				recipe.setRecipeName(rs.getString("recipe_name"));
				recipe.setRecipeIntroduction(rs.getString("recipe_introduction"));
				recipe.setRecipePicTop(rs.getBytes("recipe_pic_top"));
				recipe.setRecipeServe(rs.getInt("recipe_serve"));
				recipe.setRecipeTime(rs.getTimestamp("recipe_time"));
				recipe.setRecipeViewCount(rs.getInt("recipe_view_count"));
//				recipe.setRecipeLikeCount(rs.getInt("recipe_like_count"));
//				recipe.setRecipeCollectCount(rs.getInt("recipe_collect_count"));
				recipe.setAccountID(rs.getInt("account_id"));
				allRecipe.add(recipe);
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
		return allRecipe;
	}
	

	@Override
	public List<RecipeVO> getSomeNew() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RecipeVO> allRecipe = new ArrayList<RecipeVO>();

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ALL + NEWEST + LIMIT);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				RecipeVO recipe = new RecipeVO();
				recipe.setRecipeID(rs.getInt("recipe_id"));
				recipe.setRecipeName(rs.getString("recipe_name"));
				recipe.setRecipeIntroduction(rs.getString("recipe_introduction"));
				recipe.setRecipePicTop(rs.getBytes("recipe_pic_top"));
				recipe.setRecipeServe(rs.getInt("recipe_serve"));
				recipe.setRecipeTime(rs.getTimestamp("recipe_time"));
				recipe.setRecipeViewCount(rs.getInt("recipe_view_count"));
//				recipe.setRecipeLikeCount(rs.getInt("recipe_like_count"));
//				recipe.setRecipeCollectCount(rs.getInt("recipe_collect_count"));
				recipe.setAccountID(rs.getInt("account_id"));
				allRecipe.add(recipe);
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
		return allRecipe;
	}


//	@Override
//	public List<RecipeVO> getSomeHot() {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		List<RecipeVO> allRecipe = new ArrayList<RecipeVO>();
//
//		try {
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(SELECT_ALL + HOTTEST_YESTERDAY + LIMIT);
//
//			rs = pstmt.executeQuery();
//			while (rs.next()) {
//				RecipeVO recipe = new RecipeVO();
//				recipe.setRecipeID(rs.getInt("recipe_id"));
//				recipe.setRecipeName(rs.getString("recipe_name"));
//				recipe.setRecipeIntroduction(rs.getString("recipe_introduction"));
//				recipe.setRecipePicTop(rs.getBytes("recipe_pic_top"));
//				recipe.setRecipeServe(rs.getInt("recipe_serve"));
//				recipe.setRecipeTime(rs.getTimestamp("recipe_time"));
//				recipe.setRecipeViewCount(rs.getInt("recipe_view_count"));
//				recipe.setRecipeLikeCount(rs.getInt("recipe_like_count"));
//				recipe.setRecipeCollectCount(rs.getInt("recipe_collect_count"));
//				recipe.setAccountID(rs.getInt("account_id"));
//				allRecipe.add(recipe);
//			}
//
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//		} finally {
//
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
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
//		return allRecipe;
//	}

//	@Override
//	public List<RecipeVO> getSomeBest() {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		List<RecipeVO> allRecipe = new ArrayList<RecipeVO>();
//
//		try {
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(BEST_LAST_WEEK + LIMIT);
//
//			rs = pstmt.executeQuery();
//			while (rs.next()) {
//				RecipeVO recipe = new RecipeVO();
//				recipe.setRecipeID(rs.getInt("recipe_id"));
//				recipe.setRecipeName(rs.getString("recipe_name"));
//				recipe.setRecipeIntroduction(rs.getString("recipe_introduction"));
//				recipe.setRecipePicTop(rs.getBytes("recipe_pic_top"));
//				recipe.setRecipeServe(rs.getInt("recipe_serve"));
//				recipe.setRecipeTime(rs.getTimestamp("recipe_time"));
//				recipe.setRecipeViewCount(rs.getInt("recipe_view_count"));
//				recipe.setRecipeLikeCount(rs.getInt("recipe_like_count"));
//				recipe.setRecipeCollectCount(rs.getInt("recipe_collect_count"));
//				recipe.setAccountID(rs.getInt("account_id"));
//				allRecipe.add(recipe);
//			}
//
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//		} finally {
//
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
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
//		return allRecipe;
//	}

	
	// 測試
	public static void main(String args[]) {

		RecipeJDBCDAO dao = new RecipeJDBCDAO();
		RecipeVO recipe = new RecipeVO();

//		// 測試 insert 成功
//		recipe.setRecipeName("recipe15");
//		recipe.setRecipeIntroduction("豪吃的竹筍炒肉絲喔");
//		InputStream is = null;
//		try {
//			is = new FileInputStream("C:\\Users\\Tibame_T14\\Downloads\\temp\\messageImage_1618814342648.jpg");
//			byte[] picBuffer = new byte[is.available()];
//			is.read(picBuffer);
//			recipe.setRecipePicTop(picBuffer);			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (is != null) {
//				try {
//					is.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		recipe.setRecipeServe(3);
//		recipe.setAccountID(100002);
//		int newID = dao.insert(recipe);

//		// 測試 get on by pk 成功，圖片測在 TestPicTop.java
//		recipe = dao.getOneByPK(200004);	// 套 newID
//		System.out.println(recipe.getRecipeID() + "\n" + recipe.getRecipeName() + "\n===============");
//		
//		// 測試 update 成功
//		recipe.setRecipeName("普羅旺斯烘蛋");
//		recipe.setRecipeIntroduction("番茄炒蛋就番茄炒蛋");
//		dao.update(recipe);

//		// 測試 delete one 成功
//		int delSuccess = dao.delete(200004);
//		if (delSuccess != 0) {
//			System.out.println("刪除" + delSuccess + "筆資料成功!");
//		}

//		// 測試 delete many 成功
//		int[] delIDs = { 200005, 200006 };
//		int delSuccess = dao.delete(delIDs);
//		if (delSuccess != 0) {
//			System.out.println("刪除" + delSuccess + "筆資料成功!");
//		}

//		// 測試 get all by writer
//		List<RecipeVO> recipes = dao.getAllManyByWriter(100002);
//		for (RecipeVO rec : recipes) {
//			System.out.println(rec.getRecipeID() + "\n" + rec.getRecipeName() + "\n===============");
//		}

		
		// 測試 get all 除圖片外成功
		List<RecipeVO> allRecipe = dao.getAll();
		for (RecipeVO rec : allRecipe) {
			System.out.println(rec.getRecipeID() + "\n" + rec.getRecipeName() + "\n===============");
		}
		
//		// 測試 getSomeNew 除圖片外成功
//		List<RecipeVO> allRecipe = dao.getSomeNew();
//		for (RecipeVO rec : allRecipe) {
//			System.out.println(rec.getRecipeID() + "\n" + rec.getRecipeName() + "\n===============");
//		}
		
		// Hot、Best 狀況還很多，待測
		
	}

//	@Override
//	public InputStream getOnePicByPK(int recipeID) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		InputStream topPicIn = null;
//
//		try {
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(SELECT_ONE_BY_PK);
//
//			pstmt.setInt(1, recipeID);
//			rs = pstmt.executeQuery();
//
//			if (rs.next()) {
//				topPicIn = rs.getBinaryStream("recipe_pic_top");
//			}
//
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//		} finally {
//
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
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
//		}
//
//		return topPicIn;
//	}

	@Override
	public void updateWithDetails(RecipeVO recipe,
									List<RecipeCuisineCategoryVO> delCatList, List<RecipeCuisineCategoryVO> addCatList, 
									List<RecipeIngredientUnitVO> delIngUnitList, List<RecipeIngredientUnitVO> addIngUnitList, 
									List<RecipeStepVO> delStepList, List<RecipeStepVO> addStepList) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			
    		// before ---------------------------------------------------
    		con.setAutoCommit(false);
    		
    		// step 1 ---------------------------------------------------
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, recipe.getRecipeName());
			pstmt.setString(2, recipe.getRecipeIntroduction());
			pstmt.setBytes(3, recipe.getRecipePicTop());
			pstmt.setInt(4, recipe.getRecipeServe());
			pstmt.setInt(5, recipe.getRecipeID());

			pstmt.executeUpdate();

			// step 2 ---------------------------------------------------
			
				// (1) RecipeCuisineCategoryVO delete
			RecipeCuisineCategoryJDBCDAO catDao = new RecipeCuisineCategoryJDBCDAO();
			for (RecipeCuisineCategoryVO cat : delCatList) {
				catDao.deleteByRecipe(cat, con);
			}
			
				// (2) RecipeCuisineCategoryVO insert
			for (RecipeCuisineCategoryVO cat : addCatList) {
				catDao.insertByRecipe(cat, con);
			}

				// (3) RecipeIngredientUnitVO delete
			RecipeIngredientUnitJDBCDAO ingUnitDao = new RecipeIngredientUnitJDBCDAO();
			for (RecipeIngredientUnitVO ingUnit : delIngUnitList) {
				ingUnitDao.deleteByRecipe(ingUnit, con);
			}
				// (4) RecipeIngredientUnitVO insert
			for (RecipeIngredientUnitVO ingUnit : addIngUnitList) {
				ingUnitDao.insertByRecipe(ingUnit, con);
			}

				// (5) RecipeStepVO delete
			RecipeStepJDBCDAO stepDao = new RecipeStepJDBCDAO();
			for (RecipeStepVO step : delStepList) {
				stepDao.deleteByRecipe(step, con);
			}
			
				// (6) RecipeStepVO insert
			for (RecipeStepVO step : addStepList) {
				stepDao.insertByRecipe(step, con);
			}
			
			// after ---------------------------------------------------
			con.commit();
			con.setAutoCommit(true);
			
		} catch (SQLException se) {		// Handle any SQL errors
			if (con != null) {
				try {
					System.err.print("Transaction is being rolled back by Recipe.");
					// don't forget ---------------------------------------------------
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {		// Clean up JDBC resources
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
	public void updateViewCount(RecipeVO recipe) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_VIEW_COUNT);

			pstmt.setInt(1, recipe.getRecipeViewCount());
			pstmt.setInt(2, recipe.getRecipeID());

			pstmt.executeUpdate();

		} catch (SQLException se) {
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
	public List<RecipeVO> getAll(Map<String, String[]> map) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RecipeVO> allRecipe = new ArrayList<RecipeVO>();

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			String finalSQL = SELECT_ALL
			          + getWhereConditions(map)
			          + " order by recipe_time";
			
			System.out.println(finalSQL);
			
			pstmt = con.prepareStatement(finalSQL);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				RecipeVO recipe = new RecipeVO();
				recipe.setRecipeID(rs.getInt("recipe_id"));
				recipe.setRecipeName(rs.getString("recipe_name"));
				recipe.setRecipeIntroduction(rs.getString("recipe_introduction"));
				recipe.setRecipePicTop(rs.getBytes("recipe_pic_top"));
				recipe.setRecipeServe(rs.getInt("recipe_serve"));
				recipe.setRecipeTime(rs.getTimestamp("recipe_time"));
				recipe.setRecipeViewCount(rs.getInt("recipe_view_count"));
//				recipe.setRecipeLikeCount(rs.getInt("recipe_like_count"));
//				recipe.setRecipeCollectCount(rs.getInt("recipe_collect_count"));
				recipe.setAccountID(rs.getInt("account_id"));
				allRecipe.add(recipe);
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
		return allRecipe;
		
	}
		

	public String getWhereConditions(Map<String, String[]> map) {
		Set<String> keys = map.keySet();
		StringBuffer whereCondition = new StringBuffer();
		StringBuffer whereCondition2 = new StringBuffer();
		
		for (String key : keys) {
			String[] value = map.get(key);
			
			if (value.length != 0 && !"action".equals(key)) {
		
				for (int i = 0; i < value.length; i++) {
					
					if (value.length > 1) {
						if (value[i] != null && value[i].trim().length() != 0) {
							if (i == 0) {
								whereCondition.append(" where recipe_name in " 
								+ "(SELECT recipe_name FROM Recipe WHERE recipe_name like '%"
								+ value[i] + "%'");
							} else if (i == value.length - 1) {
								whereCondition.append(" or '%" + value[i] + "%') ");
							} else {
								whereCondition.append(" or '%" + value[i] + "%'");
							}
						}
					} else {
						whereCondition.append(" where recipe_name in " 
								+ "(SELECT recipe_name FROM Recipe WHERE recipe_name like '%"
								+ value[i] + "%') ");
					}
				}
				
				

				for (int i = 0; i < value.length; i++) {
					
					if (value.length > 1) {
						if (value[i] != null && value[i].trim().length() != 0) {
							if (i == 0) {
								whereCondition2.append(" where recipe_introduction in " 
								+ "(SELECT recipe_introduction FROM Recipe WHERE recipe_introduction like '%"
								+ value[i] + "%'");
							} else if (i == value.length - 1) {
								whereCondition2.append(" or '%" + value[i] + "%') ");
							} else {
								whereCondition2.append(" or '%" + value[i] + "%'");
							}
						}
					} else {
						whereCondition2.append(" where recipe_introduction in " 
								+ "(SELECT recipe_introduction FROM Recipe WHERE recipe_introduction like '%"
								+ value[i] + "%') ");
					}
				}
				
				
				
				
				
			}
		}
		return whereCondition.toString()
				+ " UNION SELECT * FROM Recipe "
				+ whereCondition2.toString();
	}
	
	
	
	
	
}