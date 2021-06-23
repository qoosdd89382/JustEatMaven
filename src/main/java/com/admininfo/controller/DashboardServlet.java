package com.admininfo.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.accountinfo.model.AccountInfoService;
import com.accountinfo.model.AccountInfoVO;

//我先用WEB.XML註冊
//@WebServlet("/Dashboard/Account/dashboard.do")
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
		String action = req.getParameter("action");
		
//======================================================
//收到後台回應處理
//來自DashboardPage.jsp的請求 getAll 不用
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
				
				String url = "/Dashboard/Account/updateAccountInfoPage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			
			}catch(Exception e) {
				errorMsgs.put("UnexceptionError","無法取得資料");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/Dashboard/Account/DashboardAccountPage.jsp");
				failureView.forward(req, res);
			}	
		}
//來自DashboardPage.jsp的請求 updateAccountInfo_From_Dashboard
		if ("updateAccountInfo_From_Dashboard".equals(action)) {
System.out.println("進入更新");
			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
System.out.println("開始TRY");
				//接收頁面使用者輸入的參數
				Integer accountID = new Integer(req.getParameter("accountID"));
				String accountMailInput = req.getParameter("accountMail");
				String accountNicknameInput = req.getParameter("accountNickname");
				String accountPasswordInput = req.getParameter("accountPassword");
				String accountStateInput = req.getParameter("accountState");
				String accountLevelInput = req.getParameter("accountLevel");
				
				String accountNameInput = req.getParameter("accountName");
				String accountGenderInput = req.getParameter("accountGender");
				String accountBirthInput = req.getParameter("accountBirth");
				String accountPhoneInput = req.getParameter("accountPhone");
//				String accountPicInput = req.getParameter("accountPic");
//				
//				String accountIDcardFrontInput = req.getParameter("accountIDcardFront");
//				String accountIDcardBackInput = req.getParameter("accountIDcardBack");
				String accountTextInput = req.getParameter("accountText");
System.out.println("檢查點1");

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
//				if (accountStateInput == null || (accountStateInput.trim()).length() == 0) {
//					errorMsgs.put("accountStateError","請輸入會員狀態");
//				}
				if (accountLevelInput == null || (accountLevelInput.trim()).length() == 0) {
					errorMsgs.put("accountStateError","請輸入會員層級");
				}
				if (accountNameInput == null || (accountNameInput.trim()).length() == 0) {
					errorMsgs.put("accountNameError","請輸入會員名稱");
				}
				if (accountGenderInput == null || (accountGenderInput.trim()).length() == 0) {
					errorMsgs.put("accountGenderError","請輸入會員性別");
				}
				if (accountBirthInput == null || (accountBirthInput.trim()).length() == 0) {
					errorMsgs.put("accountBirthError","請輸入生日");
				}
				if (accountPhoneInput == null || (accountPhoneInput.trim()).length() == 0) {
					errorMsgs.put("accountPhoneError","請輸入電話");
				}
				
//照片檢查後續再補
				
				if (accountTextInput == null || (accountTextInput.trim()).length() == 0) {
					errorMsgs.put("accountTextError","請輸入自我介紹");
				}
System.out.println("檢查點1.1");

				//有錯誤就返回
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/Dashboard/Account/updateAccountInfoPage.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
System.out.println("檢查點1.2");				
				//註冊正則表達式規範
			//信箱規範:任意大小寫英文(\w)或數字一個以上(+)@任意大小寫英文數字.任意大小寫英文數字
				Pattern accountMailPattern = Pattern.compile("^\\w+\\@\\w+\\.\\w+$");
				Matcher accountMailMatcher = accountMailPattern.matcher(accountMailInput);
			//O暱稱規範:兩個字以上，任意 中文 數字 英文大小寫
				Pattern accountNicknamePattern = Pattern.compile("^[\\u4E00-\\u9FA5a-zA-Z0-9]{2,8}$");
				Matcher accountNicknameMatcher = accountNicknamePattern.matcher(accountNicknameInput);
			//O密碼規範:至少8~16碼任意大小寫英文數字
				Pattern accountPasswordPattern = Pattern.compile("^\\w{8,16}$");
				Matcher accountPasswordMatcher = accountPasswordPattern.matcher(accountPasswordInput);
			//O狀態層級前面已檢查
			//O姓名規範:兩個字以上，任意 中文 英文大小寫
				Pattern accountNamePattern = Pattern.compile("^[\\u4e00-\\u9fa5a-zA-Z]{2,6}$");
				Matcher accountNameMatcher = accountNamePattern.matcher(accountNameInput);
				//O性別生日一定有值
			//O電話規範:10碼數字前面規定09
				Pattern accountPhonePattern = Pattern.compile( "^([0-9]{3}-?[0-9]{8}|[0-9]{4}-?[0-9]{7})$");
				Matcher accountPhoneMatcher = accountPhonePattern.matcher(accountPhoneInput);
System.out.println("檢查點1.3");	
				//準備回傳的值
				//要驗證
				String accountMail = null;
				String accountNickname = null;
				String accountPassword = null;
				String accountName = null;
				String accountPhone = null;
System.out.println("檢查點1.4");
				//不用驗證
				Boolean accountState =true;
//				Boolean accountState = (Integer.parseInt(accountStateInput) == 1 ? true : false );
System.out.println("檢查點1.5");
				Integer accountLevel = Integer.parseInt(accountLevelInput);
				Integer accountGender = Integer.parseInt(accountGenderInput);
				Date accountBirth = (java.sql.Date.valueOf(accountBirthInput));
				String accountText = accountTextInput;
				
System.out.println("檢查點2");

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
							.getRequestDispatcher("/Dashboard/Account/updateAccountInfoPage.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
System.out.println("檢查點3");

				//輸入資料無誤的儲存
//				req.setAttribute("accountMail",accountMailInput);
//				req.setAttribute("accountNickname",accountNicknameInput);
//				req.setAttribute("accountPassword",accountPasswordInput);
//				req.setAttribute("accountState",accountState);
//				req.setAttribute("accountLevel",accountLevel);
//				req.setAttribute("accountName",accountNameInput);
//				req.setAttribute("accountGender",accountGender);
//				req.setAttribute("accountBirth",accountBirth);
//				req.setAttribute("accountPhone",accountPhone);
//				req.setAttribute("accountText",accountTextInput);
				AccountInfoVO accountInfoVO = new AccountInfoVO();
				accountInfoVO.setAccountMail(accountMailInput);
				accountInfoVO.setAccountNickname(accountNickname);
				accountInfoVO.setAccountPassword(accountPassword);
				accountInfoVO.setAccountState(accountState);
				accountInfoVO.setAccountLevel(accountLevel);
				accountInfoVO.setAccountName(accountName);
				accountInfoVO.setAccountGender(accountGender);
				accountInfoVO.setAccountBirth(accountBirth);
				accountInfoVO.setAccountPhone(accountPhone);
				accountInfoVO.setAccountText(accountTextInput);
System.out.println("驗證完畢");
				//呼叫SERVICE來做事，把上面的值都存到AccountInfoVO物件
				AccountInfoService accountInfoSvc = new AccountInfoService();
				accountInfoVO.setAccountID(accountID);
				accountInfoVO = accountInfoSvc.updateAccountInfo(
						accountMail, accountNickname, accountPassword, accountState, accountLevel,
						accountName, accountGender, accountBirth, accountPhone,
						accountText);
				req.setAttribute("accountInfoVO",accountInfoVO);
System.out.println("req.set");
				//註冊成功就可以到登入畫面登入看自己的資料，req會順便把登入成功的資料放在登入頁面
				String url = "/Dashboard/Account/DashboardAccountPage.jsp";
System.out.println("轉交");
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}catch(Exception e) {
				e.printStackTrace();
				errorMsgs.put("UnexceptionError","無法取得資料");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/Dashboard/Account/DashboardAccountPage.jsp");
				failureView.forward(req, res);
			}
		}

		
		
		
		
//
							

//				
//				
//				if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求
		//
//					List<String> errorMsgs = new LinkedList<String>();
//					// Store this set in the request scope, in case we need to
//					// send the ErrorPage view.
//					req.setAttribute("errorMsgs", errorMsgs);
//					
//					try {
//						/***************************1.接收請求參數****************************************/
//						Integer accountID = new Integer(req.getParameter("accountID"));
//						
//						/***************************2.開始查詢資料****************************************/
//						AccountInfoService accountInfoSvc = new AccountInfoService();
//						AccountInfoVO accountInfoVO = accountInfoSvc.getAccountID(accountID);
//										
//						/***************************3.查詢完成,準備轉交(Send the Success view)************/
//						req.setAttribute("accountInfoVO", accountInfoVO);         // 資料庫取出的empVO物件,存入req
//						String url = "/Account/UpdateAccountInfoInput.jsp";
//						RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
//						successView.forward(req, res);
		//
//						/***************************其他可能的錯誤處理**********************************/
//					} catch (Exception e) {
//						errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
//						RequestDispatcher failureView = req
//								.getRequestDispatcher("/Account/listAllEmp.jsp");
//						failureView.forward(req, res);
//					}
//				}
//				
		//update
				
//				if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
//					
//					List<String> errorMsgs = new LinkedList<String>();
//					// Store this set in the request scope, in case we need to
//					// send the ErrorPage view.
//					req.setAttribute("errorMsgs", errorMsgs);
//				
//					try {
//						/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
//						Integer accountID = new Integer(req.getParameter("accountID").trim());
//						
//						String accountMail = req.getParameter("accountMail");
//						String accountMailReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
//						if (accountMail == null || accountMail.trim().length() == 0) {
//							errorMsgs.add("員工姓名: 請勿空白");
//						} else if(!accountMail.trim().matches(accountMailReg)) { //以下練習正則(規)表示式(regular-expression)
//							errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
//			            }
//						
//						String job = req.getParameter("job").trim();
//						if (job == null || job.trim().length() == 0) {
//							errorMsgs.add("職位請勿空白");
//						}	
//						
//						java.sql.Date hiredate = null;
//						try {
//							hiredate = java.sql.Date.valueOf(req.getParameter("hiredate").trim());
//						} catch (IllegalArgumentException e) {
//							hiredate=new java.sql.Date(System.currentTimeMillis());
//							errorMsgs.add("請輸入日期!");
//						}
		//
//						Double sal = null;
//						try {
//							sal = new Double(req.getParameter("sal").trim());
//						} catch (NumberFormatException e) {
//							sal = 0.0;
//							errorMsgs.add("薪水請填數字.");
//						}
		//
//						Double comm = null;
//						try {
//							comm = new Double(req.getParameter("comm").trim());
//						} catch (NumberFormatException e) {
//							comm = 0.0;
//							errorMsgs.add("獎金請填數字.");
//						}
		//
//						Integer deptno = new Integer(req.getParameter("deptno").trim());
		//
//						EmpVO empVO = new EmpVO();
//						empVO.setEmpno(empno);
//						empVO.setEname(ename);
//						empVO.setJob(job);
//						empVO.setHiredate(hiredate);
//						empVO.setSal(sal);
//						empVO.setComm(comm);
//						empVO.setDeptno(deptno);
		//
//						// Send the use back to the form, if there were errors
//						if (!errorMsgs.isEmpty()) {
//							req.setAttribute("empVO", empVO); // 含有輸入格式錯誤的empVO物件,也存入req
//							RequestDispatcher failureView = req
//									.getRequestDispatcher("/emp/update_emp_input.jsp");
//							failureView.forward(req, res);
//							return; //程式中斷
//						}
//						
//						/***************************2.開始修改資料*****************************************/
//						AccountInfoService accountInfoSvc = new AccountInfoService();
//						accountInfoVO = accountInfoSvc.updateAccountInfoVO();
//						
//						/***************************3.修改完成,準備轉交(Send the Success view)*************/
//						req.setAttribute("empVO", empVO); // 資料庫update成功後,正確的的empVO物件,存入req
//						String url = "/emp/listOneEmp.jsp";
//						RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
//						successView.forward(req, res);
		//
//						/***************************其他可能的錯誤處理*************************************/
//					} catch (Exception e) {
//						errorMsgs.add("修改資料失敗:"+e.getMessage());
//						RequestDispatcher failureView = req
//								.getRequestDispatcher("/emp/update_emp_input.jsp");
//						failureView.forward(req, res);
//					}
//				}

		//insert		
//		        if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
//					
//					List<String> errorMsgs = new LinkedList<String>();
//					// Store this set in the request scope, in case we need to
//					// send the ErrorPage view.
//					req.setAttribute("errorMsgs", errorMsgs);
		//
//					try {
//						/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
//						String ename = req.getParameter("ename");
//						String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
//						if (ename == null || ename.trim().length() == 0) {
//							errorMsgs.add("員工姓名: 請勿空白");
//						} else if(!ename.trim().matches(enameReg)) { //以下練習正則(規)表示式(regular-expression)
//							errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
//			            }
//						
//						String job = req.getParameter("job").trim();
//						if (job == null || job.trim().length() == 0) {
//							errorMsgs.add("職位請勿空白");
//						}
//						
//						java.sql.Date hiredate = null;
//						try {
//							hiredate = java.sql.Date.valueOf(req.getParameter("hiredate").trim());
//						} catch (IllegalArgumentException e) {
//							hiredate=new java.sql.Date(System.currentTimeMillis());
//							errorMsgs.add("請輸入日期!");
//						}
//						
//						Double sal = null;
//						try {
//							sal = new Double(req.getParameter("sal").trim());
//						} catch (NumberFormatException e) {
//							sal = 0.0;
//							errorMsgs.add("薪水請填數字.");
//						}
//						
//						Double comm = null;
//						try {
//							comm = new Double(req.getParameter("comm").trim());
//						} catch (NumberFormatException e) {
//							comm = 0.0;
//							errorMsgs.add("獎金請填數字.");
//						}
//						
//						Integer deptno = new Integer(req.getParameter("deptno").trim());
		//
//						EmpVO empVO = new EmpVO();
//						empVO.setEname(ename);
//						empVO.setJob(job);
//						empVO.setHiredate(hiredate);
//						empVO.setSal(sal);
//						empVO.setComm(comm);
//						empVO.setDeptno(deptno);
		//
//						// Send the use back to the form, if there were errors
//						if (!errorMsgs.isEmpty()) {
		//req.setAttribute("empVO", empVO); // 含有輸入格式錯誤的empVO物件,也存入req
//							RequestDispatcher failureView = req
//									.getRequestDispatcher("/emp/addEmp.jsp");
//							failureView.forward(req, res);
//							return;
//						}
//						
//						/***************************2.開始新增資料***************************************/
//						EmpService empSvc = new EmpService();
//						empVO = empSvc.addEmp(ename, job, hiredate, sal, comm, deptno);
//						
//						/***************************3.新增完成,準備轉交(Send the Success view)***********/
//						String url = "/emp/listAllEmp.jsp";
//						RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
//						successView.forward(req, res);				
//						
//						/***************************其他可能的錯誤處理**********************************/
//					} catch (Exception e) {
//						errorMsgs.add(e.getMessage());
//						RequestDispatcher failureView = req
//								.getRequestDispatcher("/emp/addEmp.jsp");
//						failureView.forward(req, res);
//					}
//				}
				
		//delete
//				if ("delete".equals(action)) { // 來自listAllEmp.jsp
		//
//					List<String> errorMsgs = new LinkedList<String>();
//					// Store this set in the request scope, in case we need to
//					// send the ErrorPage view.
//					req.setAttribute("errorMsgs", errorMsgs);
		//	
//					try {
//						/***************************1.接收請求參數***************************************/
//						Integer empno = new Integer(req.getParameter("empno"));
//						
//						/***************************2.開始刪除資料***************************************/
//						EmpService empSvc = new EmpService();
//						empSvc.deleteEmp(empno);
//						
//						/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
//						String url = "/emp/listAllEmp.jsp";
//						RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
//						successView.forward(req, res);
//						
//						/***************************其他可能的錯誤處理**********************************/
//					} catch (Exception e) {
//						errorMsgs.add("刪除資料失敗:"+e.getMessage());
//						RequestDispatcher failureView = req
//								.getRequestDispatcher("/emp/listAllEmp.jsp");
//						failureView.forward(req, res);
//					}
//				}
				
			
		
		
		
	}//doPost
}