package com.emc.recipester.ui;

import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.emc.recipester.R;
import com.emc.recipester.core.Callback;
import com.emc.recipester.core.RecipeService;
import com.emc.recipester.data.Recipe;
import com.emc.recipester.data.RecipeDataFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;

public class DescriptionActivity extends AppCompatActivity implements Callback {
    String category;
    TextView recipeTitle;
    TextView ingredients;
    TextView method;
    ImageView recipeImage;
    static final String STATE_TITLE = "recipeTitle.state";
    static final String STATE_INGREDIENTS = "ingredients.state";
    static final String STATE_METHOD = "method.state";
    static final String STATE_IMAGE = "recipeImage.state";
    private static final String TAG_RETAINED_FRAGMENT = "RecipeDataFragment";

    private RecipeDataFragment mRetainedFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);


        FragmentManager fm = getFragmentManager();
        mRetainedFragment = (RecipeDataFragment) fm.findFragmentByTag(TAG_RETAINED_FRAGMENT);


        if (mRetainedFragment == null) {
            // add the fragment
            mRetainedFragment = new RecipeDataFragment();
            fm.beginTransaction().add(mRetainedFragment, TAG_RETAINED_FRAGMENT).commit();
            // load data from a data source or perform any calculation
//            mRetainedFragment.setData(loadMyData());
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        category = getIntent().getExtras().getString("category");
        int id = getIntent().getExtras().getInt("recipeId");


//        if (savedInstanceState != null) {
//            recipeTitle = (TextView) savedInstanceState.getSerializable(STATE_TITLE);
//            ingredients = (TextView) savedInstanceState.getSerializable(STATE_INGREDIENTS);
//            method = (TextView) savedInstanceState.getSerializable(STATE_METHOD);
////            recipeImage = (ImageView) savedInstanceState.getSerializable(STATE_IMAGE);
//        }

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

//    @Override
//    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//
//        outState.putSerializable(STATE_TITLE, (Serializable) recipeTitle.onSaveInstanceState());
//        outState.putSerializable(STATE_INGREDIENTS, (Serializable) ingredients.onSaveInstanceState());
//        outState.putSerializable(STATE_METHOD, (Serializable) method.onSaveInstanceState());
////        outState.putSerializable(STATE_IMAGE, (Serializable) recipeImage.onSaveInstanceState());
//    }

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
