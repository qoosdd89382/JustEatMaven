package com.dish.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.accountinfo.model.AccountInfoVO;
import com.dishandingredient.model.DishAndIngredientJDBCDAO;
import com.dishandingredient.model.DishAndIngredientVO;
import com.eventinfo.model.EventInfoJDBCDAO;
import com.eventinfo.model.EventInfoVO;

public class DishJDBCDAO implements DishDAOinterface {

	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/JustEat?serverTimezone=Asia/Taipei";
	private static String user = "DBAdmin";
	private static String password = "justeat";

	private static final String Insert_Stmt = "Insert into Dish(dish_name,account_id,event_id)Values(?,(Select account_id From AccountInfo Where account_id=?),(Select event_id From EventInfo Where event_id = ?))";
	private static final String Update_Stmt = "Update Dish Set dish_name = ? Where dish_id = ?";
	private static final String Delete_Stmt = "Delete from Dish Where dish_id = ?";
	private static final String Select_Key_Stmt = "Select * From Dish Where dish_id=?";
	private static final String Select_Event_Stmt = "Select * From Dish Where event_id=?";
	private static final String Select_All_Stmt = "Select * From Dish";

	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void insert(DishVO dishVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] autoGeneratedCol = { "dish_id" };

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Insert_Stmt, autoGeneratedCol);

			pstmt.setString(1, dishVO.getDishName());
			pstmt.setInt(2, dishVO.getAccountID());
			pstmt.setInt(3, dishVO.getEventID());

			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();
			while (rs.next()) {
				Integer autoGeneratedKey = rs.getInt(1);
				System.out.println("新增的菜色流水號為:" + autoGeneratedKey);
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
	}

	@Override
	public void update(DishVO dishVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Update_Stmt);

			pstmt.setString(1, dishVO.getDishName());
			pstmt.setInt(2, dishVO.getDishID());

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
	public void delete(Integer dishID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Delete_Stmt);

			pstmt.setInt(1, dishID);

			pstmt.executeUpdate();
			System.out.println("刪除成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public DishVO findByPrimaryKey(Integer dishID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DishVO dishVO = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Select_Key_Stmt);

			pstmt.setInt(1, dishID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				dishVO = new DishVO();
				dishVO.setDishID(rs.getInt("dish_id"));
				dishVO.setDishName(rs.getString("dish_name"));
				dishVO.setAccountID(rs.getInt("account_id"));
				dishVO.setEventID(rs.getInt("event_id"));
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
		return dishVO;
	}

	@Override
	public List<DishVO> findByEventID(Integer eventID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DishVO dishVO = null;
		List<DishVO> list = new ArrayList<DishVO>();

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Select_Event_Stmt);
			pstmt.setInt(1, eventID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				dishVO = new DishVO();
				dishVO.setDishID(rs.getInt("dish_id"));
				dishVO.setDishName(rs.getString("dish_name"));
				dishVO.setAccountID(rs.getInt("account_id"));
				dishVO.setEventID(rs.getInt("event_id"));
				list.add(dishVO);
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
	public List<DishVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DishVO dishVO = null;
		List<DishVO> list = new ArrayList<DishVO>();

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Select_All_Stmt);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				dishVO = new DishVO();
				dishVO.setDishID(rs.getInt("dish_id"));
				dishVO.setDishName(rs.getString("dish_name"));
				dishVO.setAccountID(rs.getInt("account_id"));
				dishVO.setEventID(rs.getInt("event_id"));
				list.add(dishVO);
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
	
	//活動資訊 連鎖 菜色 用
	@Override
	public void insertByEventInfo(DishVO dishVO, Connection con) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;

		String[] autoGeneratedCol = { "dish_id" };

		try {
			pstmt = con.prepareStatement(Insert_Stmt, autoGeneratedCol);

			pstmt.setString(1, dishVO.getDishName());
			pstmt.setInt(2, dishVO.getAccountID());
			pstmt.setInt(3, dishVO.getEventID());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			if(con !=null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} finally {
			if(pstmt !=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	//菜色 連鎖 菜色食材 用
	@Override
	public void insertWithDishAndIngredient(DishVO dishVO, List<DishAndIngredientVO> dishAndIngredientList) {
		Connection con =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(url,user,password);
			con.setAutoCommit(false);
			String[] autoGeneratedCols = {"dish_id"};
			pstmt = con.prepareStatement(Insert_Stmt,autoGeneratedCols);
			pstmt.setString(1, dishVO.getDishName());
			pstmt.setInt(2, dishVO.getAccountID());
			pstmt.setInt(3, dishVO.getEventID());
			
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			Integer autoGeneratedKey = null;
			if(rs.next()) {
				autoGeneratedKey = rs.getInt(1);
				System.out.println("新增的菜色流水號為 :" + autoGeneratedKey);
			}
			rs.close();
			
			DishAndIngredientJDBCDAO dao = new DishAndIngredientJDBCDAO();
			for(DishAndIngredientVO vo:dishAndIngredientList) {
				vo.setDishID(autoGeneratedKey);
				dao.insertbyDish(vo, con);
			}
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block

			if(con!=null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} finally {
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
	
	// 活動資訊 連鎖 菜色
	// 菜色 再連鎖 菜色食材
	@Override
	public void insertByEventInfoAndWithDishAndIngredient(DishVO dishVO,
			List<DishAndIngredientVO> dishAndIngredientList, Connection con) {	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] autoGeneratedCol = { "dish_id" };
		//================================
		try {
			pstmt = con.prepareStatement(Insert_Stmt, autoGeneratedCol);
			pstmt.setString(1, dishVO.getDishName());
			pstmt.setInt(2, dishVO.getAccountID());
			pstmt.setInt(3, dishVO.getEventID());
			pstmt.executeUpdate();
			//================================
			rs = pstmt.getGeneratedKeys();
			Integer autoGeneratedKey = null;
			if(rs.next()) {
				autoGeneratedKey = rs.getInt(1);
				System.out.println("新增的菜色流水號為:"+autoGeneratedKey);
			} else {
				System.out.println("沒有新增菜色流水號");
			}
			rs.close();
			//================================
			DishAndIngredientJDBCDAO dishAndIngredientJDBCDAO = new DishAndIngredientJDBCDAO();
			for(DishAndIngredientVO dishAndIngredientVO: dishAndIngredientList) {
				dishAndIngredientVO.setDishID(autoGeneratedKey);
				dishAndIngredientJDBCDAO.insertbyDish(dishAndIngredientVO, con); //丟到菜色食材的方法 新增菜色食材
			}
			
		} catch (SQLException e) {
			if(con !=null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} finally {
			if(pstmt !=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	//==============測試用==================
	public static void main(String[] args) {
		DishVO dishVO = new DishVO();
		DishJDBCDAO dishJDBCDAO = new DishJDBCDAO();
		// ===================新增用================
//		dishVO.setDishName("紅燒牛頭");
//		dishVO.setAccountID(100001); //暫時表示
//		dishVO.setEventID(300001); //暫時表示
//		DishAndIngredientVO dishAndIngredientVO = new DishAndIngredientVO();
//		List<DishAndIngredientVO> dishAndIngredientList = new ArrayList<DishAndIngredientVO>();
//		dishAndIngredientVO.setIngredientID(220001);
//		dishAndIngredientList.add(dishAndIngredientVO);
//		dishJDBCDAO.insertWithDishAndIngredient(dishVO, dishAndIngredientList);
//		dishJDBCDAO.insert(dishVO);
		// ===================更新用================
//		dishVO.setDishName("清蒸牛頭");
//		dishVO.setDishID(310001);
//		dishJDBCDAO.update(dishVO);
		// ====================刪除用================
//		dishJDBCDAO.delete(310002);
		// ====================查詢ID===============
//		dishVO = dishJDBCDAO.findByPrimaryKey(310001);
//		System.out.println(dishVO.getDishID());
//		System.out.println(dishVO.getDishName());
//		System.out.println(dishVO.getAccountID());
//		System.out.println(dishVO.getEventID());
		// ====================查詢全部==============
//		List<DishVO> list = dishJDBCDAO.getAll();
//		for (DishVO dish : list) {
//			System.out.println(dish.getDishID());
//			System.out.println(dish.getDishName());
//			System.out.println(dish.getAccountID());
//			System.out.println(dish.getEventID());
//			System.out.println("=======================");
//		}
		
		List<DishVO> list = dishJDBCDAO.findByEventID(300001);
		for (DishVO dish : list) {
			System.out.println(dish.getDishID());
			System.out.println(dish.getDishName());
			System.out.println(dish.getAccountID());
			System.out.println(dish.getEventID());
			System.out.println("=======================");
		}
	}


}
