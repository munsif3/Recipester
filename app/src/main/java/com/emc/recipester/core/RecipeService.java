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

    public void requestRecipesByCategories(String category) {
        Executor executor = new Executor(BASE_URL + category, callback);
        executor.execute();
    }

    public void requestRecipesById(String category, int recipeId) {
        Executor executor = new Executor(BASE_URL + category + "/" + recipeId, callback);
        executor.execute(recipeId);
    }
}
