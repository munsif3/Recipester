package com.emc.recipester.ui;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.emc.recipester.R;
import com.emc.recipester.data.RecipesListAdapter;

public class RecipesListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String[] recipes = {"Herb Omelette", "Love Toast", "Big Breakfast"};
    Integer[] recipeImages = {
            R.drawable.spinach_mushroom_omelet_recipe,
            R.drawable.love_toast,
            R.drawable.the_big_breakfast_hero

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);

        String passedText = getIntent().getExtras().getString("category");
        getSupportActionBar().setTitle(passedText);

        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.enableTransitionType(LayoutTransition.APPEARING);

        ListView listView = findViewById(R.id.recipeListView);
        listView.setOnItemClickListener(this);

        ListAdapter recipeListAdapter = new RecipesListAdapter(this,recipes,recipeImages);
        listView.setAdapter(recipeListAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, DescriptionActivity.class);
        intent.putExtra("recipe", position);
        RecipesListActivity.this.startActivity(intent);
    }
}
