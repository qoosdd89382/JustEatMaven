package com.dishandingredient.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dish.model.DishJDBCDAO;
import com.dish.model.DishVO;
import com.ingredienttag.model.IngredientTagVO;

public class DishAndIngredientJDBCDAO implements DishAndIngredientDAOinterface {

	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/JustEat?serverTimezone=Asia/Taipei";
	private static String user = "DBAdmin";
	private static String password = "justeat";
	
	private static final String Insert_Stmt = "Insert into DishAndIngredient(dish_id,ingredient_id)Values(?,?)";
  //private static final String Update_Stmt = "Update DishAndIngredient Set dish_id = ? Where ingredient_id = ?";
	private static final String Delete_Stmt = "Delete from DishAndIngredient Where dish_id = ? ";
  //private static final String Select_Key_Stmt = "Select * From DishAndIngredient Where dish_id=?";
	private static final String Select_All_Stmt = "Select * From DishAndIngredient";
	private static final String Select_All_By_Dish_Id = "Select * From DishAndIngredient Where dish_id = ? ";
	private static final String Select_ALL_By_Ingredient_Id = "Select * From DishAndIngredient Where ingredient_id = ?";
	
	
	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void insert(DishAndIngredientVO dishAndIngredientVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Insert_Stmt);

			pstmt.setInt(1, dishAndIngredientVO.getDishID());
			pstmt.setInt(2, dishAndIngredientVO.getIngredientID());
			// pstmt.setInt(3, dishAndIngredientVO.getIngredientID());

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
//	public void update(DishAndIngredientVO dishAndIngredientVO) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//			con = DriverManager.getConnection(url, user, password);
//			pstmt = con.prepareStatement(Update_Stmt);
//
//			pstmt.setInt(1, dishAndIngredientVO.getDishID());
//			pstmt.setInt(2, dishAndIngredientVO.getIngredientID());
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

//	@Override
//	 public DishAndIngredientVO findByPrimaryKey(Integer dishID) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		DishAndIngredientVO dishAndIngredientVO = null;
//
//		try {
//			con = DriverManager.getConnection(url, user, password);
//			pstmt = con.prepareStatement(Select_Key_Stmt);
//
//			pstmt.setInt(1, dishID);
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				dishAndIngredientVO = new DishAndIngredientVO();
//				dishAndIngredientVO.setDishID(rs.getInt("dish_id"));
//				dishAndIngredientVO.setIngredientID(rs.getInt("ingredient_id"));
//				
//			}
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
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
//		return dishAndIngredientVO;
//	}
//
	@Override
	public List<DishAndIngredientVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DishAndIngredientVO dishAndIngredientVO = null;
		List<DishAndIngredientVO> list = new ArrayList<DishAndIngredientVO>();

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Select_All_Stmt);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				dishAndIngredientVO = new DishAndIngredientVO();
				dishAndIngredientVO.setDishID(rs.getInt("dish_id"));
				dishAndIngredientVO.setIngredientID(rs.getInt("ingredient_id"));

				list.add(dishAndIngredientVO);

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
	public List<DishAndIngredientVO> getAllByDish(Integer dishID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DishAndIngredientVO dishAndIngredientVO = null;
		List<DishAndIngredientVO> list = new ArrayList<DishAndIngredientVO>();

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Select_All_By_Dish_Id);
			pstmt.setInt(1, dishID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				dishAndIngredientVO = new DishAndIngredientVO();
				dishAndIngredientVO.setDishID(rs.getInt("dish_id"));
				dishAndIngredientVO.setIngredientID(rs.getInt("ingredient_id"));
				list.add(dishAndIngredientVO);
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
	public List<DishAndIngredientVO> getALLByIngredientID(Integer ingredienID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DishAndIngredientVO dishAndIngredientVO = null;
		List<DishAndIngredientVO> list = new ArrayList<DishAndIngredientVO>();

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(Select_ALL_By_Ingredient_Id);
			pstmt.setInt(1, ingredienID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				dishAndIngredientVO = new DishAndIngredientVO();
				dishAndIngredientVO.setDishID(rs.getInt("dish_id"));
				dishAndIngredientVO.setIngredientID(rs.getInt("ingredient_id"));
				list.add(dishAndIngredientVO);
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
//	@Override
//	public List<DishAndIngredientVO> getAll() {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		DishAndIngredientVO dishAndIngredientVO = null;
//		List<DishAndIngredientVO> list = new ArrayList<DishAndIngredientVO>();
//
//		try {
//			con = DriverManager.getConnection(url, user, password);
//			pstmt = con.prepareStatement(Select_All_Stmt);
//
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				dishAndIngredientVO = new DishAndIngredientVO();
//				dishAndIngredientVO.setDishID(rs.getInt("dish_id"));
//				dishAndIngredientVO.setIngredientID(rs.getInt("ingredient_id"));
//				list.add(dishAndIngredientVO);
//			}
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			
//		}
//		return list;
//	}

	@Override
	public void insertbyDish(DishAndIngredientVO dishAndIngredientVO, Connection con) {
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(Insert_Stmt);
			pstmt.setInt(1, dishAndIngredientVO.getDishID());
			pstmt.setInt(2, dishAndIngredientVO.getIngredientID());

			pstmt.executeUpdate();
			System.out.println("新增菜色食材成功");
		} catch (SQLException e) {
			e.printStackTrace();
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
		}
	}
	
//	@Override
//	public void insertbyMultiDish(List<DishAndIngredientVO> dishAndIngredientVOList, Connection con) {
//		PreparedStatement pstmt = null;
//
//		try {
//			pstmt = con.prepareStatement(Insert_Stmt);
//			pstmt.setInt(1, dishAndIngredientVO.getDishID());
//			pstmt.setInt(2, dishAndIngredientVO.getIngredientID());
//
//			pstmt.executeUpdate();
//			System.out.println("新增菜色食材成功");
//		} catch (SQLException e) {
//			e.printStackTrace();
//			if (con != null) {
//				try {
//					con.rollback();
//				} catch (SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			}
//		} finally {
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//	}
	
	public static void main(String[] args) {
		DishAndIngredientVO dishAndIngredientVO = new DishAndIngredientVO();
		DishAndIngredientJDBCDAO dishAndIngredientJDBCDAO = new DishAndIngredientJDBCDAO();
		List<DishAndIngredientVO> list;
		// ===================新增用================(完成)
//		dishAndIngredientVO.setDishID(310006);
//		dishAndIngredientVO.setIngredientID(220001); 
//		dishAndIngredientJDBCDAO.insert(dishAndIngredientVO);
////		
//				
		// ====================刪除用================(完成)
//		dishAndIngredientJDBCDAO.delete(310006);
		
//		// ====================查詢全部==============(完成)
//		List<DishAndIngredientVO> list1 = dishAndIngredientJDBCDAO.getAll();
//		for (DishAndIngredientVO DishAndIngredient : list1) {
//		
//		System.out.println(DishAndIngredient.getDishID());
//		System.out.println(DishAndIngredient.getIngredientID());
//	
//		System.out.println("=======================");
////		
//		
//		

// 		==========================更新用================================

		// ====================刪除用================
		// DishAndIngredientDAO.delete(310006);

//	}


//		}
		// ====================查詢ByDish==============(完成)
//		List<DishAndIngredientVO> list1 = dishAndIngredientJDBCDAO.getAllByDish(310006);
//		for (DishAndIngredientVO DishAndIngredient : list1) {
//		
//			System.out.println(DishAndIngredient.getDishID());
//			System.out.println(DishAndIngredient.getIngredientID());
////			
//		
//		System.out.println("=======================");
//		}
//		// ====================查詢ByIngredient==============(完成)
//		List<DishAndIngredientVO> list2 = dishAndIngredientJDBCDAO.getALLByIngredientID(220001);
//		for (DishAndIngredientVO DishAndIngredient : list2) {
//		
//			System.out.println(DishAndIngredient.getDishID());
//			System.out.println(DishAndIngredient.getIngredientID());
//			
//		
//		System.out.println("=======================");
//		}
   }
}
