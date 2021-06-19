package com.accountinfo.controller;

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

//web.xml註冊標籤
@WebServlet("/Account/Pic/*")
public class PictureShower extends HttpServlet {
	Connection con;
	//這裡放SQL指令
	private String Account_Pic = "Select account_pic From JustEat.AccountInfo where account_id=?";
	
	private String Account_IDcardFront_Pic = "Select account_idcard_front From JustEat.AccountInfo where account_id=?";
	private String Account_IDcardBack_Pic = "Select account_idcard_back From JustEat.AccountInfo where account_id=?";
	
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		doPost(req,res);
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		//設定Servlet屬性
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();
		
		try {
			//取得路徑把前面的"/"去掉
			String pathInfo = req.getPathInfo().trim().substring(1);
			PreparedStatement pstmt = null;
			
			//資料庫的欄位名稱
			String colName = null;
			//根據不同的req做不同的IF判斷
			//=收到會員註冊的請求(account_pic)
			if(pathInfo.startsWith("Pic/")) {
				pstmt = con.prepareStatement(Account_Pic);
				String path = pathInfo.substring(pathInfo.lastIndexOf("/")+1);
				int pathInt = Integer.parseInt(path);
				pstmt.setInt(1, pathInt);
				colName = "account_pic";
			}else if(pathInfo.startsWith("Front/")){
				pstmt = con.prepareStatement(Account_IDcardFront_Pic);
				String path = pathInfo.substring(pathInfo.lastIndexOf("/")+1);
				int pathInt = Integer.parseInt(path);
				pstmt.setInt(1, pathInt);
				colName = "account_idcard_front";
			}else if(pathInfo.startsWith("Back/")){
				pstmt = con.prepareStatement(Account_IDcardBack_Pic);
				String path = pathInfo.substring(pathInfo.lastIndexOf("/")+1);
				int pathInt = Integer.parseInt(path);
				pstmt.setInt(1, pathInt);
				colName = "account_idcard_back";
			}
			

			ResultSet rs = pstmt.executeQuery();

			//如果有圖片的話
			if (rs.next()) {
				BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream(colName));
				byte[] buf = new byte[4 * 1024]; // 4K buffer
				int len;
				while ((len = in.read(buf)) != -1) {
					out.write(buf, 0, len);
				}
				in.close();
			//沒有這張圖片
			} else {
				InputStream in = getServletContext().getResourceAsStream("/image/notfound.png");
				byte[] b = new byte[in.available()];
				in.read(b);
				out.write(b);
				in.close();
			}
			//關閉資源
			rs.close();
			pstmt.close();
			
		//讀取圖片發生非預期例外
		}catch(Exception e) {
			//取得這個專案裡面的資源圖片，做輸出關閉
			InputStream in = getServletContext().getResourceAsStream("/image/null.png");
			byte[] b = new byte[in.available()];
			in.read(b);
			out.write(b);
			in.close();
		}
	}
	
	//初始化
	public void init() throws ServletException{
		try {
			Context ctx = new javax.naming.InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/JustEat");
			con = ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//卸載
	public void destroy() {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		
	}
}
