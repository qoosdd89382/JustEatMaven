package com.admininfo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.admininfo.model.AdminInfoVO;
import com.recipe.model.RecipeService;

@WebServlet("/Dashboard/admin.do")
public class AdminInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");

		if ("insert".equals(action)) {
			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String adminMail = req.getParameter("adminMail");
				String adminMailReg = "^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$";
				if (adminMail.trim() == null || adminMail.trim().length() == 0) {
					errorMsgs.put("adminEmialErr", "e-mail請勿空白");
				} else if (!adminMail.trim().matches(adminMailReg)) {
					errorMsgs.put("adminEmialErr", "信箱格式錯誤");
				}

				String adminPassword = req.getParameter("adminPassword");
				String adminPasswordReg = "^[a-zA-Z0-9]{8,20}$";
				if (adminPassword.trim() == null || adminPassword.trim().length() == 0) {
					errorMsgs.put("adminPasswordErr", "密碼不可為空或只輸入空白鍵作為密碼！");
				} else if (!adminPassword.trim().matches(adminPasswordReg)) {
					errorMsgs.put("adminPasswordErr", "密碼須為8-20字英文大小寫與數字");
				}

				String adminNickname = req.getParameter("adminNickname");
				String adminNicknameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9)]{3,45}$";
				if (adminNickname.trim() == null || adminMail.trim().length() == 0) {
					errorMsgs.put("adminNicknameErr", "暱稱不可空白！");
				} else if (!adminNickname.trim().matches(adminNicknameReg)) {
					errorMsgs.put("adminNicknameErr", "密碼須為3至45個英文字母或數字，或1個中文字至15個中文字！");
				}

				byte[] adminPicBuffer = null;
				try {
					Part part = req.getPart("adminPic");

					if (part.getSubmittedFileName().length() == 0 || part.getContentType() == null) {
						System.out.println("使用者沒有上傳置頂圖片");
						if (req.getSession().getAttribute("adminPicBuffer") != null) {
							adminPicBuffer = (byte[]) req.getSession().getAttribute("adminPicBuffer");
						} else {
							errorMsgs.put("adminPicErr", "請上傳置頂圖之圖檔");
						}
					} else if (!part.getContentType().startsWith("image")) {
						errorMsgs.put("adminPicErr", "請上傳image類型之圖檔");
						if (req.getSession().getAttribute("adminPicBuffer") != null) {
							adminPicBuffer = (byte[]) req.getSession().getAttribute("adminPicBuffer");
						}
					} else if (part.getSize() > 1024 * 1024 * 3) { // 小於 3MB
						errorMsgs.put("adminPicErr", "請注意檔案尺寸過大");
						if (req.getSession().getAttribute("adminPicBuffer") != null) {
							adminPicBuffer = (byte[]) req.getSession().getAttribute("adminPicBuffer");
						}
					} else {
						InputStream in = part.getInputStream();
						adminPicBuffer = new byte[in.available()];
						in.read(adminPicBuffer);
						in.close();
						req.getSession().setAttribute("adminPicBuffer", adminPicBuffer);
					}
				} catch (Exception e) {
					System.err.println("使用者操作時發生其他例外");
				}

				AdminInfoVO adminVO = new AdminInfoVO();
				adminVO.setAdminMail(adminMail);
				adminVO.setAdminPassword(adminPassword);
				if (adminPicBuffer != null)
					adminVO.setAdminPic(adminPicBuffer);

				req.setAttribute("adminVO", adminVO);

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/Dashboard/addAdmin.jsp");
					failureView.forward(req, res);
					return;
				}

				// All parameters are correct, so we can send them to db ==========================
//				AdminInfoService recipeSvc = new RecipeService();
//				recipeVO = null;
//				recipeVO = recipeSvc.addRecipeWithDetails(recipeName, recipeIntroduction, recipePicTopBuffer,
//						recipeServe, 100001, recipeCatVOs, recipeIngUnitVOs, recipeStepVOs);
//
//				if (recipeVO != null) {
//					System.out.println("新增成功");
//					req.getSession().removeAttribute("recipePicTopBuffer");
//					req.getSession().removeAttribute("recipeStepPicBuffers");
//					RequestDispatcher successView = req
//							.getRequestDispatcher("/Recipe/recipe.jsp?id=" + recipeVO.getRecipeID());
//					successView.forward(req, res);
//				}

			} catch (Exception e) {
				errorMsgs.put("UnknowErr", "其他錯誤:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/Recipe/addRecipe.jsp");
				failureView.forward(req, res);
			}

		}
	}

}
