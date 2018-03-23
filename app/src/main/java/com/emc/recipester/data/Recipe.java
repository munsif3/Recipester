package com.emc.recipester.data;

import android.media.Image;

/**
 * Created by Munsif on 3/19/2018.
 */

public class Recipe {

    private Integer recipeId;
    private String recipeTitle;
    private Image recipeImage;
    private String[] recipeIngredients;
    private String[] recipeSteps;

    public Recipe(Integer recipeId, String recipeTitle, Image recipeImage, String[] recipeIngredients, String[] recipeSteps) {
        this.recipeId = recipeId;
        this.recipeTitle = recipeTitle;
        this.recipeImage = recipeImage;
        this.recipeIngredients = recipeIngredients;
        this.recipeSteps = recipeSteps;
    }

    public Recipe() {

    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public void setRecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public Image getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(Image recipeImage) {
        this.recipeImage = recipeImage;
    }

    public String[] getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(String[] recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public String[] getRecipeSteps() {
        return recipeSteps;
    }

    public void setRecipeSteps(String[] recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

}
