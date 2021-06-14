package com.eventmember.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventMemberJDBCDAO implements EventMemberDAOInterface {

	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/JustEat?serverTimezone=Asia/Taipei";
	private static String user = "DBAdmin";
	private static String password = "justeat";

	private static final String Insert_Stmt = "Insert into EventMember(event_id,account_id,participation_state,total_score,total_judger)Values((Select event_id From EventInfo Where event_id = ?),(Select account_id From AccountInfo Where account_id = ?),?,?,?)";
	private static final String Insert_With_Default_Stmt = "Insert into EventMember(event_id,account_id,participation_state)Values((Select event_id From EventInfo Where event_id = ?),(Select account_id From AccountInfo Where account_id = ?),?)";
	// 單一活動獲得總評分,單一活動評分總人數 可在修改用查詢獲得 暫時還沒做
	private static final String Update_Stmt = "Update EventMember Set participation_state = ? Where event_id = ? AND account_id = ?"; // 審核過就更新
	private static final String Delete_Stmt = "Delete From EventMember Where event_id = ? AND account_id = ? ";
	private static final String Select_All_Stmt = "Select * From EventMember";

	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void insert(EventMemberVO eventMemberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Insert_Stmt);
			pstmt.setInt(1, eventMemberVO.getEventID());
			pstmt.setInt(2, eventMemberVO.getAccountID());
			pstmt.setInt(3, eventMemberVO.getParticipationState());
			pstmt.setInt(4, eventMemberVO.getTotalScore());
			pstmt.setInt(5, eventMemberVO.getTotalJudger());
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
	public void update(EventMemberVO eventMemberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Update_Stmt);
			pstmt.setInt(1, eventMemberVO.getParticipationState());
			pstmt.setInt(2, eventMemberVO.getEventID());
			pstmt.setInt(3, eventMemberVO.getAccountID());
			pstmt.executeUpdate();
			System.out.println("更新成功");
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
	public void delete(Integer event_id, Integer account_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Delete_Stmt);
			pstmt.setInt(1, event_id);
			pstmt.setInt(2, account_id);
			pstmt.executeUpdate();
			System.out.println("刪除成功");
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
	public List<EventMemberVO> getAll() {
		List<EventMemberVO> list = new ArrayList<EventMemberVO>();
		EventMemberVO eventMemberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Select_All_Stmt);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				eventMemberVO = new EventMemberVO();
				eventMemberVO.setEventID(rs.getInt("event_id"));
				eventMemberVO.setAccountID(rs.getInt("account_id"));
				eventMemberVO.setParticipationState(rs.getInt("participation_state"));
				eventMemberVO.setTotalScore(rs.getInt("total_score"));
				eventMemberVO.setTotalJudger(rs.getInt("total_judger"));
				list.add(eventMemberVO);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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
		return list;
	}

	//被活動資訊 連鎖
	@Override
	public void insertByEventInfo(EventMemberVO eventMemberVO, Connection con) {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(Insert_With_Default_Stmt);
			pstmt.setInt(1, eventMemberVO.getEventID());
			pstmt.setInt(2, eventMemberVO.getAccountID());
			pstmt.setInt(3, eventMemberVO.getParticipationState());
			pstmt.executeUpdate();
			System.out.println("新增活動成員 成功");
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		EventMemberVO eventMemberVO = new EventMemberVO();
		EventMemberJDBCDAO eventMemberJDBCDAO = new EventMemberJDBCDAO();
		// =====================新增用=========================
		eventMemberVO.setEventID(300001);
		eventMemberVO.setAccountID(100001);
		eventMemberVO.setParticipationState(1);
		eventMemberVO.setTotalScore(5);
		eventMemberVO.setTotalJudger(1);
		eventMemberJDBCDAO.insert(eventMemberVO);
		// =====================更新用=========================
		eventMemberVO.setParticipationState(1);
		eventMemberVO.setEventID(300001);
		eventMemberVO.setAccountID(100001);
		eventMemberJDBCDAO.update(eventMemberVO);
		// =====================刪除用=========================
//		eventMemberJDBCDAO.delete(300001,100001);
		// =====================查詢用=========================
		List<EventMemberVO> list = eventMemberJDBCDAO.getAll();
		for (EventMemberVO eventMemberVO_temp : list) {
			System.out.println(eventMemberVO_temp.getEventID());
			System.out.println(eventMemberVO_temp.getAccountID());
			System.out.println(eventMemberVO_temp.getParticipationState());
			System.out.println(eventMemberVO_temp.getTotalScore());
			System.out.println(eventMemberVO_temp.getTotalJudger());
			System.out.println("==================================");
		}
	}

}