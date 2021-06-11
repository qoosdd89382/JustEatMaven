package com.eventcuisinecategory.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dishandingredient.model.DishAndIngredientJDBCDAO;
import com.dishandingredient.model.DishAndIngredientVO;

public class EventcuisinecategoryJDBCDAO implements EventCuisineCategoryDAOInterface {

	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/JustEat?serverTimezone=Asia/Taipei";
	private static String user = "root";
	private static String password = "831204jeff";

	private static final String Insert_Stmt = "Insert into Eventcuisinecategory(event_id,cuisinecategory_id)Values(?,?)";
	//private static final String Update_Stmt = "Update Eventcuisinecategory Set event_id = ? Where event_id = ?";
	private static final String Delete_Stmt = "Delete from Eventcuisinecategory Where event_id = ?";
	//private static final String Select_Key_Stmt = "Select * From Event Where event_id=?";
	private static final String Select_All_Stmt = "Select * From Eventcuisinecategory";
	private static final String Select_All_By_Event_Id = "Select * From Eventcuisinecategory Where event_id = ?";
	private static final String Select_All_By_Cuisinecategory_Id = "Select * From Eventcuisinecategory  Where cuisinecategory_id = ?";
	
	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void insert(EventCuisineCategoryVO eventcuisinecategoryVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
	

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Insert_Stmt);

			pstmt.setInt(1, eventcuisinecategoryVO.getEventID());
			pstmt.setInt(2, eventcuisinecategoryVO.getCuisinecategoryID());

			pstmt.executeUpdate();

		
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

//	@Override
//	public void update(EventCuisineCategoryVO eventcuisinecategoryVO) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//			con = DriverManager.getConnection(url, user, password);
//			pstmt = con.prepareStatement(Update_Stmt);
//
//			pstmt.setInt(1, eventcuisinecategoryVO.getEventID());
//		
//			pstmt.executeUpdate();
//			System.out.println("更新成功");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//	}

	@Override
	public void delete(Integer eventID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Delete_Stmt);

			pstmt.setInt(1, eventID);

			pstmt.executeUpdate();
			System.out.println("刪除成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	@Override
	public List<EventCuisineCategoryVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EventCuisineCategoryVO eventcuisinecategoryVO = null;
		List<EventCuisineCategoryVO> list = new ArrayList<EventCuisineCategoryVO>();

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Select_All_Stmt);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				eventcuisinecategoryVO = new EventCuisineCategoryVO();
				eventcuisinecategoryVO.setEventID(rs.getInt("event_id"));
				eventcuisinecategoryVO.setCuisinecategoryID(rs.getInt("cuisinecategory_id"));
				list.add(eventcuisinecategoryVO);
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
			
		}
		return list;
	
	}
	@Override
	public  List<EventCuisineCategoryVO> getAllByEventID(Integer eventID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EventCuisineCategoryVO eventCuisineCategoryVO = null;
		List<EventCuisineCategoryVO> list = new ArrayList<EventCuisineCategoryVO>();

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Select_All_By_Event_Id);
			pstmt.setInt(1, eventID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				eventCuisineCategoryVO = new EventCuisineCategoryVO();
				eventCuisineCategoryVO.setEventID(rs.getInt("event_id"));
				eventCuisineCategoryVO.setCuisinecategoryID(rs.getInt("cuisinecategory_id"));
				list.add(eventCuisineCategoryVO);
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
			
		}
		return list;
	}
	
	@Override
	 public List<EventCuisineCategoryVO> getALLByCuisineCategoryID(Integer cuisinecategoryID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EventCuisineCategoryVO eventCuisineCategoryVO = null;
		List<EventCuisineCategoryVO> list = new ArrayList<EventCuisineCategoryVO>();

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Select_All_By_Cuisinecategory_Id);
			pstmt.setInt(1, cuisinecategoryID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				eventCuisineCategoryVO = new EventCuisineCategoryVO();
				eventCuisineCategoryVO.setEventID(rs.getInt("event_id"));
				eventCuisineCategoryVO.setCuisinecategoryID(rs.getInt("cuisinecategory_id"));
				list.add(eventCuisineCategoryVO);
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
			
		}
		return list;
	}


	public static void main(String[] args) {
		EventCuisineCategoryVO eventcuisinecategoryVO = new EventCuisineCategoryVO();
		EventcuisinecategoryJDBCDAO eventcuisinecategoryJDBCDAO = new EventcuisinecategoryJDBCDAO();

		// ===================新增用================(完成)
//		eventcuisinecategoryVO.setEventID(300001);
//		eventcuisinecategoryVO.setCuisinecategoryID(250001); 
//		eventcuisinecategoryJDBCDAO.insert(eventcuisinecategoryVO);
		// ====================刪除用================(完成)
//		eventcuisinecategoryJDBCDAO.delete(300001);
		
//		// ====================查詢全部==============(完成)
//		List<EventCuisineCategoryVO> list1 = eventcuisinecategoryJDBCDAO.getAll();
//		for (EventCuisineCategoryVO EventCuisineCategory : list1) {
//		
//		System.out.println(EventCuisineCategory.getEventID());
//		System.out.println(EventCuisineCategory.getCuisinecategoryID());
//		
//		System.out.println("=======================");
////		
//			
		// ====================查詢ByEvent==============(完成)
//		List<EventCuisineCategoryVO> list = eventcuisinecategoryJDBCDAO.getAllByEventID(300001);
//		for (EventCuisineCategoryVO EventCuisineCategory : list) {
//		
//			System.out.println(EventCuisineCategory.getEventID());
//			System.out.println(EventCuisineCategory.getCuisinecategoryID());
////			
//		
//		System.out.println("=======================");
		}		
		// ====================查詢ByCuisineCategory==============(完成)
//		List<EventCuisineCategoryVO> list = eventcuisinecategoryJDBCDAO.getALLByCuisineCategoryID(250001);
//		for (EventCuisineCategoryVO EventCuisineCategory : list) {
//		
//			System.out.println(EventCuisineCategory.getEventID());
//			System.out.println(EventCuisineCategory.getCuisinecategoryID());
////			
//		
//		System.out.println("=======================");
//	}		
		
		
		
		
		
		
				
//	}
}
