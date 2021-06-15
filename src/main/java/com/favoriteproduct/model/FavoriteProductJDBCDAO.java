package com.favoriteproduct.model;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class FavoriteProductJDBCDAO implements FavoriteProductDAOinterface {

	private static final String INSERT = "INSERT INTO FavoriteProduct(account_id, fav_product_id) VALUES(?, ?)";
//	private static final String UPDATE = "UPDATE LikeIngredient SET like_ingredient_id = ? WHERE account_id = ?";
	private static final String DELETE = "DELETE FROM FavoriteProduct WHERE account_id = ? and fav_product_id = ?";
	private static final String SELECT_ONE_BY_ID = "SELECT * FROM FavoriteProduct WHERE account_id = ? and fav_product_id = ?";
	private static final String SELECT_ALL_BY_ACCOUNT = "SELECT * FROM FavoriteProduct WHERE account_id = ?";
	private static final String SELECT_ALL_BY_PRODUCT = "SELECT * FROM FavoriteProduct WHERE fav_product_id = ?";


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
	public void add(FavoriteProductVO favoriteProductVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT);

			pstmt.setInt(1, favoriteProductVO.getAccount_id());
			pstmt.setInt(2, favoriteProductVO.getFav_product_id());

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
	public void update(FavoriteProductVO favoriteProductVO) {
		// TODO Auto-generated method stub
		
	}


		
	
	@Override
	public FavoriteProductVO findByPK(int account_id) {
		FavoriteProductVO emp = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ALL_BY_ACCOUNT);
			pstmt.setInt(1, account_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				emp = new FavoriteProductVO();
				
				emp.setAccount_id(rs.getInt("account_id"));
				emp.setFav_product_id(rs.getInt("fav_product_id"));
				
//				emp.setEmpno(rs.getInt("EMPNO"));
//				emp.setEname(rs.getString("ENAME"));
//				emp.setJob(rs.getString("JOB"));
//				emp.setHiredate(rs.getDate("HIREDATE"));
//				emp.setSal(rs.getDouble("SAL"));
//				emp.setComm(rs.getDouble("COMM"));
//				emp.setDeptno(rs.getInt("DEPTNO"));
				

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
	public List<FavoriteProductVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(FavoriteProductVO favoriteProductVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, favoriteProductVO.getAccount_id());
			pstmt.setInt(2, favoriteProductVO.getFav_product_id());

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

	


}
