package com.visit.model;

import java.util.*;

import com.announce.model.AnnounceJDBCDAO;
import com.announce.model.AnnounceVO;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VisitJDBCDAO implements VisitDAOInterface{
	
	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/JustEat?serverTimezone=Asia/Taipei";
	private static String userid = "DBAdmin";
	private static String password = "justeat";
	
	private static final String Insert_Stmt = "Insert into Visit "
			+ "(account_id,visit_record)"
			+ "Values(?,?)";

	private static final String Update_Stmt = "Update Visit set "
			+ "account_id=?,visit_record=?"
			+ "Where visit_id=?";
	private static final String Delete_Stmt = 
			"Delete From Visit Where visit_id=?";
	private static final String Select_Key_Stmt = 
			"Select * From JustEat.Visit Where visit_id=?";
	private static final String Select_All_Stmt = 
			"Select * From JustEat.Visit";
	
	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace(System.err);
		}
	}

	@Override
	public void insert(VisitVO visitVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		
		String[] autoGeneratedCol = {"visit_id"};
		
		try {
			con = DriverManager.getConnection(url,userid,password);
			pstmt = con.prepareStatement(Insert_Stmt,autoGeneratedCol);
			
			pstmt.setInt(1,visitVO.getAccountID());
			pstmt.setTimestamp(2,visitVO.getVisitRecord());
			
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				Integer autoGeneratedkey = rs.getInt(1);
				System.out.println("Visit insert completed!Visit_id="+autoGeneratedkey);
			}else {
				System.out.println("Visit insert failed!");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if (rs != null) {
				try {
					rs.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {					
					pstmt.close();
				}catch (Exception e){
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void update(VisitVO visitVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(url,userid,password);
			pstmt = con.prepareStatement(Update_Stmt);
			
			pstmt.setInt(1,visitVO.getAccountID());
			pstmt.setTimestamp(2,visitVO.getVisitRecord());
			pstmt.setInt(3,visitVO.getVisitID());
			
			pstmt.executeUpdate();
			
		}catch (Exception e){
			e.printStackTrace(System.err);
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);;
				}
			}
		}
	}

	@Override
	public void delete(Integer visit_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Delete_Stmt);
			pstmt.setInt(1, visit_id);
			pstmt.executeUpdate();

			System.out.println("Visit delete completed!");
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
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
	public VisitVO findByPrimaryKey(Integer visit_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		VisitVO visitVO = null;
		
		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Select_Key_Stmt);
			
			pstmt.setInt(1, visit_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				visitVO = new VisitVO();
				visitVO.setVisitID(rs.getInt("visit_id"));
				visitVO.setAccountID(rs.getInt("account_id"));
				visitVO.setVisitRecord(rs.getTimestamp("visit_record"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();					
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
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
		return visitVO;
	}

	@Override
	public List<VisitVO> getAll() {
		List<VisitVO> list = new ArrayList<VisitVO>();
		VisitVO visitVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Select_All_Stmt);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				visitVO = new VisitVO();
				visitVO.setVisitID(rs.getInt("visit_id"));
				visitVO.setAccountID(rs.getInt("account_id"));
				visitVO.setVisitRecord(rs.getTimestamp("visit_record"));
				list.add(visitVO);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
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
		return list;
	}
	public static void main(String[] args) {
		String date = "2021-02-03 14:00";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime localDateTime = LocalDateTime.parse(date,formatter);
		Timestamp timestampdate = Timestamp.valueOf(localDateTime);
		
		VisitJDBCDAO visitJDBCDAO = new VisitJDBCDAO();
		
		VisitVO visitVO = new VisitVO();
		
		//新增 OK 要先在會員新增
//		visitVO.setAccountID(100001);
//		visitVO.setVisitTime(timestampdate);
//		visitJDBCDAO.insert(visitVO);
		//刪除 OK
//		visitJDBCDAO.delete(900001);

		//瀏覽紀錄不需要修改 
//		visitVO.setAccountID(100001);
//		visitVO.setVisitTime(timestampdate);
//		visitVO.setVisitID(900004);
//		visitJDBCDAO.update(visitVO);
		
		//查詢 OK
//		visitVO = visitJDBCDAO.findByPrimaryKey(900004);
//		System.out.println(visitVO.getAccountID()+",");
//		System.out.println(visitVO.getVisitRecord()+",");
//		System.out.println("search complete!");
		
		//查詢全部 OK
//		List<VisitVO> visitVO_list = visitJDBCDAO.getAll();
//		for (VisitVO e:visitVO_list) {
//			System.out.println(e.getAccountID()+",");
//			System.out.println(e.getVisitRecord()+",");
//		}
		
	}
}
