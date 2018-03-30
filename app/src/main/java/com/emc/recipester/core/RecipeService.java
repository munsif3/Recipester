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
        WebServiceExecutor webServiceExecutor = new WebServiceExecutor(BASE_URL + category, callback);
        webServiceExecutor.execute();
    }

    public void requestRecipesById(String category, int recipeId) {
        WebServiceExecutor webServiceExecutor = new WebServiceExecutor(BASE_URL + category + "/" + recipeId, callback);
        webServiceExecutor.execute(recipeId);
    }
}
