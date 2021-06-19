package com.recipe.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/TestGetParameter")
public class TestGetParameter extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con = null;

	private String RECIPE_PIC = "SELECT recipe_pic_top FROM Recipe WHERE recipe_id = ?";
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();

		try {
			String recipeID = req.getParameter("pic");
			PreparedStatement pstmt = con.prepareStatement(RECIPE_PIC);

			pstmt.setInt(1, new Integer(recipeID));
			
			
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream("recipe_pic_top"));
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
