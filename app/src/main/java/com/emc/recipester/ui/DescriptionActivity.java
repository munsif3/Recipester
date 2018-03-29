package com.emc.recipester.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.emc.recipester.R;
import com.emc.recipester.core.Callback;
import com.emc.recipester.core.RecipeService;
import com.emc.recipester.data.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class DescriptionActivity extends AppCompatActivity implements Callback {

    TextView recipeTitle;
    TextView ingredients;
    TextView method;
    ImageView recipeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        String category = getIntent().getExtras().getString("category");
        int id = getIntent().getExtras().getInt("recipeId");

        RecipeService recipeService = new RecipeService(this);
        recipeService.requestRecipesById(category, id);

        recipeTitle = findViewById(R.id.txtItemName);
        ingredients = findViewById(R.id.txtIngredients);
        method = findViewById(R.id.txtSteps);
        recipeImage = findViewById(R.id.imgRecipe);
    }

    @Override
    public void onCallbackCompleted(String data) {
        Gson gson = new Gson();
        Type type = new TypeToken<Recipe>() {
        }.getType();
        Recipe recipe = gson.fromJson(data, type);
        recipeTitle.setText(recipe.getName());
        StringBuilder ingredientsBuilder = new StringBuilder();
        for (String i : recipe.getIngredients()) {
            ingredientsBuilder.append(i).append("\n\n");
        }
        ingredients.setText(ingredientsBuilder);
        StringBuilder stepsBuilder = new StringBuilder();
        for (String i : recipe.getSteps()) {
            stepsBuilder.append(i).append("\n\n");
        }
        method.setText(stepsBuilder);
    }
}
