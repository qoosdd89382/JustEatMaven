package com.admininfo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.accountinfo.model.AccountInfoService;
import com.accountinfo.model.AccountInfoVO;
import com.mail.controller.RandomAuthCode;

//我先用WEB.XML註冊
//@WebServlet("/Dashboard/Account/dashboard.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 3 * 1024 * 1024, maxRequestSize = 30 * 3 * 1024 * 1024)
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) 
		throws ServletException,IOException{
		doPost(req,res);
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)
		throws ServletException,IOException{
		
		//設定編碼與確認回應
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		String action = req.getParameter("action");
		
//======================================================
//後臺回應目錄


//收到後台回應處理
//DashboardAccountPage == 增加會員請求 == insertAccountInfoFromDashboard
		if("insertAccountInfoFromDashboard".equals(action)) {
			System.out.println("收到 後台新增會員 請求");
			
			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			AccountInfoService accountInfoSvc = new AccountInfoService();
			AccountInfoVO accountInfoVO = new AccountInfoVO();
			
			try {
				//接收頁面使用者輸入的參數
				String accountMailInput = req.getParameter("accountMail");
				String accountNicknameInput = req.getParameter("accountNickname");
				String accountPasswordInput = req.getParameter("accountPassword");
				String accountNameInput = req.getParameter("accountName");
				String accountGenderInput = req.getParameter("accountGender");
				
				String accountStateInput =req.getParameter("accountState");
				String accountLevelInput =req.getParameter("accountLevel");

				String accountBirthInput = req.getParameter("accountBirth");
				String accountPhoneInput = req.getParameter("accountPhone");
				String accountTextInput = req.getParameter("accountText");
				//註冊時間用NOW account_code隨機給
				
			//檢查信箱
				String accountMail = null;
				//信箱規範表達式
				Pattern accountMailPattern = Pattern.compile("^\\w+\\@\\w+\\.\\w+$"); 
				Matcher accountMailMatcher = accountMailPattern.matcher(accountMailInput);
				try {
					if (accountMailInput == null || (accountMailInput.trim()).length() == 0) {
						errorMsgs.put("accountMailError","請輸入會員信箱");
					}else if(!accountMailMatcher.matches()){
						errorMsgs.put("accountMailError","會員信箱格式錯誤");
					}else if(accountInfoSvc.getAccountMail(accountMailInput) != null) {
						errorMsgs.put("accountMailError","此信箱已註冊過");
					}else {
						accountMail = new String(accountMailInput);
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}
			//檢查accountNickname輸入
				String accountNickname = null;
				//O暱稱規範:兩個字以上，任意 中文 數字 英文大小寫
				Pattern accountNicknamePattern = Pattern.compile("^[\\u4E00-\\u9FA5a-zA-Z0-9]{2,8}$");
				Matcher accountNicknameMatcher = accountNicknamePattern.matcher(accountNicknameInput);
				try {
					if (accountNicknameInput == null || (accountNicknameInput.trim()).length() == 0) {
						errorMsgs.put("accountNicknameError","請輸入會員暱稱");
					}else if(!accountNicknameMatcher.matches()){
						errorMsgs.put("accountNicknameError","會員暱稱格式錯誤");
					}else if(accountInfoSvc.getAccountNickname(accountNicknameInput) != null) {
						errorMsgs.put("accountNicknameError","此暱稱已有人使用");
					}else {
						accountNickname = new String(accountNicknameInput);
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}
			//檢查accountPassword輸入
				String accountPassword = null;
				//O密碼規範:至少8~16碼任意大小寫英文數字
				Pattern accountPasswordPattern = Pattern.compile("^\\w{8,16}$");
				Matcher accountPasswordMatcher = accountPasswordPattern.matcher(accountPasswordInput);
				try {
					if (accountPasswordInput == null || (accountPasswordInput.trim()).length() == 0) {
						errorMsgs.put("accountPasswordError","請輸入會員密碼");
					}else if(!accountPasswordMatcher.matches()){
						errorMsgs.put("accountPasswordError","會員密碼格式錯誤");
					}else {
						accountPassword = new String(accountPasswordInput);
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}
			//檢查accountName輸入
				String accountName = null;
				//O姓名規範:兩個字以上，任意 中文 英文大小寫
				Pattern accountNamePattern = Pattern.compile("^[\\u4e00-\\u9fa5a-zA-Z]{2,6}$");
				Matcher accountNameMatcher = accountNamePattern.matcher(accountNameInput);
				try {
					if (accountNameInput == null || (accountNameInput.trim()).length() == 0) {
						errorMsgs.put("accountNameError","請輸入姓名");
					}else if(!accountNameMatcher.matches()) {
						errorMsgs.put("accountNameError","會員姓名格式錯誤");
					}else {
						accountName = new String(accountNameInput);
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}
			//檢查accountGender輸入
				Integer accountGender = null;
				try {
					if(accountGenderInput == null) {
						errorMsgs.put("accountGenderError","請輸入性別");
					}else {
						accountGender = Integer.parseInt(accountGenderInput);
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}
			//檢查accountState輸入
				Boolean accountState = false;
				try {
					if(accountStateInput == null) {
						errorMsgs.put("accountStateError","請輸入會員狀態");
					}else {
						accountState = true;
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}
			//檢查accountLevel輸入
				Integer accountLevel = null;
				try {
					if(accountLevelInput == null) {
						errorMsgs.put("accountLevelError","請輸入會員層級");
					}else {
						accountLevel = Integer.parseInt(accountLevelInput);
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}
			//檢查accountBirth輸入
				Date accountBirth = null;
				try {
					if(accountBirthInput == null) {
						errorMsgs.put("accountBirthError","請輸入日期");
					}else {
						accountBirth = (java.sql.Date.valueOf(accountBirthInput));
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}
				
			//檢查電話輸入
				String accountPhone = null;
				//O電話規範:10碼數字前面規定09
				Pattern accountPhonePattern = Pattern.compile("^[0-9]{10}$");
				Matcher accountPhoneMatcher = accountPhonePattern.matcher(accountPhoneInput);
				try {
					if (accountPhoneInput == null || (accountPhoneInput.trim()).length() == 0) {
						errorMsgs.put("accountPhoneError","請輸入電話");
					}else if(!accountPhoneMatcher.matches()){
						errorMsgs.put("accountPhoneError","會員電話格式錯誤");
					}else {
						accountPhone = new String(accountPhoneInput);
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}
			//檢查accountText輸入
				String accountText = null;
				try {
					if(accountTextInput == null || (accountTextInput.trim()).length() == 0) {
						errorMsgs.put("accountTextError","請輸入自我介紹");
					}else {
						accountText = new String(accountTextInput);
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}
			//分隔線				
			//檢查照片輸入
				//會員大頭照
				byte[] accountPicBuffer  = null;
				try {
					//會員大頭照傳入
					Part part = req.getPart("accountPic");
					InputStream in = part.getInputStream();
					accountPicBuffer = new byte[in.available()];
					in.read(accountPicBuffer);
					in.close();
					req.getSession().setAttribute("accountPicBuffer", accountPicBuffer);
					accountInfoVO.setAccountPic(accountPicBuffer);	
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}

				//會員身分證正面
				byte[] accountIDcardFrontBuffer = null;
				try {
					Part part = req.getPart("accountIDcardFront");
					InputStream in = part.getInputStream();
					accountIDcardFrontBuffer = new byte[in.available()];
					in.read(accountIDcardFrontBuffer);
					in.close();
					req.getSession().setAttribute("accountIDcardFrontBuffer",accountIDcardFrontBuffer);
					accountInfoVO.setAccountIDcardFront(accountIDcardFrontBuffer);	
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}

				//會員身分證背面
				byte[] accountIDcardBackBuffer  = null;
				try {
					//會員大頭照傳入
					Part part = req.getPart("accountIDcardBack");
					
					InputStream in = part.getInputStream();
					accountIDcardBackBuffer = new byte[in.available()];
					in.read(accountIDcardBackBuffer);
					in.close();
					req.getSession().setAttribute("accountIDcardBackBuffer", accountIDcardBackBuffer);
					accountInfoVO.setAccountIDcardBack(accountIDcardBackBuffer);	
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}
				
				//有錯誤就返回
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/Dashboard/Account/InsertAccountInfoPage.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				//輸入資料無誤的儲存
				req.setAttribute("accountMail",accountMailInput);
				req.setAttribute("accountPassword",accountPasswordInput);
				req.setAttribute("accountNickname",accountNicknameInput);
				req.setAttribute("accountName",accountNameInput);
				req.setAttribute("accountGender",Integer.parseInt(accountGenderInput));
				
				req.setAttribute("accountLevel", Integer.parseInt(accountLevelInput));
				req.setAttribute("accountState", Integer.parseInt(accountStateInput));
				
				req.setAttribute("accountPhone", accountPhoneInput);
				req.setAttribute("accountBirth",java.sql.Date.valueOf(accountBirthInput));
				req.setAttribute("accountText",accountTextInput);

				//呼叫SERVICE來做事，把值都存到AccountInfoVO物件
				accountInfoVO.setAccountMail(accountMail);
				accountInfoVO.setAccountPassword(accountPassword);
				accountInfoVO.setAccountNickname(accountNickname);
				accountInfoVO.setAccountName(accountNameInput);
				accountInfoVO.setAccountGender(accountGender);
				
				accountInfoVO.setAccountLevel(accountLevel);
				accountInfoVO.setAccountState(accountState);

				accountInfoVO.setAccountPhone(accountPhone);
				accountInfoVO.setAccountBirth(accountBirth);
				accountInfoVO.setAccountText(accountText);
				
				accountInfoVO.setAccountPic(accountPicBuffer);
				accountInfoVO.setAccountIDcardFront(accountIDcardFrontBuffer);
				accountInfoVO.setAccountIDcardBack(accountIDcardBackBuffer);
				
				String accountCode = new RandomAuthCode().generateCode();
				accountInfoVO.setAccountCode(accountCode);
				
				accountInfoSvc.insertAccountInfo(accountInfoVO);

				//註冊成功就可以到登入畫面登入看自己的資料，req會順便把登入成功的資料放在登入頁面
				String url = "/Dashboard/Account/ListAllAccountInfoPage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}catch(Exception e) {
				errorMsgs.put("UnexceptionError","無法取得資料");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/Dashboard/Account/InsertAccountInfoPage.jsp");
				failureView.forward(req, res);
			}
		}//DashboardAccountPage == 增加會員請求 == insertAccountInfoFromDashboard

//DashboardPage == 查看一個會員資料 == getAll 不用
		if ("getOneAccountInfo_From_Dashboard".equals(action)) {
			
			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

		try {
			//1.接收請求參數 - 輸入格式的錯誤處理
			String str = req.getParameter("accountID");
			//檢查是否無輸入
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.put("accountIDError","請輸入會員編號");
			}
			//有錯誤就返回總表，顯示錯誤訊息
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/Dashboard/Account/DashboardAccountPage.jsp");
				failureView.forward(req, res);
				return;//程式中斷
			}
			
			Integer accountID = null;
			//檢查是否為數字
			try {
				accountID = new Integer(str);
			} catch (Exception e) {
				errorMsgs.put("accountIDError","會員編號格式不正確");
			}
			// 有錯誤就返回總表，顯示錯誤訊息
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/Dashboard/Account/DashboardAccountPage.jsp");
				failureView.forward(req, res);
				return;//程式中斷
			}
			AccountInfoService accountInfoSvc = new AccountInfoService();
			AccountInfoVO accountInfoVO = accountInfoSvc.selectOneAccountInfo(accountID);
			if (accountInfoVO == null) {
				errorMsgs.put("accountIDError","查無此會員資料");
			}
			// 有錯誤就返回總表，顯示錯誤訊息
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/Dashboard/Account/DashboardAccountPage.jsp");
				failureView.forward(req, res);
				return;//程式中斷
			}
			//3.查詢完成,準備轉交(Send the Success view)
			// 資料庫取出的accountVO物件,存入req
			req.setAttribute("accountInfoVO", accountInfoVO); 
			String url = "/Dashboard/Account/ListOneAccountInfoPage.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}catch(Exception e) {
			errorMsgs.put("UnexceptionError","無法取得資料");
			RequestDispatcher failureView = req
					.getRequestDispatcher("/Dashboard/Account/DashboardAccountPage.jsp");
			failureView.forward(req, res);
		}
	}//if getOneAccountInfo_From_Dashboard
		
//會員的刪除方法要封印
//		if("deleteAccountInfo".equals(action)) {
//
//			Map<String, String> errorMsgs = new HashMap<String, String>();
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//
//				Integer accountID = new Integer(req.getParameter("accountID"));
//				System.out.println(accountID);
//				AccountInfoService accountInfoSvc = new AccountInfoService();
//				accountInfoSvc.deleteAccountInfo(accountID);
//				String url = "/Dashboard/Account/ListAllAccountInfoPage.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);
//				successView.forward(req, res);
//				
//			}catch(Exception e) {
//				e.printStackTrace();
//				errorMsgs.put("UnexceptionError","無法取得資料");
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/Dashboard/Account/DashboardAccountPage.jsp");
//				failureView.forward(req, res);
//			}
//		}
		
//來自DashboardPage.jsp的請求 gotoUpdateAccountInfo
		if("gotoUpdateAccountInfo".equals(action)) {
		
			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Integer accountID = new Integer(req.getParameter("accountID"));
				AccountInfoService accountInfoSvc = new AccountInfoService();
				AccountInfoVO accountInfoVO = accountInfoSvc.selectOneAccountInfo(accountID);
				req.setAttribute("accountInfoVO", accountInfoVO); 
				
//				String url = "/Dashboard/Account/UpdateAccountInfoPage.jsp";
				String url = "/Dashboard/Admin/accountInfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			
			}catch(Exception e) {
				errorMsgs.put("UnexceptionError","無法取得資料");
				RequestDispatcher failureView = req
//						.getRequestDispatcher("/Dashboard/Account/DashboardAccountPage.jsp");
						.getRequestDispatcher("/Dashboard/Admin/listAllAccount.jsp");
				failureView.forward(req, res);
			}	
		}
//來自DashboardPage.jsp的請求 updateAccountInfo_From_Dashboard
		if ("updateAccountInfoFromDashboard".equals(action)) {
			System.out.println("後台 更新會員資料");

			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			AccountInfoService accountInfoSvc = new AccountInfoService();
			AccountInfoVO accountInfoVO = new AccountInfoVO();
			
			
			
			try {
				//接收頁面使用者輸入的參數
				Integer accountID = Integer.parseInt(req.getParameter("accountID"));
				
				String accountMailInput = req.getParameter("accountMail");
				String accountNicknameInput = req.getParameter("accountNickname");
				
				String accountPasswordInput = req.getParameter("accountPassword");
				String accountStateInput = req.getParameter("accountState");
				
				String accountLevelInput = req.getParameter("accountLevel");
				
				String accountNameInput = req.getParameter("accountName");
				String accountGenderInput = req.getParameter("accountGender");
				String accountBirthInput = req.getParameter("accountBirth");
				String accountPhoneInput = req.getParameter("accountPhone");

				String accountTextInput = req.getParameter("accountText");
			//檢查信箱
				String accountMail = null;
				//信箱規範表達式
				Pattern accountMailPattern = Pattern.compile("^\\w+\\@\\w+\\.\\w+$"); 
				Matcher accountMailMatcher = accountMailPattern.matcher(accountMailInput);
				try {
					if (accountMailInput == null || (accountMailInput.trim()).length() == 0) {
						errorMsgs.put("accountMailError","請輸入會員信箱");
					}else if(!accountMailMatcher.matches()){
						errorMsgs.put("accountMailError","會員信箱格式錯誤");
					//判斷原本是不是原本的信箱 是就儲存
					}else if((accountInfoSvc.getAccountMail(accountMailInput).getAccountMail()).equals(accountMailInput)) {
						accountMail = new String(accountMailInput);
					//判斷另外輸入的信箱有沒有人使用
					}else if(accountInfoSvc.getAccountMail(accountMailInput) != null) {
						errorMsgs.put("accountMailError","此信箱已註冊過");
					}else {
						accountMail = new String(accountMailInput);
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}
			//檢查accountNickname輸入
				String accountNickname = null;
				//O暱稱規範:兩個字以上，任意 中文 數字 英文大小寫
				Pattern accountNicknamePattern = Pattern.compile("^[\\u4E00-\\u9FA5a-zA-Z0-9]{2,20}$");
				Matcher accountNicknameMatcher = accountNicknamePattern.matcher(accountNicknameInput);
				try {
					if (accountNicknameInput == null || (accountNicknameInput.trim()).length() == 0) {
						errorMsgs.put("accountNicknameError","請輸入會員暱稱");
					}else if(!accountNicknameMatcher.matches()){
						errorMsgs.put("accountNicknameError","會員暱稱格式錯誤");
					}else if((accountInfoSvc.getAccountNickname(accountNicknameInput).getAccountNickname()).equals(accountNicknameInput)) {
						accountNickname = new String(accountNicknameInput);
					}else if(accountInfoSvc.getAccountNickname(accountNicknameInput) != null) {
						errorMsgs.put("accountNicknameError","此暱稱已有人使用");
					}else {
						accountNickname = new String(accountNicknameInput);
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}
			//檢查accountPassword輸入
				String accountPassword = null;
				//O密碼規範:至少8~16碼任意大小寫英文數字
				Pattern accountPasswordPattern = Pattern.compile("^\\w{8,16}$");
				Matcher accountPasswordMatcher = accountPasswordPattern.matcher(accountPasswordInput);
				try {
					if (accountPasswordInput == null || (accountPasswordInput.trim()).length() == 0) {
						errorMsgs.put("accountPasswordError","請輸入會員密碼");
					}else if(!accountPasswordMatcher.matches()){
						errorMsgs.put("accountPasswordError","會員密碼格式錯誤");
					}else {
						accountPassword = new String(accountPasswordInput);
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}

			//檢查accountName輸入
				String accountName = null;
				//O姓名規範:兩個字以上，任意 中文 英文大小寫
				Pattern accountNamePattern = Pattern.compile("^[\\u4e00-\\u9fa5a-zA-Z]{2,6}$");
				Matcher accountNameMatcher = accountNamePattern.matcher(accountNameInput);
				try {
					if (accountNameInput == null || (accountNameInput.trim()).length() == 0) {
						errorMsgs.put("accountNameError","請輸入姓名");
					}else if(!accountNameMatcher.matches()) {
						errorMsgs.put("accountNameError","會員姓名格式錯誤");
					}else {
						accountName = new String(accountNameInput);
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}
			//檢查accountGender輸入
				Integer accountGender = null;
				try {
					if(accountGenderInput == null) {
						errorMsgs.put("accountGenderError","請輸入性別");
					}else {
						accountGender = Integer.parseInt(accountGenderInput);
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}
			//檢查accountState輸入
				Boolean accountState = false;
				try {
					if(accountStateInput == null) {
						errorMsgs.put("accountStateError","請輸入會員狀態");
					}else {
						accountState = true;
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}
			//檢查accountLevel輸入
				Integer accountLevel = null;
				try {
					if(accountLevelInput == null) {
						errorMsgs.put("accountLevelError","請輸入會員層級");
					}else {
						accountLevel = Integer.parseInt(accountLevelInput);
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}
			//檢查accountBirth輸入

				Date accountBirth = null;
				try {
					if(accountBirthInput == null) {
						errorMsgs.put("accountBirthError","請輸入日期");
					}else {
						accountBirth = (java.sql.Date.valueOf(accountBirthInput));
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}

			//檢查電話輸入
				String accountPhone = null;
				//O電話規範:10碼數字前面規定09
				Pattern accountPhonePattern = Pattern.compile("^[0-9]{10}$");
				Matcher accountPhoneMatcher = accountPhonePattern.matcher(accountPhoneInput);
				try {
					if (accountPhoneInput == null || (accountPhoneInput.trim()).length() == 0) {
						errorMsgs.put("accountPhoneError","請輸入電話");
					}else if(!accountPhoneMatcher.matches()){
						errorMsgs.put("accountPhoneError","會員電話格式錯誤");
					}else {
						accountPhone = new String(accountPhoneInput);
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}

			//檢查accountText輸入
				String accountText = null;
				try {
					if(accountTextInput == null || (accountTextInput.trim()).length() == 0) {
						errorMsgs.put("accountTextError","請輸入自我介紹");
					}else {
						accountText = new String(accountTextInput);
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}
			//分隔線	

			//檢查照片輸入
				//會員大頭照
				byte[] accountPicBuffer  = null;
				try {
					//會員大頭照傳入
					Part part = req.getPart("accountPic");
					//如果使用者沒有圖片
					if (part.getSubmittedFileName().length() == 0 || part.getContentType() == null) {
						System.out.println("沒有上傳會員圖片");
						//現在沒抓到這個
						if (req.getSession().getAttribute("accountPicBuffer") != null) {
							accountPicBuffer = (byte[]) req.getSession().getAttribute("accountPicBuffer");
						} else {
							errorMsgs.put("accountPicErr", "請上傳會員圖片");
						}
					} else {
						InputStream in = part.getInputStream();
						accountPicBuffer = new byte[in.available()];
						in.read(accountPicBuffer);
						in.close();
						req.getSession().setAttribute("accountPicBuffer", accountPicBuffer);
						accountInfoVO.setAccountPic(accountPicBuffer);							
					}	
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}

				//會員身分證正面
				byte[] accountIDcardFrontBuffer = null;
				try {
					Part part = req.getPart("accountIDcardFront");
					
					if (part.getSubmittedFileName().length() == 0 || part.getContentType() == null) {
						System.out.println("沒有上傳身分證正面");
						if (req.getSession().getAttribute("accountIDcardFrontBuffer") != null) {
							accountIDcardFrontBuffer = (byte[]) req.getSession().getAttribute("accountIDcardFrontBuffer");
						} else {
							errorMsgs.put("accountIDcardFrontErr", "請上傳身分證正面");
						}
					} else {
						InputStream in = part.getInputStream();
						accountIDcardFrontBuffer = new byte[in.available()];
						in.read(accountIDcardFrontBuffer);
						in.close();
						req.getSession().setAttribute("accountIDcardFrontBuffer",accountIDcardFrontBuffer);
						accountInfoVO.setAccountIDcardFront(accountIDcardFrontBuffer);	
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}

				//會員身分證背面
				byte[] accountIDcardBackBuffer  = null;
				try {
					//會員大頭照傳入
					Part part = req.getPart("accountIDcardBack");
					//input沒有東西
					if (part.getSubmittedFileName().length() == 0 || part.getContentType() == null) {
						System.out.println("沒有上傳身分證背面");
						//如果session裡面有東西
						if (req.getSession().getAttribute("accountIDcardBackBuffer") != null) {
							accountIDcardBackBuffer = (byte[]) req.getSession().getAttribute("accountIDcardBackBuffer");
						} else {
							errorMsgs.put("accountIDcardBackBuffer", "請上傳身分證背面");
						}
					} else {
						InputStream in = part.getInputStream();
						accountIDcardBackBuffer = new byte[in.available()];
						in.read(accountIDcardBackBuffer);
						in.close();
						req.getSession().setAttribute("accountIDcardBackBuffer", accountIDcardBackBuffer);
						accountInfoVO.setAccountIDcardBack(accountIDcardBackBuffer);	
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}
				//有錯誤就返回
				if (!errorMsgs.isEmpty()) {
					System.out.println(errorMsgs);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/Dashboard/Admin/accountInfo.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}

				//輸入資料無誤的儲存
				req.setAttribute("accountMail",accountMailInput);
				req.setAttribute("accountPassword",accountPasswordInput);
				req.setAttribute("accountNickname",accountNicknameInput);
				req.setAttribute("accountName",accountNameInput);
				req.setAttribute("accountGender",Integer.parseInt(accountGenderInput));
				
				req.setAttribute("accountLevel", Integer.parseInt(accountLevelInput));
				req.setAttribute("accountState", Integer.parseInt(accountStateInput));
				
				req.setAttribute("accountPhone", accountPhoneInput);
				req.setAttribute("accountBirth",java.sql.Date.valueOf(accountBirthInput));
				req.setAttribute("accountText",accountTextInput);
				
				//呼叫SERVICE來做事，把值都存到AccountInfoVO物件
				accountInfoVO.setAccountMail(accountMail);
				accountInfoVO.setAccountPassword(accountPassword);
				accountInfoVO.setAccountNickname(accountNickname);
				accountInfoVO.setAccountName(accountName);
				accountInfoVO.setAccountGender(accountGender);
				
				accountInfoVO.setAccountLevel(accountLevel);
				accountInfoVO.setAccountState(accountState);

				accountInfoVO.setAccountPhone(accountPhone);
				accountInfoVO.setAccountBirth(accountBirth);
				accountInfoVO.setAccountText(accountText);
				
				accountInfoVO.setAccountPic(accountPicBuffer);
				accountInfoVO.setAccountIDcardFront(accountIDcardFrontBuffer);
				accountInfoVO.setAccountIDcardBack(accountIDcardBackBuffer);
				
				accountInfoVO.setAccountID(accountID);
				
				accountInfoSvc.updateAccountInfo(accountInfoVO);
				
				req.setAttribute("accountInfoVO",accountInfoVO);

				//註冊成功就可以到登入畫面登入看自己的資料，req會順便把登入成功的資料放在登入頁面
				String url = "/Dashboard/Admin/listAllAccount.jsp";
				System.out.println("後台 更新會員 完成準備轉交");
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}catch(Exception e) {
				e.printStackTrace();
				errorMsgs.put("UnexceptionError","無法取得資料");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/Dashboard/Admin/accountInfo.jsp");
				failureView.forward(req, res);
			}
		}
		
//收到後台會員停權通知
		if ("freezeAccountInfo".equals(action)) {
			System.out.println("收到 停權會員 請求");
			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			AccountInfoService accountInfoSvc = new AccountInfoService();
			
			try {
				//取得停權請求的ID
				Integer accountID = Integer.parseInt(req.getParameter("accountID"));
				System.out.println(accountID);
				accountInfoSvc.freezeAccountInfo(accountID);
				
//				String url = "/Dashboard/Account/ListAllAccountInfoPage.jsp";
				String url = "/Dashboard/Admin/listAllAccount.jsp";
				System.out.println("後台 停權會員 完成準備轉交");
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			}catch(Exception e) {
				e.printStackTrace();
				errorMsgs.put("UnexceptionError","無法取得資料");
				RequestDispatcher failureView = req
//						.getRequestDispatcher("/Dashboard/Account/ListAllAccountInfoPage.jsp");
						.getRequestDispatcher("/Dashboard/Admin/listAllAccount.jsp");
				failureView.forward(req, res);
			}
		}
//收到後台會員啟用通知
		if ("activeAccountInfo".equals(action)) {
			System.out.println("收到 啟用會員 請求");
			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			AccountInfoService accountInfoSvc = new AccountInfoService();
			
			try {
				//取得停權請求的ID
				Integer accountID = Integer.parseInt(req.getParameter("accountID"));
				System.out.println(accountID);
				accountInfoSvc.activeAccountInfo(accountID);
				
//				String url = "/Dashboard/Account/ListAllAccountInfoPage.jsp";
				String url = "/Dashboard/Admin/listAllAccount.jsp";
				System.out.println("後台 啟用會員 完成準備轉交");
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			}catch(Exception e) {
				e.printStackTrace();
				errorMsgs.put("UnexceptionError","無法取得資料");
				RequestDispatcher failureView = req
//						.getRequestDispatcher("/Dashboard/Account/ListAllAccountInfoPage.jsp");
						.getRequestDispatcher("/Dashboard/Admin/listAllAccount.jsp");
				failureView.forward(req, res);
			}
		}
	
	}//doPost
}