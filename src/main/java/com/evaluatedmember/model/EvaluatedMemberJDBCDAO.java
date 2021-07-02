package com.evaluatedmember.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.eventmember.model.EventMemberVO;

public class EvaluatedMemberJDBCDAO implements EvaluatedMemberDAOInterface {

	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/JustEat?serverTimezone=Asia/Taipei";
	private static String user = "DBAdmin";
	private static String password = "justeat";

	private static final String Insert_Stmt = "Insert into EvaluatedMember(accepter_account_id,giver_account_id,event_id,give_score) Values(?,?,?,?)";
	private static final String Update_Stmt = "Update EvaluatedMember Set give_score = ? Where event_id = ? AND accepter_account_id = ? AND giver_account_id = ?";
//	private static final String Delete_Stmt = "Delete From EvaluatedMember Where "; 再想想
	private static final String Select_All_Stmt = "Select * From EvaluatedMember";
	private static final String Get_AllByEventID_Stmt = "Select * From EvaluatedMember Where event_id = ?";
	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void insert(EvaluatedMemberVO evaluatedMemberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Insert_Stmt);

			pstmt.setInt(1, evaluatedMemberVO.getAccepterAccountID());
			pstmt.setInt(2, evaluatedMemberVO.getGiverAccountID());
			pstmt.setInt(3, evaluatedMemberVO.getEventID());
			pstmt.setInt(4, evaluatedMemberVO.getGiveScore());

			pstmt.executeUpdate();
			System.out.println("新增成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void update(EvaluatedMemberVO evaluatedMemberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Update_Stmt);

			pstmt.setInt(1, evaluatedMemberVO.getGiveScore());
			pstmt.setInt(2, evaluatedMemberVO.getEventID());
			pstmt.setInt(3, evaluatedMemberVO.getAccepterAccountID());
			pstmt.setInt(4, evaluatedMemberVO.getGiverAccountID());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public List<EvaluatedMemberVO> getAll() {
		List<EvaluatedMemberVO> list = new ArrayList<EvaluatedMemberVO>();
		EvaluatedMemberVO evaluatedMemberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Select_All_Stmt);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				evaluatedMemberVO = new EvaluatedMemberVO();
				evaluatedMemberVO.setAccepterAccountID(rs.getInt("accepter_account_id"));
				evaluatedMemberVO.setGiverAccountID(rs.getInt("Giver_account_id"));
				evaluatedMemberVO.setEventID(rs.getInt("Event_id"));
				evaluatedMemberVO.setGiveScore(rs.getInt("Give_score"));
				list.add(evaluatedMemberVO);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	@Override
	public List<EvaluatedMemberVO>  getAllByEventID(Integer eventID) {
		List<EvaluatedMemberVO> list = new ArrayList<EvaluatedMemberVO>();
		EvaluatedMemberVO evaluatedMemberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Get_AllByEventID_Stmt);
			pstmt.setInt(1, eventID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				evaluatedMemberVO = new EvaluatedMemberVO();
				evaluatedMemberVO.setAccepterAccountID(rs.getInt("accepter_account_id"));
				evaluatedMemberVO.setGiverAccountID(rs.getInt("Giver_account_id"));
				evaluatedMemberVO.setEventID(rs.getInt("Event_id"));
				evaluatedMemberVO.setGiveScore(rs.getInt("Give_score"));
				list.add(evaluatedMemberVO);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	public static void main(String[] args) {
		EvaluatedMemberVO evaluatedMemberVO = new EvaluatedMemberVO();
		EvaluatedMemberJDBCDAO evaluatedMemberJDBCDAO = new EvaluatedMemberJDBCDAO();
		// =====================新增用========================
//		evaluatedMemberVO.setAccepterAccountID(100001);
//		evaluatedMemberVO.setGiverAccountID(100004);
//		evaluatedMemberVO.setEventID(300001);
//		evaluatedMemberVO.setGiveScore(3);
//		evaluatedMemberJDBCDAO.insert(evaluatedMemberVO);
		// =====================更新用========================
//		evaluatedMemberVO.setGiveScore(5);
//		evaluatedMemberVO.setEventID(300001);
//		evaluatedMemberVO.setAccepterAccountID(100001);
//		evaluatedMemberJDBCDAO.update(evaluatedMemberVO);
		// =====================查詢用========================
//		List<EvaluatedMemberVO> evaluatedMember_list = evaluatedMemberJDBCDAO.getAll();
//		for (EvaluatedMemberVO evaluatedMemberVO_temp : evaluatedMember_list) {
//			System.out.println(evaluatedMemberVO_temp.getAccepterAccountID());
//			System.out.println(evaluatedMemberVO_temp.getGiverAccountID());
//			System.out.println(evaluatedMemberVO_temp.getEventID());
//			System.out.println(evaluatedMemberVO_temp.getGiveScore());
//			System.out.println("========================================");
//		}
	}
}
