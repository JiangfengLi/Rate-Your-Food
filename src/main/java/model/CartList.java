package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class CartList {
    private ObservableList<Ingredient> ingredientList = null;
    private static final CartList cartList = new CartList();

    public static CartList getCartList()
    {
        return cartList;
    }

    public ObservableList<Ingredient> getIngredientList() {
        if( ingredientList == null )
        {
            ingredientList = FXCollections.observableArrayList();
        }
        return ingredientList;
    }

    public void addToList( Ingredient aIngredient )
    {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add( aIngredient );
        addBulkIngredients( ingredients );
    }

    public void addBulkIngredients( List<Ingredient> bulkIngredients )
    {
        for( Ingredient aIngredient : bulkIngredients )
        {
            boolean found = false;
            for (Ingredient originalIngredient : ingredientList) {
                if (aIngredient.getName().equals(originalIngredient.getName())) {
                    updateIngredient(aIngredient);
                    found = true;
                    break;
                }
            }
            if( !found )
            {
                ingredientList.add(aIngredient);
            }
        }
    }

    private void updateIngredient( Ingredient aIngredient )
    {
        for (Ingredient originalIngredient : ingredientList) {
            if (aIngredient.getName().equals(originalIngredient.getName())) {
                int index = ingredientList.indexOf(originalIngredient);
                Ingredient foundIngredient = ingredientList.get(index);
                foundIngredient.setAmount(foundIngredient.getAmount() + aIngredient.getAmount());
                ingredientList.set(index, foundIngredient);
                break;
            }
        }
    }
}