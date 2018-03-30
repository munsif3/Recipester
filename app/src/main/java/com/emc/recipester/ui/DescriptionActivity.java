package com.emc.recipester.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.emc.recipester.R;
import com.emc.recipester.core.Callback;
import com.emc.recipester.core.ImageManager;
import com.emc.recipester.core.RecipeService;
import com.emc.recipester.data.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class DescriptionActivity extends AppCompatActivity implements Callback {
    String category;
    TextView recipeTitle;
    TextView ingredients;
    TextView method;
    ImageView recipeImage;
    public ImageManager imageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        category = getIntent().getExtras().getString("category");
        int id = getIntent().getExtras().getInt("recipeId");

        getSupportActionBar().setTitle(category);
        RecipeService recipeService = new RecipeService(this);
        recipeService.requestRecipesById(category, id);
        imageManager = new ImageManager(getApplicationContext());

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
//        ViewHolder holder = new ViewHolder(recipeTitle, ingredients, method, recipeImage);
        imageManager.displayImage(recipe.getImage(), recipeImage);

//        holder.imageURL = recipe.getImage();
//        new DownloadAsyncTask().execute(holder);
    }

//    static class ViewHolder {
//        TextView recipe;
//        TextView ingredients;
//        TextView method;
//        ImageView backgroundImage;
//
//        ViewHolder(TextView tvRecipe, TextView tvIngredients, TextView tvMethod, ImageView ivImage) {
//            this.recipe = tvRecipe;
//            this.ingredients = tvIngredients;
//            this.method = tvMethod;
//            this.backgroundImage = ivImage;
//        }
//    }
//
//    class DownloadAsyncTask extends AsyncTask<ViewHolder, Void, ViewHolder> {
//
//        @Override
//        protected ViewHolder doInBackground(ViewHolder... params) {
//            ViewHolder viewHolder = params[0];
//            try {
//                URL imageURL = new URL(viewHolder.imageURL);
//                viewHolder.bitmap = BitmapFactory.decodeStream(imageURL.openStream());
//            } catch (IOException e) {
//                Log.e("error", "Downloading Image Failed");
//                viewHolder.bitmap = null;
//            }
//            return viewHolder;
//        }
//
//        @Override
//        protected void onPostExecute(ViewHolder result) {
//            if (result.bitmap == null) {
//                result.backgroundImage.setImageResource(android.R.drawable.gallery_thumb);
//            } else {
//                result.backgroundImage.setImageBitmap(result.bitmap);
//                result.backgroundImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            }
//        }
//    }
}
