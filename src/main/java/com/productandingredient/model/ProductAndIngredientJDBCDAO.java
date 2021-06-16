package com.productandingredient.model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class ProductAndIngredientJDBCDAO implements ProductAndIngredientDAOinterface {

	private static final String INSERT = "INSERT INTO ProductAndIngredient(product_id, ingredient_id) VALUES(?, ?)";
//	private static final String UPDATE = "UPDATE LikeIngredient SET like_ingredient_id = ? WHERE account_id = ?";
	private static final String DELETE = "DELETE FROM ProductAndIngredient WHERE product_id = ? and ingredient_id = ?";
	private static final String SELECT_ONE_BY_ID = "SELECT * FROM ProductAndIngredient WHERE product_id = ? and ingredient_id = ?";
	private static final String SELECT_ALL_BY_PRODUCT = "SELECT * FROM ProductAndIngredient WHERE product_id = ?";
	private static final String SELECT_ALL_BY_INGREDIENT = "SELECT * FROM ProductAndIngredient WHERE ingredient_id = ?";

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
	
	
	@Override
	public void add(ProductAndIngredientVO productAndIngredientVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT);

			pstmt.setInt(1, productAndIngredientVO.getProduct_id());
			pstmt.setInt(2, productAndIngredientVO.getIngredient_id());

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
	public void update(ProductAndIngredientVO productAndIngredientVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(ProductAndIngredientVO productAndIngredientVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, productAndIngredientVO.getProduct_id());
			pstmt.setInt(2, productAndIngredientVO.getIngredient_id());

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
	public ProductAndIngredientVO findByPK(int product_id) {
		ProductAndIngredientVO emp = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ALL_BY_PRODUCT);
			pstmt.setInt(1, product_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				emp = new ProductAndIngredientVO();
				
				emp.setProduct_id( rs.getInt("product_id") );
				emp.setIngredient_id(rs.getInt("ingredient_id") );
				
//				emp.setAccount_id(rs.getInt("account_id"));
//				emp.setFav_product_id(rs.getInt("fav_product_id"));
//				

				

			}

		} catch (SQLException se) {
			se.printStackTrace();
			// Clean up JDBC resources
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

		return emp;
	}

	@Override
	public List<ProductAndIngredientVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}



	
}
