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
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.mindrot.jbcrypt.BCrypt;

import com.admininfo.model.AdminInfoService;
import com.admininfo.model.AdminInfoVO;
import com.mail.controller.MailService;
import com.mail.controller.RandomAuthCode;
import com.recipe.model.RecipeVO;

@WebServlet("/Dashboard/admin.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 3 * 1024 * 1024, maxRequestSize = 30 * 3 * 1024 * 1024)
public class AdminInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		AdminInfoService adminSvc = new AdminInfoService();

		String action = req.getParameter("action");
		HttpSession session = req.getSession();

		if ("insert".equals(action)) {
			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String adminMail = req.getParameter("adminMail");
				String adminMailReg = "^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$";
				if (adminMail.trim() == null || adminMail.trim().length() == 0) {
					errorMsgs.put("adminEmailErr", "e-mail請勿空白");
				} else if (!adminMail.trim().matches(adminMailReg)) {
					errorMsgs.put("adminEmailErr", "信箱格式錯誤");
				} else if (adminSvc.getOneAdmin(adminMail) != null) {
					errorMsgs.put("adminEmailErr", "信箱已被註冊使用");
				}

				String adminNickname = req.getParameter("adminNickname");
				String adminNicknameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9)]{3,45}$";
				if (adminNickname.trim() == null || adminNickname.trim().length() == 0) {
					errorMsgs.put("adminNicknameErr", "暱稱不可空白");
				} else if (!adminNickname.trim().matches(adminNicknameReg)) {
					errorMsgs.put("adminNicknameErr", "暱稱須為3至45字元");
				} else if (adminSvc.isNicknameExist(adminNickname)) {
					errorMsgs.put("adminNicknameErr", "暱稱已被註冊使用");
				}

				AdminInfoVO adminVO = new AdminInfoVO();
				adminVO.setAdminMail(adminMail);
				adminVO.setAdminNickname(adminNickname);

				req.setAttribute("adminVO", adminVO);

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/Dashboard/Admin/addAdmin.jsp");
					failureView.forward(req, res);
					return;
				}

				// All parameters are correct, so we can send them to db
				// ==========================
				String adminPassword = new RandomAuthCode().generateCode();

				adminVO = adminSvc.addAdmin(adminMail, adminNickname, adminPassword);

				if (adminSvc != null) {
					System.out.println("新增成功");

					MailService mailSvc = new MailService();
					String subject = "JustEat管理員註冊認證信";
					String messageText =
							"請點擊本連結，啟用帳號：<br>" + req.getRequestURL() + "?action=auth" + 
							"&adminID="	+ adminVO.getAdminID() + 
							"&authCode=" + adminPassword + 
							"<br><br>或至" +  req.getRequestURL() + "?adminID="	+ adminVO.getAdminID() + 
							"輸入驗證碼" + adminPassword;

					mailSvc.sendMail(adminMail, subject, messageText);
//					
					RequestDispatcher inputAuthCodeView = req.getRequestDispatcher(
//							"/Dashboard/adminRegisterAuth.jsp?adminID=" + adminVO.getAdminID());
							"/Dashboard/Admin/addAdminSuccess.jsp?adminID=" + adminVO.getAdminID());
					inputAuthCodeView.forward(req, res);
				}

			} catch (Exception e) {
				errorMsgs.put("UnknowErr", "其他錯誤:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/Dashboard/");
				failureView.forward(req, res);
			}

		}

		if ("auth".equals(action)) {
			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

//			try {
				Integer adminID = new Integer(req.getParameter("adminID"));

				String authCode = req.getParameter("authCode");
//				String authCodeAttr = (String) req.getAttribute("authCode");

//				AdminInfoVO adminVO = adminSvc.getOneAdmin(adminID);
//				req.setAttribute("adminVO", adminVO);
				if (authCode == null) {
					errorMsgs.put("authWrongErr", "請輸入驗證碼");
					RequestDispatcher inputAuthCodeView = req
							.getRequestDispatcher("/adminRegisterAuth.jsp?adminID=" + adminID);
					inputAuthCodeView.forward(req, res);
					return;
				} else if (adminSvc.getOneAdmin(adminID).getAdminPassword().equals(authCode)) {
					RequestDispatcher successView = req
							.getRequestDispatcher("/Dashboard/adminPasswordSet.jsp"
									+ "?adminID=" + adminID
									+ "&authCode=" + authCode);
					successView.forward(req, res);
				} else {
					errorMsgs.put("authWrongErr", "驗證錯誤");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/Dashboard/adminRegisterAuth.jsp?adminID=" + adminID);
					failureView.forward(req, res);
				}

//			} catch (Exception e) {
//				errorMsgs.put("authWrongErr", "驗證錯誤");
//				RequestDispatcher successView = req
//						.getRequestDispatcher("/Dashboard/addAdmin.jsp");
//				successView.forward(req, res);
//			}

		}
		
		if ("setpwd".equals(action)) {
			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

//			try {
				Integer adminID = new Integer(req.getParameter("adminID"));
//				Integer adminID = new Integer((String) req.getAttribute("adminID"));
				AdminInfoVO adminVO = adminSvc.getOneAdmin(adminID);
				String authCode = req.getParameter("authCodePara");
				
				
				String adminPassword = req.getParameter("adminPassword");
				String adminPasswordReg = "^[a-zA-Z0-9]{8,20}$";
				if (adminPassword == null || adminPassword.trim().length() == 0) {
					errorMsgs.put("adminPasswordErr", "密碼不可空白");
					errorMsgs.put("adminPasswordRecheckErr", "密碼不可空白");
				} else if (!adminPassword.trim().matches(adminPasswordReg)) {
					errorMsgs.put("adminPasswordErr", "密碼須為8-20字英文大小寫與數字");
					errorMsgs.put("adminPasswordRecheckErr", "請再次輸入密碼");
				} else {
					String adminPasswordRecheck = req.getParameter("adminPasswordRecheck");
					if (adminPasswordRecheck == null || adminPasswordRecheck.trim().length() == 0) {
						errorMsgs.put("adminPasswordRecheckErr", "請再次輸入密碼");
					} else if (!adminPasswordRecheck.trim().equals(adminPassword.trim())) {
						errorMsgs.put("adminPasswordErr", "請重新輸入密碼");
						errorMsgs.put("adminPasswordRecheckErr", "與第一次輸入不符，重新輸入");
					}
				}

				byte[] adminPicBuffer = null;
				try {
					Part part = req.getPart("adminPic");

					if (part.getSubmittedFileName().length() == 0 || part.getContentType() == null) {
						System.out.println("使用者沒有上傳置頂圖片");
						if (session.getAttribute("adminPicBuffer") != null) {
							adminPicBuffer = (byte[]) session.getAttribute("adminPicBuffer");
						} 
//						else {
//							errorMsgs.put("adminPicErr", "請上傳置頂圖之圖檔");
//						}
					} else if (!part.getContentType().startsWith("image")) {
						errorMsgs.put("adminPicErr", "請上傳image類型之圖檔");
						if (session.getAttribute("adminPicBuffer") != null) {
							adminPicBuffer = (byte[]) session.getAttribute("adminPicBuffer");
						}
					} else if (part.getSize() > 1024 * 1024 * 3) { // 小於 3MB
						errorMsgs.put("adminPicErr", "請注意檔案尺寸過大");
						if (session.getAttribute("adminPicBuffer") != null) {
							adminPicBuffer = (byte[]) session.getAttribute("adminPicBuffer");
						}
					} else {
						InputStream in = part.getInputStream();
						adminPicBuffer = new byte[in.available()];
						in.read(adminPicBuffer);
						in.close();
						session.setAttribute("adminPicBuffer", adminPicBuffer);
					}
				} catch (Exception e) {
					System.err.println("使用者操作時發生其他例外");
				}
				

				adminVO.setAdminPassword(adminPassword);
				adminVO.setAdminPic(adminPicBuffer);
				req.setAttribute("adminVO", adminVO);

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = 
							req.getRequestDispatcher("/Dashboard/adminPasswordSet.jsp");
					failureView.forward(req, res);
					return;
				}

				// All parameters are correct, so we can send them to db
				// ==========================
				
				String hashedPassword = BCrypt.hashpw(adminPassword, BCrypt.gensalt());
				System.out.println(hashedPassword);
				if (adminSvc.setPasswordAndPic(adminID, hashedPassword, adminPicBuffer) > 0) {
					session.removeAttribute("adminPicBuffer");
					session.removeAttribute("adminID");
					System.out.println("修改密碼成功");
					RequestDispatcher successView = 
							req.getRequestDispatcher("/Dashboard");
					successView.forward(req, res);
					return;
				}
				
				
//			} catch (Exception e) {
////			errorMsgs.put("UnknowErr", "發生錯誤，或您輸入的編號不存在！");
//			e.printStackTrace();
////			RequestDispatcher failureView = req.getRequestDispatcher("/");
////			failureView.forward(req, res);
////				res.sendRedirect(req.getContextPath());
//			}
		}
		
		
		
		if ("logout".equals(action)) {
			
			try {
				
				session.removeAttribute("loginAdminID");

				String adminLocation = (String) session.getAttribute("adminLocation");
				if (adminLocation != null) {
					session.removeAttribute("adminLocation");
					res.sendRedirect(adminLocation);
					return;
				}

				res.sendRedirect(req.getContextPath() + "/Dashboard/");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		if ("update".equals(action)) {
			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				
				int adminID = (int) session.getAttribute("loginAdminID");
				String adminPassword = req.getParameter("adminPassword");
				
				
				if (adminPassword == null || adminPassword.trim().length() == 0) {
					errorMsgs.put("adminPasswordErr", "未輸入密碼");
				} else if (allowUser(adminID, adminPassword) == null) {
					errorMsgs.put("adminPasswordErr", "舊密碼輸入錯誤");
				}
				
				byte[] adminPicBuffer = null;
				try {
					Part part = req.getPart("adminPic");
	
					if (part.getSubmittedFileName().length() == 0 || part.getContentType() == null) {
						System.out.println("使用者沒有上傳置頂圖片");
						if (session.getAttribute("adminPicBuffer") != null) {
							adminPicBuffer = (byte[]) session.getAttribute("adminPicBuffer");
						} else {
							adminPicBuffer = ((AdminInfoVO) adminSvc.getOneAdmin(adminID)).getAdminPic();
							session.setAttribute("adminPicBuffer", adminPicBuffer);
						}
					} else if (!part.getContentType().startsWith("image")) { 
						errorMsgs.put("adminPicErr", "請上傳image類型之圖檔");
						if (session.getAttribute("adminPicBuffer") != null) {
							adminPicBuffer = (byte[]) session.getAttribute("adminPicBuffer");
						}
					} else if (part.getSize() > 1024 * 1024 * 3) { // 小於 3MB
						errorMsgs.put("adminPicErr", "請注意檔案尺寸過大");
						if (session.getAttribute("adminPicBuffer") != null) {
							adminPicBuffer = (byte[]) session.getAttribute("adminPicBuffer");
						}
					} else {
						InputStream in = part.getInputStream();
						adminPicBuffer = new byte[in.available()];
						in.read(adminPicBuffer);
						in.close();
						session.setAttribute("adminPicBuffer", adminPicBuffer);
					}
				} catch (Exception e) {
					System.err.println("使用者操作時發生其他例外");
				}
				
				String adminNickname = req.getParameter("adminNickname");
				String adminNicknameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9)]{3,45}$";
				if (adminNickname.trim() == null || adminNickname.trim().length() == 0) {
					errorMsgs.put("adminNicknameErr", "暱稱不可空白");
				} else if (!adminNickname.trim().matches(adminNicknameReg)) {
					errorMsgs.put("adminNicknameErr", "暱稱須為3至45字元");
				} else if (adminSvc.isNicknameExist(adminNickname) && !adminNickname.equals(adminSvc.getOneAdmin(adminID).getAdminNickname())) {
					errorMsgs.put("adminNicknameErr", "暱稱已被註冊使用");
				}

				String newPassword = req.getParameter("newPassword");
				String adminPasswordReg = "^[a-zA-Z0-9]{8,20}$";
				if (newPassword != null && newPassword.trim().length() != 0) {
					if (!newPassword.trim().matches(adminPasswordReg)) {
						errorMsgs.put("newPasswordErr", "密碼須為8-20字英文大小寫與數字");
						errorMsgs.put("newPasswordRecheckErr", "請再次輸入密碼");
					} else {
						String newPasswordRecheck = req.getParameter("newPasswordRecheck");
						if (newPasswordRecheck == null || newPasswordRecheck.trim().length() == 0) {
							errorMsgs.put("newPasswordErr", "請再次輸入密碼");
							errorMsgs.put("newPasswordRecheckErr", "請再次輸入密碼");
						} else if (!newPasswordRecheck.trim().equals(newPassword.trim())) {
							errorMsgs.put("newPasswordErr", "請重新輸入密碼");
							errorMsgs.put("newPasswordRecheckErr", "與第一次輸入不符");
						}
					}
				}
//				
//				if (newPassword == null || newPassword.trim().length() == 0) {
////					errorMsgs.put("newPasswordErr", "密碼不可空白");
////					errorMsgs.put("newPasswordRecheckErr", "密碼不可空白");
//				} else if (!newPassword.trim().matches(adminPasswordReg)) {
//					errorMsgs.put("newPasswordErr", "密碼須為8-20字英文大小寫與數字");
//					errorMsgs.put("newPasswordRecheckErr", "請再次輸入密碼");
//				} else {
//					String newPasswordRecheck = req.getParameter("newPasswordRecheck");
//					if (newPasswordRecheck == null || newPasswordRecheck.trim().length() == 0) {
//						errorMsgs.put("newPasswordRecheckErr", "請再次輸入密碼");
//					} else if (!newPasswordRecheck.trim().equals(adminPassword.trim())) {
//						errorMsgs.put("newPasswordErr", "請重新輸入密碼");
//						errorMsgs.put("newPasswordRecheckErr", "與第一次輸入不符");
//					}
//				}
				

				AdminInfoVO adminVO = new AdminInfoVO();	// 導回用
				adminVO.setAdminNickname(adminNickname);
				adminVO.setAdminPic(adminPicBuffer);
				req.setAttribute("adminVO", adminVO);

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = 
							req.getRequestDispatcher("/Dashboard/Admin/adminInfo.jsp");
					failureView.forward(req, res);
					return;
				}

				// All parameters are correct, so we can send them to db
				// ==========================
				if (newPassword != null || newPassword.trim().length() != 0) {
					adminPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
				}
				if (adminSvc.setPasswordAndPic(adminID, adminPassword, adminPicBuffer) > 0) {
					session.removeAttribute("adminPicBuffer");
					req.setAttribute("updateSuccess", "資訊已更新成功");
					RequestDispatcher successView = 
							req.getRequestDispatcher("/Dashboard/Admin/adminInfo.jsp");
					successView.forward(req, res);
					return;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

	}
	

	protected AdminInfoVO allowUser(int adminID, String adminPassword) {
		AdminInfoService adminSvc = new AdminInfoService();
		AdminInfoVO adminVO = adminSvc.getOneAdmin(adminID);
		if (adminVO != null) {
			String hashedPassword = adminVO.getAdminPassword();
			if (BCrypt.checkpw(adminPassword, hashedPassword)) {
				return adminVO;// 匹配
			}
		}
		return null;	// 無此e-mail
	}


}
