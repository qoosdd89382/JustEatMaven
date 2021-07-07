package com.accountinfo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import com.dislikeingredient.model.DislikeIngredientService;
import com.ingredient.model.IngredientVO;
import com.likeingredient.model.LikeIngredientService;
import com.likeingredient.model.LikeIngredientVO;
import com.mail.controller.MailService;
import com.mail.controller.RandomAuthCode;
import com.recipeingredientunit.model.RecipeIngredientUnitVO;
import com.visit.model.VisitService;

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
			VisitService visitSvc = new VisitService();
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

				//新增瀏覽紀錄
//				visitSvc.insertVisitRecordByAccountID(accountInfoSvc.getAccountIDByAccountMail(accountMail).getAccountID());
				
				// 資料庫取出的accountVO物件,存入req，登入成功進入會員中心看自己資料
				//session的accountInfoVO有該會員的全部資料
				session.setAttribute("accountInfoVOLogin", accountInfoVO); 
				session.setAttribute("nowAccount", accountInfoVO); 


								
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
							"<br>馬上點擊" + req.getRequestURL() +"?action=getAccountForgetCode"+
							"&accountPassword=" + accountPassword +
							"&accountCode=" + accountCode +
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
			AccountInfoService accountInfoSvc = new AccountInfoService();
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
				Integer accountID = 
						(accountInfoSvc.getAccountIDByAccountMail(accountInfoVO.getAccountMail())).getAccountID();
				//清空 準備放入登入資料
				accountInfoVO =null;
				
				accountMail = accountInfoSvc.selectOneAccountInfo(accountID).getAccountMail();
				accountPassword = accountInfoSvc.selectOneAccountInfo(accountID).getAccountPassword();

				accountInfoVO = accountInfoSvc.getAccountInfoForLogin(accountMail, accountPassword);
				
				//session的accountInfoVO有該會員的全部資料
				session.setAttribute("accountInfoVOLogin", accountInfoVO); 
					
				//輸入成功就可以到登入畫面登入看自己的資料，req會順便把登入成功的資料放在登入頁面
				String url = "/Account/AccountInfoPage.jsp";
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
			//取得當前session中的會員資料
			AccountInfoVO accountInfoVOLogin = (AccountInfoVO) session.getAttribute("accountInfoVOLogin");
			//取得原本會員的資料
			Integer accountID = accountInfoVOLogin.getAccountID();
			String accountMail =accountInfoVOLogin.getAccountMail();
			
			try {
			//接收頁面使用者輸入的參數
				//基本認證
				String accountPasswordInput = req.getParameter("accountPassword");
				String accountPasswordRepeatInput = req.getParameter("accountPasswordRepeat");
				
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
					}else if (!(accountPasswordInput.equals(accountPasswordRepeatInput))) {
						errorMsgs.put("accountPasswordRepeatError","與上面密碼不符合");
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
					//如果使用者沒有圖片 抓不到檔名
					//把存在SESSION中的BUFFER存入
					if (part.getSubmittedFileName().length() == 0 || part.getContentType() == null) {
						System.out.println("沒有上傳會員圖片");
						//現在沒抓到這個
						if (req.getSession().getAttribute("accountPicBuffer") != null) {
							accountPicBuffer = (byte[]) req.getSession().getAttribute("accountPicBuffer");
						} else if(req.getSession().getAttribute("accountPicBuffer") == null){
							accountPicBuffer = accountInfoSvc.selectOneAccountInfo(accountID).getAccountPic();
						}else {
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
				
				//有錯誤就返回
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/Account/AccountChangePage.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}

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
			session.removeAttribute("nowAccount"); 

			//回到登入頁面
			String url = "/Account/AccountLoginPage.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}//在AccountInfoPage.jsp收到登出的請求 review06261600
		

//===========================================================================================
//註冊相關請求
//前往註冊頁面為A標籤
//在AccountRegisterPage.jsp收到加入會員的請求 驗證信箱
		//檢查信箱在資料庫狀態  發送驗證信
		if ("sendAccountCode".equals(action)) {
			System.out.println("收到 還不是會員 請求");

			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			//產生service、vo物件
			AccountInfoService accountInfoSvc = new AccountInfoService();
			AccountInfoVO accountInfoVO = new AccountInfoVO();
			HttpSession session =req.getSession();
			
			//清除SESSION中的所有東西
			session.removeAttribute("accountInfoVO");
			session.removeAttribute("accountInfoVOLogin");

			try {
				//取得 使用者想註冊 輸入的資料
				String accountMailInput = req.getParameter("accountMail");
				
				//檢查accountMail輸入
				String accountMail = null;
				//O帳號規範:任意大小寫英文(\w)或數字一個以上(+)@任意大小寫英文數字.任意大小寫英文數字
				Pattern accountMailPattern = Pattern.compile("^\\w+\\@\\w+\\.\\w+$");
				Matcher accountMailMatcher = accountMailPattern.matcher(accountMailInput);
				try {
					//檢查基本輸入
					if (accountMailInput == null || (accountMailInput.trim()).length() == 0) {
						errorMsgs.put("accountMailError","請輸入會員信箱");
					}else if(!accountMailMatcher.matches()){
						errorMsgs.put("accountMailError","會員信箱格式錯誤");
					//如果這個信箱有在資料庫 且 有層級 表示有註冊成功 這個帳戶至少是一般帳戶
					}else if(accountInfoSvc.getAccountMail(accountMailInput) != null && 
							(accountInfoSvc.getAccountLevelByAccountMail(accountMailInput)).getAccountLevel() > 0) {
						errorMsgs.put("accountMailError","此信箱已有人使用");
					}else {
						accountMail = new String(accountMailInput);
					}
				} catch (Exception e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
				}

			// 有錯誤就返回總表，顯示錯誤訊息
				if (!errorMsgs.isEmpty()) {
					//用來儲存使用者輸入
					accountInfoVO.setAccountMail(accountMailInput);
					req.setAttribute("accountInfoVO",accountInfoVO);
					
					RequestDispatcher failureView = req
							.getRequestDispatcher("/Account/AccountRegister/AccountRegisterPage.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}					
				//信箱有三種狀態 已使用被認證 已使用等認證 沒使用等認證
				//已使用等認證 沒使用等認證 會到這裡
				
				//使用情況 
				//登入直接跑去信箱看
				//一直重複拿驗證碼
				//關掉瀏覽器 跑去信箱
				
				//寄信流程
				String accountCode = new RandomAuthCode().generateCode();
				//沒使用等認證 在資料庫產生一個信箱 驗證碼
				if(accountInfoSvc.getAccountMail(accountMailInput) == null) {					
					accountInfoVO = accountInfoSvc.setBlankAccountInfoFromRegister(accountMail, accountCode);
				//有使用沒認證
				}else if(accountInfoSvc.getAccountMail(accountMailInput) != null) {
				//修改該名會員密碼
					accountInfoSvc.updateBlankAccountCodeFromRegister(accountMail,accountCode);
				}else {
					
				}
				//使用者ID等於資料庫內合格的帳號的ID
				Integer accountID = (accountInfoSvc.getAccountIDByAccountMail(accountMail)).getAccountID();
				if (accountInfoSvc != null) {
					System.out.println("新增會員成功");
					
					//送出ID CODE 參數
					MailService mailSvc = new MailService();
					String subject = "JustEat會員註冊認證信";
					String messageText =
							"請點擊本連結，啟用帳號：<br>" + req.getRequestURL() + "?action=getAccountCode" + 
							"&accountID="	+ accountID + 
							"&accountCode=" + accountCode + 
							"<br><br>或至" +  req.getRequestURL() + "?action=getAccountCode"+
							"&accountID="+ accountID + 
							"輸入驗證碼" + accountCode;

					mailSvc.sendMail(accountMail, subject, messageText);
					
				//寄信完成 將資料放在SESSION VO物件
					accountInfoVO.setAccountMail(accountMail);
					accountInfoVO.setAccountCode(accountCode);
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
		//使用者從信箱進入 或 從前一個網頁進入
		if("getAccountCode".equals(action)) {
			System.out.println("收到驗證碼請求");
			
			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			//產生service、vo物件
			AccountInfoService accountInfoSvc = new AccountInfoService();
			HttpSession session = req.getSession();
			//前一個網頁如果是信箱來的也會存在SESSION，這個SESSION可能只有信箱 或全部資料
			AccountInfoVO accountInfoVO = (AccountInfoVO) session.getAttribute("accountInfoVO");
			//從網頁跳轉來的
			if (accountInfoVO == null) {
				Integer accountID = Integer.parseInt(req.getParameter("accountID"));
				accountInfoVO = accountInfoSvc.selectOneAccountInfo(accountID);
				session.setAttribute("accountInfoVO", accountInfoVO);
			}
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
				e.printStackTrace();
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
			//取的SESSION中的值
			AccountInfoVO accountInfoVO = (AccountInfoVO) session.getAttribute("accountInfoVO");
			Integer accountID = accountInfoSvc.getAccountIDByAccountMail(accountInfoVO.getAccountMail()).getAccountID();

			try {
				//取得 基本認證 輸入的資料
				String accountMail = accountInfoVO.getAccountMail();
				String accountNicknameInput = req.getParameter("accountNickname");
				
				String accountPasswordInput = req.getParameter("accountPassword");
				String accountPasswordRepeatInput = req.getParameter("accountPasswordRepeat");
				
				String accountNameInput = req.getParameter("accountName");
				String accountGenderInput = req.getParameter("accountGender");
				String accountBirthInput = req.getParameter("accountBirth");
				String accountTextInput = req.getParameter("accountText");
	
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
					}else if(!(accountPasswordInput.equals(accountPasswordRepeatInput))) {
						errorMsgs.put("accountPasswordRepeatError","與上面輸入密碼不符合");
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
					if(accountGenderInput == null || accountGenderInput.trim().length() == 0 || accountGenderInput.isEmpty()) {
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
					if(accountBirthInput.isEmpty()) {
						errorMsgs.put("accountBirthError","請輸入日期");
					}else {
						accountBirth = (java.sql.Date.valueOf(accountBirthInput));
					}
				} catch (Exception e) {
					e.printStackTrace();
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
					//保留使用者確認送出的輸入資料 在VO物件 
//					accountInfoVO.setAccountMail(accountMailInput);
					accountInfoVO.setAccountNickname(accountNicknameInput);
					accountInfoVO.setAccountPassword(accountPasswordInput);
					accountInfoVO.setAccountName(accountNameInput);
//					accountInfoVO.setAccountGender(Integer.parseInt(accountGenderInput));
//					accountInfoVO.setAccountBirth((java.sql.Date.valueOf(accountBirthInput)));
					accountInfoVO.setAccountText(accountTextInput);
					req.setAttribute("accountInfoVO",accountInfoVO);
					
					RequestDispatcher failureView = req
							.getRequestDispatcher("/Account/AccountRegister/AccountRegisterPageLevelOne.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				

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
			System.out.println("收到 進階註冊 請求");
			
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
				//檢查照片輸入
				//會員大頭照
				byte[] accountPicBuffer  = null;
				try {
					//會員大頭照傳入
					Part part = req.getPart("accountPic");
					//如果使用者沒有圖片 抓不到檔名
					//把存在SESSION中的BUFFER存入
					if (part.getSubmittedFileName().length() == 0 || part.getContentType() == null) {
						System.out.println("沒有上傳會員圖片");
						//現在沒抓到這個
						if (req.getSession().getAttribute("accountPicBuffer") != null) {
							accountPicBuffer = (byte[]) req.getSession().getAttribute("accountPicBuffer");
						} else if(req.getSession().getAttribute("accountPicBuffer") == null){
							accountPicBuffer = accountInfoSvc.selectOneAccountInfo(accountID).getAccountPic();
						}else {
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
						}else if(req.getSession().getAttribute("accountIDcardFrontBuffer") == null){
							accountIDcardFrontBuffer = accountInfoSvc.selectOneAccountInfo(accountID).getAccountIDcardFront();
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
						} else if(req.getSession().getAttribute("accountIDcardBackBuffer") == null){
							accountIDcardBackBuffer = accountInfoSvc.selectOneAccountInfo(accountID).getAccountIDcardBack();
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
				String url = "/Account/AccountFoodPage.jsp";
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
//AccountFoodPage.jsp收到getAccountFood
		if("getAccountFood".equals(action)) {
			
			System.out.println("收到 選取食物 請求");
			
			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			AccountInfoService accountInfoSvc = new AccountInfoService();
			HttpSession session =req.getSession();
			AccountInfoVO accountInfoVO = (AccountInfoVO) session.getAttribute("accountInfoVO");
			Integer accountID = 
					(accountInfoSvc.getAccountIDByAccountMail(accountInfoVO.getAccountMail())).getAccountID();
			System.out.println(accountID);
			LikeIngredientService likeIngredientService = new LikeIngredientService();
			DislikeIngredientService dislikeIngredientService = new DislikeIngredientService();

			
			try {
				//這個陣列儲存使用者喜歡的食材
				List<IngredientVO> likeIngredientVOs = new ArrayList<IngredientVO>();
				//取得使用者輸入的標籤
				String likeIngredientIDString = req.getParameter("likeIngredientIDs");
				if(likeIngredientIDString == null || likeIngredientIDString.trim().length()==0) {
					errorMsgs.put("likeIngredientIDError", "請至少選取一個喜歡的食材");
				}else {
					//喜歡的食物字串集合
					String[] likeIngredientIDs = likeIngredientIDString.trim().split(" ");
					for(String likeIngredientID : likeIngredientIDs) {
						IngredientVO ingredientVO = new IngredientVO();
						ingredientVO.setIngredientID(new Integer(likeIngredientID));
						likeIngredientVOs.add(ingredientVO);
					}
				}
				System.out.println(likeIngredientVOs);
				//這個陣列儲存使用者不喜歡的食材
				List<IngredientVO> dislikeIngredientVOs = new ArrayList<IngredientVO>();
				//取得使用者輸入的標籤
				String dislikeIngredientIDString = req.getParameter("dislikeIngredientIDs");
				if(dislikeIngredientIDString == null || dislikeIngredientIDString.trim().length()==0) {
					errorMsgs.put("dislikeIngredientIDError", "請至少選取一個喜歡的食材");
				}else {
					//喜歡的食物字串集合
					String[] dislikeIngredientIDs = dislikeIngredientIDString.trim().split(" ");
					for(String dislikeIngredientID : dislikeIngredientIDs) {
						IngredientVO ingredientVO = new IngredientVO();
						ingredientVO.setIngredientID(new Integer(dislikeIngredientID));
						dislikeIngredientVOs.add(ingredientVO);
					}
				}
				System.out.println(dislikeIngredientVOs);
				
				likeIngredientService.addAccountLikeIngredient(likeIngredientVOs, accountID);
				dislikeIngredientService.addAccountDislikeIngredient(dislikeIngredientVOs, accountID);
				
				//清空 準備放入登入資料
				accountInfoVO =null;
				
				String accountMail = accountInfoSvc.selectOneAccountInfo(accountID).getAccountMail();
				String accountPassword = accountInfoSvc.selectOneAccountInfo(accountID).getAccountPassword();

				accountInfoVO = accountInfoSvc.getAccountInfoForLogin(accountMail, accountPassword);
				
				//session的accountInfoVO有該會員的全部資料
				session.setAttribute("accountInfoVOLogin", accountInfoVO); 
				
				//註冊成功就可以到登入畫面登入看自己的資料，req會順便把登入成功的資料放在登入頁面
				String url = "/Recipe/listAllRecipe.jsp";
				System.out.println("食物完成準備轉交");
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}catch(Exception e) {
				errorMsgs.put("UnexceptionError","無法取得資料");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/Account/AccountFoodPage.jsp");
				failureView.forward(req, res);
			}
		}//getAccountFood
		
		
//===	
//CODE 墳場	
//		//檢查accountMail輸入
//		String accountMail = null;
//		//O帳號規範:任意大小寫英文(\w)或數字一個以上(+)@任意大小寫英文數字.任意大小寫英文數字
//		Pattern accountMailPattern = Pattern.compile("^\\w+\\@\\w+\\.\\w+$");
//		Matcher accountMailMatcher = accountMailPattern.matcher(accountMailInput);
//		try {
//			if (accountMailInput == null || (accountMailInput.trim()).length() == 0) {
//				errorMsgs.put("accountMailError","請輸入會員信箱");
//			}else if(!accountMailMatcher.matches()){
//				errorMsgs.put("accountMailError","會員信箱格式錯誤");
//			}else if(accountInfoSvc.getAccountMail(accountMail) != null) {
//				errorMsgs.put("accountMailError","此信箱已有人使用");
//			}else {
//				accountMail = new String(accountMailInput);
//			}
//		} catch (Exception e) {
//		throw new RuntimeException("A database error occured. "
//				+ e.getMessage());
//		}		
//===
	}
}

