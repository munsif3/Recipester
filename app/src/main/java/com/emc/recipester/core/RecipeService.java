package com.emc.recipester.core;

/**
 * Created by Munsif on 3/21/2018.
 */

public class RecipeService {

    private Callback callback;
    private static final String BASE_URL = "http://10.0.2.2/api/";

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
