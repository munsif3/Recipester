package com.emc.recipester.core;

/**
 * Created by Munsif on 3/21/2018.
 */

public class RecipeService {

    private Callback callback;
    private static final String BASE_URL = "https://us-central1-recipes-api-3180b.cloudfunctions.net/app/api/recipes/";

    public RecipeService(Callback callback) {
        this.callback = callback;
    }

    public void requestCategoriesById() {
        Executor executor = new Executor(BASE_URL + "categories", callback);
        executor.execute();
    }

    public void requestRecipesById(int recipeId) {
        Executor executor = new Executor(BASE_URL + "recipes", callback);
        executor.execute(recipeId);
    }
}
