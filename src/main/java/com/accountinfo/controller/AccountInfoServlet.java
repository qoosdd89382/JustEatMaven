package com.accountinfo.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
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

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		//設定編碼與確認回應
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
//<--會員登入處理-->
		if ("getAccountInfo_For_Login".equals(action)) {
			//已取得回傳之accountMail、accountPassword資料
			
			//儲存錯誤訊息
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				//1.接收請求參數 - 輸入格式的錯誤處理
				String accountMailInput = req.getParameter("accountMail");
				String accountPasswordInput = req.getParameter("accountPassword");
				String accountRandomNumberInput = req.getParameter("RandomNumberInput");
				
				//檢查會員信箱跟會員密碼，還有驗證碼是否無輸入
				if (accountMailInput == null || (accountMailInput.trim()).length() == 0) {
					errorMsgs.add("請輸入會員信箱");
				}
				if (accountPasswordInput == null || (accountPasswordInput.trim()).length() == 0) {
					errorMsgs.add("請輸入會員密碼");
				}
				if (accountRandomNumberInput == null || (accountRandomNumberInput.trim()).length() == 0) 
				{
					errorMsgs.add("請輸入驗證碼");
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
					errorMsgs.add("會員信箱格式錯誤");
				}
				try {
					if (accountPasswordMatcher.matches()) {
						accountPassword = new String(accountPasswordInput);
					}
				} catch (Exception e) {
					errorMsgs.add("會員密碼格式錯誤");
				}
				
//驗證碼放在SESSION
				//驗證碼存取
				HttpSession session = req.getSession();
				String CorrectNumber = (String)session.getAttribute("RandomNumber");

				//如果驗證碼輸入錯誤就給錯誤訊息
				if(!accountRandomNumberInput.equals(CorrectNumber)) {
					errorMsgs.add("驗證碼可能輸入錯誤");
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
					System.out.println("這裡有人");
				}
				
				AccountInfoService accountInfoSvc = new AccountInfoService();
				//資料庫找不到該會員
				if(accountInfoSvc.getAccountMail(accountMailInput) == null) {
					errorMsgs.add("查無此會員資料");
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
					errorMsgs.add("密碼輸入錯誤");
				}
				
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
				req.setAttribute("accountInfoVO", accountInfoVO); 
				String url = "/Account/AccountInfoPage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);
				
			//其他例外
			}catch(Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/Account/AccountPage.jsp");
				failureView.forward(req, res);
			}
		}
//在AccountLoginPage.jsp收到加入會員的請求
		if ("setAccountInfo_For_Register".equals(action)) {
			//轉到輸入會員資料的頁面
			String url = "/Account/AccountRegisterPage.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		
		
		
// 來自AccountPage.jsp103的請求
//		if ("getOne_For_Display".equals(action)) { 
//
//			//儲存錯誤訊息
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
//			
//			try {
//				//1.接收請求參數 - 輸入格式的錯誤處理
//				String str = req.getParameter("accountID");
//				//檢查是否無輸入
//				if (str == null || (str.trim()).length() == 0) {
//					errorMsgs.add("請輸入會員編號");
//				}
//				//有錯誤就返回總表，顯示錯誤訊息
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/Account/AccountPage.jsp");
//					failureView.forward(req, res);
//					return;//程式中斷
//				}
//				
//				Integer accountID = null;
//				//檢查是否為數字
//				try {
//					accountID = new Integer(str);
//				} catch (Exception e) {
//					errorMsgs.add("會員編號格式不正確");
//				}
//				// 有錯誤就返回總表，顯示錯誤訊息
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/Account/AccountPage.jsp");
//					failureView.forward(req, res);
//					return;//程式中斷
//				}
//				
//				//2.開始查詢資料
//				AccountInfoService accountInfoSvc = new AccountInfoService();
//				AccountInfoVO accountInfoVO = accountInfoSvc.getAccountID(accountID);
//				if (accountInfoVO == null) {
//					errorMsgs.add("查無此會員資料");
//				}
//				// 有錯誤就返回總表，顯示錯誤訊息
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/Account/AccountPage.jsp");
//					failureView.forward(req, res);
//					return;//程式中斷
//				}
//				
////進度=======================================================
//
//				//3.查詢完成,準備轉交(Send the Success view)
//				// 資料庫取出的accountVO物件,存入req
//				req.setAttribute("accountInfoVO", accountInfoVO); 
//				String url = "/Account/ListOneAccountInfo.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
//				successView.forward(req, res);
//
//				/***************************其他可能的錯誤處理*************************************/
//			} catch (Exception e) {
//				errorMsgs.add("無法取得資料:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/Account/AccountPage.jsp");
//				failureView.forward(req, res);
//			}
//		}
//		
//		
//		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//			
//			try {
//				/***************************1.接收請求參數****************************************/
//				Integer accountID = new Integer(req.getParameter("accountID"));
//				
//				/***************************2.開始查詢資料****************************************/
//				AccountInfoService accountInfoSvc = new AccountInfoService();
//				AccountInfoVO accountInfoVO = accountInfoSvc.getAccountID(accountID);
//								
//				/***************************3.查詢完成,準備轉交(Send the Success view)************/
//				req.setAttribute("accountInfoVO", accountInfoVO);         // 資料庫取出的empVO物件,存入req
//				String url = "/Account/UpdateAccountInfoInput.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
//				successView.forward(req, res);
//
//				/***************************其他可能的錯誤處理**********************************/
//			} catch (Exception e) {
//				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/Account/listAllEmp.jsp");
//				failureView.forward(req, res);
//			}
//		}
//		
//update
		
//		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
//			
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//		
//			try {
//				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
//				Integer accountID = new Integer(req.getParameter("accountID").trim());
//				
//				String accountMail = req.getParameter("accountMail");
//				String accountMailReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
//				if (accountMail == null || accountMail.trim().length() == 0) {
//					errorMsgs.add("員工姓名: 請勿空白");
//				} else if(!accountMail.trim().matches(accountMailReg)) { //以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
//	            }
//				
//				String job = req.getParameter("job").trim();
//				if (job == null || job.trim().length() == 0) {
//					errorMsgs.add("職位請勿空白");
//				}	
//				
//				java.sql.Date hiredate = null;
//				try {
//					hiredate = java.sql.Date.valueOf(req.getParameter("hiredate").trim());
//				} catch (IllegalArgumentException e) {
//					hiredate=new java.sql.Date(System.currentTimeMillis());
//					errorMsgs.add("請輸入日期!");
//				}
//
//				Double sal = null;
//				try {
//					sal = new Double(req.getParameter("sal").trim());
//				} catch (NumberFormatException e) {
//					sal = 0.0;
//					errorMsgs.add("薪水請填數字.");
//				}
//
//				Double comm = null;
//				try {
//					comm = new Double(req.getParameter("comm").trim());
//				} catch (NumberFormatException e) {
//					comm = 0.0;
//					errorMsgs.add("獎金請填數字.");
//				}
//
//				Integer deptno = new Integer(req.getParameter("deptno").trim());
//
//				EmpVO empVO = new EmpVO();
//				empVO.setEmpno(empno);
//				empVO.setEname(ename);
//				empVO.setJob(job);
//				empVO.setHiredate(hiredate);
//				empVO.setSal(sal);
//				empVO.setComm(comm);
//				empVO.setDeptno(deptno);
//
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					req.setAttribute("empVO", empVO); // 含有輸入格式錯誤的empVO物件,也存入req
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/emp/update_emp_input.jsp");
//					failureView.forward(req, res);
//					return; //程式中斷
//				}
//				
//				/***************************2.開始修改資料*****************************************/
//				AccountInfoService accountInfoSvc = new AccountInfoService();
//				accountInfoVO = accountInfoSvc.updateAccountInfoVO();
//				
//				/***************************3.修改完成,準備轉交(Send the Success view)*************/
//				req.setAttribute("empVO", empVO); // 資料庫update成功後,正確的的empVO物件,存入req
//				String url = "/emp/listOneEmp.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
//				successView.forward(req, res);
//
//				/***************************其他可能的錯誤處理*************************************/
//			} catch (Exception e) {
//				errorMsgs.add("修改資料失敗:"+e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/emp/update_emp_input.jsp");
//				failureView.forward(req, res);
//			}
//		}

//insert		
//        if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
//			
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
//				String ename = req.getParameter("ename");
//				String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
//				if (ename == null || ename.trim().length() == 0) {
//					errorMsgs.add("員工姓名: 請勿空白");
//				} else if(!ename.trim().matches(enameReg)) { //以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
//	            }
//				
//				String job = req.getParameter("job").trim();
//				if (job == null || job.trim().length() == 0) {
//					errorMsgs.add("職位請勿空白");
//				}
//				
//				java.sql.Date hiredate = null;
//				try {
//					hiredate = java.sql.Date.valueOf(req.getParameter("hiredate").trim());
//				} catch (IllegalArgumentException e) {
//					hiredate=new java.sql.Date(System.currentTimeMillis());
//					errorMsgs.add("請輸入日期!");
//				}
//				
//				Double sal = null;
//				try {
//					sal = new Double(req.getParameter("sal").trim());
//				} catch (NumberFormatException e) {
//					sal = 0.0;
//					errorMsgs.add("薪水請填數字.");
//				}
//				
//				Double comm = null;
//				try {
//					comm = new Double(req.getParameter("comm").trim());
//				} catch (NumberFormatException e) {
//					comm = 0.0;
//					errorMsgs.add("獎金請填數字.");
//				}
//				
//				Integer deptno = new Integer(req.getParameter("deptno").trim());
//
//				EmpVO empVO = new EmpVO();
//				empVO.setEname(ename);
//				empVO.setJob(job);
//				empVO.setHiredate(hiredate);
//				empVO.setSal(sal);
//				empVO.setComm(comm);
//				empVO.setDeptno(deptno);
//
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//req.setAttribute("empVO", empVO); // 含有輸入格式錯誤的empVO物件,也存入req
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/emp/addEmp.jsp");
//					failureView.forward(req, res);
//					return;
//				}
//				
//				/***************************2.開始新增資料***************************************/
//				EmpService empSvc = new EmpService();
//				empVO = empSvc.addEmp(ename, job, hiredate, sal, comm, deptno);
//				
//				/***************************3.新增完成,準備轉交(Send the Success view)***********/
//				String url = "/emp/listAllEmp.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
//				successView.forward(req, res);				
//				
//				/***************************其他可能的錯誤處理**********************************/
//			} catch (Exception e) {
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/emp/addEmp.jsp");
//				failureView.forward(req, res);
//			}
//		}
		
//delete
//		if ("delete".equals(action)) { // 來自listAllEmp.jsp
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//	
//			try {
//				/***************************1.接收請求參數***************************************/
//				Integer empno = new Integer(req.getParameter("empno"));
//				
//				/***************************2.開始刪除資料***************************************/
//				EmpService empSvc = new EmpService();
//				empSvc.deleteEmp(empno);
//				
//				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
//				String url = "/emp/listAllEmp.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
//				successView.forward(req, res);
//				
//				/***************************其他可能的錯誤處理**********************************/
//			} catch (Exception e) {
//				errorMsgs.add("刪除資料失敗:"+e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/emp/listAllEmp.jsp");
//				failureView.forward(req, res);
//			}
//		}
		
		
	}
}

