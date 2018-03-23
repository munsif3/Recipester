package com.emc.recipester.ui;

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

    String[] categories = {"Beverage", "Breakfast", "Brunch", "Dessert", "Lunch", "Snack"};
    Integer[] categoryImages = {
            R.drawable.beverage,
            R.drawable.breakfast,
            R.drawable.brunch,
            R.drawable.dessert,
            R.drawable.lunch,
            R.drawable.snack
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ListView listView = findViewById(R.id.recipeCategoryList);
        listView.setOnItemClickListener(this);

        ListAdapter categoriesAdapter = new CategoryListAdapter(this, categories, categoryImages);
        listView.setAdapter(categoriesAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, DescriptionActivity.class);
        intent.putExtra("category", categories[position]);
        CategoryActivity.this.startActivity(intent);
    }
}
