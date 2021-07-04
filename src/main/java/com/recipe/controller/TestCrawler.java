package com.recipe.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TestCrawler")
public class TestCrawler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/JustEat?serverTimezone=Asia/Taipei"
			+ "&rewriteBatchedStatements=true";
	private static String userid = "DBAdmin";
	private static String passwd = "justeat";
	
	private String IN = 
			"INSERT INTO `Test_Table`.`new_table` (`type_name`) VALUES (?)";
       

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = res.getWriter();
		
//		$(".list-unstyled").each(function(index, ele){
//		    $($(ele).children()).each(function(i,e) {
//		        $.ajax({
//		            'type': 'post',
//		            'url': 'http://localhost:8081/justeat-maven/TestCrawler',
//		            'data': {
//		                'name': $(e).find("a").text()
//		             },
//		             'success': function(data){
//		                console.log(data);
//		            }
//		        });
//		    });
//		});
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(IN);
			pstmt.setString(1, req.getParameter("name"));
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
//		req.getParameter("name");
		
		out.print("success");
	}

}
