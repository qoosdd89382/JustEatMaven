package com.eventinfo.model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dish.model.DishJDBCDAO;
import com.dish.model.DishVO;
import com.dishandingredient.model.DishAndIngredientVO;
import com.eventmember.model.EventMemberJDBCDAO;
import com.eventmember.model.EventMemberVO;

public class EventInfoJDBCDAO implements EventInfoDAOinterface {
	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/JustEat?serverTimezone=Asia/Taipei";
	private static String userid = "DBAdmin";
	private static String password = "justeat";

	private static final String Insert_Stmt = "Insert into EventInfo (event_name,event_current_count,event_description,group_type,group_city,group_address,event_registartion_start_time,event_registartion_end_time,event_start_time,event_end_time,event_state,event_pic) Values(?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String Update_Stmt = "Update EventInfo set event_name=?,event_current_count=?,event_description=?,group_type=?,group_city=?,group_address=?,event_registartion_start_time=?,event_registartion_end_time=?,event_start_time=?,event_end_time=?,event_state=?,event_pic=? Where event_id = ?";
	private static final String Delete_Stmt = "Delete From EventInfo Where event_id = ?";
	private static final String Select_Key_Stmt = "Select * From EventInfo Where event_id = ? Order by event_id";
	private static final String Select_Name_Stmt = "Select * From EventInfo Where event_name Like ? ";
	private static final String Select_Start_Date_Stmt = "Select * From EventInfo Where event_start_time Like ? ";
	private static final String Select_All_Stmt = "Select * From EventInfo Order by event_id";

	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insert(EventInfoVO eventVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] autoGeneratedCol = { "event_id" };

		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Insert_Stmt, autoGeneratedCol);

			pstmt.setString(1, eventVO.getEventName());
			pstmt.setInt(2, eventVO.getEventCurrentCount());
			pstmt.setString(3, eventVO.getEventDescription());
			pstmt.setInt(4, eventVO.getGroupType());
			pstmt.setString(5, eventVO.getGroupCity());
			pstmt.setString(6, eventVO.getGroupAddress());
			pstmt.setTimestamp(7, eventVO.getEventRegistartionStartTime());
			pstmt.setTimestamp(8, eventVO.getEventRegistartionEndTime());
			pstmt.setTimestamp(9, eventVO.getEventStartTime());
			pstmt.setTimestamp(10, eventVO.getEventEndTime());
			pstmt.setInt(11, eventVO.getEventState());
			pstmt.setBytes(12, eventVO.getEventPic());

			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				Integer autoGeneratedkey = rs.getInt(1);
				System.out.println("新增的活動流水號為:" + autoGeneratedkey);
			} else {
				System.out.println("沒有新增任何東西");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void update(EventInfoVO eventVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Update_Stmt);

			pstmt.setString(1, eventVO.getEventName());
			pstmt.setInt(2, eventVO.getEventCurrentCount());
			pstmt.setString(3, eventVO.getEventDescription());
			pstmt.setInt(4, eventVO.getGroupType());
			pstmt.setString(5, eventVO.getGroupCity());
			pstmt.setString(6, eventVO.getGroupAddress());
			pstmt.setTimestamp(7, eventVO.getEventRegistartionStartTime());
			pstmt.setTimestamp(8, eventVO.getEventRegistartionEndTime());
			pstmt.setTimestamp(9, eventVO.getEventStartTime());
			pstmt.setTimestamp(10, eventVO.getEventEndTime());
			pstmt.setInt(11, eventVO.getEventState());
			pstmt.setBytes(12, eventVO.getEventPic());
			pstmt.setInt(13, eventVO.getEventID());

			pstmt.executeUpdate();
			System.out.println("更新成功");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void delete(Integer eventID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Delete_Stmt);
			pstmt.setInt(1, eventID);
			pstmt.executeUpdate();

			System.out.println("刪除成功");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public EventInfoVO findByPrimaryKey(Integer eventID) {

		EventInfoVO eventVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Select_Key_Stmt);

			pstmt.setInt(1, eventID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				eventVO = new EventInfoVO();
				eventVO.setEventID(rs.getInt("event_id"));
				eventVO.setEventName(rs.getString("event_name"));
				eventVO.setEventCurrentCount(rs.getInt("event_current_count"));
				eventVO.setEventDescription(rs.getString("event_description"));
				eventVO.setGroupType(rs.getInt("group_type"));
				eventVO.setGroupCity(rs.getString("group_city"));
				eventVO.setGroupAddress(rs.getString("group_address"));
				eventVO.setEventRegistartionStartTime(rs.getTimestamp("event_registartion_start_time"));
				eventVO.setEventRegistartionEndTime(rs.getTimestamp("event_registartion_end_time"));
				eventVO.setEventStartTime(rs.getTimestamp("event_start_time"));
				eventVO.setEventEndTime(rs.getTimestamp("event_end_time"));
				eventVO.setEventState(rs.getInt("event_state"));
				eventVO.setEventPic(rs.getBytes("event_pic"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return eventVO;
	}

	public List<EventInfoVO> findByName(String eventName) {
		List<EventInfoVO> list = new ArrayList<EventInfoVO>();
		EventInfoVO eventVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Select_Name_Stmt);

			pstmt.setString(1, "%"+eventName+"%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				eventVO = new EventInfoVO();
				eventVO.setEventID(rs.getInt("event_id"));
				eventVO.setEventName(rs.getString("event_name"));
				eventVO.setEventCurrentCount(rs.getInt("event_current_count"));
				eventVO.setEventDescription(rs.getString("event_description"));
				eventVO.setGroupType(rs.getInt("group_type"));
				eventVO.setGroupCity(rs.getString("group_city"));
				eventVO.setGroupAddress(rs.getString("group_address"));
				eventVO.setEventRegistartionStartTime(rs.getTimestamp("event_registartion_start_time"));
				eventVO.setEventRegistartionEndTime(rs.getTimestamp("event_registartion_end_time"));
				eventVO.setEventStartTime(rs.getTimestamp("event_start_time"));
				eventVO.setEventEndTime(rs.getTimestamp("event_end_time"));
				eventVO.setEventState(rs.getInt("event_state"));
				eventVO.setEventPic(rs.getBytes("event_pic"));
				list.add(eventVO);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public List<EventInfoVO> findByStartDate(String eventStartDate) {
		List<EventInfoVO> list = new ArrayList<EventInfoVO>();
		EventInfoVO eventVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Select_Start_Date_Stmt);

			pstmt.setString(1, eventStartDate+"%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				eventVO = new EventInfoVO();
				eventVO.setEventID(rs.getInt("event_id"));
				eventVO.setEventName(rs.getString("event_name"));
				eventVO.setEventCurrentCount(rs.getInt("event_current_count"));
				eventVO.setEventDescription(rs.getString("event_description"));
				eventVO.setGroupType(rs.getInt("group_type"));
				eventVO.setGroupCity(rs.getString("group_city"));
				eventVO.setGroupAddress(rs.getString("group_address"));
				eventVO.setEventRegistartionStartTime(rs.getTimestamp("event_registartion_start_time"));
				eventVO.setEventRegistartionEndTime(rs.getTimestamp("event_registartion_end_time"));
				eventVO.setEventStartTime(rs.getTimestamp("event_start_time"));
				eventVO.setEventEndTime(rs.getTimestamp("event_end_time"));
				eventVO.setEventState(rs.getInt("event_state"));
				eventVO.setEventPic(rs.getBytes("event_pic"));
				list.add(eventVO);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	
	public List<EventInfoVO> getAll() {
		List<EventInfoVO> list = new ArrayList<EventInfoVO>();
		EventInfoVO eventVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(Select_All_Stmt);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				eventVO = new EventInfoVO();
				eventVO.setEventID(rs.getInt("event_id"));
				eventVO.setEventName(rs.getString("event_name"));
				eventVO.setEventCurrentCount(rs.getInt("event_current_count"));
				eventVO.setEventDescription(rs.getString("event_description"));
				eventVO.setGroupType(rs.getInt("group_type"));
				eventVO.setGroupCity(rs.getString("group_city"));
				eventVO.setGroupAddress(rs.getString("group_address"));
				eventVO.setEventRegistartionStartTime(rs.getTimestamp("event_registartion_start_time"));
				eventVO.setEventRegistartionEndTime(rs.getTimestamp("event_registartion_end_time"));
				eventVO.setEventStartTime(rs.getTimestamp("event_start_time"));
				eventVO.setEventEndTime(rs.getTimestamp("event_end_time"));
				eventVO.setEventState(rs.getInt("event_state"));
				eventVO.setEventPic(rs.getBytes("event_pic"));
				list.add(eventVO);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	//連鎖新增菜色用
	@Override
	public void insertWithDish(EventInfoVO eventInfoVO, List<DishVO> dishList) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(url, userid, password);
			con.setAutoCommit(false);
			String[] autoGeneratedCol = { "event_id" };
			pstmt = con.prepareStatement(Insert_Stmt,autoGeneratedCol);
			
			pstmt.setString(1, eventInfoVO.getEventName());
			pstmt.setInt(2, eventInfoVO.getEventCurrentCount());
			pstmt.setString(3, eventInfoVO.getEventDescription());
			pstmt.setInt(4, eventInfoVO.getGroupType());
			pstmt.setString(5, eventInfoVO.getGroupCity());
			pstmt.setString(6, eventInfoVO.getGroupAddress());
			pstmt.setTimestamp(7, eventInfoVO.getEventRegistartionStartTime());
			pstmt.setTimestamp(8, eventInfoVO.getEventRegistartionEndTime());
			pstmt.setTimestamp(9, eventInfoVO.getEventStartTime());
			pstmt.setTimestamp(10, eventInfoVO.getEventEndTime());
			pstmt.setInt(11, eventInfoVO.getEventState());
			pstmt.setBytes(12, eventInfoVO.getEventPic());
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			Integer autoGeneratedKey = null;
			if(rs.next()) {
				autoGeneratedKey = rs.getInt(1);
				System.out.println("新增的活動流水號為:" +autoGeneratedKey);
			}else {
				System.out.println("未新增活動");
			}
			rs.close();
			DishJDBCDAO dao = new DishJDBCDAO();
			System.out.println("DishList.size() = "+ dishList.size());
			for(DishVO dishVO : dishList) {
				dishVO.setEventID(autoGeneratedKey);
				dao.insertByEventInfo(dishVO, con);
			}
			
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			if(con!=null) {
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
	
	//先新增活動 再
	//連鎖 新增菜色
	//再把資料丟到菜色處理
	//菜色新增完後
	//再丟到菜色食材
	//菜色食材新增後
	//資料都新增完後 回到該方法關閉各種東西
	@Override
	public void insertWithDishIngredient(EventInfoVO eventInfoVO, List<DishVO> dishList,
			List<DishAndIngredientVO> dishAndIngredientList) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(url, userid, password);
			//================================
			con.setAutoCommit(false);
			//================================
			String[] autoGeneratedCol = { "event_id" };
			//================================
			pstmt = con.prepareStatement(Insert_Stmt,autoGeneratedCol);
			pstmt.setString(1, eventInfoVO.getEventName());
			pstmt.setInt(2, eventInfoVO.getEventCurrentCount());
			pstmt.setString(3, eventInfoVO.getEventDescription());
			pstmt.setInt(4, eventInfoVO.getGroupType());
			pstmt.setString(5, eventInfoVO.getGroupCity());
			pstmt.setString(6, eventInfoVO.getGroupAddress());
			pstmt.setTimestamp(7, eventInfoVO.getEventRegistartionStartTime());
			pstmt.setTimestamp(8, eventInfoVO.getEventRegistartionEndTime());
			pstmt.setTimestamp(9, eventInfoVO.getEventStartTime());
			pstmt.setTimestamp(10, eventInfoVO.getEventEndTime());
			pstmt.setInt(11, eventInfoVO.getEventState());
			pstmt.setBytes(12, eventInfoVO.getEventPic());
			pstmt.executeUpdate();
			//================================
			rs = pstmt.getGeneratedKeys();
			Integer autoGeneratedKey = null; //其實可以只寫 Integer autoGeneratedKey = rs.getInt(1); 為了設置ID資料
			if(rs.next()) {
				autoGeneratedKey = rs.getInt(1);
				System.out.println("新增的活動流水號為:" +autoGeneratedKey);
			}else {
				System.out.println("未新增活動");
			}
			rs.close();
			//================================
			DishJDBCDAO dao = new DishJDBCDAO();
			for(DishVO dishVO : dishList) {
				dishVO.setEventID(autoGeneratedKey);
				dao.insertByEventInfoAndWithDishAndIngredient(dishVO, dishAndIngredientList, con); //丟到菜色的這個方法 新增菜色 再 處理菜色食材
			}
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			if(con!=null) {
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
	
	//新增活動資訊 同時新增"菜色食材" ,"菜色","活動成員"
	@Override
	public void insertWithDishIngredientMember(EventInfoVO eventInfoVO, List<DishVO> dishList,
			List<DishAndIngredientVO> dishAndIngredientList, List<EventMemberVO> eventMemberList) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(url, userid, password);
			//================================
			con.setAutoCommit(false);
			//================================
			String[] autoGeneratedCol = { "event_id" };
			//================================
			pstmt = con.prepareStatement(Insert_Stmt,autoGeneratedCol);
			pstmt.setString(1, eventInfoVO.getEventName());
			pstmt.setInt(2, eventInfoVO.getEventCurrentCount());
			pstmt.setString(3, eventInfoVO.getEventDescription());
			pstmt.setInt(4, eventInfoVO.getGroupType());
			pstmt.setString(5, eventInfoVO.getGroupCity());
			pstmt.setString(6, eventInfoVO.getGroupAddress());
			pstmt.setTimestamp(7, eventInfoVO.getEventRegistartionStartTime());
			pstmt.setTimestamp(8, eventInfoVO.getEventRegistartionEndTime());
			pstmt.setTimestamp(9, eventInfoVO.getEventStartTime());
			pstmt.setTimestamp(10, eventInfoVO.getEventEndTime());
			pstmt.setInt(11, eventInfoVO.getEventState());
			pstmt.setBytes(12, eventInfoVO.getEventPic());
			pstmt.executeUpdate();
			//================================
			rs = pstmt.getGeneratedKeys();
			Integer autoGeneratedKey = null; //其實可以只寫 Integer autoGeneratedKey = rs.getInt(1); 為了設置ID資料
			if(rs.next()) {
				autoGeneratedKey = rs.getInt(1);
				System.out.println("新增的活動流水號為:" +autoGeneratedKey);
			}else {
				System.out.println("未新增活動");
			}
			rs.close();
			//================================
			DishJDBCDAO dishDAO = new DishJDBCDAO();
			for(DishVO dishVO : dishList) {
				dishVO.setEventID(autoGeneratedKey);
				dishDAO.insertByEventInfoAndWithDishAndIngredient(dishVO, dishAndIngredientList, con); //丟到菜色的這個方法 新增菜色 再 處理菜色食材
			}
			
			EventMemberJDBCDAO memberDAO = new EventMemberJDBCDAO();
			for(EventMemberVO eventMemberVO : eventMemberList) {
				eventMemberVO.setEventID(autoGeneratedKey);
				memberDAO.insertByEventInfo(eventMemberVO, con);
			}
			
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			if(con!=null) {
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

	//==============測試用==================
	public static void main(String[] args) {
		String date = "2021-02-03 14:00";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
		Timestamp timestampdate = Timestamp.valueOf(localDateTime);

		EventInfoVO eventInfoVO = new EventInfoVO();
		EventInfoJDBCDAO eventInfoDAO = new EventInfoJDBCDAO();

//		File file = new File("C:\\Users\\Tibame_T14\\Downloads\\123.jpg");
//		InputStream in = null;
//		BufferedInputStream bis = null;
//		try {
//			in = new FileInputStream(file);
//			bis = new BufferedInputStream(in);
//			byte[] picBuffer = new byte[bis.available()];
//			bis.read(picBuffer);
//			eventInfoVO.setEventPic(picBuffer);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			if (bis != null) {
//				try {
//					bis.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			if (in != null) {
//				try {
//					in.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
		
//============================共通新增用===============================
		eventInfoVO.setEventName("天天吃");
		eventInfoVO.setEventCurrentCount(5);
		eventInfoVO.setGroupType(1);
		eventInfoVO.setGroupCity("新北市");
		eventInfoVO.setGroupAddress("板橋區中山路二段87巷");
		eventInfoVO.setEventRegistartionStartTime(timestampdate);
		eventInfoVO.setEventRegistartionEndTime(timestampdate);
		eventInfoVO.setEventStartTime(timestampdate);
		eventInfoVO.setEventEndTime(timestampdate);
		eventInfoVO.setEventState(1);
//		eventInfoDAO.insert(eventInfoVO);
//====================只連動新增菜色=======================	
//		List<DishVO> dishList = new ArrayList<DishVO>();
//		DishVO dishVO = new DishVO();
//		dishVO.setDishName("紅燒牛頭");
//		dishVO.setAccountID(100001); //暫時表示
//		dishList.add(dishVO);
//		eventInfoDAO.insertWithDish(eventInfoVO,dishList);
		
//===================連動菜色和菜色食材=====================
		List<DishVO> dishList = new ArrayList<DishVO>();
		DishVO dishVO = new DishVO();
		dishVO.setDishName("紅燒牛頭");
		dishVO.setAccountID(100001); //暫時表示
		dishList.add(dishVO);
		List<DishAndIngredientVO> dishAndIngredientList = new ArrayList<DishAndIngredientVO>();
		DishAndIngredientVO dishAndIngredientVO = new DishAndIngredientVO();
		dishAndIngredientVO.setIngredientID(220001);
		dishAndIngredientList.add(dishAndIngredientVO);
		
//		eventInfoDAO.insertWithDishIngredient(eventInfoVO, dishList, dishAndIngredientList);
		
		List<EventMemberVO> eventMemberList = new ArrayList<EventMemberVO>();
		EventMemberVO eventMemberVO = new EventMemberVO();
		eventMemberVO.setAccountID(100001);
		eventMemberVO.setParticipationState(1);
		eventMemberList.add(eventMemberVO);
		
		eventInfoDAO.insertWithDishIngredientMember(eventInfoVO, dishList, dishAndIngredientList, eventMemberList);
		
		// ==========================更新用================================
//		eventInfoVO.setEventName("每天吃");
//		eventInfoVO.setEventCurrentCount(8);
//		eventInfoVO.setGroupType(1);
//		eventInfoVO.setGroupCity("新北市");
//		eventInfoVO.setGroupAddress("板橋區中山路二段487巷");
//		eventInfoVO.setEventRegistartionStartTime(timestampdate);
//		eventInfoVO.setEventRegistartionEndTime(timestampdate);
//		eventInfoVO.setEventStartTime(timestampdate);
//		eventInfoVO.setEventEndTime(timestampdate);
//		eventInfoVO.setEventState(1);
//		eventInfoVO.setEventID(300001);
//		
//		eventInfoDAO.update(eventInfoVO);
		// ==========================刪除用=====================================
//		eventInfoDAO.delete(300002);
		// ==========================查詢ID=====================================

//		eventInfoVO = eventInfoDAO.findByPrimaryKey(300001);
//		System.out.println(eventInfoVO.getEventID()+",");
//		System.out.println(eventInfoVO.getEventName()+",");
//		System.out.println(eventInfoVO.getEventCurrentCount()+",");
//		System.out.println(eventInfoVO.getEventDescription()+",");
//		System.out.println(eventInfoVO.getGroupType()+",");
//		System.out.println(eventInfoVO.getGroupCity()+",");
//		System.out.println(eventInfoVO.getGroupAddress()+",");
//		System.out.println(eventInfoVO.getEventRegistartionStartTime()+",");
//		System.out.println(eventInfoVO.getEventRegistartionEndTime()+",");
//		System.out.println(eventInfoVO.getEventStartTime()+",");
//		System.out.println(eventInfoVO.getEventEndTime()+",");
//		System.out.println(eventInfoVO.getEventState()+",");
//		System.out.println(eventInfoVO.getEventPic()+",");
		
//		List<EventInfoVO> eventInfo_findName = eventInfoDAO.findByName("天");
//		for (EventInfoVO info : eventInfo_findName) {
//			System.out.println(info.getEventID() + ",");
//			System.out.println(info.getEventName() + ",");
//			System.out.println(info.getEventCurrentCount() + ",");
//			System.out.println(info.getEventDescription() + ",");
//			System.out.println(info.getGroupType() + ",");
//			System.out.println(info.getGroupCity() + ",");
//			System.out.println(info.getGroupAddress() + ",");
//			System.out.println(info.getEventRegistartionStartTime() + ",");
//			System.out.println(info.getEventRegistartionEndTime() + ",");
//			System.out.println(info.getEventStartTime() + ",");
//			System.out.println(info.getEventEndTime() + ",");
//			System.out.println(info.getEventState() + ",");
//			System.out.println(info.getEventPic() + ",");
//			System.out.println("==================================================");
//		}
		
		// =========================查詢全部======================================
//		List<EventInfoVO> eventInfo_list = eventInfoDAO.getAll();
//		for (EventInfoVO info : eventInfo_list) {
//			System.out.println(info.getEventID() + ",");
//			System.out.println(info.getEventName() + ",");
//			System.out.println(info.getEventCurrentCount() + ",");
//			System.out.println(info.getEventDescription() + ",");
//			System.out.println(info.getGroupType() + ",");
//			System.out.println(info.getGroupCity() + ",");
//			System.out.println(info.getGroupAddress() + ",");
//			System.out.println(info.getEventRegistartionStartTime() + ",");
//			System.out.println(info.getEventRegistartionEndTime() + ",");
//			System.out.println(info.getEventStartTime() + ",");
//			System.out.println(info.getEventEndTime() + ",");
//			System.out.println(info.getEventState() + ",");
//			System.out.println(info.getEventPic() + ",");
//			System.out.println("==================================================");
//		}

	}
}