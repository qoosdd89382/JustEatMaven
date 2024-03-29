package com.eventmember.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dish.model.DishJDBCDAO;
import com.dish.model.DishVO;
import com.dishandingredient.model.DishAndIngredientVO;

public class EventMemberJDBCDAO implements EventMemberDAOInterface {

	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/JustEat?serverTimezone=Asia/Taipei";
	private static String user = "DBAdmin";
	private static String password = "justeat";

	private static final String Insert_Stmt = "Insert into EventMember(event_id,account_id,participation_state,host_identifier)Values((Select event_id From EventInfo Where event_id = ?),(Select account_id From AccountInfo Where account_id = ?),?,?)";
	private static final String Insert_With_Default_Stmt = "Insert into EventMember(event_id,account_id,participation_state,host_identifier)Values((Select event_id From EventInfo Where event_id = ?),(Select account_id From AccountInfo Where account_id = ?),?,?)";
	// 單一活動獲得總評分,單一活動評分總人數 可在修改用查詢獲得 暫時還沒做
	private static final String Update_Stmt = "Update EventMember Set participation_state = ? Where event_id = ? AND account_id = ?"; // 審核過就更新
	private static final String Delete_Stmt = "Delete From EventMember Where event_id = ? AND account_id = ? ";
	private static final String Select_All_Stmt = "Select * From EventMember";
	private static final String Select_MemberID_Stmt = "Select * From EventMember Where account_id = ?";
	private static final String Select_EventID_Stmt = "Select * From EventMember Where event_id = ?";
	private static final String Select_Avgscore_Stmt = "SELECT \r\n" + 
			"    SUM(EvaluatedMember.give_score) / COUNT(*)\r\n" + 
			"FROM EvaluatedMember\r\n" + 
			" left join EventMember\r\n" + 
			"  on EvaluatedMember.accepter_account_id = EventMember.account_id\r\n" + 
			"WHERE\r\n" + 
			" participation_state = 3\r\n" + 
			" and accepter_account_id = ?";
	private static final String Select_Totalevent_Stmt ="SELECT COUNT(*) FROM EventMember WHERE account_id = ? and (participation_state = 3 or participation_state = 4)";
	private static final String Select_TotalAttendance_Stmt ="SELECT COUNT(*) FROM EventMember WHERE account_id = ? and (participation_state = 3 )";
//	private static final String Select_EventStatus_Stmt ="SELECT * FROM EventMember WHERE account_id = ? and event_id = ? and (participation_state = 1 or participation_state = 2)";
	private static final String Select_EventStatus_Stmt ="SELECT participation_state FROM EventMember WHERE account_id = ? and event_id = ? ";
	private static final String Select_Account_Stmt ="SELECT * FROM EventMember WHERE account_id = ?";
	private static final String Select_EventandHost_Stmt ="SELECT account_id FROM EventMember WHERE event_id = ? and host_identifier = 1";
	private static final String Select_AuditPass_Stmt ="SELECT * FROM EventMember WHERE event_id = ? AND participation_state = 2";
	

	private static final String Select_EventID_And_MemberID_Stmt = "Select * From EventMember Where event_id = ? And account_id = ?";
	private static final String Select_Count_Member_By_EventID = "Select count(event_id) from EventMember Where event_id = ?";

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
			pstmt.setBoolean(4, eventMemberVO.isHostIdentifier());
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
//			pstmt.setBoolean(4, eventMemberVO.isHostIdentifier());
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
	public EventMemberVO getByEventIDAndMemberID(Integer eventID,Integer accountID) {
		EventMemberVO eventMemberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Select_EventID_And_MemberID_Stmt);
			pstmt.setInt(1, eventID);
			pstmt.setInt(2, accountID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				eventMemberVO = new EventMemberVO();
				eventMemberVO.setEventID(rs.getInt("event_id"));
				eventMemberVO.setAccountID(rs.getInt("account_id"));
				eventMemberVO.setParticipationState(rs.getInt("participation_state"));
				eventMemberVO.setHostIdentifier(rs.getBoolean("host_identifier"));
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
		return eventMemberVO;
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
				eventMemberVO.setHostIdentifier(rs.getBoolean("host_identifier"));
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

	
	@Override
	public List<EventMemberVO> getAllByMemberID(Integer accountID) {
		List<EventMemberVO> list = new ArrayList<EventMemberVO>();
		EventMemberVO eventMemberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Select_MemberID_Stmt);
			pstmt.setInt(1, accountID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				eventMemberVO = new EventMemberVO();
				eventMemberVO.setEventID(rs.getInt("event_id"));
				eventMemberVO.setAccountID(rs.getInt("account_id"));
				eventMemberVO.setParticipationState(rs.getInt("participation_state"));
				eventMemberVO.setHostIdentifier(rs.getBoolean("host_identifier"));
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
	@Override
	public List<EventMemberVO> getAllByAccount(Integer accountID) {
		List<EventMemberVO> list = new ArrayList<EventMemberVO>();
		EventMemberVO eventMemberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Select_Account_Stmt);
			pstmt.setInt(1, accountID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				eventMemberVO = new EventMemberVO();
				eventMemberVO.setEventID(rs.getInt("event_id"));
				eventMemberVO.setAccountID(rs.getInt("account_id"));
				eventMemberVO.setParticipationState(rs.getInt("participation_state"));
				eventMemberVO.setHostIdentifier(rs.getBoolean("host_identifier"));
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
	@Override
	public int getAvgScoreByAccountID(Integer accountID) {
		List<EventMemberVO> list = new ArrayList<EventMemberVO>();
		int avgScore = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Select_Avgscore_Stmt);
			pstmt.setInt(1,  accountID);
			rs = pstmt.executeQuery();
			if (rs.next()) {	
				avgScore = rs.getInt(1);	
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
		return avgScore;
	}
	
	@Override
	public int getTotalEventByAccountID(Integer accountID) {
		List<EventMemberVO> list = new ArrayList<EventMemberVO>();
		int totalevent = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Select_Totalevent_Stmt);
			pstmt.setInt(1,  accountID);
			rs = pstmt.executeQuery();
			if (rs.next()) {	
				totalevent = rs.getInt(1);	
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
		return totalevent;
	}
	
	@Override
	public int getTotalAttendanceByAccountID(Integer accountID) {
		List<EventMemberVO> list = new ArrayList<EventMemberVO>();
		int totalattendance = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Select_TotalAttendance_Stmt);
			pstmt.setInt(1,  accountID);
			rs = pstmt.executeQuery();
			if (rs.next()) {	
				totalattendance = rs.getInt(1);	
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
		return totalattendance;
	}
	
	@Override
	public int getEventStatusByAccountID(Integer accountID, Integer eventID) {
		List<EventMemberVO> list = new ArrayList<EventMemberVO>();
		int eventstatus = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Select_EventStatus_Stmt);
			pstmt.setInt(1,  accountID);
			pstmt.setInt(2,  eventID);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {	
				eventstatus = rs.getInt(1);	
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
		return eventstatus;
	}
	
	@Override
	public int getOneByEventAndHost(int eventID) {
		List<EventMemberVO> list = new ArrayList<EventMemberVO>();
		int accountID = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Select_EventandHost_Stmt);
			pstmt.setInt(1,  eventID);
			rs = pstmt.executeQuery();
			if (rs.next()) {	
				accountID = rs.getInt(1);	
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
		return accountID;
	}
	
	@Override
		public List<EventMemberVO> getAuditPassbyeventID(Integer eventID) {
		List<EventMemberVO> list = new ArrayList<EventMemberVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Select_AuditPass_Stmt);
			pstmt.setInt(1,  eventID);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {	
				EventMemberVO eventMemberVO = new EventMemberVO();
				eventMemberVO.setEventID(rs.getInt("event_id"));
				eventMemberVO.setAccountID(rs.getInt("account_id"));
				eventMemberVO.setParticipationState(rs.getInt("participation_state"));
				eventMemberVO.setHostIdentifier(rs.getBoolean("host_identifier"));
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
	
	@Override
	public List<EventMemberVO> getAllByEventID(Integer eventID) {
		List<EventMemberVO> list = new ArrayList<EventMemberVO>();
		EventMemberVO eventMemberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Select_EventID_Stmt);
			pstmt.setInt(1, eventID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				eventMemberVO = new EventMemberVO();
				eventMemberVO.setEventID(rs.getInt("event_id"));
				eventMemberVO.setAccountID(rs.getInt("account_id"));
				eventMemberVO.setParticipationState(rs.getInt("participation_state"));
				eventMemberVO.setHostIdentifier(rs.getBoolean("host_identifier"));
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
	
	@Override
	public int getCountMemberbyEvent(Integer eventID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Select_Count_Member_By_EventID);
			pstmt.setInt(1, eventID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
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
		return 0;
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
			pstmt.setBoolean(4, eventMemberVO.isHostIdentifier());
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

	public void insertWithDishIngredient(EventMemberVO eventMemberVO,List<DishVO> dishList,List<DishAndIngredientVO> dishAndIngredientList) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(Insert_Stmt);
			pstmt.setInt(1, eventMemberVO.getEventID());
			pstmt.setInt(2, eventMemberVO.getAccountID());
			pstmt.setInt(3, eventMemberVO.getParticipationState());
			pstmt.setBoolean(4, eventMemberVO.isHostIdentifier());
			pstmt.executeUpdate();
			
			DishJDBCDAO dao = new DishJDBCDAO();
			for(DishVO dishVO : dishList) {
				dishVO.setEventID(eventMemberVO.getEventID());
				dao.insertByEventInfoAndWithDishAndIngredient(dishVO, dishAndIngredientList, con);
			}
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				if(con!=null) {
					con.rollback();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
	
	public static void main(String[] args) {
		EventMemberVO eventMemberVO = new EventMemberVO();
		EventMemberJDBCDAO eventMemberJDBCDAO = new EventMemberJDBCDAO();
		// =====================新增用=========================
//		eventMemberVO.setEventID(300001);
//		eventMemberVO.setAccountID(100001);
//		eventMemberVO.setParticipationState(1);
//		eventMemberJDBCDAO.insert(eventMemberVO);
		// =====================更新用=========================
//		eventMemberVO.setParticipationState(1);
//		eventMemberVO.setEventID(300001);
//		eventMemberVO.setAccountID(100001);
//		eventMemberJDBCDAO.update(eventMemberVO);
		// =====================刪除用=========================
//		eventMemberJDBCDAO.delete(300001,100001);
		// =====================查詢用=========================
//		List<EventMemberVO> list = eventMemberJDBCDAO.getAll();
//		for (EventMemberVO eventMemberVO_temp : list) {
//			System.out.println(eventMemberVO_temp.getEventID());
//			System.out.println(eventMemberVO_temp.getAccountID());
//			System.out.println(eventMemberVO_temp.getParticipationState());
//			System.out.println("==================================");
//		}
		
//		List<EventMemberVO> list2 = eventMemberJDBCDAO.getAllByMemberID(100001);
//		for (EventMemberVO eventMemberVO_temp : list2) {
//			System.out.println(eventMemberVO_temp.getEventID());
//			System.out.println(eventMemberVO_temp.getAccountID());
//			System.out.println(eventMemberVO_temp.getParticipationState());
//			System.out.println("==================================");
//		}
		//===================查詢平均星星數=============================
//		EventMemberService eventMemberSvc = new EventMemberService();
//		int accountAvgScore = eventMemberSvc.getAvgScoreByAccountID(100001);
//		System.out.println(accountAvgScore);
//		System.out.println("==================================");
	
		//=================查詢總活動次數=========================
		
		
		
		
	}
}
