package model;

/*
 * create a baseline for the ingredient
 * used for the recipe and Shopping cart
 * 
 * WIP
 * 
 * Joseph Corona 
 */
public class Ingredient {
	
	private String name;
	private int amount;
	private String amountMeasure; // for type e.g. grams, ounces.
	
	public Ingredient() {
		
		name = "testing";
		amount = 1;
		amountMeasure = null;
	}
	
	public Ingredient(String name, int amount) {
		this.name = name;
		this.amount = amount;
		this.amountMeasure = null;
	}
	
	public Ingredient(String name, int amount, String amountType) {
		this.name = name;
		this.amount = amount;
		this.amountMeasure = amountType;
		
	}
	
	
	public String toString() {
		
		if (amountMeasure == null) {
			return "" + amount + " " + name;
		
		} else {
			return "" + amount + " " + amountMeasure + " " + name;
		}
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getAmountType() {
		return amountMeasure;
	}

	public void setAmountType(String amountType) {
		this.amountMeasure = amountType;
	}
	
	

}
