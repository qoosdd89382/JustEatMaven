package com.productandingredient.model;


import java.io.Serializable;

public class ProductAndIngredientVO implements Serializable {
	private static final long serialVersionUID = 1L;
//	product_id		Int not null comment "�ӫ~�y����",
//	ingredient_id	int not null comment "�������Ҭy����",
//    constraint PK_ProductAndIngredient primary key (product_id, ingredient_id),
//    constraint FK_ProductAndIngredient_Product foreign key (product_id) references Product (product_id) on delete cascade,
//    constraint FK_ProductAndIngredient_Ingredient foreign key (ingredient_id) references Ingredient (ingredient_id) on delete cascade

	private Integer product_id;//FK
	private Integer ingredient_id;// (FK)
	
	public ProductAndIngredientVO() {
		super();
	}

	public Integer getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}

	public Integer getIngredient_id() {
		return ingredient_id;
	}

	public void setIngredient_id(Integer ingredient_id) {
		this.ingredient_id = ingredient_id;
	}
	


}