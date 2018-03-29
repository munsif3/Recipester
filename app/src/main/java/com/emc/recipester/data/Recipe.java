package com.emc.recipester.data;

//import android.graphics.String;
//import android.media.Image;

/**
 * Created by Munsif on 3/19/2018.
 */

public class Recipe {

    private Integer id;
    private String name;
    private String image;
    private String[] ingredients;
    private String[] steps;

    public Recipe(Integer id, String name, String image, String[] ingredients, String[] steps) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public Recipe() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getSteps() {
        return steps;
    }

    public void setSteps(String[] steps) {
        this.steps = steps;
    }

}
