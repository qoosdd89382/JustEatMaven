package com.unit.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UnitJDBCDAO implements UnitDAOInterface {
	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/JustEat?serverTimezone=Asia/Taipei" + "&rewriteBatchedStatements=true";
	private static String userid = "DBAdmin";
	private static String passwd = "justeat";

	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}
	private static final String INSERT = "INSERT INTO Unit(unit_name) VALUES(?);";
	private static final String UPDATE = "UPDATE Unit SET unit_name = ? WHERE unit_id = ?;";
	private static final String DELETE = "DELETE FROM Unit WHERE unit_id = ?;";
	// 順序寫死
	private static final String SELECT_ALL = "SELECT * FROM Unit ORDER BY unit_id DESC;";
	private static final String SELECT_ONE_BY_ID = "SELECT * FROM Unit WHERE unit_id = ? ;"; // SELECT * 擇不能用欄位名set ?
	private static final String SELECT_ONE_BY_NAME = "SELECT * FROM Unit WHERE unit_name = ?";
	

	@Override
	public int insert(UnitVO unit) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String[] autoGeneratedCol = { "unit_id" };
		int insertRow = 0;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT, autoGeneratedCol);
			
			pstmt.setString(1, unit.getUnitName());
			insertRow = pstmt.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
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
		
		return insertRow;
	}

	@Override
	public int update(UnitVO unit) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int updateRow = 0;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, unit.getUnitName());
			pstmt.setInt(2, unit.getUnitID());
			
			updateRow = pstmt.executeUpdate();					
		} catch (SQLException e) {
			e.printStackTrace();
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
		
		return updateRow;
	}

	@Override
	public List<UnitVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<UnitVO> allUnit = new ArrayList<UnitVO>();
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ALL);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				UnitVO unit = new UnitVO();
				unit.setUnitID(rs.getInt("unit_id"));
				unit.setUnitName(rs.getString("unit_name"));
				allUnit.add(unit);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
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
		return allUnit;
	}

	@Override
	public UnitVO getOne(int unitID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs  = null;
		UnitVO unit = null;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ONE_BY_ID);
			
			pstmt.setInt(1, unitID);	
			rs = pstmt.executeQuery();

			if (rs.next()) {
				unit = new UnitVO();
				unit.setUnitID(rs.getInt("unit_id"));
				unit.setUnitName(rs.getString("unit_name"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
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
		return unit;
	}

	@Override
	public int delete(int unitID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int deleteRow = 0;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, unitID);
			
			deleteRow = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
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
		return deleteRow;
	}

	@Override
	public int delete(int[] unitIDs) {

		Connection con = null;
		PreparedStatement pstmt = null;
		int deleteRows = 0;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			for (int unitID : unitIDs) {
				pstmt.setInt(1, unitID);
				pstmt.addBatch();
			}
			
			int[] tempRows = pstmt.executeBatch();
			for (int ele : tempRows) {
				deleteRows++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
		return deleteRows;
	}


	@Override
	public boolean isExist(String unitName) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean ExistStatus = false;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_ONE_BY_NAME);

			pstmt.setString(1, unitName);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				ExistStatus = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			if (rs != null)
				try {
					rs.close();
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
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
		return ExistStatus;
	}
	public static void main(String args[]) {
		UnitVO unit = new UnitVO();
		UnitJDBCDAO dao = new UnitJDBCDAO();
		
		// 測試 insert 成功
		unit.setUnitName("台斤");
		if (dao.insert(unit) == 1) {
			System.out.println("新增成功!\n=====");
		}
		
//		// 測試 get one 成功
//		unit = dao.getOne(230001);
//		System.out.println(unit.getUnitID() + "\t" + unit.getUnitName() + "\n=========");
//
//		// 測試 update 成功
//		unit.setUnitName("公克");
//		int updateSuccess = dao.update(unit);
//		System.out.println("成功更新" + updateSuccess + "筆資料");
		
		// 測試 get all
		List<UnitVO> units = dao.getAll();
		for (UnitVO ele : units) {
			System.out.println(ele.getUnitID() + "\t" + ele.getUnitName() + "\n================");
		}
		
	}

}