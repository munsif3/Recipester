package com.emc.recipester.data;

import android.media.Image;

/**
 * Created by Munsif on 3/19/2018.
 */

class Recipe {
    private Integer recipeId;
    private String recipeTitle;
    private Image recipeImage;

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
}
