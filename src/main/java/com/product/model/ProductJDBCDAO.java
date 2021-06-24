package com.product.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductJDBCDAO implements ProfuctDAOinterface {

	private static final String INSERT_STMT = "INSERT INTO Product(seller_id, product_state,product_price , product_amount,product_unit ,product_specification ,product_origin ,product_storage_method, product_release_time,product_expire_time,product_discount,product_text,product_sgs_pic) VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?)";
	private static final String UPDATE_STMT = "UPDATE Product SET  product_state = ?, product_price = ?, product_amount = ?, product_unit = ? , product_specification = ? , product_origin = ?, product_storage_method = ?  product_release_time = ?, product_expire_time = ?, product_discount = ?, product_text = ?, product_sgs_pic = ? WHERE product_id = ?";
	private static final String DELETE_STMT = "DELETE FROM Product WHERE product_id = ?";
	private static final String FIND_BY_PK = "SELECT * FROM Product WHERE product_id = ?";
	private static final String GET_ALL = "SELECT * FROM Product";
	private static final String GET_ALL_BY_CLICK_COUNT = "SELECT * FROM Product order by product_click_count ";
	
	
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
	public void add(ProductVO productVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

//		"INSERT INTO Product(seller_id, product_state,product_price , product_amount,product_unit ,product_specification ,product_origin ,product_storage_method, product_release_time,product_expire_time,product_discount,product_text,product_sgs_pic) VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?)";

		try {

			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, productVO.getSellerID());
			pstmt.setInt(2, productVO.getProductState());
			pstmt.setInt(3, productVO.getProductPrice());
			pstmt.setInt(4, productVO.getProductAmount());
			pstmt.setString(5, productVO.getProductUnit());
			pstmt.setString(6, productVO.getProductSpecification());
			pstmt.setString(7, productVO.getProductOrigin());
			pstmt.setString(8, productVO.getProductStorageMethod());
			pstmt.setTimestamp(9, productVO.getProductReleaseTime());
			pstmt.setTimestamp(10, productVO.getProductExpireTime());
			pstmt.setBoolean(11, productVO.getProductDiscount());
			pstmt.setString(12, productVO.getProductText());
			pstmt.setBytes(13, productVO.getProductSgsPic());
//			pstmt.setInt(1, sellerVO.getAccountID());
//			pstmt.setBytes(2, sellerVO.getSellerCertification());
//			pstmt.setString(3, sellerVO.getSellerName());
//			pstmt.setString(4, sellerVO.getSellerMasterName());
//			pstmt.setString(5, sellerVO.getSellerPhone());
//			pstmt.setString(6, sellerVO.getSellerTaxnumber());
//			pstmt.setString(7, sellerVO.getSellerCompany());
//			pstmt.setString(8, sellerVO.getSellerAddress());

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
	public void update(ProductVO productVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

//		"UPDATE Product SET  product_state = ?, product_price = ?, product_amount = ?, product_unit = ? , product_specification = ? , product_origin = ?, product_storage_method = ?  product_release_time = ?, product_expire_time = ?, product_discount = ?, product_text = ?, product_sgs_pic = ? WHERE product_id = ?";

		try {

			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setInt(1, productVO.getProductState());
			pstmt.setInt(2, productVO.getProductPrice());
			pstmt.setInt(3, productVO.getProductAmount());
			pstmt.setString(4, productVO.getProductUnit());
			pstmt.setString(5, productVO.getProductSpecification());
			pstmt.setString(6, productVO.getProductOrigin());
			pstmt.setString(7, productVO.getProductStorageMethod());
			pstmt.setTimestamp(8, productVO.getProductReleaseTime());
			pstmt.setTimestamp(9, productVO.getProductExpireTime());
			pstmt.setBoolean(10, productVO.getProductDiscount());
			pstmt.setString(11, productVO.getProductText());
			pstmt.setBytes(12, productVO.getProductSgsPic());

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
	public void delete(int productID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setInt(1, productID);

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
	public ProductVO findByPK(int productID) {
		ProductVO emp = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setInt(1, productID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				emp = new ProductVO();

//				`product_id` int NOT NULL AUTO_INCREMENT COMMENT '�ӫ~�y����',
//				  `seller_id` int DEFAULT NULL COMMENT '�Ӯa�y����',
//				  `product_state` int DEFAULT NULL COMMENT '�ӫ~�W�[���A',
//				  `product_price` int DEFAULT NULL COMMENT '�ӫ~����',
//				  `product_amount` int DEFAULT NULL COMMENT '�ӫ~�ƶq',
//				  `product_unit` varchar(10) DEFAULT NULL COMMENT '�ӫ~���',
//				  `product_specification` varchar(10) DEFAULT NULL COMMENT '�ӫ~�W��',
//				  `product_origin` varchar(10) DEFAULT NULL COMMENT '�ӫ~���a',
//				  `product_storage_method` varchar(10) DEFAULT NULL COMMENT '�ӫ~�O�s�覡',
//				  `product_release_time` timestamp NULL DEFAULT NULL COMMENT '�ӫ~�W�[�ɶ�',
//				  `product_expire_time` timestamp NULL DEFAULT NULL COMMENT '�ӫ~����ɶ�',
//				  `product_discount` tinyint(1) DEFAULT NULL COMMENT '�ӫ~�O�_�馩',
//				  `product_text` text COMMENT '�ӫ~����',
//				  `product_sgs_pic` mediumblob COMMENT '�ӫ~sgs�{�ҹϤ�',

				emp.setProductID(rs.getInt("product_id"));
				emp.setSellerID(rs.getInt("seller_id"));
				emp.setProductState(rs.getInt("product_state"));
				emp.setProductPrice(rs.getInt("product_price"));
				emp.setProductAmount(rs.getInt("product_amount"));
				emp.setProductUnit(rs.getString("product_unit"));
				emp.setProductSpecification(rs.getString("product_specification"));
				emp.setProductOrigin(rs.getString("product_origin"));
				emp.setProductStorageMethod(rs.getString("product_storage_method"));
				emp.setProductReleaseTime(rs.getTimestamp("product_release_time"));
				emp.setProductExpireTime(rs.getTimestamp("product_expire_time"));
				emp.setProductDiscount(rs.getBoolean("product_discount"));
				emp.setProductText(rs.getString("product_text"));
				emp.setProductSgsPic(rs.getBytes("product_sgs_pic"));

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
	public List<ProductVO> getAll() {
		List<ProductVO> empList = new ArrayList<>();
		ProductVO emp = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				emp = new ProductVO();
				emp.setProductID(rs.getInt("product_id"));
				emp.setSellerID(rs.getInt("seller_id"));
				emp.setProductState(rs.getInt("product_state"));
				emp.setProductPrice(rs.getInt("product_price"));
				emp.setProductAmount(rs.getInt("product_amount"));
				emp.setProductUnit(rs.getString("product_unit"));
				emp.setProductSpecification(rs.getString("product_specification"));
				emp.setProductOrigin(rs.getString("product_origin"));
				emp.setProductStorageMethod(rs.getString("product_storage_method"));
				emp.setProductReleaseTime(rs.getTimestamp("product_release_time"));
				emp.setProductExpireTime(rs.getTimestamp("product_expire_time"));
				emp.setProductDiscount(rs.getBoolean("product_discount"));
				emp.setProductText(rs.getString("product_text"));
				emp.setProductSgsPic(rs.getBytes("product_sgs_pic"));
//				emp.setEmpno(rs.getInt("sellerID"));
//				emp.setEname(rs.getString("sellerCompany"));
//				emp.setJob(rs.getString("JOB"));
//				emp.setHiredate(rs.getDate("HIREDATE"));
//				emp.setSal(rs.getDouble("SAL"));
//				emp.setComm(rs.getDouble("COMM"));
//				emp.setDeptno(rs.getInt("DEPTNO"));

				empList.add(emp);
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
		return empList;
	}

	@Override
	public List<ProductVO> getAllByClickCount() {
		
		List<ProductVO> empList = new ArrayList<>();
		ProductVO emp = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_BY_CLICK_COUNT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				emp = new ProductVO();
				emp.setProductID(rs.getInt("product_id"));
				emp.setSellerID(rs.getInt("seller_id"));
				emp.setProductState(rs.getInt("product_state"));
				emp.setProductPrice(rs.getInt("product_price"));
				emp.setProductAmount(rs.getInt("product_amount"));
				emp.setProductUnit(rs.getString("product_unit"));
				emp.setProductSpecification(rs.getString("product_specification"));
				emp.setProductOrigin(rs.getString("product_origin"));
				emp.setProductStorageMethod(rs.getString("product_storage_method"));
				emp.setProductReleaseTime(rs.getTimestamp("product_release_time"));
				emp.setProductExpireTime(rs.getTimestamp("product_expire_time"));
				emp.setProductDiscount(rs.getBoolean("product_discount"));
				emp.setProductText(rs.getString("product_text"));
				emp.setProductSgsPic(rs.getBytes("product_sgs_pic"));
//				emp.setEmpno(rs.getInt("sellerID"));
//				emp.setEname(rs.getString("sellerCompany"));
//				emp.setJob(rs.getString("JOB"));
//				emp.setHiredate(rs.getDate("HIREDATE"));
//				emp.setSal(rs.getDouble("SAL"));
//				emp.setComm(rs.getDouble("COMM"));
//				emp.setDeptno(rs.getInt("DEPTNO"));

				empList.add(emp);
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
		return empList;
		
	}

}
