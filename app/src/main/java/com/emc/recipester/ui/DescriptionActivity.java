package com.emc.recipester.ui;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.emc.recipester.R;
import com.emc.recipester.core.Callback;
import com.emc.recipester.core.RecipeService;
import com.emc.recipester.data.Recipe;
import com.emc.recipester.data.RecipesListAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.logging.Logger;

public class DescriptionActivity extends AppCompatActivity implements Callback {
    String category;
    TextView recipeTitle;
    TextView ingredients;
    TextView method;
    ImageView recipeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        category = getIntent().getExtras().getString("category");
        int id = getIntent().getExtras().getInt("recipeId");

        RecipeService recipeService = new RecipeService(this);
        recipeService.requestRecipesById(category, id);

        recipeTitle = findViewById(R.id.txtItemName);
        ingredients = findViewById(R.id.txtIngredients);
        method = findViewById(R.id.txtSteps);
        recipeImage = findViewById(R.id.imgRecipe);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onCallbackCompleted(String data) {
        getSupportActionBar().setTitle(category);
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
        ViewHolder holder;
        holder = new ViewHolder(recipeImage);
        holder.imageURL = recipe.getImage();
        new DownloadAsyncTask().execute(holder);
    }

    static class ViewHolder {
        ImageView backgroundImage;
        String imageURL;
        Bitmap bitmap;

        ViewHolder(ImageView view) {
            this.backgroundImage = view;
        }
    }

    class DownloadAsyncTask extends AsyncTask<ViewHolder, Void, ViewHolder> {

        @Override
        protected ViewHolder doInBackground(ViewHolder... params) {
            ViewHolder viewHolder = params[0];
            try {
                URL imageURL = new URL(viewHolder.imageURL);
                viewHolder.bitmap = BitmapFactory.decodeStream(imageURL.openStream());
            } catch (IOException e) {
                Log.e("error", "Downloading Image Failed");
                viewHolder.bitmap = null;
            }
            return viewHolder;
        }

        @Override
        protected void onPostExecute(ViewHolder result) {
            if (result.bitmap == null) {
                result.backgroundImage.setImageResource(android.R.drawable.gallery_thumb);
            } else {
                result.backgroundImage.setImageBitmap(result.bitmap);
                result.backgroundImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        }
    }
}
