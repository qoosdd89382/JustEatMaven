package com.accountinfo.controller;

import java.io.IOException;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import com.accountinfo.model.AccountInfoService;
import com.accountinfo.model.AccountInfoVO;

public class AccountInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		//設定編碼與確認回應
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
//========================================================================
//帳號登入與修改相關請求
//來自AccountLoginPage的登入請求
		if ("getAccountInfo_For_Login".equals(action)) {
			//已取得回傳之accountMail、accountPassword資料
			
			//利用map儲存錯誤訊,用put將錯誤資料塞到值，之後到jsp就可以取出鍵顯示值，可以在顯示位置更彈性
			//用list有順序性，在顯示錯誤使用上不彈性
			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				//1.接收請求參數 - 輸入格式的錯誤處理
				String accountMailInput = req.getParameter("accountMail");
				String accountPasswordInput = req.getParameter("accountPassword");
				String accountRandomNumberInput = req.getParameter("RandomNumberInput");
				
				//檢查會員信箱跟會員密碼，還有驗證碼是否輸入
				if (accountMailInput == null || (accountMailInput.trim()).length() == 0) {
					errorMsgs.put("accountMailError","請輸入會員信箱");
				}
				if (accountPasswordInput == null || (accountPasswordInput.trim()).length() == 0) {
					errorMsgs.put("accountPasswordError","請輸入會員密碼");
				}
				if (accountRandomNumberInput == null || (accountRandomNumberInput.trim()).length() == 0) 
				{
					errorMsgs.put("randomNumberError","請輸入驗證碼");
				}
				
				//有錯誤就返回總表，顯示錯誤訊息
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/Account/AccountLoginPage.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				//帳號密碼正則表達式規範
				//帳號規範:任意大小寫英文(\w)或數字一個以上(+)@任意大小寫英文數字.任意大小寫英文數字
				Pattern accountMailPattern = Pattern.compile("^\\w+\\@\\w+\\.\\w+$");
				//判斷使用者帳號輸入是否符合，回傳boolean值
				Matcher accountMailMatcher = accountMailPattern.matcher(accountMailInput);
				//密碼規範:至少8碼任意大小寫英文數字
				Pattern accountPasswordPattern = Pattern.compile("^\\w{8,}$");
				//判斷使用者密碼輸入是否符合，回傳boolean值
				Matcher accountPasswordMatcher = accountPasswordPattern.matcher(accountPasswordInput);
				
				//準備回傳的值
				String accountMail = null;
				String accountPassword = null;

				//帳號密碼輸入格式驗證
				try {
					if (accountMailMatcher.matches()) {
						accountMail = new String(accountMailInput);
					}
				} catch (Exception e) {
					errorMsgs.put("accountMailError","會員信箱格式錯誤");
				}
				try {
					if (accountPasswordMatcher.matches()) {
						accountPassword = new String(accountPasswordInput);
					}
				} catch (Exception e) {
					errorMsgs.put("accountPasswordError","會員密碼格式錯誤");
				}
				
				//驗證碼存取放置session
				HttpSession session = req.getSession();
				String CorrectNumber = (String)session.getAttribute("RandomNumber");

				//如果驗證碼輸入錯誤就給錯誤訊息
				if(!accountRandomNumberInput.equals(CorrectNumber)) {
					errorMsgs.put("randomNumberError","驗證碼可能輸入錯誤");
				}

				// 有錯誤就返回總表，顯示錯誤訊息
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/Account/AccountLoginPage.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				//2.開始進行登入驗證
				if(session.getAttribute("accountMail")!=null) {
					System.out.println("這裡有信箱SESSION");
				}
				
				AccountInfoService accountInfoSvc = new AccountInfoService();
				//資料庫找不到該會員
				if(accountInfoSvc.getAccountMail(accountMailInput) == null) {
					errorMsgs.put("accountMailError","查無此會員資料");
				} 
				
				// 有錯誤就返回總表，顯示錯誤訊息
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/Account/AccountLoginPage.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}

				//資料庫有該會員，但輸入密碼跟資料庫不符合
				if(!((accountInfoSvc.getAccountPassword(accountMailInput)).getAccountPassword()).equals(accountPassword)) {
					errorMsgs.put("accountPasswordError","密碼輸入錯誤");
				}
				
				req.setAttribute("accountMail",accountMailInput);
				req.setAttribute("accountPassword",accountPasswordInput);
				// 有錯誤就返回總表，顯示錯誤訊息
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/Account/AccountLoginPage.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				//檢查完成，取得資料庫內的帳號密碼資料
				AccountInfoVO accountInfoVO = accountInfoSvc.getAccountInfo(accountMail,accountPassword);
					
				//3.查詢完成,準備轉交(Send the Success view)
				// 資料庫取出的accountVO物件,存入req，登入成功進入會員中心看自己資料
				session.setAttribute("accountInfoVO", accountInfoVO); 
				String url = "/Account/AccountInfoPage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);
				
			//其他例外
			}catch(Exception e) {
				errorMsgs.put("UnexceptionError","無法取得資料");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/Account/AccountPage.jsp");
				failureView.forward(req, res);
			}
		}
//在AccountInfoPage.jsp收到"會員資料"的請求
		if("gotoAccountInfoPage".equals(action)) {
			//取得帳號資料
			HttpSession session = req.getSession();
			AccountInfoVO accountInfoVO = (AccountInfoVO) session.getAttribute("accountInfoVO"); 
			req.setAttribute("accountMail", accountInfoVO.getAccountMail());
			//轉移到帳號修改頁面
			String url = "/Account/AccountInfoPage.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		
//在AccountInfoPage.jsp收到"修改會員資料"的請求
		if("Account_Change_Info".equals(action)) {
			//取得帳號資料
			HttpSession session = req.getSession();
			AccountInfoVO accountInfoVO = (AccountInfoVO) session.getAttribute("accountInfoVO"); 
			req.setAttribute("accountMail", accountInfoVO.getAccountMail());
			//轉移到帳號修改頁面
			String url = "/Account/AccountChangePage.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		
//在AccountInfoPage.jsp收到登出的請求
		if("Account_Logout".equals(action)) {
			//清除在SESSION中的帳號資料
			HttpSession session = req.getSession();
			session.removeAttribute("accountInfoVO");
			session.removeAttribute("accountMail");
			//回到登入頁面
			String url = "/Account/AccountLoginPage.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		

//===========================================================================================
//註冊相關請求
//在AccountRegisterPage.jsp收到加入會員的請求
		if ("setAccountInfo_For_Register".equals(action)) {
			
			//儲存註冊錯誤的訊息
			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				//接收頁面使用者輸入的參數
				String accountMailInput = req.getParameter("accountMail");
				String accountNicknameInput = req.getParameter("accountNickname");
				String accountPasswordInput = req.getParameter("accountPassword");
				String accountNameInput = req.getParameter("accountName");
				String accountGenderInput = req.getParameter("accountGender");
				String accountBirthInput = req.getParameter("accountBirth");
				String accountPhoneInput = req.getParameter("accountPhone");
				String accountPicInput = req.getParameter("accountPic");
				String accountIDcardFrontInput = req.getParameter("accountIDcardFront");
				String accountIDcardBackInput = req.getParameter("accountIDcardBack");
				String accountTextInput = req.getParameter("accountText");
				
				//檢查各項輸入
				if (accountMailInput == null || (accountMailInput.trim()).length() == 0) {
					errorMsgs.put("accountMailError","請輸入會員信箱");
				}
				if (accountNicknameInput == null || (accountNicknameInput.trim()).length() == 0) {
					errorMsgs.put("accountNicknameError","請輸入會員暱稱");
				}
				if (accountPasswordInput == null || (accountPasswordInput.trim()).length() == 0) {
					errorMsgs.put("accountPasswordError","請輸入會員密碼");
				}
				if (accountNameInput == null || (accountNameInput.trim()).length() == 0) {
					errorMsgs.put("accountNameError","請輸入名稱");
				}
				if (accountGenderInput == null || (accountGenderInput.trim()).length() == 0) {
					errorMsgs.put("accountGenderError","請輸入性別");
				}
				if (accountBirthInput == null || (accountBirthInput.trim()).length() == 0) {
					errorMsgs.put("accountBirthError","請輸入生日");
				}
//				if (accountPhoneInput == null || (accountPhoneInput.trim()).length() == 0) {
//					errorMsgs.put("accountPhoneError","請輸入電話");
//				}
				
//照片檢查後續再補
				
				if (accountTextInput == null || (accountTextInput.trim()).length() == 0) {
					errorMsgs.put("accountTextError","請輸入自我介紹");
				}
				
				//有錯誤就返回
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/Account/AccountRegisterPage.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				//註冊正則表達式規範
			//O帳號規範:任意大小寫英文(\w)或數字一個以上(+)@任意大小寫英文數字.任意大小寫英文數字
				Pattern accountMailPattern = Pattern.compile("^\\w+\\@\\w+\\.\\w+$");
				Matcher accountMailMatcher = accountMailPattern.matcher(accountMailInput);
			//O密碼規範:至少8~16碼任意大小寫英文數字
				Pattern accountPasswordPattern = Pattern.compile("^\\w{8,16}$");
				Matcher accountPasswordMatcher = accountPasswordPattern.matcher(accountPasswordInput);
			//O暱稱規範:兩個字以上，任意 中文 數字 英文大小寫
				Pattern accountNicknamePattern = Pattern.compile("^[\\u4E00-\\u9FA5a-zA-Z0-9]{2,8}$");
				Matcher accountNicknameMatcher = accountNicknamePattern.matcher(accountNicknameInput);
			//O名稱規範:兩個字以上，任意 中文 英文大小寫
				Pattern accountNamePattern = Pattern.compile("^[\\u4e00-\\u9fa5a-zA-Z]{2,6}$");
				Matcher accountNameMatcher = accountNamePattern.matcher(accountNameInput);
			//O電話規範:10碼數字前面規定09
				Pattern accountPhonePattern = Pattern.compile( "^([0-9]{3}-?[0-9]{8}|[0-9]{4}-?[0-9]{7})$");
				Matcher accountPhoneMatcher = accountPhonePattern.matcher(accountPhoneInput);
				
				//準備回傳的值
				//要驗證
				String accountMail = null;
				String accountPassword = null;
				String accountNickname = null;
				String accountName = null;
				String accountPhone = null;
				//不用驗證
				Integer accountGender = Integer.parseInt(accountGenderInput);
				Date accountBirth = (java.sql.Date.valueOf(accountBirthInput));
				String accountText = accountTextInput;

				//輸入格式驗證
				try {
					if (accountMailMatcher.matches()) {
						accountMail = new String(accountMailInput);
					}
				} catch (Exception e) {
					errorMsgs.put("accountMailError","會員信箱格式錯誤");
				}
				
				try {
					if (accountPasswordMatcher.matches()) {
						accountPassword = new String(accountPasswordInput);
					}
				} catch (Exception e) {
					errorMsgs.put("accountPasswordError","會員密碼格式錯誤");
				}
				
				try {
					if (accountNicknameMatcher.matches()) {
						accountNickname = new String(accountNicknameInput);
					}
				} catch (Exception e) {
					errorMsgs.put("accountNicknameError","會員暱稱格式錯誤");
				}
				
				try {
					if (accountNameMatcher.matches()) {
						accountName = new String(accountNameInput);
					}
				} catch (Exception e) {
					errorMsgs.put("accountPasswordError","會員名稱格式錯誤");
				}
				
				try {
					if (accountPhoneMatcher.matches()) {
						accountPhone = new String(accountPhoneInput);
					}
				} catch (Exception e) {
					errorMsgs.put("accountPasswordError","會員電話格式錯誤");
				}
				
				// 有錯誤就返回總表，顯示錯誤訊息
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/Account/AccountRegisterPage.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
//補各級驗證標準	
				//輸入資料無誤的儲存
				req.setAttribute("accountMail",accountMailInput);
				req.setAttribute("accountPassword",accountPasswordInput);
				req.setAttribute("accountNickname",accountNicknameInput);
				req.setAttribute("accountName",accountNameInput);
				req.setAttribute("accountGender",accountGender);
				req.setAttribute("accountBirth",accountBirth);
				req.setAttribute("accountText",accountTextInput);
				//呼叫SERVICE來做事，把上面的值都存到AccountInfoVO物件
				AccountInfoService accountInfoSvc = new AccountInfoService();
				accountInfoSvc.setLevelOneAccountInfoFromRegister(
						accountMail,accountNickname,accountPassword,accountName,accountGender,accountBirth,accountPhone,
						accountText);

				//註冊成功就可以到登入畫面登入看自己的資料，req會順便把登入成功的資料放在登入頁面
				String url = "/Account/AccountLoginPage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}catch(Exception e) {
				errorMsgs.put("UnexceptionError","無法取得資料");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/Account/AccountRegisterPage.jsp");
				failureView.forward(req, res);
			}
		}

//===	
	}
}

