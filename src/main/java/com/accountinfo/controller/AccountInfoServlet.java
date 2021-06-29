package com.accountinfo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
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
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.hibernate.Session;

import com.accountinfo.model.AccountInfoService;
import com.accountinfo.model.AccountInfoVO;
import com.mail.controller.MailService;
import com.mail.controller.RandomAuthCode;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 3 * 1024 * 1024, maxRequestSize = 30 * 3 * 1024 * 1024)
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
//if目錄
//登入相關
//首頁  == 點選會員中心請求 == gotoAccountLoginPage review06261500
		
//AccountLoginPage == 登入請求 ==  getAccountInfoForLogin review06261500
		
//AccountLoginPage == 忘記密碼 getAccountInfoForForget
		
//AccountInfoPage == functionbar收到"會員資料"請求  review06261500
	
//AccountChangePage == 提交修改資料的請求 == setAccountInfoForChange
		
//在AccountInfoPage == 收到登出的請求 == getAccountLogout
		
//註冊相關
//在AccountRegisterPage.jsp收到加入會員的請求 基本會員資料 setAccountInfoForRegister
		
		
//首頁  == 點選會員中心請求 == gotoAccountLoginPage review06261500
		//用來判斷session裡面有沒有帳號資料沒有的話就給重導到會員登入頁面
		if("gotoAccountLoginPage".equals(action)) {
			//取得帳號資料
			HttpSession session = req.getSession();
			if(session.getAttribute("accountInfoVOLogin")==null) {
				res.sendRedirect(req.getContextPath() + "/Account/AccountLoginPage.jsp");
				return;
			}else {				
				//轉移到帳號頁面
				String url = "/Account/AccountInfoPage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			}
		}//首頁  == 點選會員中心請求 == gotoAccountLoginPage review06261500

//AccountLoginPage == 登入請求 ==  getAccountInfoForLogin review06261500
		//使用者點選登入後檢查信箱、密碼、驗證碼資料
		if ("getAccountInfoForLogin".equals(action)) {
			System.out.println("收到登入請求 getAccountInfoForLogin");

			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			AccountInfoService accountInfoSvc = new AccountInfoService();
			HttpSession session = req.getSession();
			
			try {
				//取得輸入參數
				String accountMailInput = req.getParameter("accountMail");
				String accountPasswordInput = req.getParameter("accountPassword");
				String accountRandomNumberInput = req.getParameter("RandomNumberInput");
				//開始輸入參數檢查
			//檢查信箱
				String accountMail = null;
				//帳號規範:任意大小寫英文(\w)或數字一個以上(+)@任意大小寫英文數字.任意大小寫英文數字
				Pattern accountMailPattern = Pattern.compile("^\\w+\\@\\w+\\.\\w+$");
				Matcher accountMailMatcher = accountMailPattern.matcher(accountMailInput);
				try {
					if (accountMailInput == null || (accountMailInput.trim()).length() == 0) {
						errorMsgs.put("accountMailError","請輸入會員信箱");
					}else if(!accountMailMatcher.matches()) {
						errorMsgs.put("accountMailError","會員信箱格式錯誤");
					}else {
						accountMail = new String(accountMailInput);
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}
			//檢查密碼
				String accountPassword = null;
				//密碼規範:至少8碼任意大小寫英文數字
				Pattern accountPasswordPattern = Pattern.compile("^\\w{8,}$");
				Matcher accountPasswordMatcher = accountPasswordPattern.matcher(accountPasswordInput);
				try {
					if (accountPasswordInput == null || (accountPasswordInput.trim()).length() == 0) {
						errorMsgs.put("accountPasswordError","請輸入會員密碼");
					}else if(!accountPasswordMatcher.matches()) {
						errorMsgs.put("accountPasswordError","會員密碼格式錯誤");
					}else {
						accountPassword = new String(accountPasswordInput);
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}
			//檢查驗證碼
				try {
					String CorrectNumber = (String)session.getAttribute("RandomNumber");
					if (accountRandomNumberInput == null || (accountRandomNumberInput.trim()).length() == 0){
						errorMsgs.put("randomNumberError","請輸入驗證碼");
					}else if(!accountRandomNumberInput.equals(CorrectNumber)) {
						errorMsgs.put("randomNumberError","驗證碼可能輸入錯誤");
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}
				
				//有錯誤就返回總表，顯示錯誤訊息
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/Account/AccountLoginPage.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				System.out.println("檢查資料輸入完成");
				
				//進行登入資料驗證
				try {
					//資料庫找不到該會員
					if(accountInfoSvc.getAccountMail(accountMailInput) == null) {
						errorMsgs.put("accountMailError","查無此會員資料");
					//資料庫有該會員，但輸入密碼跟資料庫不符合
					}else if(!((accountInfoSvc.getAccountPasswordByAccountMail(accountMailInput)).getAccountPassword()).equals(accountPassword)) {
							errorMsgs.put("accountPasswordError","密碼輸入錯誤");
						}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}

				// 有錯誤就返回總表，顯示錯誤訊息
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/Account/AccountLoginPage.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				System.out.println("檢查資料驗證完成");
				
				//確認帳號 密碼 驗證碼 無誤後 將資料放入request保存 讓使用者重整頁面資料也存在
				req.setAttribute("accountMail",accountMailInput);
				req.setAttribute("accountPassword",accountPasswordInput);
				
				//檢查完成，將確認過的資料轉交 取得資料庫內該會員的所有資料
				AccountInfoVO accountInfoVO = accountInfoSvc
						.getAccountInfoForLogin(accountMail,accountPassword);

				// 資料庫取出的accountVO物件,存入req，登入成功進入會員中心看自己資料
				//session的accountInfoVO有該會員的全部資料
				session.setAttribute("accountInfoVOLogin", accountInfoVO); 
								
				//成功準備轉交
//				String url = "/Account/AccountInfoPage.jsp";
				String originWeb = (String)session.getAttribute("location");
//				if(!originWeb.equals(url)) {
//					originWeb = (String) session.getAttribute("location");
//				}else {
//					originWeb =url;
//				}
				if(((String)session.getAttribute("location"))==null) {
					originWeb = "/Account/AccountInfoPage.jsp";
				}
				System.out.println(originWeb);
				System.out.println("準備進行轉交");
				RequestDispatcher successView = req.getRequestDispatcher(originWeb);
				successView.forward(req, res);
				
			}catch(Exception e) {
				e.printStackTrace();
				errorMsgs.put("UnexceptionError","無法取得資料");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/Account/AccountLoginPage.jsp");
				failureView.forward(req, res);
			}
		}//AccountLoginPage == 登入請求 review06261500
		
//AccountLoginPage == 忘記密碼 == getAccountInfoForForget
		//登入頁面中點擊忘記密碼 以A標籤到登記信箱頁面
		//在登記信箱頁面點擊發送驗證碼
		if("getAccountInfoForForget".equals(action)) {
			System.out.println("收到 忘記密碼  請求");
			
			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			//產生service、vo物件
			AccountInfoService accountInfoSvc = new AccountInfoService();
			AccountInfoVO accountInfoVO = new AccountInfoVO();
			HttpSession session =req.getSession();
			
			try{
				//取得 使用者想註冊 輸入的資料
				String accountMailInput = req.getParameter("accountMail");
			//檢查accountMail輸入
				String accountMail = null;
				//O帳號規範:任意大小寫英文(\w)或數字一個以上(+)@任意大小寫英文數字.任意大小寫英文數字
				Pattern accountMailPattern = Pattern.compile("^\\w+\\@\\w+\\.\\w+$");
				Matcher accountMailMatcher = accountMailPattern.matcher(accountMailInput);
				try {
					if (accountMailInput == null || (accountMailInput.trim()).length() == 0) {
						errorMsgs.put("accountMailError","請輸入會員信箱");
					}else if(!accountMailMatcher.matches()){
						errorMsgs.put("accountMailError","會員信箱格式錯誤");
					}else if(accountInfoSvc.getAccountMail(accountMailInput) == null) {
						errorMsgs.put("accountMailError","此信箱未註冊過");
					}else if(accountInfoSvc.getAccountMail(accountMailInput) != null) {
						accountMail = new String(accountMailInput);
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}
				req.setAttribute("accountMailInput",accountMailInput);
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/Account/AccountForgetPage.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String accountCode = new RandomAuthCode().generateCode();
				Integer accountID =(Integer) (accountInfoSvc.getAccountIDByAccountMail(accountMail)).getAccountID();
				System.out.println(accountID);
				System.out.println(accountCode);

				//更改原本會員的驗證碼
				accountInfoSvc.updateAccountCodeFromForget(accountCode,accountID);

				String accountPassword = (accountInfoSvc.getAccountPasswordByAccountMail(accountMail)).getAccountPassword();

				if (accountInfoSvc != null) {
					System.out.println("會員更新驗證碼成功");

					MailService mailSvc = new MailService();
					String subject = "JustEat會員 忘記密碼 認證信";
					String connect = 
							"http://localhost:8081/justeat-maven/Account/AccountForgetCodePage.jsp";

					
					String messageText =
							"您好，以下是您的驗證資訊" + 
							"<br>您的密碼為" + accountPassword +
							"<br>輸入驗證碼" + accountCode +
//							"<br>馬上點擊" + req.getContextPath()+ "/Account/AccountForgetCodePage.jsp" +
							//測試用
							"<br>馬上點擊" + connect +
							"進行驗證";

					mailSvc.sendMail(accountMail, subject, messageText);
					
					//新的VO物件用來傳遞信箱資訊到下一個頁面
					accountInfoVO.setAccountMail(accountMail);
					accountInfoVO.setAccountCode(accountCode);
					session.setAttribute("accountInfoVO",accountInfoVO);

					String url = "/Account/AccountForgetCodePage.jsp";
					RequestDispatcher inputAuthCodeView = req.getRequestDispatcher(url);
					inputAuthCodeView.forward(req, res);
				}		
			}catch(Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
			}
		}
//AccountForgetPage == 檢驗驗證碼 ==getAccountForgetCode
		if("getAccountForgetCode".equals(action)) {
			System.out.println("收到 忘記密碼檢查驗證碼 請求");
			
			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			//產生service、vo物件
			HttpSession session = req.getSession();
			//取出SESSION中的VO物件 裡面有使用者前面填入的信箱
			AccountInfoVO accountInfoVO =  (AccountInfoVO) session.getAttribute("accountInfoVO");
			//將驗證碼存在VO物件
//			accountInfoVO = accountInfoSvc.getAccountCodeByAccountMail(accountInfoVO.getAccountMail());
			String accountMail = accountInfoVO.getAccountMail();
			String accountCode =  accountInfoVO.getAccountCode();

			try{
				System.out.println("開始檢查");
				
				String accountPasswordInput = req.getParameter("accountPassword");
				String accountCodeInput = req.getParameter("accountCode");

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
				
			//驗證碼檢查
				try {
					if (accountCodeInput == null || (accountCodeInput.trim()).length() == 0) {
						errorMsgs.put("accountCodeError","請輸入驗證碼");
					}else if(!accountCode.equals(accountCodeInput)){
						errorMsgs.put("accountCodeError","驗證碼錯誤");
					}else {
						System.out.println("驗證碼驗證成功");
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}
				
				//有錯誤就返回
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/Account/AccountForgetCodePage.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}

				//記錄使用者輸入的資料到登入頁面給使用者使用
				accountInfoVO.setAccountMail(accountMail);
				accountInfoVO.setAccountPassword(accountPassword);
				req.setAttribute("accountInfoVO", accountInfoVO);
					
				//輸入成功就可以到登入畫面登入看自己的資料，req會順便把登入成功的資料放在登入頁面
				String url = "/Account/AccountLoginPage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			}catch(Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
			}
		}
		
		
//AccountInfoPage == functionbar收到"會員資料"請求  review06261500
		if("gotoAccountInfoPage".equals(action)) {
			//重新前往頁面
			String url = "/Account/AccountInfoPage.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}//AccountInfoPage == functionbar收到"會員資料"請求  review06261500

		
//AccountInfoPage == 收到"修改會員資料"請求 == 轉接到修改會員資料的頁面  review06261500
		//看需求也可以轉成A標籤
		if("gotoAccountChangePage".equals(action)) {
			String url = "/Account/AccountChangePage.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}//AccountInfoPage == 收到"修改會員資料"請求 == 轉接到修改會員資料的頁面  review06261500
		
//AccountChangePage == 提交修改資料的請求 == setAccountInfoForChange review
		//檢查使用者修改參數
		if("setAccountInfoForChange".equals(action)) {
			
			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			AccountInfoVO accountInfoVO = new AccountInfoVO();
			AccountInfoService accountInfoSvc = new AccountInfoService();
			HttpSession session = req.getSession();

			try {
			//接收頁面使用者輸入的參數
				//基本認證
				String accountPasswordInput = req.getParameter("accountPassword");
//看要不要重新認證密碼輸入
				String accountNameInput = req.getParameter("accountName");
				String accountGenderInput = req.getParameter("accountGender");
				String accountBirthInput = req.getParameter("accountBirth");
				String accountTextInput = req.getParameter("accountText");
				//其他認證
				String accountPhoneInput = req.getParameter("accountPhone");

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
			//檢查照片輸入
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
				
				//有錯誤就返回
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/Account/AccountChangePage.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}

				//呼叫SERVICE來做事，把上面的值都存到AccountInfoVO物件
				//取得當前session中的會員資料
				AccountInfoVO accountInfoVOLogin = (AccountInfoVO) session.getAttribute("accountInfoVOLogin");
				//取得原本會員的資料
				Integer accountID = accountInfoVOLogin.getAccountID();
				String accountMail =accountInfoVOLogin.getAccountMail();
				//將會員輸入的資料update
				accountInfoSvc.updateAccountInfoFromChange(
						accountPassword,accountName,accountGender,accountBirth,accountPhone,
						accountPicBuffer,accountText,accountID);
				//將升級過後的資料透過帳號與新密碼傳入新的總資料
				AccountInfoVO accountInfoVONew =
						accountInfoSvc.getAccountInfoForLogin(accountMail,accountPassword);
				//移除頁面中原本的session
				session.removeAttribute("accountInfoVOLogin");
				//用含有新資料的session取代
				session.setAttribute("accountInfoVOLogin", accountInfoVONew); 
	
				//更改成功就可以到會員中心畫面登入看自己的資料，req會順便把登入成功的資料放在登入頁面
				String url = "/Account/AccountInfoPage.jsp";
				System.out.println("轉換資料成功");
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				}catch(Exception e) {
					e.printStackTrace();
					errorMsgs.put("UnexceptionError","無法取得資料");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/Account/AccountInfoPage.jsp");
					failureView.forward(req, res);
				}
			}//AccountChangePage == 提交修改資料的請求 == setAccountInfoForChange review

//在AccountInfoPage.jsp收到登出的請求 review06261600
		//會員登出 清除所有在SESSIO的登入資料
		if("getAccountLogout".equals(action)) {
			//清除在SESSION中的帳號資料
			HttpSession session = req.getSession();
			//把所有的資料清除
			session.removeAttribute("accountInfoVOLogin");
			//回到登入頁面
			String url = "/Account/AccountLoginPage.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}//在AccountInfoPage.jsp收到登出的請求 review06261600
		

//===========================================================================================
//註冊相關請求
//前往註冊頁面為A標籤
//在AccountRegisterPage.jsp收到加入會員的請求 驗證信箱
		//檢查帳號暱稱 發送驗證信
		if ("sendAccountCode".equals(action)) {
			System.out.println("收到 還不是會員 請求");
			
			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			//產生service、vo物件
			AccountInfoService accountInfoSvc = new AccountInfoService();
			AccountInfoVO accountInfoVO = new AccountInfoVO();
			HttpSession session =req.getSession();

			try {
				//取得 使用者想註冊 輸入的資料
				String accountMailInput = req.getParameter("accountMail");
				String accountNicknameInput = req.getParameter("accountNickname");
				
				//檢查accountMail輸入
				String accountMail = null;
				//O帳號規範:任意大小寫英文(\w)或數字一個以上(+)@任意大小寫英文數字.任意大小寫英文數字
				Pattern accountMailPattern = Pattern.compile("^\\w+\\@\\w+\\.\\w+$");
				Matcher accountMailMatcher = accountMailPattern.matcher(accountMailInput);
				try {
					if (accountMailInput == null || (accountMailInput.trim()).length() == 0) {
						errorMsgs.put("accountMailError","請輸入會員信箱");
					}else if(!accountMailMatcher.matches()){
						errorMsgs.put("accountMailError","會員信箱格式錯誤");
					}else if(accountInfoSvc.getAccountMail(accountMailInput) != null) {
						errorMsgs.put("accountMailError","此信箱已有人使用");
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
				
				//用來儲存使用者輸入
				accountInfoVO.setAccountMail(accountMailInput);
				accountInfoVO.setAccountNickname(accountNicknameInput);
				req.setAttribute("accountInfoVO",accountInfoVO);
				
			// 有錯誤就返回總表，顯示錯誤訊息
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/Account/AccountRegister/AccountRegisterPage.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				//寄信流程
				String accountCode = new RandomAuthCode().generateCode();

				accountInfoVO = accountInfoSvc.setBlankAccountInfoFromRegister(accountMail, accountNickname, accountCode);

				if (accountInfoSvc != null) {
					System.out.println("新增會員成功");

					MailService mailSvc = new MailService();
					String subject = "JustEat會員註冊認證信";
					String messageText =
							"請點擊本連結，啟用帳號：<br>" + req.getRequestURL() + "?action=auth" + 
							"&accountID="	+ accountInfoVO.getAccountID() + 
							"&authCode=" + accountCode + 
							"<br><br>或至" +  req.getRequestURL() + "?accountID="	+ accountInfoVO.getAccountID() + 
							"輸入驗證碼" + accountCode;

					mailSvc.sendMail(accountMail, subject, messageText);
				//寄信完成 將資料放在SESSION VO物件
					accountInfoVO.setAccountMail(accountMail);
					accountInfoVO.setAccountNickname(accountNickname);
					session.setAttribute("accountInfoVO",accountInfoVO);
					
					String url = "/Account/AccountRegister/AccountRegisterCodePage.jsp?accountID=" + accountInfoVO.getAccountID();
					RequestDispatcher inputAuthCodeView = req.getRequestDispatcher(url);
					inputAuthCodeView.forward(req, res);
				}		
			}catch(Exception e) {
				errorMsgs.put("UnexceptionError","無法取得資料");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/Account/AccountRegister/AccountRegisterPage.jsp");
				failureView.forward(req, res);
			}
		}
//在AccountRegisterCodePage.jsp收到檢查驗證碼資料
		//使用者收到驗證碼輸入檢查
		if("getAccountCode".equals(action)) {
			System.out.println("收到驗證碼請求");
			
			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			//產生service、vo物件
			AccountInfoService accountInfoSvc = new AccountInfoService();
			HttpSession session = req.getSession();
			AccountInfoVO accountInfoVO = (AccountInfoVO) session.getAttribute("accountInfoVO");
			
			//這裡不確定VO物件有沒有帶到CODE
			
			try {
			//驗證碼檢查
				String accountCodeInput = req.getParameter("accountCode");
				try {
					if (accountCodeInput == null || (accountCodeInput.trim()).length() == 0) {
						errorMsgs.put("accountCodeError","請輸入驗證碼");
					}else if(!((accountInfoVO.getAccountCode()).equals(accountCodeInput))){
						errorMsgs.put("accountCodeError","驗證碼錯誤");
					}else {
						System.out.println("驗證碼驗證成功");
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/Account/AccountRegister/AccountRegisterCodePage.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				//輸入成功就可以到登入畫面登入看自己的資料，req會順便把登入成功的資料放在登入頁面
				String url = "/Account/AccountRegister/AccountRegisterPageLevelOne.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}catch(Exception e) {
				errorMsgs.put("UnexceptionError","無法取得資料");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/Account/AccountRegister/AccountRegisterCodePage.jsp");
				failureView.forward(req, res);
			}
		}
		
//在AccountRegisterPage.jsp收到加入會員的請求 基本會員資料
		if ("setAccountInfoForRegister".equals(action)) {
			System.out.println("收到註冊請求");
			
			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			//產生service、vo物件
			AccountInfoService accountInfoSvc = new AccountInfoService();
			HttpSession session =req.getSession();
			AccountInfoVO accountInfoVO = (AccountInfoVO) session.getAttribute("accountInfoVO");
			Integer accountID = 
					(accountInfoSvc.getAccountIDByAccountMail(accountInfoVO.getAccountMail())).getAccountID();

			try {
				//取得 基本認證 輸入的資料
//				String accountMailInput = req.getParameter("accountMail");
//				String accountNicknameInput = req.getParameter("accountNickname");
				String accountMail = accountInfoVO.getAccountMail();
				String accountNickname = accountInfoVO.getAccountNickname();	
				
				String accountPasswordInput = req.getParameter("accountPassword");
				String accountNameInput = req.getParameter("accountName");
				String accountGenderInput = req.getParameter("accountGender");
				String accountBirthInput = req.getParameter("accountBirth");
				String accountTextInput = req.getParameter("accountText");
				
//			//檢查accountMail輸入
//				String accountMail = null;
//				//O帳號規範:任意大小寫英文(\w)或數字一個以上(+)@任意大小寫英文數字.任意大小寫英文數字
//				Pattern accountMailPattern = Pattern.compile("^\\w+\\@\\w+\\.\\w+$");
//				Matcher accountMailMatcher = accountMailPattern.matcher(accountMailInput);
//				try {
//					if (accountMailInput == null || (accountMailInput.trim()).length() == 0) {
//						errorMsgs.put("accountMailError","請輸入會員信箱");
//					}else if(!accountMailMatcher.matches()){
//						errorMsgs.put("accountMailError","會員信箱格式錯誤");
//					}else if(accountInfoSvc.getAccountMail(accountMail) != null) {
//						errorMsgs.put("accountMailError","此信箱已有人使用");
//					}else {
//						accountMail = new String(accountMailInput);
//					}
//				} catch (Exception e) {
//				throw new RuntimeException("A database error occured. "
//						+ e.getMessage());
//				}
//			//檢查accountNickname輸入
//				String accountNickname = null;
//				//O暱稱規範:兩個字以上，任意 中文 數字 英文大小寫
//				Pattern accountNicknamePattern = Pattern.compile("^[\\u4E00-\\u9FA5a-zA-Z0-9]{2,8}$");
//				Matcher accountNicknameMatcher = accountNicknamePattern.matcher(accountNicknameInput);
//				try {
//					if (accountNicknameInput == null || (accountNicknameInput.trim()).length() == 0) {
//						errorMsgs.put("accountNicknameError","請輸入會員暱稱");
//					}else if(!accountNicknameMatcher.matches()){
//						errorMsgs.put("accountNicknameError","會員暱稱格式錯誤");
//					}else if(accountInfoSvc.getAccountNickname(accountNicknameInput) != null) {
//						errorMsgs.put("accountMailError","此暱稱已有人使用");
//					}else {
//						accountNickname = new String(accountNicknameInput);
//					}
//				} catch (Exception e) {
//				throw new RuntimeException("A database error occured. "
//						+ e.getMessage());
//				}
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

			// 有錯誤就返回總表，顯示錯誤訊息
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/Account/AccountRegister/AccountRegisterPageLevelOne.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				//保留使用者確認送出的輸入資料 在VO物件 
//				accountInfoVO.setAccountMail(accountMailInput);
//				accountInfoVO.setAccountNickname(accountNicknameInput);
				accountInfoVO.setAccountPassword(accountPasswordInput);
				accountInfoVO.setAccountName(accountNameInput);
				accountInfoVO.setAccountGender(Integer.parseInt(accountGenderInput));
				accountInfoVO.setAccountBirth((java.sql.Date.valueOf(accountBirthInput)));
				accountInfoVO.setAccountText(accountTextInput);
				req.setAttribute("accountInfoVORequest",accountInfoVO);

				//呼叫SERVICE來做事，把上面的值都存到AccountInfoVO物件
				accountInfoSvc.setLevelOneAccountInfoFromRegister(
						accountMail,accountNickname,accountPassword,accountName,
						accountGender,accountBirth,accountText,accountID);
				//註冊成功就可以到登入畫面登入看自己的資料，req會順便把登入成功的資料放在登入頁面
				String url = "/Account//AccountRegister/AccountRegisterPageLevelThree.jsp";

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}catch(Exception e) {
				e.printStackTrace();
				errorMsgs.put("UnexceptionError","無法取得資料");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/Account/AccountRegister/AccountRegisterPageLevelOne.jsp");
				failureView.forward(req, res);
			}
		}
// 在AccountRegisterPageLevelOne 收到進階註冊資料
		if ("setLevelThreeAccountInfoForRegister".equals(action)) {
			System.out.println("進入");
			
			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			//產生service、vo物件
			AccountInfoService accountInfoSvc = new AccountInfoService();
			HttpSession session =req.getSession();
			AccountInfoVO accountInfoVO = (AccountInfoVO) session.getAttribute("accountInfoVO");
			Integer accountID = 
					(accountInfoSvc.getAccountIDByAccountMail(accountInfoVO.getAccountMail())).getAccountID();

			try {
				//二級認證
				String accountPhoneInput = req.getParameter("accountPhone");
				//三級認證
//				String accountPicInput = req.getParameter("accountPic");
//				String accountIDcardFrontInput = req.getParameter("accountIDcardFront");
//				String accountIDcardBackInput = req.getParameter("accountIDcardBack");

				String accountPhone = null;
				//O電話規範:10碼數字前面規定09
				Pattern accountPhonePattern = Pattern.compile( "^[0-9]{10}$");
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
				
				//三級驗證
				byte[] accountPicBuffer  = null;
				Part part = req.getPart("accountPic");
				
				InputStream in = part.getInputStream();
				accountPicBuffer = new byte[in.available()];
				in.read(accountPicBuffer);
				in.close();
				req.getSession().setAttribute("accountPicBuffer", accountPicBuffer);
				accountInfoVO.setAccountPic(accountPicBuffer);

				byte[] accountIDcardFrontBuffer  = null;
				Part part1 = req.getPart("accountIDcardFront");
				
				InputStream in1 = part1.getInputStream();
				accountIDcardFrontBuffer = new byte[in1.available()];
				in1.read(accountIDcardFrontBuffer);
				in1.close();
				req.getSession().setAttribute("aaccountIDcardFrontBuffer", accountIDcardFrontBuffer);
				accountInfoVO.setAccountIDcardFront(accountIDcardFrontBuffer);

				byte[] accountIDcardBackBuffer  = null;
				Part part2 = req.getPart("accountIDcardBack");
				
				InputStream in2 = part2.getInputStream();
				accountIDcardBackBuffer = new byte[in2.available()];
				in2.read(accountIDcardBackBuffer);
				in2.close();
				req.getSession().setAttribute("accountIDcardBackBuffer", accountIDcardBackBuffer);
				accountInfoVO.setAccountIDcardBack(accountIDcardBackBuffer);
				
			//有錯誤就返回
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/Account/AccountRegister/AccountRegisterPageLevelThree.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				//輸入資料無誤的儲存
//				req.setAttribute("accountMail",accountMailInput);
//				req.setAttribute("accountPassword",accountPasswordInput);
//				req.setAttribute("accountNickname",accountNicknameInput);
//				req.setAttribute("accountName",accountNameInput);
//				req.setAttribute("accountGender",accountGender);
//				req.setAttribute("accountBirth",accountPhoneInput);
//				req.setAttribute("accountBirth",accountBirth);
//				req.setAttribute("accountText",accountTextInput);
				//呼叫SERVICE來做事，把上面的值都存到AccountInfoVO物件
//				accountInfoSvc.setLevelOneAccountInfoFromRegister(
//						accountMail,accountNickname,accountPassword,accountName,accountGender,accountBirth,accountPhone,
//						accountText);
				//測試用
				accountInfoSvc.setLevelThreeAccountInfoFromRegister(
						accountPhone,
						accountPicBuffer,
						accountIDcardFrontBuffer,
						accountIDcardBackBuffer,
						accountID);

				//註冊成功就可以到登入畫面登入看自己的資料，req會順便把登入成功的資料放在登入頁面
				String url = "/Account/AccountLoginPage.jsp";
				System.out.println("註冊完成準備轉交");
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}catch(Exception e) {
				errorMsgs.put("UnexceptionError","無法取得資料");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/Account/AccountRegister/AccountRegisterPageLevelThree.jsp");
				failureView.forward(req, res);
			}
		}

		
		
		
//===	
	}
}

