package com.recipestep.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.recipestep.model.RecipeStepService;
import com.recipestep.model.RecipeStepVO;
@WebServlet("/Recipe/deleteRecipeStep.do")
public class DeleteOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");		// for ajax，但輸出的參數是數字，其實可以不用設...
		
		// 會不會有誤刪？記得測
		/**********************刪除食譜步驟**********************/
		String delOrder = req.getParameter("delOrder");
		String recipeIDforUpdate = req.getParameter("update");
		Map<Integer, byte[]> recipeStepPicBuffers = null;
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
			
			if (req.getSession().getAttribute("recipeStepPicBuffers") != null) {
				
				recipeStepPicBuffers = (Map<Integer, byte[]>) req.getSession().getAttribute("recipeStepPicBuffers");
				Map<Integer, byte[]> deepCopy = new LinkedHashMap<Integer, byte[]>(recipeStepPicBuffers);
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
		        return;
			}
		}
	}
}
