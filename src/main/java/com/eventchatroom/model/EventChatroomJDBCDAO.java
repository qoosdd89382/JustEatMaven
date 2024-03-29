package com.eventchatroom.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventChatroomJDBCDAO implements EventChartroomDAOInterfaceForJDBC {
	
	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/JustEat?serverTimezone=Asia/Taipei";
	private static String user = "DBAdmin";
	private static String password = "justeat";
	
	private static final String Insert_Stmt = "Insert into EventChatroom(event_id,account_id,message_time,chat_text)Values((Select event_id From EventInfo Where event_id=?),(Select account_id From AccountInfo Where account_id = ?),?,?)";
	private static final String Update_Stmt = "Update EventChatroom Set chat_text = ? Where account_id = ?"; //�s��T�� ����A��ĳ
	private static final String Select_AccountID_Stmt = "Select * From EventChatroom Where account_id = ?"; //�j�M��ѫǽֵo��
	private static final String Select_All_Stmt = "Select * From EventChatroom";
	
	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("找不到驅動(driver)" + e.getMessage());
		}
	}
	
	@Override
	public void insert(EventChatroomVO eventChatroomVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] autoGeneratedCol = {"message_id"};
		try {
			con = DriverManager.getConnection(url,user,password);
			pstmt = con.prepareStatement(Insert_Stmt,autoGeneratedCol);
			pstmt.setInt(1, eventChatroomVO.getEventID());
			pstmt.setInt(2, eventChatroomVO.getAccountID());
			pstmt.setTimestamp(3, eventChatroomVO.getMessageTime());
			pstmt.setString(4, eventChatroomVO.getChatText());
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				Integer autoGeneratedKey = rs.getInt(1);
				System.out.println("新增的活動聊天室流水號為:"+autoGeneratedKey);
			}else {
				System.out.println("沒有新增任何東西");
			}
		} catch (SQLException e) {
			throw new RuntimeException("資料庫發生錯誤" + e.getMessage());
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					throw new RuntimeException("資料庫發生錯誤" + e.getMessage());
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					throw new RuntimeException("資料庫發生錯誤" + e.getMessage());
				}
			}
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					throw new RuntimeException("資料庫發生錯誤" + e.getMessage());
				}
			}
		}
	}

	@Override
	public void update(EventChatroomVO eventChatroomVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(url,user,password);
			pstmt = con.prepareStatement(Update_Stmt);
			pstmt.setString(1,eventChatroomVO.getChatText());
			pstmt.setInt(2, eventChatroomVO.getAccountID());
			pstmt.executeUpdate();
			System.out.println("更新成功");
		} catch (SQLException e) {
			throw new RuntimeException("資料庫發生錯誤" + e.getMessage());
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					throw new RuntimeException("資料庫發生錯誤" + e.getMessage());
				}
			}
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					throw new RuntimeException("資料庫發生錯誤" + e.getMessage());
				}
			}
		}
	}

	@Override
	public EventChatroomVO findAccountID(Integer accountID) {
		EventChatroomVO eventChatroomVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(url,user,password);
			pstmt = con.prepareStatement(Select_AccountID_Stmt);
			pstmt.setInt(1, accountID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				eventChatroomVO = new EventChatroomVO();
				eventChatroomVO.setMessageID(rs.getInt("message_id"));
				eventChatroomVO.setEventID(rs.getInt("event_id"));
				eventChatroomVO.setAccountID(rs.getInt("account_id"));
				eventChatroomVO.setMessageTime(rs.getTimestamp("message_time"));
				eventChatroomVO.setChatText(rs.getString("chat_text"));
			}
		} catch (SQLException e) {
			throw new RuntimeException("資料庫發生錯誤" + e.getMessage());
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					throw new RuntimeException("資料庫發生錯誤" + e.getMessage());
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					throw new RuntimeException("資料庫發生錯誤" + e.getMessage());
				}
			}
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					throw new RuntimeException("資料庫發生錯誤" + e.getMessage());
				}
			}
		}
		return eventChatroomVO;
	}

	@Override
	public List<EventChatroomVO> getAll() {
		List<EventChatroomVO> list = new ArrayList<EventChatroomVO>();
		EventChatroomVO eventChatroomVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(url,user,password);
			pstmt = con.prepareStatement(Select_All_Stmt);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				eventChatroomVO = new EventChatroomVO();
				eventChatroomVO.setMessageID(rs.getInt("message_id"));
				eventChatroomVO.setEventID(rs.getInt("event_id"));
				eventChatroomVO.setAccountID(rs.getInt("account_id"));
				eventChatroomVO.setMessageTime(rs.getTimestamp("message_time"));
				eventChatroomVO.setChatText(rs.getString("chat_text"));
				list.add(eventChatroomVO);
			}
		} catch (SQLException e) {
			throw new RuntimeException("資料庫發生錯誤" + e.getMessage());
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					throw new RuntimeException("資料庫發生錯誤" + e.getMessage());
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					throw new RuntimeException("資料庫發生錯誤" + e.getMessage());
				}
			}
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					throw new RuntimeException("資料庫發生錯誤" + e.getMessage());
				}
			}
		}
		return list;
	}
	
	public static void main(String[] args) {
		EventChatroomVO eventChatroomVO = new EventChatroomVO();
		EventChatroomJDBCDAO eventChatroomJDBCDAO = new EventChatroomJDBCDAO();
		Date date = new Date();
		Timestamp messageTime = new Timestamp(date.getTime());
		//===================新增用=======================
//		eventChatroomVO.setEventID(300001);
//		eventChatroomVO.setAccountID(100001);
//		eventChatroomVO.setMessageTime(messageTime);
//		eventChatroomVO.setChatText("大家好，歡迎一起吃飯");
//		eventChatroomJDBCDAO.insert(eventChatroomVO);
		//===================更新用=======================
//		eventChatroomVO.setChatText("你們好，歡迎大家一起吃飯");
//		eventChatroomVO.setAccountID(100001);
//		eventChatroomJDBCDAO.update(eventChatroomVO);
		//===================查詢AccountID用=======================
//		eventChatroomVO = eventChatroomJDBCDAO.findAccountID(100001);
//		System.out.println(eventChatroomVO.getMessageID());
//		System.out.println(eventChatroomVO.getEventID());
//		System.out.println(eventChatroomVO.getAccountID());
//		System.out.println(eventChatroomVO.getMessageTime());
//		System.out.println(eventChatroomVO.getChatText());
		//===================查詢全部用=======================
//		List<EventChatroomVO> list = eventChatroomJDBCDAO.getAll();
//		for(EventChatroomVO eventChatroomVO_temp : list) {
//			System.out.println(eventChatroomVO_temp.getMessageID());
//			System.out.println(eventChatroomVO_temp.getEventID());
//			System.out.println(eventChatroomVO_temp.getAccountID());
//			System.out.println(eventChatroomVO_temp.getMessageTime());
//			System.out.println(eventChatroomVO_temp.getChatText());
//			System.out.println("================================");
//		}
	}
}
