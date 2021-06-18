package com.recipe.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.cuisinecategory.model.CuisineCategoryService;
import com.cuisinecategory.model.CuisineCategoryVO;
import com.recipecuisinecategory.model.RecipeCuisineCategoryService;
import com.recipecuisinecategory.model.RecipeCuisineCategoryVO;
import com.recipeingredientunit.model.RecipeIngredientUnitService;
import com.recipeingredientunit.model.RecipeIngredientUnitVO;
import com.recipestep.model.RecipeStepService;
import com.recipestep.model.RecipeStepVO;
import com.unit.model.UnitService;
import com.unit.model.UnitVO;
import com.recipe.model.RecipeService;
import com.recipe.model.RecipeVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 3 * 1024 * 1024, maxRequestSize = 30 * 3 * 1024 * 1024)
public class RecipeServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// 不需要 res.setContentType，這邊不負責輸出到螢幕上
		req.setCharacterEncoding("UTF-8");
//		res.setContentType("text/html; charset=UTF-8");		// for ajax，但輸出的參數是數字，其實可以不用設...
		
		/**********************刪除食譜步驟**********************/
		String delOrder = req.getParameter("delOrder");
		String recipeIDforUpdate = req.getParameter("update");
		Map<Integer, byte[]> recipeStepPicBuffers = null;
//		List<byte[]> recipeStepPicBuffers = null;
		if (delOrder != null) {
			System.out.println(delOrder);
			System.out.println(recipeIDforUpdate);
			if (recipeIDforUpdate != null) {
				RecipeStepService recipeStepSvc = new RecipeStepService();
				List<RecipeStepVO> orgRecipeStepVOs = recipeStepSvc.getAllByRecipe(new Integer(recipeIDforUpdate));
				Map<Integer, byte[]> orgRecipeStepPicBuffers = new LinkedHashMap<Integer, byte[]>();
				for (RecipeStepVO orgRecipeStepVO : orgRecipeStepVOs) {
					orgRecipeStepPicBuffers.put(new Integer(orgRecipeStepVO.getRecipeStepOrder()-1), orgRecipeStepVO.getRecipeStepPic());
				}
				recipeStepPicBuffers = orgRecipeStepPicBuffers;
				req.getSession().setAttribute("recipeStepPicBuffers", recipeStepPicBuffers);
			}
			if(req.getSession().getAttribute("recipeStepPicBuffers") != null) {
				recipeStepPicBuffers = (Map<Integer, byte[]>) req.getSession().getAttribute("recipeStepPicBuffers");
				Map<Integer, byte[]> deepCopy = new LinkedHashMap<Integer, byte[]>(recipeStepPicBuffers);
//				recipeStepPicBuffers = (List<byte[]>) req.getSession().getAttribute("recipeStepPicBuffers");
//				recipeStepPicBuffers.remove((new Integer(delOrder)-1));
//				recipeStepPicBuffers.remove(String.valueOf((new Integer(delOrder)-1)));
				System.out.println(recipeStepPicBuffers.size());
				System.out.println(delOrder);
				System.out.println(Integer.parseInt(delOrder) == recipeStepPicBuffers.size());
				if(Integer.parseInt(delOrder) == recipeStepPicBuffers.size()) {
					recipeStepPicBuffers.remove(Integer.parseInt(delOrder)-1);
					System.out.println("recipeStepPicBuffers:" + recipeStepPicBuffers.toString());
				} else {
					for (Integer key : recipeStepPicBuffers.keySet()) {
						byte[] temp = recipeStepPicBuffers.get(key+1);
						System.out.println(key);
						if(key.equals(recipeStepPicBuffers.size() - 1)) {
							System.out.println("last deepCopy original value=" + deepCopy.get(key));
							deepCopy.remove(key);
							System.out.println("last one(order:" + key + ")has been deleted");
						} else if (key >= Integer.parseInt(delOrder) - 1) {
							deepCopy.put(key, temp);
							System.out.println("deepCopy new value=" + deepCopy.get(key));
						}  
					}
					System.out.println("deepCopy:" + deepCopy.toString());
					req.getSession().setAttribute("recipeStepPicBuffers", deepCopy);
				}
				PrintWriter out = res.getWriter();
		        out.write(delOrder);
			}
		}
		
		String action = req.getParameter("action");

		/**********************新增食譜**********************/
		if ("insert".equals(action)) { // 來自addRecipe.jsp的請求
			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String recipeName = req.getParameter("recipeName");
				String recipeNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9)]{3,45}$"; // 逗號後面不可以亂空白，REX的規定
				if (recipeName == null || recipeName.trim().length() == 0) {
					errorMsgs.put("recipeNameErr", "食譜名稱請勿空白");
				} else if (!recipeName.trim().matches(recipeNameReg)) {
					errorMsgs.put("recipeNameErr", "食譜名稱只能是中、英字母、數字，英數3至45字、中文1至15字");
				}
				
				String recipeIntroduction = req.getParameter("recipeIntroduction");
				String recipeIntroductionReg = "^.{3,450}$";
				if (recipeName == null || recipeName.trim().length() == 0) {
//					recipeIntroduction = "";
					errorMsgs.put("recipeIntroductionErr", "食譜介紹請勿空白");
				} else if (!recipeIntroduction.trim().matches(recipeIntroductionReg)) {
					errorMsgs.put("recipeIntroductionErr", "食譜介紹長度需介於英數3至450字、中文1至150字");
				}
	
				Integer recipeServe = null;
				try {
					recipeServe = new Integer(req.getParameter("recipeServe"));
					if (recipeServe < 1 || recipeServe > 20) {
						errorMsgs.put("recipeServeErr", "享用人數請勿低於1人，或高於20人");
					}
				} catch (NumberFormatException e) {
					recipeServe = 1;
					errorMsgs.put("recipeServeErr", "享用人數填寫格式錯誤，請勿空白，並請填寫1至20人份之數字");
				}
	
				byte[] recipePicTopBuffer = null;
				try {
					Part part = req.getPart("recipePicTop");
	
					if (part.getSubmittedFileName().length() == 0 || part.getContentType() == null) {
						System.out.println("使用者沒有上傳置頂圖片");
						if (req.getSession().getAttribute("recipePicTopBuffer") != null) {
							recipePicTopBuffer = (byte[]) req.getSession().getAttribute("recipePicTopBuffer");
						} else {
							errorMsgs.put("recipePicTopErr", "請上傳置頂圖之圖檔");
						}
					} else if (!part.getContentType().startsWith("image")) { 
						errorMsgs.put("recipePicTopErr", "請上傳image類型之圖檔");
						if (req.getSession().getAttribute("recipePicTopBuffer") != null) {
							recipePicTopBuffer = (byte[]) req.getSession().getAttribute("recipePicTopBuffer");
						}
					} else if (part.getSize() > 1024 * 1024 * 3) { // 小於 3MB
						errorMsgs.put("recipePicTopErr", "請注意檔案尺寸過大");
						if (req.getSession().getAttribute("recipePicTopBuffer") != null) {
							recipePicTopBuffer = (byte[]) req.getSession().getAttribute("recipePicTopBuffer");
						}
					} else {
						InputStream in = part.getInputStream();
						recipePicTopBuffer = new byte[in.available()];
						in.read(recipePicTopBuffer); // 順利的話把picBuffer放進VO，然後傳回去顯示在畫面上
						in.close();
						req.getSession().setAttribute("recipePicTopBuffer", recipePicTopBuffer);
					}
				} catch (Exception e) {
					System.err.println("使用者操作時發生其他例外");
				}

				// ------------------------食譜料理分類------------------------
				List<RecipeCuisineCategoryVO> recipeCatVOs = new ArrayList<RecipeCuisineCategoryVO>();
				
				String recipeCategoryIDStr = req.getParameter("recipeCategoryIDs");
				if (recipeCategoryIDStr == null || recipeCategoryIDStr.trim().length() == 0) {
					errorMsgs.put("recipeCategoryIDErr", "請至少選取一個料理分類");
				} else {
					String[] recipeCategoryIDs = recipeCategoryIDStr.trim().split(" ");
					for (String recipeCategoryID : recipeCategoryIDs) {
						RecipeCuisineCategoryVO recipeCatVO = new RecipeCuisineCategoryVO();
						recipeCatVO.setCuisineCategoryID(new Integer(recipeCategoryID));
						recipeCatVOs.add(recipeCatVO);
					}
				}
				
				// ------------------------食譜食材單位------------------------
				List<RecipeIngredientUnitVO> recipeIngUnitVOs = new ArrayList<RecipeIngredientUnitVO>();
				
				String recipeIngredientIDStr = req.getParameter("recipeIngredientIDs");
				if (recipeIngredientIDStr == null || recipeIngredientIDStr.trim().length() == 0) {
					errorMsgs.put("recipeIngredientIDErr", "請至少選取一個食材");
				} else {
					String[] recipeIngredientIDs = recipeIngredientIDStr.trim().split(" ");
					for (String recipeIngredientID : recipeIngredientIDs) {
						RecipeIngredientUnitVO recipeIngUnitVO = new RecipeIngredientUnitVO();
						recipeIngUnitVO.setIngredientID(new Integer(recipeIngredientID));
						recipeIngUnitVOs.add(recipeIngUnitVO);
					}
				}
	
				String[] recipeUnitIDs = req.getParameterValues("unitIDs");
				if (recipeIngredientIDStr.trim().length() > 0) {
					if (recipeUnitIDs == null) {
						errorMsgs.put("recipeUnitIDErr", "請為您的所有食材選擇單位");
					} else {
						for (int index = 0; index < recipeUnitIDs.length; index++) {
							if (new Integer(recipeUnitIDs[index]) == 0) {
								errorMsgs.put("recipeUnitIDErr", "請為您的所有食材選擇單位");
							}
							RecipeIngredientUnitVO recipeIngUnitVO = recipeIngUnitVOs.get(index);
							recipeIngUnitVO.setUnitID(new Integer(recipeUnitIDs[index]));
						}
					}
				}
					
				String[] recipeUnitAmounts = req.getParameterValues("unitAmounts");
				if (recipeIngredientIDStr.trim().length() > 0 && recipeUnitIDs != null) {
					if (recipeUnitAmounts == null || recipeUnitAmounts.length == 0) { 
						errorMsgs.put("recipeunitAmountErrNull", "請為您的所有食材填寫單位數量");
//					} else if (recipeUnitAmouts.length < recipeIngredientCount) {
//						errorMsgs.put("recipeUnitAmountErrNull", "請為您的所有食材填寫單位數量！2");
					} else {
						for (int index = 0; index < recipeUnitAmounts.length; index++) {
							if (recipeUnitAmounts[index].trim().length() == 0) {
								errorMsgs.put("recipeUnitAmountErrNull", "請為您的所有食材填寫單位數量");
							}

							String recipeAmoutReg = "^[(0-9)]{1,4}.[(0-9)]{1,2}$";
							try {
								double amoutDouble = Double.parseDouble(recipeUnitAmounts[index].trim());
								if (amoutDouble <= 0) {
									recipeUnitAmounts[index] = "1";
									errorMsgs.put("recipeunitAmountErrNumber", "單位數量不可小於0");
								} else if (!BigDecimal.valueOf(amoutDouble).toString().trim().matches(recipeAmoutReg)) {
									BigDecimal amoutDCM = BigDecimal.valueOf(amoutDouble);
									recipeUnitAmounts[index] = amoutDCM.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
									errorMsgs.put("recipeunitAmountErrNumber", "單位數量必須符合個位數4位、小數2位之格式");
								}
							} catch (NumberFormatException nfe) {
								recipeUnitAmounts[index] = "1";
								errorMsgs.put("recipeunitAmountErrNumber", "請填寫數字");
							}
							RecipeIngredientUnitVO recipeIngUnitVO = recipeIngUnitVOs.get(index);
							recipeIngUnitVO.setUnitAmount(BigDecimal.valueOf(new Double(recipeUnitAmounts[index])));
						}
					}
				}
				
				// ------------------------食譜步驟------------------------
				List<RecipeStepVO> recipeStepVOs = new ArrayList<RecipeStepVO>();

				int recipeStepCount = req.getParameterValues("recipeStepOrders").length;
				String[] recipeStepOrders = req.getParameterValues("recipeStepOrders");
				for (String recipeStepOrder : recipeStepOrders) {
					RecipeStepVO recipeStepVO = new RecipeStepVO();
					recipeStepVO.setRecipeStepOrder(new Integer(recipeStepOrder));
					recipeStepVOs.add(recipeStepVO);
				}
				
				String[] recipeStepTexts = req.getParameterValues("recipeStepTexts");
//				if (recipeStepTexts == null || recipeStepTexts.length == 0) { 
//					errorMsgs.put("recipeStepErrNull", "請填寫所有食譜步驟之說明！");
//				} else {
				for (int index = 0; index < recipeStepTexts.length; index++) {
					if (recipeStepTexts[index].trim().length() == 0) {
						errorMsgs.put("recipeStepErr", "請填寫所有食譜步驟之說明");
					}
					RecipeStepVO recipeStepVO = recipeStepVOs.get(index);
					recipeStepVO.setRecipeStepText(recipeStepTexts[index]);
				}
				
				Collection<Part> collection = req.getParts();
				List<Part> parts = new ArrayList<Part>();
				for (Part part : collection) {
					if ("recipeStepPic".equals(part.getName())){
						parts.add(part);
					}
				}
				String[] oldFileIdentify = req.getParameterValues("oldFileIdentify");
//				Map<String, byte[]> recipeStepPicBuffers = null;
				if(req.getSession().getAttribute("recipeStepPicBuffers") != null) {
					recipeStepPicBuffers = (Map<Integer, byte[]>) req.getSession().getAttribute("recipeStepPicBuffers");
//					recipeStepPicBuffers = (List<byte[]>) req.getSession().getAttribute("recipeStepPicBuffers");
					System.out.println("session gets buffers OOOOOOOOOO");
				} else {
					recipeStepPicBuffers = new LinkedHashMap<Integer, byte[]>();
//					recipeStepPicBuffers = new ArrayList<byte[]>();
					System.out.println("session doesn't get buffers XXXXXXXXXX");
				}
				for(int index = 0; index < parts.size(); index++) {
					
					byte[] recipeStepPicBuffer = null;
					try {
						Part part = parts.get(index);
						if (part.getSubmittedFileName().length() == 0 || part.getContentType() == null) {
							System.out.println("使用者沒有上傳步驟" + (index + 1) + "之圖片");
							if ("false".equals(oldFileIdentify[index])) {
								recipeStepPicBuffers.remove(index);
								errorMsgs.put("recipeStepPicErr", "請上傳所有步驟之圖檔");
							}
						} else if (!part.getContentType().startsWith("image")) { 
							errorMsgs.put("recipeStepPicErr", "請上傳image類型之圖檔");
						} else if (part.getSize() > 1024 * 1024 * 3) { // 小於 3MB
							errorMsgs.put("recipeStepPicErr", "請注意檔案尺寸過大");
						} else {
							InputStream in = part.getInputStream();
							recipeStepPicBuffer = new byte[in.available()];
							in.read(recipeStepPicBuffer);
							in.close();
							System.out.println(index + 1 + ": "+ recipeStepPicBuffer.length);
							RecipeStepVO recipeStepVO = recipeStepVOs.get(index);
							recipeStepVO.setRecipeStepPic(recipeStepPicBuffer);
							recipeStepPicBuffers.put(index, recipeStepPicBuffer);
//							recipeStepPicBuffers.add(recipeStepPicBuffer);
							oldFileIdentify[index] = "true";
						}
					} catch (Exception e) {
						System.err.println("使用者操作時發生其他例外");
					}
				}
//				int outOfRange = recipeStepPicBuffers.size() - parts.size();
//				System.out.println(outOfRange);
//				while(outOfRange > 0) {
//					recipeStepPicBuffers.remove(String.valueOf(outOfRange));
//					outOfRange--;
//				}
				System.out.println(recipeStepPicBuffers.toString());
				req.getSession().setAttribute("recipeStepPicBuffers", recipeStepPicBuffers);
				req.setAttribute("oldFileIdentify", oldFileIdentify);
				
				String agreement = req.getParameter("agreement");
				if (agreement == null || agreement.trim().length() == 0) {
					errorMsgs.put("agreementErr", "未勾選同意本站之使用規範與協議");
				} else if (!"agree".equals(agreement.trim())) {
					errorMsgs.put("agreementErr", "未勾選同意本站之使用規範與協議");
				}
				
				RecipeVO recipeVO = new RecipeVO();
				recipeVO.setRecipeName(recipeName);
				recipeVO.setRecipeIntroduction(recipeIntroduction);
				recipeVO.setRecipeServe(recipeServe);
				if (recipePicTopBuffer != null) recipeVO.setRecipePicTop(recipePicTopBuffer);
				
	
				// Send the use back to the form, if there were errors ==========================
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("recipeVO", recipeVO); // 含有輸入格式錯誤的VO物件,也存入req
					req.setAttribute("recipeCatVOs", recipeCatVOs);
					req.setAttribute("recipeIngUnitVOs", recipeIngUnitVOs);
					req.setAttribute("recipeStepVOs", recipeStepVOs);
					RequestDispatcher failureView = req.getRequestDispatcher("/Recipe/addRecipe.jsp");
					failureView.forward(req, res);
					return;
				}
				
				// All parameters are correct, so we can send them to db ==========================
				RecipeService recipeSvc = new RecipeService();
				recipeVO = null;
				recipeVO = recipeSvc.addRecipeWithDetails(
										recipeName, recipeIntroduction, 
										recipePicTopBuffer, recipeServe, 
										100001, recipeCatVOs, recipeIngUnitVOs, recipeStepVOs);
				
				if (recipeVO != null) {
					System.out.println("新增成功");
					req.getSession().removeAttribute("recipePicTopBuffer");
					req.getSession().removeAttribute("recipeStepPicBuffers");
					RequestDispatcher successView = req.getRequestDispatcher("/Recipe/recipe.jsp?id=" + recipeVO.getRecipeID());
					successView.forward(req, res);
				}
				
			} catch (Exception e) {
				errorMsgs.put("UnknowErr", "其他錯誤:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/Recipe/addRecipe.jsp");
				failureView.forward(req, res);
			}
		}
		
		

		/**********************取得一個食譜，轉交給updateRecipe.jsp**********************/
		if ("getOneForUpdate".equals(action)) { // 來自listAllRecipe.jsp的請求
			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				Integer recipeID = new Integer(req.getParameter("recipeID"));
				
				RecipeService recipeSvc = new RecipeService();
				RecipeVO recipeVO = recipeSvc.getOneRecipe(recipeID);
				
				req.setAttribute("recipeVO", recipeVO);
				RequestDispatcher successView = req.getRequestDispatcher("/Recipe/updateRecipe.jsp");
				successView.forward(req, res);
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.put("UnknowErr", "其他錯誤:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/Recipe/listAllRecipe.jsp");
				failureView.forward(req, res);
			}
		}

		

		/**********************更新食譜**********************/
		if ("update".equals(action)) { // 來自updateRecipe.jsp的請求
			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			RecipeService recipeSvc = new RecipeService();
			RecipeCuisineCategoryService recipeCatSvc = new RecipeCuisineCategoryService();
			RecipeIngredientUnitService recipeIngUnitSvc = new RecipeIngredientUnitService();
			RecipeStepService recipeStepSvc = new RecipeStepService();

			try {
				Integer recipeID = new Integer(req.getParameter("recipeID"));
				
				// ------------------------食譜------------------------
				String recipeName = req.getParameter("recipeName");
				String recipeNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9)]{3,45}$"; // 逗號後面不可以亂空白，REX的規定
				if (recipeName == null || recipeName.trim().length() == 0) {
					errorMsgs.put("recipeNameErr", "食譜名稱請勿空白");
				} else if (!recipeName.trim().matches(recipeNameReg)) {
					errorMsgs.put("recipeNameErr", "食譜名稱只能是中、英字母、數字，英數3至45字、中文1至15字");
				}
				
				String recipeIntroduction = req.getParameter("recipeIntroduction");
				String recipeIntroductionReg = "^.{3,450}$";
				if (recipeName == null || recipeName.trim().length() == 0) {
					errorMsgs.put("recipeIntroductionErr", "食譜介紹請勿空白");
				} else if (!recipeIntroduction.trim().matches(recipeIntroductionReg)) {
					errorMsgs.put("recipeIntroductionErr", "食譜介紹長度需介於英數3至450字、中文1至150字");
				}
	
				Integer recipeServe = null;
				try {
					recipeServe = new Integer(req.getParameter("recipeServe"));
					if (recipeServe < 1 || recipeServe > 20) {
						errorMsgs.put("recipeServeErr", "享用人數請勿低於1人，或高於20人");
					}
				} catch (NumberFormatException e) {
					recipeServe = 1;
					errorMsgs.put("recipeServeErr", "享用人數填寫格式錯誤，請勿空白，並請填寫1至20人份之數字");
				}
	
				byte[] recipePicTopBuffer = null;
				try {
					Part part = req.getPart("recipePicTop");
	
					if (part.getSubmittedFileName().length() == 0 || part.getContentType() == null) {
						System.out.println("使用者沒有上傳置頂圖片");
						if (req.getSession().getAttribute("recipePicTopBuffer") != null) {
							recipePicTopBuffer = (byte[]) req.getSession().getAttribute("recipePicTopBuffer");
						} else {
							recipePicTopBuffer = ((RecipeVO) recipeSvc.getOneRecipe(recipeID)).getRecipePicTop();
							req.getSession().setAttribute("recipePicTopBuffer", recipePicTopBuffer);
//							errorMsgs.put("recipePicTopErr", "請上傳置頂圖之圖檔");
//							System.out.println(req.getSession().getAttribute("recipePicTopBuffer"));
						}
					} else if (!part.getContentType().startsWith("image")) { 
						errorMsgs.put("recipePicTopErr", "請上傳image類型之圖檔");
						if (req.getSession().getAttribute("recipePicTopBuffer") != null) {
							recipePicTopBuffer = (byte[]) req.getSession().getAttribute("recipePicTopBuffer");
						}
					} else if (part.getSize() > 1024 * 1024 * 3) { // 小於 3MB
						errorMsgs.put("recipePicTopErr", "請注意檔案尺寸過大");
						if (req.getSession().getAttribute("recipePicTopBuffer") != null) {
							recipePicTopBuffer = (byte[]) req.getSession().getAttribute("recipePicTopBuffer");
						}
					} else {
						InputStream in = part.getInputStream();
						recipePicTopBuffer = new byte[in.available()];
						in.read(recipePicTopBuffer);
						in.close();
						req.getSession().setAttribute("recipePicTopBuffer", recipePicTopBuffer);
					}
				} catch (Exception e) {
					System.err.println("使用者操作時發生其他例外");
				}

				// ------------------------食譜料理分類------------------------
				List<RecipeCuisineCategoryVO> orgRecipeCatVOs = recipeCatSvc.getAllByRecipe(recipeID);	// 最後2個陣列全部裝一起用Hashset比對
				List<RecipeCuisineCategoryVO> recipeCatVOs = new ArrayList<RecipeCuisineCategoryVO>();
				
				String recipeCategoryIDStr = req.getParameter("recipeCategoryIDs");
				if (recipeCategoryIDStr == null || recipeCategoryIDStr.trim().length() == 0) {
					errorMsgs.put("recipeCategoryIDErr", "請至少選取一個料理分類");
				} else {
					String[] recipeCategoryIDs = recipeCategoryIDStr.trim().split(" ");
					for (String recipeCategoryID : recipeCategoryIDs) {
						RecipeCuisineCategoryVO recipeCatVO = new RecipeCuisineCategoryVO();
						recipeCatVO.setRecipeID(recipeID);
						recipeCatVO.setCuisineCategoryID(new Integer(recipeCategoryID));
						recipeCatVOs.add(recipeCatVO);
					}
				}
				
				// ------------------------食譜食材單位------------------------
				List<RecipeIngredientUnitVO> orgRecipeIngUnitVOs = recipeIngUnitSvc.getAllByRecipe(recipeID);	// 最後2個陣列全部裝一起用Hashset比對
				List<RecipeIngredientUnitVO> recipeIngUnitVOs = new ArrayList<RecipeIngredientUnitVO>();
				
				String recipeIngredientIDStr = req.getParameter("recipeIngredientIDs");
				if (recipeIngredientIDStr == null || recipeIngredientIDStr.trim().length() == 0) {
					errorMsgs.put("recipeIngredientIDErr", "請至少選取一個食材");
				} else {
					String[] recipeIngredientIDs = recipeIngredientIDStr.trim().split(" ");
					for (String recipeIngredientID : recipeIngredientIDs) {
						RecipeIngredientUnitVO recipeIngUnitVO = new RecipeIngredientUnitVO();
						recipeIngUnitVO.setRecipeID(recipeID);
						recipeIngUnitVO.setIngredientID(new Integer(recipeIngredientID));
						recipeIngUnitVOs.add(recipeIngUnitVO);
					}
				}
	
				String[] recipeUnitIDs = req.getParameterValues("unitIDs");
				if (recipeIngredientIDStr.trim().length() > 0) {
					if (recipeUnitIDs == null) {
						errorMsgs.put("recipeUnitIDErr", "請為您的所有食材選擇單位");
					} else {
						for (int index = 0; index < recipeUnitIDs.length; index++) {
							if (new Integer(recipeUnitIDs[index]) == 0) {
								errorMsgs.put("recipeUnitIDErr", "請為您的所有食材選擇單位");
							}
							RecipeIngredientUnitVO recipeIngUnitVO = recipeIngUnitVOs.get(index);
							recipeIngUnitVO.setUnitID(new Integer(recipeUnitIDs[index]));
						}
					}
				}
					
				String[] recipeUnitAmounts = req.getParameterValues("unitAmounts");
				if (recipeIngredientIDStr.trim().length() > 0 && recipeUnitIDs != null) {
					if (recipeUnitAmounts == null || recipeUnitAmounts.length == 0) { 
						errorMsgs.put("recipeunitAmountErrNull", "請為您的所有食材填寫單位數量");
					} else {
						for (int index = 0; index < recipeUnitAmounts.length; index++) {
							if (recipeUnitAmounts[index].trim().length() == 0) {
								errorMsgs.put("recipeUnitAmountErrNull", "請為您的所有食材填寫單位數量");
							}

							String recipeAmoutReg = "^[(0-9)]{1,4}.[(0-9)]{1,2}$";
							try {
								double amoutDouble = Double.parseDouble(recipeUnitAmounts[index].trim());
								if (amoutDouble <= 0) {
									recipeUnitAmounts[index] = "1";
									errorMsgs.put("recipeunitAmountErrNumber", "單位數量不可小於0");
								} else if (!BigDecimal.valueOf(amoutDouble).toString().trim().matches(recipeAmoutReg)) {
									BigDecimal amoutDCM = BigDecimal.valueOf(amoutDouble);
//									BigDecimal amoutDCM = new BigDecimal(amoutDouble);
									recipeUnitAmounts[index] = amoutDCM.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
									System.out.println();
									errorMsgs.put("recipeunitAmountErrNumber", "單位數量必須符合個位數4位、小數2位之格式");
								}
							} catch (NumberFormatException nfe) {
								recipeUnitAmounts[index] = "1";
								errorMsgs.put("recipeunitAmountErrNumber", "請填寫數字");
							}
							RecipeIngredientUnitVO recipeIngUnitVO = recipeIngUnitVOs.get(index);
							recipeIngUnitVO.setUnitAmount(BigDecimal.valueOf(new Double(recipeUnitAmounts[index])));
						}
					}
				}
				
				// ------------------------食譜步驟------------------------
				List<RecipeStepVO> orgRecipeStepVOs = recipeStepSvc.getAllByRecipe(recipeID);	// 最後2個陣列全部裝一起用Hashset比對
				List<RecipeStepVO> recipeStepVOs = new ArrayList<RecipeStepVO>();

				String[] recipeStepOrders = req.getParameterValues("recipeStepOrders");
				for (String recipeStepOrder : recipeStepOrders) {
					RecipeStepVO recipeStepVO = new RecipeStepVO();
					recipeStepVO.setRecipeID(recipeID);
					recipeStepVO.setRecipeStepOrder(new Integer(recipeStepOrder));
					recipeStepVOs.add(recipeStepVO);
				}
				
				String[] recipeStepTexts = req.getParameterValues("recipeStepTexts");
				for (int index = 0; index < recipeStepTexts.length; index++) {
					if (recipeStepTexts[index].trim().length() == 0) {
						errorMsgs.put("recipeStepErr", "請填寫所有食譜步驟之說明");
					}
					RecipeStepVO recipeStepVO = recipeStepVOs.get(index);
					recipeStepVO.setRecipeStepText(recipeStepTexts[index]);
				}
				
				Collection<Part> collection = req.getParts();
				List<Part> parts = new ArrayList<Part>();
				for (Part part : collection) {
					if ("recipeStepPic".equals(part.getName())){
						parts.add(part);
					}
				}

				Map<Integer, byte[]> orgRecipeStepPicBuffers = new LinkedHashMap<Integer, byte[]>();
				for (RecipeStepVO orgRecipeStepVO : orgRecipeStepVOs) {
					orgRecipeStepPicBuffers.put(new Integer(orgRecipeStepVO.getRecipeStepOrder()-1), orgRecipeStepVO.getRecipeStepPic());
				}
				String[] oldFileIdentify = req.getParameterValues("oldFileIdentify");
				if (req.getSession().getAttribute("recipeStepPicBuffers") != null) {
					recipeStepPicBuffers = (Map<Integer, byte[]>) req.getSession().getAttribute("recipeStepPicBuffers");
					System.out.println("session gets buffers! OOOOOOOOOO");
				} else {
					recipeStepPicBuffers = orgRecipeStepPicBuffers;
					req.getSession().setAttribute("recipeStepPicBuffers", recipeStepPicBuffers);
//					recipeStepPicBuffers = new LinkedHashMap<Integer, byte[]>();
					System.out.println("session doesn't get buffers so we take original buffers to use. XXXXXXXXX");
				}
				for(int index = 0; index < parts.size(); index++) {
					
					byte[] recipeStepPicBuffer = null;
					try {
						Part part = parts.get(index);
						if (part.getSubmittedFileName().length() == 0 || part.getContentType() == null) {
							System.out.println("使用者沒有上傳步驟" + (index + 1) + "之圖片");
							
							RecipeStepVO recipeStepVO = recipeStepVOs.get(index);
							if (req.getSession().getAttribute("recipePicTopBuffer") != null) {
								recipeStepVO.setRecipeStepPic((byte[]) ((Map<Integer, byte[]>) req.getSession().getAttribute("recipeStepPicBuffers")).get(index));
							}
							if ("false".equals(oldFileIdentify[index])) {
								recipeStepPicBuffers.remove(index);
								errorMsgs.put("recipeStepPicErr", "請上傳所有步驟之圖檔");
							}
//							else {
//								if (req.getSession().getAttribute("recipePicTopBuffer") != null) {
//									recipeStepPicBuffer = (byte[]) ((Map<Integer, byte[]>) req.getSession().getAttribute("recipeStepPicBuffers")).get(index);
//									recipeStepPicBuffers.put(index, recipeStepPicBuffer);
//								}
//							}
						} else if (!part.getContentType().startsWith("image")) { 
							errorMsgs.put("recipeStepPicErr", "請上傳image類型之圖檔");
							RecipeStepVO recipeStepVO = recipeStepVOs.get(index);
							if (req.getSession().getAttribute("recipePicTopBuffer") != null) {
								recipeStepVO.setRecipeStepPic((byte[]) ((Map<Integer, byte[]>) req.getSession().getAttribute("recipeStepPicBuffers")).get(index));
							}
						} else if (part.getSize() > 1024 * 1024 * 3) { // 小於 3MB
							errorMsgs.put("recipeStepPicErr", "請注意檔案尺寸過大");
							RecipeStepVO recipeStepVO = recipeStepVOs.get(index);
							if (req.getSession().getAttribute("recipePicTopBuffer") != null) {
								recipeStepVO.setRecipeStepPic((byte[]) ((Map<Integer, byte[]>) req.getSession().getAttribute("recipeStepPicBuffers")).get(index));
							}
						} else {
							InputStream in = part.getInputStream();
							recipeStepPicBuffer = new byte[in.available()];
							in.read(recipeStepPicBuffer);
							in.close();
							System.out.println(index + 1 + ": "+ recipeStepPicBuffer.length);
							RecipeStepVO recipeStepVO = recipeStepVOs.get(index);
							recipeStepVO.setRecipeStepPic(recipeStepPicBuffer);
							recipeStepPicBuffers.put(index, recipeStepPicBuffer);
							oldFileIdentify[index] = "true";
						}
					} catch (Exception e) {
						System.err.println("使用者操作時發生其他例外");
					}
				}
				System.out.println(recipeStepPicBuffers.toString());
				req.getSession().setAttribute("recipeStepPicBuffers", recipeStepPicBuffers);
				req.setAttribute("oldFileIdentify", oldFileIdentify);
				
				String agreement = req.getParameter("agreement");
				System.out.println(agreement);
				if (agreement == null || agreement.trim().length() == 0) {
					errorMsgs.put("agreementErr", "未勾選同意本站之使用規範與協議");
				} else if (!"agree".equals(agreement.trim())) {
					errorMsgs.put("agreementErr", "未勾選同意本站之使用規範與協議");
				}
				
				RecipeVO recipeVO = new RecipeVO();
				recipeVO.setRecipeID(recipeID);
				recipeVO.setRecipeName(recipeName);
				recipeVO.setRecipeIntroduction(recipeIntroduction);
				recipeVO.setRecipeServe(recipeServe);
				recipeVO.setRecipePicTop(recipePicTopBuffer);
				
	
				// Send the use back to the form, if there were errors ==========================
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("recipeVO", recipeVO); // 含有輸入格式錯誤的VO物件,也存入req
					req.setAttribute("recipeCatVOs", recipeCatVOs);
					req.setAttribute("recipeIngUnitVOs", recipeIngUnitVOs);
					req.setAttribute("recipeStepVOs", recipeStepVOs);
					RequestDispatcher failureView = req.getRequestDispatcher("/Recipe/updateRecipe.jsp");
					failureView.forward(req, res);
					return;
				}

				// All parameters are correct, so we can send them to db ==========================
				
				// ----------------比對食譜料理分類是否有重複----------------
				// 全集：舊資料+新資料，結果：recipeCatAllSet
				Collection<RecipeCuisineCategoryVO> orgRecipeCatCol = new ArrayList<RecipeCuisineCategoryVO>(orgRecipeCatVOs);
				Collection<RecipeCuisineCategoryVO> recipeCatCol = new ArrayList<RecipeCuisineCategoryVO>(recipeCatVOs);
				Set<RecipeCuisineCategoryVO> recipeCatAllSet = new HashSet<RecipeCuisineCategoryVO>();
				recipeCatAllSet.addAll(orgRecipeCatVOs);
				recipeCatAllSet.addAll(recipeCatVOs);
				Collection<RecipeCuisineCategoryVO> recipeCatDiff = new ArrayList<RecipeCuisineCategoryVO>(recipeCatAllSet);
				recipeCatDiff.removeAll(recipeCatCol);
//				System.out.println("------刪除------");
//				for (RecipeCuisineCategoryVO one : recipeCatDiff) {
//					System.out.println(one.getCuisineCategoryID());
//				}
				Collection<RecipeCuisineCategoryVO> recipeCatAdd = new ArrayList<RecipeCuisineCategoryVO>(recipeCatAllSet);
				recipeCatAdd.removeAll(orgRecipeCatCol);
//				System.out.println("------新增------");
//				for (RecipeCuisineCategoryVO one : recipeCatAdd) {
//					System.out.println(one.getCuisineCategoryID());
//				}
				
				// ----------------比對食譜食材單位是否有重複----------------
				Collection<RecipeIngredientUnitVO> orgRecipeIngUnitCol = new ArrayList<RecipeIngredientUnitVO>(orgRecipeIngUnitVOs);
				Collection<RecipeIngredientUnitVO> recipeIngUnitCol = new ArrayList<RecipeIngredientUnitVO>(recipeIngUnitVOs);
				Set<RecipeIngredientUnitVO> recipeIngUnitAllSet = new HashSet<RecipeIngredientUnitVO>();
				recipeIngUnitAllSet.addAll(orgRecipeIngUnitVOs);
				recipeIngUnitAllSet.addAll(recipeIngUnitVOs);		// 全集
				Collection<RecipeIngredientUnitVO> recipeIngUnitDiff = new ArrayList<RecipeIngredientUnitVO>(recipeIngUnitAllSet);
				recipeIngUnitDiff.removeAll(recipeIngUnitCol);		// 要刪除的資料=全集-新資料
//				System.out.println("---刪除---");
//				for(RecipeIngredientUnitVO one : recipeIngUnitDiff) {
//					System.out.print("getIngredientID:" + one.getIngredientID());
//					System.out.print("-getUnitID:" + one.getUnitID());
//					System.out.println("-getUnitAmount:" + one.getUnitAmount());
//				}
				Collection<RecipeIngredientUnitVO> recipeIngUnitAdd = new ArrayList<RecipeIngredientUnitVO>(recipeIngUnitAllSet);
				recipeIngUnitAdd.removeAll(orgRecipeIngUnitCol);	// 要新增的資料=全集-舊資料
//				System.out.println("---新增---");
//				for(RecipeIngredientUnitVO one : recipeIngUnitAdd) {
//					System.out.print("getIngredientID:" + one.getIngredientID());
//					System.out.print("-getUnitID:" + one.getUnitID());
//					System.out.println("-getUnitAmount:" + one.getUnitAmount());
//				}
				
				// ----------------比對食譜步驟是否有重複----------------
				Collection<RecipeStepVO> orgRecipeStepCol = new ArrayList<RecipeStepVO>(orgRecipeStepVOs);
				Collection<RecipeStepVO> recipeStepCol = new ArrayList<RecipeStepVO>(recipeStepVOs);
				Set<RecipeStepVO> recipeStepAllSet = new HashSet<RecipeStepVO>();
				recipeStepAllSet.addAll(orgRecipeStepVOs);
				recipeStepAllSet.addAll(recipeStepVOs);			// 全集
//				System.out.println("---全集---");
//				for(RecipeStepVO one : recipeStepAllSet) {
//					System.out.print("getRecipeStepOrder:" + one.getRecipeStepOrder());
//					System.out.print("-getRecipeStepText:" + one.getRecipeStepText());
//					System.out.println("-getRecipeStepPic():" + one.getRecipeStepPic());
//				}
				Collection<RecipeStepVO> recipeStepDiff = new ArrayList<RecipeStepVO>(recipeStepAllSet);
				recipeStepDiff.removeAll(recipeStepCol);		// 要刪除的資料=全集-新資料
//				System.out.println("---刪除---");
//				for(RecipeStepVO one : recipeStepDiff) {
//					System.out.print("getRecipeStepOrder:" + one.getRecipeStepOrder());
//					System.out.print("-getRecipeStepText:" + one.getRecipeStepText());
//					System.out.println("-getRecipeStepPic():" + one.getRecipeStepPic());
//				}
				Collection<RecipeStepVO> recipeStepAdd = new ArrayList<RecipeStepVO>(recipeStepAllSet);
				recipeStepAdd.removeAll(orgRecipeStepCol);		// 要新增的資料=全集-舊資料
//				System.out.println("---新增---");
//				for(RecipeStepVO one : recipeStepAdd) {
//					System.out.print("getRecipeStepOrder:" + one.getRecipeStepOrder());
//					System.out.print("-getRecipeStepText:" + one.getRecipeStepText());
//					System.out.println("-getRecipeStepPic():" + one.getRecipeStepPic());
//				}
				
				recipeSvc.updateRecipeWithDetails(recipeVO,
						(List<RecipeCuisineCategoryVO>) recipeCatDiff, (List<RecipeCuisineCategoryVO>) recipeCatAdd, 
						(List<RecipeIngredientUnitVO>) recipeIngUnitDiff, (List<RecipeIngredientUnitVO>) recipeIngUnitAdd,
						(List<RecipeStepVO>) recipeStepDiff, (List<RecipeStepVO>) recipeStepAdd);
				
				req.getSession().removeAttribute("recipePicTopBuffer");
				req.getSession().removeAttribute("recipeStepPicBuffers");
				RequestDispatcher successView = req.getRequestDispatcher("/Recipe/recipe.jsp?id=" + recipeVO.getRecipeID());
				successView.forward(req, res);
				
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.put("UnknowErr", "其他錯誤:" + e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/Recipe/updateRecipe.jsp");
//				failureView.forward(req, res);
			}
		}
		
		
	}

}
