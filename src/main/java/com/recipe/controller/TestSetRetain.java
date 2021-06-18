package com.recipe.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.recipecuisinecategory.model.RecipeCuisineCategoryVO;

public class TestSetRetain {

	public static void main(String args[]) {

		RecipeCuisineCategoryVO vo1 = new RecipeCuisineCategoryVO();
		vo1.setRecipeID(200001);
		vo1.setCuisineCategoryID(250001);
		
		RecipeCuisineCategoryVO vo2 = new RecipeCuisineCategoryVO();
		vo2.setRecipeID(200001);
		vo2.setCuisineCategoryID(250001);
		
		System.out.println(vo1.equals(vo2));
		System.out.println(vo1.getCuisineCategoryID().equals(vo2.getCuisineCategoryID()));

		RecipeCuisineCategoryVO vo3 = new RecipeCuisineCategoryVO();
		vo3.setRecipeID(200001);
		vo3.setCuisineCategoryID(250002);
		
		RecipeCuisineCategoryVO vo4 = new RecipeCuisineCategoryVO();
		vo4.setRecipeID(200001);
		vo4.setCuisineCategoryID(250003);

		RecipeCuisineCategoryVO vo5 = new RecipeCuisineCategoryVO();
		vo5.setRecipeID(200001);
		vo5.setCuisineCategoryID(250001);
		
		System.out.println(vo1);
		System.out.println(vo2);
		System.out.println(vo3);
		System.out.println(vo4);
		System.out.println(vo5);
		
		List<RecipeCuisineCategoryVO> list1 = new ArrayList<RecipeCuisineCategoryVO>();
		list1.add(vo1);
		list1.add(vo2);
		list1.add(vo3);
		list1.add(vo4);
		list1.add(vo5);
		
		list1 = new ArrayList<RecipeCuisineCategoryVO>(new HashSet<RecipeCuisineCategoryVO>(list1));
		System.out.println(list1);

	}

}
