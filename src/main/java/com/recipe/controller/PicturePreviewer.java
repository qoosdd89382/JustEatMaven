package com.recipe.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/PicturePreviewer")
public class PicturePreviewer extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();
		
		byte[] picBuffer = (byte[]) req.getSession().getAttribute("picBuffer");
		InputStream in = null;
		
		try {
			out.write(picBuffer);
			out.flush();
			out.close();
		} catch (Exception e) {
			in = getServletContext().getResourceAsStream("/image/null.png");
			byte[] b = new byte[in.available()];
			in.read(b);
			out.write(b);
			out.flush();
			out.close();
			if (in != null) {
				in.close();
			}
		} finally {
			out.close();
			if (in != null) {
				in.close();
			}
		}
	}

	public void init() throws ServletException {
	}

	public void destroy() {
	}
}
