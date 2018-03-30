package com.emc.recipester.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.emc.recipester.R;
import com.emc.recipester.core.Callback;
import com.emc.recipester.core.RecipeService;
import com.emc.recipester.data.Recipe;
import com.emc.recipester.data.RecipesListAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RecipesListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, Callback {

    private String titleName = null;
    private RecipesListAdapter recipeListAdapter;
    List<Recipe> output = new ArrayList<>();
    ListView listView;
    static final String STATE_RECIPES = "recipesList.state";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);

        titleName = getIntent().getExtras().getString("category");
        getSupportActionBar().setTitle(titleName);

        RecipeService recipeService = new RecipeService(this);
        recipeService.requestRecipesByCategories(titleName);

        if (savedInstanceState != null) {
            listView = (ListView) savedInstanceState.getSerializable(STATE_RECIPES);
        }

        listView = findViewById(R.id.recipeListView);
        listView.setOnItemClickListener(this);

        recipeListAdapter = new RecipesListAdapter(this, output);
        listView.setAdapter(recipeListAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, DescriptionActivity.class);
        intent.putExtra("category", titleName);
        intent.putExtra("recipeId", position + 1);
        RecipesListActivity.this.startActivity(intent);
    }

    @Override
    public void onCallbackCompleted(String data) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Recipe>>() {
        }.getType();
        output = gson.fromJson(data, type);
        recipeListAdapter.clear();
        recipeListAdapter.addAll(output);
        recipeListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putSerializable(STATE_RECIPES, (Serializable) listView.onSaveInstanceState());
    }
}
