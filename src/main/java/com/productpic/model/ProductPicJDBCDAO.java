package com.productpic.model;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;



public class ProductPicJDBCDAO implements ProductPicDAOinterface{

//	CREATE TABLE `ProductPic` (
//			  `pic_id` int NOT NULL AUTO_INCREMENT COMMENT '�ӫ~�Ϥ��y����',
//			  `product_id` int NOT NULL COMMENT '�ӫ~�y����',
//			  `product_pic` mediumblob NOT NULL COMMENT '�ӫ~�Ϥ�',
//	
	private static final String INSERT = "INSERT INTO ProductPic(product_id, product_pic) VALUES( ?, ?)";
	private static final String UPDATE = "UPDATE ProductPic SET product_pic = ? WHERE pic_id = ?";
	private static final String FIND_BY_PK = "SELECT * FROM ProductPic WHERE product_id = ?";
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
	public void add(ProductPicVO productPicVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String[] autoGeneratedCol = { "pic_id" };

		try {

con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT,autoGeneratedCol);
			pstmt.setInt(1, productPicVO.getProduct_id());
			pstmt.setBytes(2, productPicVO.getProduct_pic());
//			pstmt.setInt(1, favoriteProductVO.getAccount_id());
//			pstmt.setInt(2, favoriteProductVO.getFav_product_id());

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
	public void update(ProductPicVO productPicVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
//			private static final String UPDATE = "UPDATE ProductPic SET product_pic = ? WHERE pic_id = ?";
			pstmt.setBytes(1, productPicVO.getProduct_pic());
			pstmt.setInt(2, productPicVO.getPic_id());
			
			
//			pstmt.setBytes(1, sellerVO.getSellerCertification());
//			pstmt.setString(2, sellerVO.getSellerName());
//			pstmt.setString(3, sellerVO.getSellerMasterName());
//			pstmt.setString(4, sellerVO.getSellerPhone());
//			pstmt.setString(5, sellerVO.getSellerTaxnumber());
//			pstmt.setString(6, sellerVO.getSellerCompany());
//			pstmt.setString(7, sellerVO.getSellerAddress());
//			pstmt.setInt(8, sellerVO.getSellerID());
			


			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			se.printStackTrace();
			// Clean up JDBC resources
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
	public void delete(int picID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ProductPicVO findByPK(int product_id) {
		ProductPicVO emp = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//		private static final String FIND_BY_PK = "SELECT * FROM ProductPic WHERE product_id = ?";
		try {


con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setInt(1, product_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				emp = new ProductPicVO();
				emp.setPic_id(rs.getInt("pic_id") );
				emp.setProduct_id(rs.getInt("product_id") );
				emp.setProduct_pic(rs.getBytes("product_pic") );
//				emp.setSellerID(rs.getInt("seller_id"));
//				emp.setAccountID(rs.getInt("account_id"));
//				emp.setSellerCertification(rs.getBytes("seller_certification"));
//				emp.setSellerName(rs.getString("seller_name"));
//				emp.setSellerMasterName(rs.getString("seller_master_name"));
//				emp.setSellerPhone(rs.getString("seller_phone"));
//				emp.setSellerTaxnumber(rs.getString("seller_taxnumber"));
//				emp.setSellerCompany(rs.getString("seller_company"));
//				emp.setSellerAddress(rs.getString("seller_address"));
				

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
	public List<ProductPicVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	

}