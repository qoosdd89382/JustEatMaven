package com.recipe.controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.recipe.model.RecipeJDBCDAO;
import com.recipe.model.RecipeVO;

@WebServlet("/Recipe/Pic/*")
public class PicturesViewer extends HttpServlet {
	Connection con;
	private String RECIPE_PIC = "SELECT recipe_pic_top FROM Recipe WHERE recipe_id = ?";
	private String RECIPE_STEP_PICS = "SELECT recipe_step_pic FROM RecipeStep WHERE recipe_step_id = ?";
       
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();

		try {
			String pathInfo = req.getPathInfo().trim().substring(1);
			PreparedStatement pstmt = null;

			String colName = null;
			if (pathInfo.startsWith("Top/")) {
				pstmt = con.prepareStatement(RECIPE_PIC);
				String path = pathInfo.substring(pathInfo.lastIndexOf("/")+1);
				int pathInt = Integer.parseInt(path);
				pstmt.setInt(1, pathInt);
				colName = "recipe_pic_top";
			} else if (pathInfo.startsWith("Step/")) {
				pstmt = con.prepareStatement(RECIPE_STEP_PICS);
				String path = pathInfo.substring(pathInfo.lastIndexOf("/")+1);
				int pathInt = Integer.parseInt(path);
//				int paraValue = Integer.parseInt(req.getParameter("step").trim());
				pstmt.setInt(1, pathInt);
				colName = "recipe_step_pic";
			}
			
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream(colName));
				byte[] buf = new byte[4 * 1024]; // 4K buffer
				int len;
				while ((len = in.read(buf)) != -1) {
					out.write(buf, 0, len);
				}
				in.close();
			} else {
				InputStream in = getServletContext().getResourceAsStream("/image/notfound.png");
				byte[] b = new byte[in.available()];
				in.read(b);
				out.write(b);
				in.close();
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			InputStream in = getServletContext().getResourceAsStream("/image/null.png");
			byte[] b = new byte[in.available()];
			in.read(b);
			out.write(b);
			in.close();
		}
	}
	

	public void init() throws ServletException {
		try {
			Context ctx = new javax.naming.InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/JustEat"); // 用這個註冊名稱可找到同一個連線池
			con = ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

}
