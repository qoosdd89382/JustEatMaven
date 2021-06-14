package com.orderinfo.model;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;



public class OrderInfoJDBCDAO implements OrderInfoDAOinterface{


	
	
	
	private static final String INSERT = "INSERT INTO OrderInfo(account_id, order_start_time,order_complete_date,order_type,order_total_cost,orderer,orderer_phone,orderer_address,order_payment,orderer_card,receiver,receiver_phone,receiver_address,order_ship_time,order_receive_time,order_deliver_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_STMT = "UPDATE OrderInfo SET  order_start_time  = ?, order_complete_date = ?, order_type = ?, order_total_cost = ? , orderer = ? , orderer_phone = ?, orderer_address = ?,order_payment=?,orderer_card=?,receiver=?,receiver_phone=? ,receiver_address=? ,order_ship_time=?,order_receive_time=?,order_deliver_id=? WHERE order_id = ?";
	private static final String DELETE = "DELETE FROM OrderInfo WHERE order_id = ?";
	private static final String FIND_BY_PK = "SELECT * FROM OrderInfo WHERE order_id = ?";
	
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
	public void add(OrderInfoVO orderInfoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT);
			
//			CREATE TABLE `OrderInfo` (
//					  `order_id` int NOT NULL AUTO_INCREMENT COMMENT '�q��y����',
			pstmt.setInt(1, orderInfoVO.getAccountID());//					  `account_id` int NOT NULL COMMENT '�|���y����',
			pstmt.setTimestamp(2, orderInfoVO.getOrderStartTime());//					  `order_start_time` timestamp NOT NULL COMMENT '�q�沣�ͮɶ�',
			pstmt.setTimestamp(3, orderInfoVO.getOrderCompleteDate());//					  `order_complete_date` timestamp NOT NULL COMMENT '�q�槹���ɶ�',
			pstmt.setInt(4, orderInfoVO.getOrderType());//					  `order_type` int NOT NULL COMMENT '�q�檬�A',
			pstmt.setBigDecimal(5, orderInfoVO.getOrderTotalCost());//					  `order_total_cost` decimal(10,0) NOT NULL COMMENT '�`���B',
			pstmt.setString(6, orderInfoVO.getOrderer());//					  `orderer` varchar(20) NOT NULL COMMENT '�q�ʤH',
			pstmt.setString(7, orderInfoVO.getOrdererPhone());//					  `orderer_phone` varchar(10) NOT NULL COMMENT '�q�ʤH���',
			pstmt.setString(8, orderInfoVO.getOrdererAddress());//					  `orderer_address` varchar(80) NOT NULL COMMENT '�q�ʤH�a�}',
			pstmt.setString(9, orderInfoVO.getOrderPayment());//					  `order_payment` varchar(50) NOT NULL COMMENT '�I�ڸ�T',
			pstmt.setString(10, orderInfoVO.getOrdererCard());//					  `orderer_card` varchar(16) NOT NULL COMMENT '�q�ʤH�H�Υd��',
			pstmt.setString(11, orderInfoVO.getReceiver());//					  `receiver` varchar(20) NOT NULL COMMENT '����H',
			pstmt.setString(12, orderInfoVO.getReceiverPhone());//					  `receiver_phone` varchar(10) NOT NULL COMMENT '����H���',
			pstmt.setString(13, orderInfoVO.getReceiverAddress());//					  `receiver_address` varchar(80) NOT NULL COMMENT '����H�a�}',
			pstmt.setTimestamp(14, orderInfoVO.getOrderShipTime());//					  `order_ship_time` timestamp NOT NULL COMMENT '�X�f�ɶ�',
			pstmt.setTimestamp(15, orderInfoVO.getOrderReceiveTime());//					  `order_receive_time` timestamp NOT NULL COMMENT '���f�ɶ�',
			pstmt.setInt(16, orderInfoVO.getOrderDeliverId());//					  `order_deliver_id` int NOT NULL COMMENT '���y�s��',
			
			
			
			
			
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
	public void update(OrderInfoVO orderInfoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		
//		`order_start_time` timestamp NOT NULL COMMENT '�q�沣�ͮɶ�',
//		  `order_complete_date` timestamp NOT NULL COMMENT '�q�槹���ɶ�',
//		  `order_type` int NOT NULL COMMENT '�q�檬�A',
//		  `order_total_cost` decimal(10,0) NOT NULL COMMENT '�`���B',
//		  `orderer` varchar(20) NOT NULL COMMENT '�q�ʤH',
//		  `orderer_phone` varchar(10) NOT NULL COMMENT '�q�ʤH���',
//		  `orderer_address` varchar(80) NOT NULL COMMENT '�q�ʤH�a�}',
//		  `order_payment` varchar(50) NOT NULL COMMENT '�I�ڸ�T',
//		  `orderer_card` varchar(16) NOT NULL COMMENT '�q�ʤH�H�Υd��',
//		  `receiver` varchar(20) NOT NULL COMMENT '����H',
//		  `receiver_phone` varchar(10) NOT NULL COMMENT '����H���',
//		  `receiver_address` varchar(80) NOT NULL COMMENT '����H�a�}',
//		  `order_ship_time` timestamp NOT NULL COMMENT '�X�f�ɶ�',
//		  `order_receive_time` timestamp NOT NULL COMMENT '���f�ɶ�',
//		  `order_deliver_id` int NOT NULL COMMENT '���y�s��',
		
		try {
//			private static final String UPDATE_STMT = "UPDATE Seller SET  seller_certification  = ?, seller_name = ?, seller_master_name = ?, seller_phone = ? , seller_taxnumber = ? , seller_company = ?, seller_address = ? WHERE seller_id = ?";
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STMT);
			pstmt.setTimestamp(1, orderInfoVO.getOrderStartTime());//					  `order_start_time` timestamp NOT NULL COMMENT '�q�沣�ͮɶ�',
			pstmt.setTimestamp(2, orderInfoVO.getOrderCompleteDate());//					  `order_complete_date` timestamp NOT NULL COMMENT '�q�槹���ɶ�',
			pstmt.setInt(3, orderInfoVO.getOrderType());//					  `order_type` int NOT NULL COMMENT '�q�檬�A',
			pstmt.setBigDecimal(4, orderInfoVO.getOrderTotalCost());//					  `order_total_cost` decimal(10,0) NOT NULL COMMENT '�`���B',
			pstmt.setString(5, orderInfoVO.getOrderer());//					  `orderer` varchar(20) NOT NULL COMMENT '�q�ʤH',
			pstmt.setString(6, orderInfoVO.getOrdererPhone());//					  `orderer_phone` varchar(10) NOT NULL COMMENT '�q�ʤH���',
			pstmt.setString(7, orderInfoVO.getOrdererAddress());//					  `orderer_address` varchar(80) NOT NULL COMMENT '�q�ʤH�a�}',
			pstmt.setString(8, orderInfoVO.getOrderPayment());//					  `order_payment` varchar(50) NOT NULL COMMENT '�I�ڸ�T',
			pstmt.setString(9, orderInfoVO.getOrdererCard());//					  `orderer_card` varchar(16) NOT NULL COMMENT '�q�ʤH�H�Υd��',
			pstmt.setString(10, orderInfoVO.getReceiver());//					  `receiver` varchar(20) NOT NULL COMMENT '����H',
			pstmt.setString(11, orderInfoVO.getReceiverPhone());//					  `receiver_phone` varchar(10) NOT NULL COMMENT '����H���',
			pstmt.setString(12, orderInfoVO.getReceiverAddress());//					  `receiver_address` varchar(80) NOT NULL COMMENT '����H�a�}',
			pstmt.setTimestamp(13, orderInfoVO.getOrderShipTime());//					  `order_ship_time` timestamp NOT NULL COMMENT '�X�f�ɶ�',
			pstmt.setTimestamp(14, orderInfoVO.getOrderReceiveTime());//					  `order_receive_time` timestamp NOT NULL COMMENT '���f�ɶ�',
			pstmt.setInt(15, orderInfoVO.getOrderDeliverId());//	
			pstmt.setInt(16, orderInfoVO.getOrderID());


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
	public void delete(int orderID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, orderID);
			
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
	public OrderInfoVO findByPK(int orderID) {
		OrderInfoVO emp = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setInt(1, orderID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				emp = new OrderInfoVO();
				
				emp.setOrderID(rs.getInt("orderID") );
				emp.setAccountID(rs.getInt("account_id"));
				emp.setOrderStartTime(rs.getTimestamp("order_start_time"));
				emp.setOrderCompleteDate(rs.getTimestamp("order_complete_date"));
				emp.setOrderType(rs.getInt("order_type"));
				emp.setOrderTotalCost(rs.getBigDecimal("order_total_cost"));
				emp.setOrderer(rs.getString("orderer"));
				emp.setOrdererPhone(rs.getString("orderer_phone"));
				emp.setOrdererAddress(rs.getString("orderer_address"));
				emp.setOrderPayment(rs.getString("order_payment"));
				emp.setOrdererCard(rs.getString("orderer_card"));
				emp.setReceiver(rs.getString("receiver"));
				emp.setReceiverPhone(rs.getString("receiverPhone") );
				emp.setReceiverAddress(rs.getString("receiverAddress") );
				emp.setOrderShipTime(rs.getTimestamp("orderShipTime") );
				emp.setOrderReceiveTime(rs.getTimestamp("orderReceiveTime") );
				emp.setOrderDeliverId(rs.getInt("orderDeliverId") );
//				`order_id` int NOT NULL AUTO_INCREMENT COMMENT '�q��y����',
//				  `account_id` int NOT NULL COMMENT '�|���y����',
//				  `order_start_time` timestamp NOT NULL COMMENT '�q�沣�ͮɶ�',
//				  `order_complete_date` timestamp NOT NULL COMMENT '�q�槹���ɶ�',
//				  `order_type` int NOT NULL COMMENT '�q�檬�A',
//				  `order_total_cost` decimal(10,0) NOT NULL COMMENT '�`���B',
//				  `orderer` varchar(20) NOT NULL COMMENT '�q�ʤH',
//				  `orderer_phone` varchar(10) NOT NULL COMMENT '�q�ʤH���',
//				  `orderer_address` varchar(80) NOT NULL COMMENT '�q�ʤH�a�}',
//				  `order_payment` varchar(50) NOT NULL COMMENT '�I�ڸ�T',
//				  `orderer_card` varchar(16) NOT NULL COMMENT '�q�ʤH�H�Υd��',
//				  `receiver` varchar(20) NOT NULL COMMENT '����H',
//				  `receiver_phone` varchar(10) NOT NULL COMMENT '����H���',
//				  `receiver_address` varchar(80) NOT NULL COMMENT '����H�a�}',
//				  `order_ship_time` timestamp NOT NULL COMMENT '�X�f�ɶ�',
//				  `order_receive_time` timestamp NOT NULL COMMENT '���f�ɶ�',
//				  `order_deliver_id` int NOT NULL COMMENT '���y�s��',
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
	public List<OrderInfoVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
	

}
