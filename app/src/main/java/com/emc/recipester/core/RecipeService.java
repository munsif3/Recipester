package com.emc.recipester.core;

/**
 * Created by Munsif on 3/21/2018.
 */

public class RecipeService {

    private Callback callback;
    private static final String BASE_URL = "http://localhost:3030/api/recipes";

    public RecipeService(Callback callback) {
        this.callback = callback;
    }

    public void requestAllRecipes() {
        Executor executor = new Executor(BASE_URL, callback);
        executor.execute();
    }

    public void requestRecipeById(int id) {
        Executor executor = new Executor(BASE_URL, callback);
        executor.execute(id);
    }
}
