package com.emc.recipester.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.emc.recipester.R;
import com.emc.recipester.core.Callback;
import com.emc.recipester.core.RecipeService;

public class DescriptionActivity extends AppCompatActivity implements Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        int id = getIntent().getExtras().getInt("recipeId");

        RecipeService recipeService = new RecipeService(this);
        recipeService.requestRecipesById(id);
    }

    @Override
    public void onCallbackCompleted(String data) {

    }
}
