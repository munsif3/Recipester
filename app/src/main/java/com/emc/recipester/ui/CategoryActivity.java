package com.emc.recipester.ui;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.emc.recipester.R;
import com.emc.recipester.data.CategoryListAdapter;

public class CategoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String[] categories = {"Beverage", "Breakfast", "Brunch", "Dessert", "Dinner", "Lunch", "Snack", "Soup"};
    Integer[] categoryImages = {
            R.drawable.beverage,
            R.drawable.breakfast,
            R.drawable.brunch,
            R.drawable.dessert,
            R.drawable.dinner,
            R.drawable.lunch,
            R.drawable.snack,
            R.drawable.soup
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.enableTransitionType(LayoutTransition.APPEARING);

        ListView listView = findViewById(R.id.lstCategories);
        listView.setOnItemClickListener(this);

        ListAdapter categoriesAdapter = new CategoryListAdapter(this, categories, categoryImages);
        listView.setAdapter(categoriesAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, RecipesListActivity.class);
        intent.putExtra("category", categories[position]);
        CategoryActivity.this.startActivity(intent);
    }
}
