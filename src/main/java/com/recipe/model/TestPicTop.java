package com.recipe.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TestPicTop")
public class TestPicTop extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TestPicTop() {
		super();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();
		InputStream in = null;

		RecipeJDBCDAO dao = new RecipeJDBCDAO();
		RecipeVO recipe = null;
		// 網址傳參數進來測，如：http://localhost:8081/JustEat/TestPicTop?test=200007
		recipe = dao.getOneByPK(Integer.parseInt(req.getParameter("test")));

		try {
			if (recipe == null) {
				// 參數輸錯，導致recipe無資料
				in = new FileInputStream("C:\\Users\\Tibame_T14\\Downloads\\none.jpg");
				// ServletContext 目前沒有資源可以 get，所以先用本地端檔案測
				byte[] buf = new byte[in.available()];
				in.read(buf);
				out.write(buf);
			} else if (recipe.getRecipePicTop() == null) {
				// 有這筆食譜，但沒有上傳圖片
				in = new FileInputStream("C:\\Users\\Tibame_T14\\Downloads\\null.jpg");
				// ServletContext 目前沒有資源可以get，所以先用本地端檔案測
				byte[] buf = new byte[in.available()];
				in.read(buf);
				out.write(buf);
			} else if (recipe.getRecipePicTop().length != 0) {
				// 一切正常，顯示圖片至瀏覽器
				out.write(recipe.getRecipePicTop());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
